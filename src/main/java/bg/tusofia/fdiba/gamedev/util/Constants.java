package bg.tusofia.fdiba.gamedev.util;

/**
 * Utility class with constants
 * @author Borislav Donev
 *
 */
public final class Constants {

	private Constants() {
		throw new AssertionError("This class should not be instanced");
	}
	
	/** Game title */
	public static final String GAME_TITLE = "Jump and run";
	
	/** Screen size */
	public static final int SCREEN_SIZE_WIDTH = 1280;
	public static final int SCREEN_SIZE_HEIGHT = 720;

	/** Desired frame time */
	public static final int FRAMERATE = 60;
	
	public static final int STARTING_NUMBER_OF_LIVES = 3;

	/** Picture name constants */
	public static final String PNG_PIC_FORMAT = "PNG";
	public static final String HERO_PIC = "santa";
	public static final String CHEST_PIC = "chest";
	public static final String MINE_PIC = "mine";
	public static final String HEART_PIC = "heart";
	public static final String GIFT_PIC = "gift";
	public static final String BACKGROUND_PIC = "background";
	public static final String POPUP_WINDOW_PIC = "popup";

	/** Labels */
	public static final String SCORE_INFO = "Score: ";
	public static final String USER_INPUT_EXIT = "Press ESC to exit";
	
	/** Starting player position */
	public static final int PLAYER_POS_X = 360;
	public static final int PLAYER_POS_Y = 585;
	
	/** Score popup screen position */
	public static final int POPUP_POS_X = SCREEN_SIZE_WIDTH / 3;
	public static final int POPUP_POS_Y = SCREEN_SIZE_HEIGHT / 3;
	
	public static final int OBJECT_MAX_POS_X = SCREEN_SIZE_WIDTH - 60;
	public static final int OBJECT_MAX_POS_Y = 3*SCREEN_SIZE_HEIGHT;
	public static final int MAX_OBJECT_COUNT = 5;
	public static final int SCORE_COEFFICIENT = 10;
	public static final int NUM_GIFTS_COLLECTED_TO_PROGRESS = 10;
	
}
