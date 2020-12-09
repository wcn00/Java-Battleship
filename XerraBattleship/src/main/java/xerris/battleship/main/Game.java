package xerris.battleship.main;

import xerris.battleship.board.Cell;
import xerris.battleship.board.Player;

public class Game {

	public static void main(String[] argv) {

		boolean autoPlay = true;
		@SuppressWarnings("unused")
		boolean display = false; // display will be implemented if I get time before the challenge expires.
		boolean quiet = false;
		Player playera, playerb;
		for (String arg : argv) {
			if (arg.equals("--quiet"))
				quiet = true;
			else if (arg.equals("--manual"))
				autoPlay = false;
		}
		if (!autoPlay) {
			System.out.println("Please enter your name:");
			String resp = System.console().readLine();
			playera = new Player(resp, quiet, autoPlay);
			Cell shipCell = null;
			while (shipCell == null) {
				shipCell = playera.getCellToBombFromPlayer(playera.getBoard(),"Please enter the cell for the middle of your ship",null);
			}
			playera.placeShip(shipCell);
		} else {
			playera = new Player("Kane", quiet, autoPlay);
		}
		playerb = new Player("Able", quiet, true);
		try {
			while (true) {
				if (playera.go(playerb.getBoard(), autoPlay)) {
					System.out.println(playerb.getName() + ":  You sank my battleship!!!");
					break;
				}
				if (playerb.go(playera.getBoard(), true)) {
					System.out.println(playera.getName() + ":  You sank my battleship!!!");
					break;
				}
			}
		} catch (Exception e) {
			System.out.println("Fatal error in game logic:");
			e.printStackTrace();
		}
	}
}
