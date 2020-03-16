package com.followanalytics.battleship.entities.ships;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.followanalytics.battleship.entities.grid.GridSquare;

public abstract class Ship {

	//The type of the ship
	protected ShipType shipType;
	
	//The number of the square in the grid
	protected int size;
	
	//The orientation of the ship on the grid, either horizontal (true) or else vertical
	protected Boolean horizontal;
	
	//The grid squares where the ship is placed
	protected List<GridSquare> squares;

	public Ship(ShipType shipType, int size) {
		super();
		this.shipType = shipType;
		this.size = size;
		this.squares = new ArrayList<GridSquare>();
	}

	public ShipType getShipType() {
		return shipType;
	}

	public void setShipType(ShipType shipType) {
		this.shipType = shipType;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Boolean getHorizontal() {
		return horizontal;
	}

	public void setHorizontal(Boolean horizontal) {
		this.horizontal = horizontal;
	}

	public List<GridSquare> getSquares() {
		return squares;
	}

	public void setSquares(List<GridSquare> squares) {
		this.squares = squares;
	}
	
	public void addSquare(GridSquare square) {
		this.squares.add(square);
	}
	
	/**
	 * Checks if the ship is hit in a given grid square
	 * @param x
	 * @param y
	 * @return true if the ship is hit, false otherwise
	 */
	public boolean isHit(int x, int y) {
		Optional<GridSquare> squareOpt = squares.stream()
			.filter(square -> square.getX() == x && square.getY() == y)
			.findFirst();
		
		return squareOpt.isPresent() && squareOpt.get().isHit();
	}
	
	/**
	 * Checks if the ship is destroyed
	 * @return true if all ship squares are hit, false otherwise
	 */
	public boolean isDestroyed() {
		boolean isDestroyed = true;
		
		for (GridSquare square : this.squares) {
			isDestroyed = isDestroyed && square.isHit();
		}
		
		return isDestroyed;
	}
	
}
