package bg.tusofia.fdiba.gamedev.entity;

import org.newdawn.slick.opengl.Texture;

import bg.tusofia.fdiba.gamedev.Game;
import bg.tusofia.fdiba.gamedev.Sprite;
import bg.tusofia.fdiba.gamedev.util.Constants;
import bg.tusofia.fdiba.gamedev.util.Util;

/**
 * This class represents a gift entity. When collected the score is increased.
 * When it falls to the ground the score is decreased.
 * @author Borislav Donev
 */
public class GiftEntity extends ObjectEntity {

	private static Texture giftTexture = Util.loadPicture(Constants.GIFT_PIC, Constants.PNG_PIC_FORMAT);
	private static Sprite giftSprite = new Sprite(giftTexture);
	private Game game = Game.getInstance();
	
	public GiftEntity(int x, int y) {
		super(giftSprite, x, y);
	}
	
	@Override
	public void collected() {
		this.setVisible(false);
		game.increaseScore();
	}

	public void fallen() {
		game.decreaseScore();
	}

}
