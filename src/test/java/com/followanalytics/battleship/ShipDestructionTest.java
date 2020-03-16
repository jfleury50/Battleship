package com.followanalytics.battleship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import com.followanalytics.battleship.entities.grid.Grid;
import com.followanalytics.battleship.entities.grid.ShipLocation;
import com.followanalytics.battleship.entities.ships.Destroyer;
import com.followanalytics.battleship.entities.ships.Ship;
import com.followanalytics.battleship.entities.ships.Submarine;
import com.followanalytics.battleship.exceptions.NoSquareFoundException;
import com.followanalytics.battleship.services.GridService;
import com.followanalytics.battleship.services.GridServiceImpl;

class ShipDestructionTest {

	/**
	 * Creates a grid and place 2 ships
	 * Checks ship destruction if
	 * 		- no ship is bombarded
	 * 		- only one of the ships is destroyed
	 * 		- all ships are destroyed
	 */
	@Test
	void shipDestructionTest() {
		Grid grid = new Grid(10, 10);
		GridService gridService = new GridServiceImpl(grid);
		Ship firstShip = new Destroyer();
		Ship secondShip = new Submarine();
		
		//Place the first ship horizontally at (1, 1)
		grid.insertShipInGrid(firstShip, new ShipLocation(1, 1, true));
		//Place the second ship vertically at (1, 2)
		grid.insertShipInGrid(secondShip, new ShipLocation(1, 2, false));
		
		//Check if all ships are destroyed, false expected
		assertFalse(gridService.areShipsDestroyed());
		
		try {
			//Destroy the first ship
			assertTrue(gridService.bombardSquare(1, 1));
			assertTrue(gridService.bombardSquare(2, 1));
			
			//Check if the first ship is destroyed 
			assertTrue(firstShip.isDestroyed());
			//Check if the second ship is not destroyed
			assertFalse(secondShip.isDestroyed());
			//Check if all ships are destroyed, false expected
			assertFalse(gridService.areShipsDestroyed());
			
			//Destroy the second ship
			assertTrue(gridService.bombardSquare(1, 2));
			assertTrue(gridService.bombardSquare(1, 3));
			assertTrue(gridService.bombardSquare(1, 4));
			
			//Check if the first ship is destroyed 
			assertTrue(firstShip.isDestroyed());
			//Check if the second ship is destroyed
			assertTrue(secondShip.isDestroyed());
			//Check if all ships are destroyed, true expected
			assertTrue(gridService.areShipsDestroyed());
		}
		catch (NoSquareFoundException e) {
			fail(e.getMessage());
		}
	}

}
