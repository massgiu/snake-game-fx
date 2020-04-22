package game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.*;
import javafx.stage.Stage;

public class Main extends Application {

	public static final Color color = Color.RED;
	public static final Color cookieColor = Color.GREEN;
	private static final int initPosx = Cube.W / 2;
	private static final int initPosy = Cube.W / 2;
	private String codeString = "DOWN";

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		Stage stage = new Stage();
		stage.setWidth(Cube.W);
		stage.setHeight(Cube.H);
		stage.setTitle("Snake");
		Pane root = new Pane();
		Scene scene = new GameScene(root);
		stage.setScene(scene);
		stage.setResizable(false);
		scene.setOnKeyPressed(event -> {
			codeString = event.getCode().toString();
		});
		((GameScene) scene).initScreen();
		Snake snake = new Snake(scene, color, new Position(initPosx, initPosy), root);
		Cube cookie = new Cube(Utils.randomSnack(snake), cookieColor, root, scene);
//        snake.draw(root,scene);
		new AnimationTimer() {

			int frameCount = 0;

			@Override
			public void handle(long now) {
				if (frameCount % 6 == 0) { // to slowdown animation (higher->slower)
					switch (codeString) {
					case "LEFT":
						snake.move(scene, "LEFT", cookie);
						break;
					case "RIGHT":
						snake.move(scene, "RIGHT", cookie);
						break;
					case "UP":
						snake.move(scene, "UP", cookie);
						break;
					case "DOWN":
						snake.move(scene, "DOWN", cookie);
						break;
					}
				}
				frameCount++;
			}
		}.start();
//    	snake.draw(root,scene);
		stage.showAndWait();
	}
}