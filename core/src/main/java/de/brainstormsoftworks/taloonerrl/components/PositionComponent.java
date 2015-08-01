package de.brainstormsoftworks.taloonerrl.components;

import com.artemis.PooledComponent;

/**
 * component to store the position of an entity
 *
 * @author David Becker
 *
 */
public class PositionComponent extends PooledComponent {

	private int x = 0;
	private int y = 0;

	@Override
	protected void reset() {
		x = 0;
		y = 0;
	}

	/**
	 * getter for x
	 *
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * setter for x
	 *
	 * @param x
	 *            the x to set
	 */
	public void setX(final int x) {
		this.x = x;
	}

	/**
	 * getter for y
	 *
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * setter for y
	 *
	 * @param y
	 *            the y to set
	 */
	public void setY(final int y) {
		this.y = y;
	}

}
