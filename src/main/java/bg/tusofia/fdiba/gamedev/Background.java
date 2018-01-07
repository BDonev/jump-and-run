package bg.tusofia.fdiba.gamedev;

import static bg.tusofia.fdiba.gamedev.util.Constants.BACKGROUND_PIC;
import static bg.tusofia.fdiba.gamedev.util.Constants.PNG_PIC_FORMAT;
import static bg.tusofia.fdiba.gamedev.util.Util.loadPicture;

import org.newdawn.slick.opengl.Texture;

/**
 * A background sprite
 * @author Borislav Donev
 *
 */
public class Background extends Sprite {

	private static Texture texture = loadPicture(BACKGROUND_PIC, PNG_PIC_FORMAT);
	
	public Background() {
		super(texture);
	}

}
