package com.followanalytics.battleship.entities.grid;

import com.followanalytics.battleship.entities.ships.Ship;

public class GridSquare {

	private int x;
	private int y;
	private boolean hit;
	
	private Ship ship;

	public GridSquare(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.hit = false;
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

	public boolean isHit() {
		return hit;
	}

	public void setHit(boolean hit) {
		this.hit = hit;
	}

	public Ship getShip() {
		return ship;
	}

	public void setShip(Ship ship) {
		this.ship = ship;
		this.ship.addSquare(this);
	}
	
}
