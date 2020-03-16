package com.followanalytics.battleship;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.followanalytics.battleship.entities.grid.Grid;
import com.followanalytics.battleship.entities.grid.ShipLocation;
import com.followanalytics.battleship.entities.ships.Battleship;
import com.followanalytics.battleship.entities.ships.Carrier;
import com.followanalytics.battleship.entities.ships.Cruiser;
import com.followanalytics.battleship.entities.ships.Destroyer;
import com.followanalytics.battleship.entities.ships.Ship;
import com.followanalytics.battleship.entities.ships.Submarine;
import com.followanalytics.battleship.exceptions.NoShipLocationException;
import com.followanalytics.battleship.services.GridService;
import com.followanalytics.battleship.services.GridServiceImpl;

class ShipsPlacementTest {

	/**
	 * Creates a grid and randomly places 5 ships.
	 * Checks if the ships are correctly initialized
	 */
	@Test
	void randomShipsPlacementTest() {
		Grid grid = new Grid(10, 10);
		GridService gridService = new GridServiceImpl(grid);
		
		//Create ships to place
		List<Ship> ships = new ArrayList<Ship>(List.of(
				new Battleship(),
				new Carrier(),
				new Cruiser(),
				new Submarine(),
				new Destroyer()
		));
		
		try {
			//Randomly place ships in the grid
			gridService.placeShips(ships);
		}
		catch (NoShipLocationException e) {
			fail(e.getMessage());
		}
		
		//Check if the grid has all ships
		assertEquals(ships.size(), grid.getShips().size());
		
		//For each ship, check if the number of grid squares assigned to the ship matches the ship size
		ships.forEach(ship -> assertEquals(ship.getSquares().size(), ship.getSize()));
	}
	
	/**
	 * Creates a grid with a ship.
	 * Checks if the method GridServiceImpl.checkShipCollision works properly when trying to place another ship all around the first one or on it 
	 */
	@Test
	void shipCollisionTest() {
		Grid grid = new Grid(10, 10);
		GridServiceImpl gridService = new GridServiceImpl(grid);
		Ship ship = new Submarine();
		
		//Place the ship horizontally
		grid.insertShipInGrid(ship, new ShipLocation(2, 2, true));
		
		int destroyerSize = 2;
		
		//Try to place horizontally a Destroyer at (0, 2), expected true
		assertTrue(gridService.checkShipCollision(0, 2, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (1, 2), expected false
		assertFalse(gridService.checkShipCollision(1, 2, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (2, 2), expected false
		assertFalse(gridService.checkShipCollision(2, 2, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (3, 2), expected false
		assertFalse(gridService.checkShipCollision(3, 2, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (4, 2), expected false
		assertFalse(gridService.checkShipCollision(4, 2, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (5, 2), expected true
		assertTrue(gridService.checkShipCollision(5, 2, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (2, 1), expected true
		assertTrue(gridService.checkShipCollision(2, 1, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (3, 1), expected true
		assertTrue(gridService.checkShipCollision(3, 1, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (4, 1), expected true
		assertTrue(gridService.checkShipCollision(4, 1, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (2, 3), expected true
		assertTrue(gridService.checkShipCollision(2, 3, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (3, 3), expected true
		assertTrue(gridService.checkShipCollision(3, 3, destroyerSize, true));
		
		//Try to place horizontally a Destroyer at (4, 3), expected true
		assertTrue(gridService.checkShipCollision(4, 3, destroyerSize, true));
		
		
		
		
		//Try to place vertically a Destroyer at (1, 2), expected true
		assertTrue(gridService.checkShipCollision(1, 2, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (2, 0), expected true
		assertTrue(gridService.checkShipCollision(2, 0, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (3, 0), expected true
		assertTrue(gridService.checkShipCollision(3, 0, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (4, 0), expected true
		assertTrue(gridService.checkShipCollision(4, 0, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (2, 1), expected false
		assertFalse(gridService.checkShipCollision(2, 1, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (3, 1), expected false
		assertFalse(gridService.checkShipCollision(3, 1, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (4, 1), expected false
		assertFalse(gridService.checkShipCollision(4, 1, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (5, 2), expected true
		assertTrue(gridService.checkShipCollision(5, 2, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (2, 2), expected false
		assertFalse(gridService.checkShipCollision(2, 2, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (3, 2), expected false
		assertFalse(gridService.checkShipCollision(3, 2, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (4, 2), expected false
		assertFalse(gridService.checkShipCollision(4, 2, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (2, 3), expected true
		assertTrue(gridService.checkShipCollision(2, 3, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (3, 3), expected true
		assertTrue(gridService.checkShipCollision(3, 3, destroyerSize, false));
		
		//Try to place vertically a Destroyer at (4, 3), expected true
		assertTrue(gridService.checkShipCollision(4, 3, destroyerSize, false));
	}
	
	/**
	 * Creates a grid with a Carrier ship placed at (0, 0)
	 * Checks if the number of possibilities to place another Carrier ship is correct
	 */
	@Test
	void availableShipLocationsTest() {
		Grid grid = new Grid(10, 10);
		GridServiceImpl gridService = new GridServiceImpl(grid);
		Ship ship = new Carrier();
		
		//Place the ship horizontally
		grid.insertShipInGrid(ship, new ShipLocation(0, 0, true));
		
		//Get available locations for another Carrier ship
		//To place another Carrier ship, we should have 6*9 + 1 possibilities to place it horizontally
		//and 6*5 + 5*5 possibilities to place it vertically. A total of 110 possibilities
		List<ShipLocation> locations = gridService.getAvailableShipLocations(5);
		
		assertEquals(locations.size(), 110);
	}

}
