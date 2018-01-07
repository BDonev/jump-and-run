package bg.tusofia.fdiba.gamedev.entity;

import org.newdawn.slick.opengl.Texture;

import bg.tusofia.fdiba.gamedev.Game;
import bg.tusofia.fdiba.gamedev.Sprite;
import bg.tusofia.fdiba.gamedev.util.Constants;
import bg.tusofia.fdiba.gamedev.util.Util;

/**
 * Represents a mine entity. When collected it reduces the lives a player has by one.
 * @author Borislav Donev
 *
 */
public class MineEntity extends ObjectEntity {
	private static Texture mineTexture = Util.loadPicture(Constants.MINE_PIC, Constants.PNG_PIC_FORMAT);
	private static Sprite mineSprite = new Sprite(mineTexture);
	private Game game = Game.getInstance();

	public MineEntity(int x, int y) {
		super(mineSprite, x, y);
	}
	
	@Override
	public void collected() {
		this.setVisible(false);
		game.reduceLives();
	}

}
