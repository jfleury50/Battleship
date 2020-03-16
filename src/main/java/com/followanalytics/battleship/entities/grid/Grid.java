package com.followanalytics.battleship.entities.grid;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import com.followanalytics.battleship.entities.ships.Ship;
import com.followanalytics.battleship.exceptions.NoSquareFoundException;

public class Grid {

	private int width;
	
	private int height;
	
	private List<Ship> ships;
	
	private GridSquare[][] squares;

	public Grid(int width, int height) {
		super();
		this.width = width;
		this.height = height;
		this.ships = new ArrayList<Ship>();
		this.initSquares();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public List<Ship> getShips() {
		return ships;
	}

	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}
	
	public GridSquare[][] getSquares() {
		return squares;
	}

	public void setSquares(GridSquare[][] squares) {
		this.squares = squares;
	}
	
	/**
	 * Initializes grid squares composing the grid
	 */
	public void initSquares() {
		this.squares = new GridSquare[this.height][this.width];
		
		IntStream.range(0, this.height).forEach(
				x -> IntStream.range(0, this.width).forEach(
						y -> this.squares[y][x] = new GridSquare(x, y)
				)
		);
	}

	
	/**
	 * Inserts the ship in all grid squares he's occupying
	 * @param ship The ship to place
	 * @param shipLocation The ship location randomly selected
	 */
	public void insertShipInGrid(Ship ship, ShipLocation shipLocation) {
		ship.setHorizontal(shipLocation.isHorizontal());
		
		this.ships.add(ship);
		
		//Set the ship in grid squares and allocates grid squares to the ship
		IntStream.range(0, ship.getSize()).forEach(
				size -> {
					int x = (ship.getHorizontal()) ? shipLocation.getX() + size : shipLocation.getX();
					int y = (!ship.getHorizontal()) ? shipLocation.getY() + size : shipLocation.getY();
					
					this.squares[y][x].setShip(ship);
				}
		);
	}
	
	/**
	 * Checks if there is a ship in the given coordinates
	 * @param x
	 * @param y
	 * @return true if a ship is in the given coordinates, false otherwise
	 */
	public boolean isThereAShip(int x, int y) {
		return this.squares[y][x].getShip() != null;
	}
	
	/**
	 * Checks if all ships in the grid are destroyed
	 * @return true if all ships are destroyed, false otherwise
	 */
	public boolean areShipsDestroyed() {
		boolean areShipsDestroyed = true;
		
		for (Ship ship : this.ships) {
			if(!ship.isDestroyed())
				return false;
		}
		
		return areShipsDestroyed;
	}
	
	/**
	 * Bombards a grid square
	 * @param x
	 * @param y
	 * @return true if a ship is hit, false otherwise
	 * @throws NoSquareFoundException if there is no square at these coordinates
	 */
	public boolean bombardSquare(int x, int y) throws NoSquareFoundException {
		GridSquare square = this.squares[y][x];
		
		if(square == null) {
			throw new NoSquareFoundException("No square fount at (" + x + ", " + y + ")");
		}
		
		square.setHit(true);
		
		return square.getShip() != null;
	}
	
}
