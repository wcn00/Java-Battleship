package xerris.battleship.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player {
	private boolean horizontal;
	private Random rand;
	private Board board;
	private Ship ship;
	private List<Cell> opponentShip;
	private int hitState = 0;
	private List<Cell> attackNext;
	private String playerName;

	public Player(String playerName, boolean silent) {
		this.playerName = playerName;
		rand = new Random(java.time.Instant.now().getEpochSecond());
		horizontal = rand.nextBoolean();
		board = new Board(silent);
		ship = board.allocRandomShip(silent);
		opponentShip = new ArrayList<Cell>();
		attackNext = new ArrayList<Cell>();
	}

	public String getName() {
		return playerName;
	}

	public Board getBoard() {
		return board;
	}

	public boolean go(Board opponentBoard) throws Exception {
		Cell bombedCell = null;
		if (hitState == 0) {
			bombedCell = opponentBoard.bombRandomCell();
			if (bombedCell != null && bombedCell.isShip()) {
				hitState++;
				opponentShip.add(bombedCell);
			}
			hitMsg(bombedCell);
		} else if (hitState == 1) {
			if ((bombedCell = opponentBoard.getCellAvail(opponentShip.get(0), "east")) != null) {
				if (opponentBoard.bombCell(bombedCell)) {
					hitState++;
					opponentShip.add(bombedCell);
					Cell attackNextCell = null;
					if ((attackNextCell = opponentBoard.getCellAvail(bombedCell, "east")) != null)
						attackNext.add(attackNextCell);
					if ((attackNextCell = opponentBoard.getCellAvail(opponentShip.get(0), "west")) != null)
						attackNext.add(attackNextCell);
				}
				hitMsg(bombedCell);
			} else if ((bombedCell = opponentBoard.getCellAvail(opponentShip.get(0), "north")) != null) {
				if (opponentBoard.bombCell(bombedCell)) {
					hitState++;
					opponentShip.add(bombedCell);
					Cell attackNextCell = null;
					if ((attackNextCell = opponentBoard.getCellAvail(bombedCell, "north")) != null)
						attackNext.add(attackNextCell);
					if ((attackNextCell = opponentBoard.getCellAvail(opponentShip.get(0), "south")) != null)
						attackNext.add(attackNextCell);
				}
				hitMsg(bombedCell);
			} else if ((bombedCell = opponentBoard.getCellAvail(opponentShip.get(0), "west")) != null) {
				if (opponentBoard.bombCell(bombedCell)) {
					hitState++;
					opponentShip.add(bombedCell);
					Cell attackNextCell = null;
					if ((attackNextCell = opponentBoard.getCellAvail(bombedCell, "west")) != null)
						attackNext.add(attackNextCell);
				}
				hitMsg(bombedCell);
			} else if ((bombedCell = opponentBoard.getCellAvail(opponentShip.get(0), "south")) != null) {
				if (opponentBoard.bombCell(bombedCell)) {
					hitState++;
					opponentShip.add(bombedCell);
					Cell attackNextCell = null;
					if ((attackNextCell = opponentBoard.getCellAvail(bombedCell, "south")) != null)
						attackNext.add(attackNextCell);
				}
				hitMsg(bombedCell);
			}
		} else if (hitState == 2 && attackNext.size() > 0){
	            bombedCell = attackNext.remove(0);
	            hitMsg(bombedCell);
	            if(opponentBoard.bombCell(bombedCell))
	                return true;
		}
		return false;
	}

	private void hitMsg(Cell attackCell) {
		if (attackCell.isShip())
			System.out.println(playerName + " scored a hit at" + attackCell.getColl() + ":" + attackCell.getRow());
		else
			System.out.println(playerName + " missed");
	}
}
