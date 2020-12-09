package xerris.battleship.board;


public class Ship {
	private Cell bow,midships,stern;
	private boolean horizontal = true;
	public Ship(boolean horizontal, Cell bow, Cell midships,Cell stern) {
		this.bow = bow;
		this.midships = midships;
		this.stern = stern;
		this.horizontal = horizontal;
	}

	public boolean isHorizontal() {
		return horizontal;
	}

	public Cell getBow() {
		return bow;
	}

	public Cell getMidships() {
		return midships;
	}

	public Cell getStern() {
		return stern;
	}

	public boolean isSunk() {
		return bow.isHit() && midships.isHit() && stern.isHit();
	}

}
