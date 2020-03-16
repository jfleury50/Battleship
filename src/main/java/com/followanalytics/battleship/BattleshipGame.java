package com.followanalytics.battleship;

import java.util.ArrayList;
import java.util.List;

import com.followanalytics.battleship.entities.grid.Grid;
import com.followanalytics.battleship.entities.ships.Battleship;
import com.followanalytics.battleship.entities.ships.Carrier;
import com.followanalytics.battleship.entities.ships.Cruiser;
import com.followanalytics.battleship.entities.ships.Destroyer;
import com.followanalytics.battleship.entities.ships.Ship;
import com.followanalytics.battleship.entities.ships.Submarine;
import com.followanalytics.battleship.exceptions.NoShipLocationException;
import com.followanalytics.battleship.exceptions.NoSquareFoundException;
import com.followanalytics.battleship.services.GridService;
import com.followanalytics.battleship.services.GridServiceImpl;

public class BattleshipGame {
	
	private static int gridWidth = 10;
	private static int gridHeight = 10;

	public static void main(String[] args) {
		
		//Create a grid with given dimensions
		Grid grid = new Grid(gridWidth, gridHeight);
		
		//Create ships to place
		List<Ship> ships = new ArrayList<Ship>(List.of(
				new Battleship(),
				new Carrier(),
				new Cruiser(),
				new Submarine(),
				new Destroyer()
		));
		
		GridService gridService = new GridServiceImpl(grid);
		
		try {
			//Randomly place ships in the grid
			gridService.placeShips(ships);
			
			//Bombard a square
			boolean isShipHit = gridService.bombardSquare(3, 4);
			
			if(isShipHit)
				System.out.println("Congratulations, a ship is hit !");
			
			//Check if all ships are destroyed
			boolean areShipsDestroyed = gridService.areShipsDestroyed();
			
			if(areShipsDestroyed)
				System.out.println("Game Over !");
		}
		catch (NoShipLocationException nsle) {
			System.err.println(nsle.getMessage());
		}
		catch (NoSquareFoundException nsfe) {
			System.err.println(nsfe.getMessage());
		}
	}

}
