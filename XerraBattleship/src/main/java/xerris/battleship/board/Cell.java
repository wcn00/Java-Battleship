package xerris.battleship.board;

public class Cell {
	public final static char[] COLNAMES = {'a','b','c','d','e','f','g','h'};
	public final static char[] ROWNAMES = {'1','2','3','4','5','6','7','8'};

	private int row,col;
	private char rowLbl,colLbl;
	private boolean ship = false;
	private boolean hit = false;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col =col;
		this.rowLbl = ROWNAMES[row];
		this.colLbl = COLNAMES[col];
	}

	public int getRow() {
		return row;
	}

	public int getColl() {
		return col;
	}

	public boolean isShip() {
		return ship;
	}
	public void setShip() {
		ship = true;
	}
	public void hit() {
		this.hit = true;
	}
	
	public boolean isHit() {
		return hit;
	}

	public char getRowLbl() {
		return rowLbl;
	}
	public char getColLbl() {
		return colLbl;
	}
}
