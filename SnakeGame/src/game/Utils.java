package game;

import java.util.Random;

public class Utils {

	private static Random random = new Random();

	public Utils() {

	}

	public static Position randomSnack(Snake snake) {
		boolean loop = true;
		int randomx = 0;
		int randomy = 0;
		while (loop) {
			boolean validCookie = true;
			randomx = random.nextInt(Cube.ROWS) * Cube.dist;
			randomy = random.nextInt(Cube.ROWS) * Cube.dist;
			for (Cube elem : snake.getBodyList()) {
				int posx = elem.getPos().getX();
				int posy = elem.getPos().getY();
				if ((randomx == posx) || (randomy == posy)) {
					validCookie = false;
				}
			}
			if (validCookie) {
				loop = false;
			}
		}
		return new Position(randomx, randomy);
	}

	public static boolean checkCrossing(Snake snake) {
		boolean check = true;
		Position headPos = snake.getHead().getPos();
		for (int i = 1; i < snake.getBodyList().size(); i++) {
			if (snake.getBodyList().get(i).getPos().getX() == headPos.getX()
					&& snake.getBodyList().get(i).getPos().getY() == headPos.getY()) {
				check = false;
			}
		}
		return check;
	}
}
