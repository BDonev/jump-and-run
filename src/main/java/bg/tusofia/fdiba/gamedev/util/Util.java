package bg.tusofia.fdiba.gamedev.util;

import static bg.tusofia.fdiba.gamedev.util.Constants.SCREEN_SIZE_WIDTH;

import java.io.IOException;
import java.util.Random;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import bg.tusofia.fdiba.gamedev.entity.GiftEntity;
import bg.tusofia.fdiba.gamedev.entity.MineEntity;
import bg.tusofia.fdiba.gamedev.entity.ObjectEntity;

/**
 * Utility class with helper methods
 * @author Borislav Donev
 *
 */
public class Util {
	
	
	/** Used to randomize falling objects */
	private final static Random random = new Random();

	/**
	 * Loads a picture from resource
	 * 
	 * @param pictureName
	 *            the name of the file
	 * @param format
	 *            the file format
	 * @return a texture from this resource
	 */
	public static Texture loadPicture(String pictureName, String format) {
		Texture texture = null;
		try {
			texture = TextureLoader.getTexture(format,
					ResourceLoader.getResourceAsStream(pictureName + "." + format.toLowerCase()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return texture;
	}
	
	/**
	 * Randomly loads a new object (either a mine or a gift) at a given position
	 * 
	 * @param randomPosition
	 *            the position for the object to be loaded
	 * @return the loaded object
	 */
	public static ObjectEntity loadNextObject() {
		// Get random position within the screen boundaries where a sprite would be
		// completely visible
		int randomPosition = random.nextInt(SCREEN_SIZE_WIDTH - 60);
		
		// Is the object a mine or a gift
		boolean isMine = random.nextBoolean();
		ObjectEntity fallingObject = null;
		if (isMine) {
			fallingObject = new MineEntity(randomPosition, 0);
		} else {
			fallingObject = new GiftEntity(randomPosition, 0);
		}
		return fallingObject;
	}
	
}
