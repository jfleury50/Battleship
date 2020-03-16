package com.followanalytics.battleship.entities.grid;

/**
 * A ShipLocation is composed by the coordinates of the first square of the ship and its orientation
 *
 */
public class ShipLocation {

	private int x;
	private int y;
	private boolean horizontal;

	public ShipLocation(int x, int y, boolean horizontal) {
		super();
		this.x = x;
		this.y = y;
		this.horizontal = horizontal;
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
	
	public boolean isHorizontal() {
		return horizontal;
	}

	public void setHorizontal(boolean horizontal) {
		this.horizontal = horizontal;
	}
	
}
