package com.followanalytics.battleship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

import com.followanalytics.battleship.entities.grid.Grid;
import com.followanalytics.battleship.entities.grid.ShipLocation;
import com.followanalytics.battleship.entities.ships.Carrier;
import com.followanalytics.battleship.entities.ships.Ship;
import com.followanalytics.battleship.exceptions.NoSquareFoundException;
import com.followanalytics.battleship.services.GridService;
import com.followanalytics.battleship.services.GridServiceImpl;

class ShipBombardmentTest {

	/**
	 * Creates a grid and place a ship.
	 * Bombards the ship and tests if the ship is hit
	 */
	@Test
	void shipHitTest() {
		Grid grid = new Grid(10, 10);
		GridService gridService = new GridServiceImpl(grid);
		Ship ship = new Carrier();
		
		//Place the ship horizontally
		grid.insertShipInGrid(ship, new ShipLocation(1, 1, true));
		
		try {
			//Bombard the ship
			int hitX = 2;
			int hitY = 1;
			
			//Check if the method return the right hit status
			assertTrue(gridService.bombardSquare(hitX, hitY));
			
			//Check if the square is really hit
			assertTrue(grid.getSquares()[hitY][hitX].isHit());
			
			//Check if the ship is hit at this position
			assertTrue(ship.isHit(hitX, hitY));
			
			//Hit a square without ship
			hitX = 4;
			hitY = 4;
			
			//Check if the method return the right hit status
			assertFalse(gridService.bombardSquare(hitX, hitY));
			
			//Check if the square is really hit
			assertTrue(grid.getSquares()[hitY][hitX].isHit());
			
			//Check if the ship is hit at this position
			assertFalse(ship.isHit(hitX, hitY));
			
		}
		catch (NoSquareFoundException e) {
			fail(e.getMessage());
		}
	}

}
