package bg.tusofia.fdiba.gamedev;

import static bg.tusofia.fdiba.gamedev.util.Constants.*;
import static bg.tusofia.fdiba.gamedev.util.Util.*;

import java.awt.Font;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.opengl.Texture;

import bg.tusofia.fdiba.gamedev.entity.Entity;
import bg.tusofia.fdiba.gamedev.entity.GiftEntity;
import bg.tusofia.fdiba.gamedev.entity.HeroEntity;
import bg.tusofia.fdiba.gamedev.entity.FallingObjectEntity;

/**
 * The main game class
 *  
 * @author Borislav Donev
 *
 */
public class Game {

	/** The only instance of the class */
	private static Game instance;
	
	/** Exit the game */
	private boolean finished;
	
	/** The main character entity */
	private Entity hero;
	
	/** A list containing the currently falling objects */
	private ArrayList<FallingObjectEntity> fallingObjects = new ArrayList<>();
	
	/** A list containing the objects that will be removed in the current iteration */
	private ArrayList<FallingObjectEntity> objectsToRemove = new ArrayList<>();
	
	/** The number of lives left */
	private int numlives = STARTING_NUMBER_OF_LIVES;
	
	/** The sprite representing the lives of a player */
	private Sprite heartSprite;
	
	/** The background sprite */
	private Sprite background;
	
	/** A pop-up window shown at the end of the game */
	private Sprite popupWindow;

	/** The player score */
	private int score = 0;

	/**
	 * The level the player is at. It determines how fast the objects are moving and
	 * how the score is calculated.
	 */
	private int level = 1;

	/** Set to false when no lives are left */
	private boolean heroAlive = true;

	/** The number of gifts collected */
	private int giftsCollected = 0;

	private String scoreString;

	/** The font that is used to represent the score */
	private final Font awtScoreFont = new Font("Times New Roman", Font.BOLD, 48);
	private TrueTypeFont scoreFont;
	
	/** The font that is used to tell the user what is expected from him */
	private final Font awtInputFont = new Font("Times New Roman", Font.PLAIN, 18);
	private TrueTypeFont inputFont;

	/** The position where the final score will be displayed */
	private int finalScorePosX;
	private int finalScorePosY;

	/** The position where user input will be prompted */
	private int inputPromptPosX;
	private int inputPromptPosY;

	/**
	 * A private constructor that can be called only by the getInstance method.
	 */
	private Game() {
	}
	
	/**
	 * Returns the instance of the Game class or initiates it if it does not yet exist.
	 */
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}

	/**
	 * Starts and exits the game 
	 */
	public void start() {
		try {
			init();
			run();
		} catch (Exception e) {
			e.printStackTrace(System.err);
			Sys.alert(GAME_TITLE, "An error occured and the game will exit.");
		} finally {
			cleanup();
		}
		System.exit(0);
	}

	/**
	 * Initialise the game
	 * 
	 * @throws Exception
	 *             if init fails
	 */
	private void init() throws Exception {
		// Create a fullscreen window with 1:1 orthographic 2D projection, and
		// with
		// mouse, keyboard, and gamepad inputs.
		initGL(SCREEN_SIZE_WIDTH, SCREEN_SIZE_HEIGHT);
		initObjects();
		
	}

	/**
	 * Initiates the GL context and the display
	 * @param width the screen width
	 * @param height the screen height
	 */
	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle(GAME_TITLE);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	/**
	 * Initiates the objects in the game
	 */
	private void initObjects() {
		background = new Background();
		
		hero = new HeroEntity(PLAYER_POS_X, PLAYER_POS_Y);
		
		Texture heartTexture = loadPicture(HEART_PIC, PNG_PIC_FORMAT);
		heartSprite = new Sprite(heartTexture);
//		for (int i = 0; i < STARTING_NUMBER_OF_LIVES; i++) {
//			lives[i] = new Sprite(heartTexture);
//		}
		
		Texture popupWindowTexture = loadPicture(POPUP_WINDOW_PIC, PNG_PIC_FORMAT);
		popupWindow = new Sprite(popupWindowTexture);
		
		scoreFont = new TrueTypeFont(awtScoreFont, false);
		inputFont = new TrueTypeFont(awtInputFont, false);
		
		// The position of the score should be on the center of the popup window
		finalScorePosX = POPUP_POS_X + 50;
		finalScorePosY = POPUP_POS_Y + (popupWindow.getHeight() - scoreFont.getHeight()) / 2;

		// The position of the user input prompt should be on the bottom center of the
		// popup window
		inputPromptPosX = POPUP_POS_X + (popupWindow.getWidth() - inputFont.getWidth(USER_INPUT_EXIT)) / 2;
		inputPromptPosY = POPUP_POS_Y + popupWindow.getHeight() - 2 * inputFont.getHeight();
		
	}

	/**
	 * Runs the game (the "main loop")
	 */
	private void run() {
		while (!finished) {
			// Always call Window.update(), all the time

				Display.update();

				if (Display.isCloseRequested()) {
					// Check for O/S close requests
					finished = true;
				} else if (Display.isActive()) {
					// The window is in the foreground, so we should play the game
					Display.sync(FRAMERATE);
					render();
					logic();
				} else {
					// The window is not in the foreground, so we can allow other
					// stuff to run and
					// infrequently update
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
					}
					logic();
					if (Display.isVisible() || Display.isDirty()) {
						// Only bother rendering if the window is visible or dirty
						render();
					}
			}
		}
	}

	/**
	 * Do all calculations, handle input, etc.
	 */
	private void logic() {
		if (!heroAlive) {
			endGame();
		} else {
			handleInput();
			updateObjects();
		}
	}
	
	private void handleInput() {
		// The game ends instantly when ESC is pressed
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (hero.getX() + hero.getWidth() < SCREEN_SIZE_WIDTH) {
				hero.setX(hero.getX() + 10 + 2 * level);
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (hero.getX() > 0) {
				hero.setX(hero.getX() - 10 - 2 * level);
			}
		}
	}
	
	/**
	 * Updates the status of the currently falling objects. Checks for collision with
	 * the player.
	 */
	private void updateObjects() {
		for (FallingObjectEntity fallingObject : fallingObjects) {
			fallingObject.setY(fallingObject.getY() + fallingObject.getFallingSpeed());

			// If an objects falls to the ground
			if (fallingObject.getY() + fallingObject.getHeight() >= PLAYER_POS_Y + hero.getHeight()) {
				objectsToRemove.add(fallingObject);

				// If a gift is missed, reduce score
				if (fallingObject instanceof GiftEntity) {
					decreaseScore();
				}
			}
			if (fallingObject.collidesWith(hero)) {
				fallingObject.collected();
				objectsToRemove.add(fallingObject);
			}
		}
		fallingObjects.removeAll(objectsToRemove);
		objectsToRemove.clear();
		generateNewObjects();
	}

	/**
	 * Generates new objects when the number of objects is less than the maximum
	 * object count.
	 */
	private void generateNewObjects() {
		int numberOfNewObjects =  MAX_OBJECT_COUNT - fallingObjects.size();
		for (int i = 0; i < numberOfNewObjects; i++) {
			FallingObjectEntity newObject = loadNextObject();
			fallingObjects.add(newObject);
		}
	}

	/**
	 * Render the current frame
	 */
	private void render() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
		Color.white.bind();

		background.draw(0, 0);
		
		if (heroAlive) {
			hero.draw();

			for (FallingObjectEntity fallingObject : fallingObjects) {
				if (fallingObject != null && fallingObject.isVisible()) {
					fallingObject.draw();
				}
			}

			for (int i = 0; i < numlives; i++) {
				heartSprite.draw(SCREEN_SIZE_WIDTH - (1+i)*heartSprite.getWidth(), 0);
			}
			
			scoreString = SCORE_INFO + String.valueOf(score);
			scoreFont.drawString(0, 0, scoreString, Color.white);
		}

	}
	
	/**
	 * Displays a popup window at the end of the game showing the score and waiting for the
	 * player to press the 'Escape' key
	 */
	private void endGame() {
		popupWindow.draw(POPUP_POS_X, POPUP_POS_Y);
		
		scoreFont.drawString(finalScorePosX, finalScorePosY, scoreString, Color.black);
		inputFont.drawString(inputPromptPosX, inputPromptPosY, USER_INPUT_EXIT, Color.black);
		
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			finished = true;
		}
	}
	
	
	/**
	 * Do any game-specific cleanup
	 */
	private void cleanup() {
		// Close the window
		Display.destroy();
	}
	
	/**
	 * Increases the score after a gift is collected
	 */
	public void increaseScore() {
		this.score += SCORE_COEFFICIENT*level;
		this.giftsCollected++;
		if (giftsCollected >= NUM_GIFTS_COLLECTED_TO_PROGRESS*level) {
			level++;
		}
	}
	
	/**
	 * Decreases the score after a gift has fallen to the ground
	 */
	public void decreaseScore() {
		this.score -= SCORE_COEFFICIENT;
		if (this.score < 0) {
			this.score = 0;
		}
	}

	/**
	 * Reduces the number of lives after a mine has collided with the character
	 */
	public void reduceLives() {
		numlives--;
		if (numlives == 0) {
			heroAlive = false;
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}


	
}
