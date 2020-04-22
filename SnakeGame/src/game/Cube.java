package game;
import javafx.scene.shape.Rectangle;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Cube {

	public final static int ROWS = 20;
	public final static int W = 500;
	public final static int H = 535;
	public final static int dist = W / ROWS;

	// used for cookie
	private int initialX;
	private int initialY;

	private Position pos;
	private Direction dir = new Direction(0, 0);
	private Color color;
	private Rectangle rect;

	public Cube(Position initialPos, Color color, Pane root, Scene scene) {
		this.color = color;
		initialX = initialPos.getX();
		initialY = initialPos.getY();
		this.pos = new Position(initialPos.getX(), initialPos.getY());
		this.drawCube(root, scene);
	}

	public void move_cube(Pane root, int dirnx, int dirny) {
		// update position of cube
		this.setDir(dirnx, dirny);
		this.pos = new Position(pos.getX() + (dist * dirnx), pos.getY() + (-dist * dirny));
//		System.out.println("x "+this.pos.getX()+" y "+this.pos.getY());
		this.rect.setTranslateX(this.pos.getX() - initialX);
		this.rect.setTranslateY(this.pos.getY() - initialY);
	}

	// called by constructor
	public void drawCube(Pane root, Scene scene) {
		int i = this.pos.getX();
		int j = this.pos.getY();
		this.rect = new Rectangle(i, j, dist, dist);
		this.rect.setFill(color);
		root.getChildren().add(this.rect);
	}

	public void moveCookie(Snake snake) {
		Position newCookiePos = Utils.randomSnack(snake);
		this.setPos(newCookiePos.getX(), newCookiePos.getY());
		this.rect.setTranslateX(this.pos.getX() - initialX);
		this.rect.setTranslateY(this.pos.getY() - initialY);
	}

	public Position getPos() {
		return this.pos;
	}

	public void setPos(int posx, int posy) {
		this.pos.setX(posx);
		this.pos.setY(posy);
	}

	public void setDir(int dirnx, int dirny) {
		this.dir.setDirnx(dirnx);
		this.dir.setDirny(dirny);
	}

	public Direction getDir() {
		return this.dir;
	}

}
