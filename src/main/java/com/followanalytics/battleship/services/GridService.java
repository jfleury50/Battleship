package com.followanalytics.battleship.services;

import java.util.List;

import com.followanalytics.battleship.entities.ships.Ship;
import com.followanalytics.battleship.exceptions.NoShipLocationException;
import com.followanalytics.battleship.exceptions.NoSquareFoundException;

public interface GridService {

	/**
	 * Randomly places ships in the grid 
	 * @param shipsToPlace
	 */
	public void placeShips(List<Ship> shipsToPlace) throws NoShipLocationException;
	
	/**
	 * Bombards a given grid square
	 * @param x
	 * @param y
	 * @return true if a ship is hit, false otherwise
	 * @throws NoSquareFoundException
	 */
	public boolean bombardSquare(int x, int y) throws NoSquareFoundException;
	
	/**
	 * Checks if all ships in the grid are destroyed
	 * @return true if all ships are destroyed, false otherwise
	 */
	public boolean areShipsDestroyed();
	
}
