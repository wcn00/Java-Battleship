package xerris.battleship.board;

public class Cell {
	public final static char[] COLNAMES = {'a','b','c','d','e','f','g','h'};
	public final static char[] ROWNAMES = {'1','2','3','4','5','6','7','8'};

	private int row,col;
	private char rowLbl,colLbl;
	private boolean ship = false;
	private boolean hit = false;
	private Ship belongsToShip  = null;
	
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
	public void setShip(Ship s) {
		ship = true;
		belongsToShip = s;
	}
	public void hit() {
		this.hit = true;
	}
	
	public boolean isHit() {
		return hit;
	}

	public String print() {
		if(isHit() && isShip()) {
			return "**";
		} else if(isHit()){
			return "XX";
		}else	{
			char[] lbl = {rowLbl,colLbl};
			return new String(lbl);
		}
	}
	public char getRowLbl() {
		return rowLbl;
	}
	public char getColLbl() {
		return colLbl;
	}
	
	public static int getColNameIndex(char c) {		
		for(int i = 0;i<COLNAMES.length;i++) {
			if(COLNAMES[i] == Character.toLowerCase(c))
				return i;
		}
		return -1;
	}
	public static int getRowNameIndex(char c) {
		for(int i = 0;i<ROWNAMES.length;i++) {
			if(ROWNAMES[i] == c)
				return i;
		}
		return -1;
	}
	
	
	public  Ship getShip() {
		return this.belongsToShip;
	}
}
