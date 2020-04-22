package game;

public class Direction {

	private int dirnx;
	private int dirny;

	public Direction(int dirnx, int dirny) {
		this.dirnx = dirnx;
		this.dirny = dirny;
	}

	public int getDirnx() {
		return dirnx;
	}

	public void setDirnx(int dirnx) {
		this.dirnx = dirnx;
	}

	public int getDirny() {
		return dirny;
	}

	public void setDirny(int dirny) {
		this.dirny = dirny;
	}
}
