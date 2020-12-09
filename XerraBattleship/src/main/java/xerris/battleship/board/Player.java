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

	public Player(String playerName, boolean silent,boolean autoplay) {
		this.playerName = playerName;
		rand = new Random(java.time.Instant.now().getEpochSecond());
		horizontal = rand.nextBoolean();
		board = new Board(silent);
		opponentShip = new ArrayList<Cell>();
		attackNext = new ArrayList<Cell>();
		if(autoplay)
			ship = board.allocRandomShip(silent);
	}

	public String getName() {
		return playerName;
	}

	public void placeShip(Cell shipcell) {
		board.allocShip(horizontal, shipcell.getRow(), shipcell.getColl());
	}
	public Board getBoard() {
		return board;
	}

	/*
	 * Note that the interactive portions of this method don't junit check
	 */
	public boolean go(Board opponentBoard,boolean autoPlay) throws Exception {
		Cell bombedCell = null;
		if(autoPlay)
			return goAuto(opponentBoard);
		else {
			boolean bombed = false;
			while (!bombed) {
				opponentBoard.print();
				bombedCell = getCellToBombFromPlayer(opponentBoard,"please enter the coordinates to bomb",null);
				if(bombedCell != null) {
					if(bombedCell.isHit()) {
						System.out.println("You chose an already bombed cell; try again");
						continue;
					}
					opponentBoard.bombCell(bombedCell);
					hitMsg(bombedCell);
					bombed = true;
					}
				}				
			return board.shipForCellIsSunk(bombedCell);
		}
	}

	public Cell getCellToBombFromPlayer(Board opponentboard,String msg,String junitstring) {		
		int col=-1,row=-1;
		System.out.println(playerName+":"+msg);
		String line = "";
		if(junitstring != null) {
			line = junitstring;
		} else {
			line = System.console().readLine();
		}
		if(line.length() >= 2) {
			row = Cell.getRowNameIndex(line.charAt(0));
			col = Cell.getColNameIndex(line.charAt(1));
		}
		if(row < 0|| col < 0) {
			System.out.println("Invalid coordinate specified, use format:  5a");
			return null;
		} else {
			return opponentboard.getCellByCorrids(row, col);
		}
	}
	/*
	 * Code coverage of junit tests here is hit and miss because the location of the ship is random as is the bombing
	 * 
	 */
	private boolean goAuto(Board opponentBoard) throws Exception {
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
			System.out.println(playerName + " scored a hit at " + attackCell.getRowLbl() + ":" + attackCell.getColLbl());
		else
			System.out.println(playerName + " missed");
	}
}
