package bg.tusofia.fdiba.gamedev.entity;

import java.awt.Rectangle;

import bg.tusofia.fdiba.gamedev.Game;
import bg.tusofia.fdiba.gamedev.Sprite;

/**
 * Class representing a falling object
 * @author Borislav Donev
 *
 */
public abstract class ObjectEntity extends Entity {

	private Rectangle me = new Rectangle();
	private Rectangle him = new Rectangle();
	
	/** The default speed at which the object is falling */
	private int fallingSpeed;
	
	private Game game = Game.getInstance();

	public ObjectEntity(Sprite sprite, int x, int y) {
		super(sprite, x, y);
		this.fallingSpeed = 5 + 2*game.getLevel();
	}

	/**
	 * Checks if an object has collided with another entity
	 * 
	 * @param other
	 *            the other entity
	 * @return <b>true</b> if collision is detected, <b>false</b> otherwise
	 */
	public boolean collidesWith(Entity other) {
		me.setBounds((int) x, (int) y, sprite.getWidth(), sprite.getHeight());
		him.setBounds((int) other.x, (int) other.y, other.sprite.getWidth(),
				other.sprite.getHeight());

		return me.intersects(him);
	}

	public int getFallingSpeed() {
		return this.fallingSpeed;
	}
	
	/**
	 * This method is called when the object is collected by the player
	 */
	public abstract void collected();
}
