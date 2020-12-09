package xerris.battleship.main;

import xerris.battleship.board.Player;

public class Game {

	public static void main(String[] argv) {
		boolean autoPlay = true;
		boolean display = false;
		Player playera = new Player("Kane",false);
		Player playerb = new Player("Able",false);
		try {
			if(autoPlay) {
				while(true) {
					if(playera.go(playerb.getBoard()))
						System.out.println(playera.getName() + " has won!!!");
					if(playerb.go(playera.getBoard()))
						System.out.println(playerb.getName() + " has won!!!");
				}
			}
		} catch (Exception e) {
			System.out.println("Fatal error in game logic:");
			e.printStackTrace();
		}
	}
}
