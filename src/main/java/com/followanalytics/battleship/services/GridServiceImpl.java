package com.followanalytics.battleship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.followanalytics.battleship.entities.grid.Grid;
import com.followanalytics.battleship.entities.grid.ShipLocation;
import com.followanalytics.battleship.entities.ships.Ship;
import com.followanalytics.battleship.exceptions.NoShipLocationException;
import com.followanalytics.battleship.exceptions.NoSquareFoundException;

public class GridServiceImpl implements GridService {

	private Grid grid;
	
	public GridServiceImpl(Grid grid) {
		super();
		this.grid = grid;
	}

	public Grid getGrid() {
		return grid;
	}

	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Randomly places ships in the grid 
	 * @param shipsToPlace The ships to place
	 */
	@Override
	public void placeShips(List<Ship> shipsToPlace) throws NoShipLocationException {
		//First, order ships by size descending, because it's easier to place a small ship once other ships are placed
		shipsToPlace.sort((s1, s2) -> s2.getSize() - s1.getSize());
		
		for (Ship ship : shipsToPlace) {
			placeShip(ship);
		}
	}
	
	/**
	 * Randomly places a ship in the grid 
	 * @param ship The ship to place
	 */
	private void placeShip(Ship ship) throws NoShipLocationException {
		//Determine all possible ship locations
		List<ShipLocation> availableLocations = getAvailableShipLocations(ship.getSize());
		
		if(availableLocations.size() > 0) {
			//Get a random location in the list and place the ship in the grid
			ShipLocation shipLocation = availableLocations.get(new Random().nextInt(availableLocations.size()));
			grid.insertShipInGrid(ship, shipLocation);
		}
		else {
			throw new NoShipLocationException("Can't find available location for ship " + ship.getShipType());
		}
	}
	
	/**
	 * Gets all available ship locations for horizontal and vertical orientation
	 * @param shipSize The size of the ship to place
	 * @return The list of available locations for this ship
	 */
	public List<ShipLocation> getAvailableShipLocations(int shipSize) {
		List<ShipLocation> availableLocations = new ArrayList<ShipLocation>();
		
		//get all possible locations for horizontal orientation, we excludes squares regarding the orientation of the ship and his size, in order to avoid ship placement off the grid
		getAvailableShipLocations(shipSize, grid.getWidth() - shipSize + 1, grid.getHeight(), true, availableLocations);
		
		//get all possible locations for vertical orientation, we excludes squares regarding the orientation of the ship and his size, in order to avoid ship placement off the grid
		getAvailableShipLocations(shipSize, grid.getWidth(), grid.getHeight() - shipSize + 1, false, availableLocations);
		
		return availableLocations;
	}
	
	/**
	 * Gets all available ship locations for a given ship orientation
	 * @param shipSize The size of the ship
	 * @param xBound The max x coordinate
	 * @param yBound The max y coordinate
	 * @param horizontal The ship orientation
	 * @param availableLocations The list of available locations for this ship
	 */
	private void getAvailableShipLocations(int shipSize, int xBound, int yBound, boolean horizontal, List<ShipLocation> availableLocations) {
		for (int x = 0; x < xBound; x++) {
			for (int y = 0; y < yBound; y++) {
				//if the ship don't override another ship, the location is valid
				if(checkShipCollision(x, y, shipSize, horizontal))
					availableLocations.add(new ShipLocation(x, y, horizontal));
			}
		}
	}
	
	/**
	 * Checks if the ship is not overriding another ship
	 * @param x The x coordinate of the first grid case
	 * @param y The y coordinate of the first grid case
	 * @param shipSize The size of the ship
	 * @param horizontal The ship orientation
	 * @return true if the ship is not overriding another ship, false otherwise
	 */
	public boolean checkShipCollision(int x, int y, int shipSize, boolean horizontal) {
		boolean canBePlaced = true;
		
		//check for all squares occupied by the ship if there is another ship
		for (int squareIndex = 0; squareIndex < shipSize; squareIndex++) {
			int currentX = (horizontal) ? x + squareIndex : x;
			int currentY = (!horizontal) ? y + squareIndex : y;
			
			if(grid.isThereAShip(currentX, currentY)) return false;
		}
		
		return canBePlaced;
	}

	/**
	 * Bombards a given grid square
	 * @param x
	 * @param y
	 * @return true if a ship is hit, false otherwise
	 * @throws NoSquareFoundException
	 */
	@Override
	public boolean bombardSquare(int x, int y) throws NoSquareFoundException {
		return grid.bombardSquare(x, y);
	}

	/**
	 * Checks if all ships in the grid are destroyed
	 * @return true if all ships are destroyed, false otherwise
	 */
	@Override
	public boolean areShipsDestroyed() {
		return grid.areShipsDestroyed();
	}
	
}
