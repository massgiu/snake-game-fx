package game;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GameScene extends Scene{
	
	private GraphicsContext gc;
	private Canvas canvas;

	public GameScene(Parent root) {
		super(root);

		canvas = new Canvas(Cube.W, Cube.H);
		((Pane) root).getChildren().add(canvas);

		gc = canvas.getGraphicsContext2D();
	}
	
	public void initScreen() {
		
		//Draw background
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, Cube.W, Cube.H);
		//Draw grid
		gc.setStroke(Color.GRAY);
		int sizeBwn = Cube.W / Cube.ROWS;
		for (int i = 0; i < Cube.W; i += sizeBwn) {
			gc.strokeLine(i, 0, i, Cube.H); //vertical lines
			gc.strokeLine(0, i, Cube.W, i); //horizontal lines
		}

//		initSnake();
//
	}
}
