package bg.tusofia.fdiba.gamedev.entity;

import bg.tusofia.fdiba.gamedev.Sprite;

/**
 * An abstract class representing a single entity in the game
 */
public abstract class Entity {
	protected int x;
	protected int y;
	protected Sprite sprite;
	private boolean visible;
	
	public Entity(Sprite sprite, int x, int y) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.visible = true;
	}
	
	public void draw() {
		sprite.draw(x, y);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return sprite.getWidth();
	}

	public int getHeight() {
		return sprite.getHeight();
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void setSprite (Sprite sprite) {
		this.sprite = sprite;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
}
