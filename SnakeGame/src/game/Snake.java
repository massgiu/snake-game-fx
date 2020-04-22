package game;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Snake {

	private List<Cube> body_list = new ArrayList<>();
	private Map<Position, Direction> turns = new LinkedHashMap<>();
	private Cube head;
	private Pane root;
	private Color color;
	private Scene scene;

	private int dirnx;
	private int dirny;
	private String actualDir = "initial";

	public Snake(Scene scene, Color color, Position pos, Pane root) {
		this.root = root;
		this.color = color;
		this.scene = scene;
		this.head = new Cube(pos, this.color, root, scene);
		this.body_list.add(this.head); // add head to snake
	}

	public List<Cube> getBodyList() {
		return this.body_list;
	}

	public void move(Scene scene, String direction, Cube cookie) {
		boolean addTurn = false;
		switch (direction) {
		case "LEFT":
			if ((this.body_list.size() > 1 && actualDir != "RIGHT") || (this.body_list.size() == 1)) {
				this.dirnx = -1;
				this.dirny = 0;
				addTurn = true;
				// System.out.println("left");
			}
			break;
		case "RIGHT":
			if ((this.body_list.size() > 1 && actualDir != "LEFT") || (this.body_list.size() == 1)) {
				this.dirnx = 1;
				this.dirny = 0;
				addTurn = true;
				// System.out.println("right");
			}
			break;
		case "UP":
			if ((this.body_list.size() > 1 && actualDir != "DOWN") || (this.body_list.size() == 1)) {
				this.dirnx = 0;
				this.dirny = 1;
				addTurn = true;
				// System.out.println("up");
			}
			break;
		case "DOWN":
			if ((this.body_list.size() > 1 && actualDir != "UP") || (this.body_list.size() == 1)) {
				this.dirnx = 0;
				this.dirny = -1;
				addTurn = true;
//			System.out.println("down");
			}
			break;
		}
		// After rotation add direction to turns list (key=position head)
		if (!direction.equals(actualDir) || actualDir.equals("initial")) {
			if (addTurn) {
				this.turns.put(this.head.getPos(), new Direction(this.dirnx, this.dirny));
				addTurn = false;
			}
			actualDir = direction;
		}

		for (int i = 0; i < this.body_list.size(); i++) {
			boolean turnPresent = false;
			Cube cub = this.body_list.get(i);
			Position p = cub.getPos();
			Direction turnDirection = null;
			for (Position posKey : turns.keySet()) {
				if (posKey.getX() == p.getX() && posKey.getY() == p.getY()) {
					turnPresent = true;
					turnDirection = turns.get(posKey); // get rotation direction from list
				}
			}
			if (turnPresent) { // if a block is in a turn position
				// move() cube in the rotation's direction
				cub.move_cube(root, turnDirection.getDirnx(), turnDirection.getDirny());
				// remove first rotation from list
				if (i == this.body_list.size() - 1) { // last cube
					Position firstPos = this.turns.keySet().iterator().next();
					turns.remove(firstPos);
				}
				// scene edges
				System.out.println("dirnx " + turnDirection.getDirnx() + "dirny " + turnDirection.getDirny());
			} else {
				int dirnx = cub.getDir().getDirnx();
				int dirny = cub.getDir().getDirny();
				int posx = cub.getPos().getX();
				int posy = cub.getPos().getY();
				// se la direz è sx e la pos_x<0 => riporto la x all'estremo dx
				if ((dirnx == -1) && (posx <= 0)) {
					cub.setPos(Cube.W, posy);
				}
				// se la direz è dx e la pos_x è al termine => riporto la x allo 0
				else if ((dirnx == 1) && (posx >= Cube.W - Cube.dist)) {
					cub.setPos(-Cube.dist, posy);
				}
				// se la direz y è down e la pos_y è al termine => riporto la y allo 0
				else if ((dirny == -1) && (posy >= Cube.W - Cube.dist)) {
					cub.setPos(posx, -Cube.dist);
				}
				// se la direz y è up e la pos_y è al termine => riporto la y al termine
				else if ((dirny == 1) && (posy <= 0)) {
					cub.setPos(posx, Cube.W);
				} else {
					// If we haven't reached the edge just move in our current direction
					cub.move_cube(root, dirnx, dirny);
				}
			}
		}
		// check if head overlaps a cookie
		if (this.getHead().getPos().getX() == cookie.getPos().getX()
				&& this.getHead().getPos().getY() == cookie.getPos().getY()) {
			this.addCube();
			cookie.moveCookie(this);
		}
		if (!Utils.checkCrossing(this)) {
			System.out.println("You lost!");
			System.exit(0);
		}
	}

	private void addCube() {
		Cube tail = this.getTail(); // at the beginning it's the head
		Position newPos = null;
		int dx = tail.getDir().getDirnx();
		int dy = tail.getDir().getDirny();

		// We need to know which side of the snake to add the cube to.
		// So we check what direction we are currently moving in to determine if we
		// need to add the cube to the left, right, above or below.
		if (dx == 1 && dy == 0) { // going right (x-1, same y)
			newPos = new Position(tail.getPos().getX() - Cube.dist, tail.getPos().getY());
		} else if (dx == -1 && dy == 0) {
			newPos = new Position(tail.getPos().getX() + Cube.dist, tail.getPos().getY());
		} else if (dx == 0 && dy == 1) { // going up (same x, y+1)
			newPos = new Position(tail.getPos().getX(), tail.getPos().getY() + Cube.dist);
		} else if (dx == 0 && dy == -1) { // going donw (same x, y-1)
			newPos = new Position(tail.getPos().getX(), tail.getPos().getY() - Cube.dist);
		}
		this.body_list.add(new Cube(newPos, this.color, this.root, this.scene));
		// set the cube (new tail) to same direction of the snake's tail.
		this.getTail().setDir(dx, dy);
	}

	public void draw(Pane root, Scene scene) {
		for (Cube cube : body_list) {
			cube.drawCube(root, scene);
		}
	}

	public Cube getHead() {
		return this.getBodyList().get(0);
	}

	private Cube getTail() {
		return this.getBodyList().get(this.getBodyList().size() - 1);
	}
}
