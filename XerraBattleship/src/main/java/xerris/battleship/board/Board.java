package xerris.battleship.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Board {
	@SuppressWarnings("unused")
	private boolean silent = false;
	private static final int BOARDSIZE  = 8;
	private List<List<Cell>> board;
	private List<Cell> openCells;
	private Random rand;

	public Board(boolean silent) {
		this.silent = silent;
		openCells = new ArrayList<Cell>();
		board = new ArrayList<List<Cell>>();
		for(int r = 0;r< BOARDSIZE;r++) {
			List<Cell> row = new ArrayList<Cell>(BOARDSIZE);
			for(int c = 0;c< BOARDSIZE;c++) {
				row.add(c,new Cell(r,c));
				openCells.add(row.get(c));
			}
			board.add(row);
		}
		rand = new Random(java.time.Instant.now().getEpochSecond());
	}

	public void playShoot() {
		// TODO Auto-generated method stub
	}

	public void playHit() {
		// TODO Auto-generated method stub
	}

	public Cell getCellAvail(Cell cell, String compasDirection) {
		if (compasDirection.toLowerCase().equals("north") && cell.getRow() > 0) {
			Cell northCell = this.board.get(cell.getRow() - 1).get(cell.getColl());
			if (!northCell.isHit()) {
				return northCell;
			}
		} else if (compasDirection.toLowerCase().equals("south") && cell.getRow() < 7) {
			Cell southCell = this.board.get(cell.getRow() + 1).get(cell.getColl());
			if (!southCell.isHit()) {
				return southCell;
			}
		} else if (compasDirection.toLowerCase().equals("east") && cell.getColl() < 7) {
			Cell eastCell = this.board.get(cell.getRow()).get(cell.getColl() + 1);
			if (!eastCell.isHit()) {
				return eastCell;
			}
		} else if (compasDirection.toLowerCase().equals("west") && cell.getColl() > 0) {
			Cell westCell = this.board.get(cell.getRow()).get(cell.getColl() - 1);
			if (!westCell.isHit()) {
				return westCell;
			}
		}
		return null;
	}

	public Cell bombRandomCell() throws Exception{
		if (openCells.size() == 0)
            throw new Exception("No open cells to bomb");
        int openCellNum = Math.abs(rand.nextInt()) % (openCells.size()-1);   
        Cell c = openCells.remove(openCellNum);
        c.hit();
        if(c.isShip())
            playHit();
        return c;
	}

	public boolean bombCell(Cell cell) throws Exception {
	       if (cell == null ||cell.isHit() || !openCells.contains(cell))
	            throw new Exception("Cell is not available");
	        cell.hit();
	        openCells.remove(cell);
	        if(cell.isShip()) {
	            playHit();
	            return true;
	        }
	        return false;	
	}

	public Ship allocRandomShip(boolean horizontal) {
		Cell bowCell,midCell,sternCell;
	       if (horizontal) { 
	            int colNo =Math.abs( (rand.nextInt()) % 6)+1;
	            int rowNo = (Math.abs(rand.nextInt()) % 8);
	            bowCell = board.get(rowNo).get(colNo);
	            midCell = board.get(rowNo).get(colNo-1);
	            sternCell = board.get(rowNo).get(colNo+1);
	       }else {
	            int colNo = Math.abs(rand.nextInt()) % 8;
	            int rowNo = (Math.abs(rand.nextInt()) % 6) +1;
	            bowCell = board.get(rowNo).get(colNo);
	            midCell = board.get(rowNo+1).get(colNo);
	            sternCell = board.get(rowNo-1).get(colNo);
	       }
           bowCell.setShip();
           midCell.setShip();
           sternCell.setShip();
  	       return new Ship(horizontal,bowCell,midCell,sternCell);
	}

	public Ship allocShip(boolean horizontal, int row,int coll) {
		Cell bowCell,midCell,sternCell;
	    if(horizontal) {
            if (coll == 0)
                coll++;
            if(coll == 7)
            	coll--;
            midCell = board.get(row).get(coll);
            bowCell = board.get(row).get(coll-1); 
            sternCell = board.get(row).get(coll+1);
	    }else {
            if (row == 0) 
            	row++;
            if (row == 7)
            	row--;
            midCell = board.get(row).get(coll);
            bowCell = board.get(row-1).get(coll); 
            sternCell = board.get(row+1).get(coll);
	    }  
	    bowCell.setShip();
	    midCell.setShip();
	    sternCell.setShip();
        return new Ship(horizontal,bowCell,midCell,sternCell);
	}
}
