package bg.tusofia.fdiba.gamedev.entity;

import org.newdawn.slick.opengl.Texture;

import bg.tusofia.fdiba.gamedev.Sprite;
import bg.tusofia.fdiba.gamedev.util.Constants;
import bg.tusofia.fdiba.gamedev.util.Util;

/**
 * Represents the player character.
 * @author Borislav Donev
 *
 */
public class HeroEntity extends Entity {
	private static Texture heroTexture = Util.loadPicture(Constants.HERO_PIC, Constants.PNG_PIC_FORMAT);
	private static Sprite heroSprite = new Sprite(heroTexture);

	public HeroEntity(int x, int y) {
		super(heroSprite, x, y);
	}
	
}
