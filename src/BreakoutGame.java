import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BreakoutGame extends Application implements GameConstants {

	public Timeline timeline;

	public ArrayList<BreakoutObject> gameObjects;
	public ArrayList<Ball> gameBalls;

	public static boolean startedGame = false;
	public int timer = 0;
	public Paddle player;
	public static int score = 0, lives = INITIAL_LIVES;
	public int numBlocksRemaining = BLOCK_COLUMN_COUNT*BLOCK_ROW_COUNT, numBlockHitDuringThisLife = 0;

	public void start(Stage stage) throws Exception {
		Canvas canvas = new Canvas(WINSIZE_X, WINSIZE_Y);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		showMenu(gc);
		timeline = new Timeline(new KeyFrame(Duration.millis(GAME_TICK_SPEED), e -> run(gc)));
		timeline.setCycleCount(Timeline.INDEFINITE);

		canvas.setOnMouseMoved(
				e -> this.player.position.xvalue = (e.getX() + PADDLE_WIDTH > WINSIZE_X) ? e.getX() - PADDLE_WIDTH
						: e.getX()
		// lambda function sets player's position given mouse coordinates. ternary
		// operator prevents paddle
		// from going off the canvas on the right side of the game
		);
		canvas.setOnMouseClicked(e -> startedGame = true);
 
		stage.setScene(new Scene(new StackPane(canvas)));
		stage.setTitle(WINTITLE);
		stage.setResizable(false);
		stage.show();

		initializeScene();
		timeline.play();
	}

	private void paint(GraphicsContext gc) {

		// black background
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, WINSIZE_X, WINSIZE_Y);

		// score text
		gc.setFill(Color.WHITE);
		gc.setFont(SCORE_FONT);
		gc.fillText("Score: " + score, 5, 20);

		// lives remaining text
		gc.setFill(Color.WHITE);
		gc.setFont(SCORE_FONT);
		gc.fillText("Lives: " + lives, WINSIZE_X - 80, 20);

		// draw all objects
		for (BreakoutObject c : gameObjects) {
			if(c.visible) {
				c.render();
				c.draw(gc);
			}
		}
	}

	private void run(GraphicsContext gc) {
		if(startedGame) {
			if(timer > 0) {
				timer -= GAME_TICK_SPEED;
			}
			//check if player lost
			//if the game has started and if the user ran out of lives and if the user didn't clear
			else if(lives <= 0 && numBlocksRemaining > 0) {
				// score text
				showEndGame(gc, true);
			}
			//check if player won
			else if(lives > 0 && numBlocksRemaining <= 0) {
				showEndGame(gc, false);
			}
			//check if player still has lives
			else if(lives > 0) {
				paint(gc);
				//increase velocity randomly every 8000 frames
				//calculates ball interactions
				for(BreakoutObject c : gameObjects)
					if(c.visible && !(c instanceof Ball))
						for(Ball b : gameBalls) {
							switch(b.isOverlapping(c)) {
								case HIT_SPECIAL_BLOCK: //add a new ball if a blinking block is hit
									Ball newBallAdditional = new Ball();
									gameObjects.add(newBallAdditional);
									gameBalls.add(newBallAdditional);
								case HIT_BLOCK:
									score++;
									numBlocksRemaining--;
									numBlockHitDuringThisLife++;
									b.velocity.multiply(1 + VELOCITY_INCREASE_PERCENT);
									break;
								case HIT_PADDLE:
									b.velocity.multiply(1 + VELOCITY_INCREASE_PERCENT);
									break;
								case HIT_WALL:
									b.velocity.multiply(1 + VELOCITY_INCREASE_PERCENT);
									break;
								case NO_HIT:
									break;
								case OUT_OF_BOUNDS:
									gameBalls.remove(b);
									gameObjects.remove(b);

									if(gameBalls.size() <= 0) {
										score -= SCORE_LOSS;
										readjustPaddle();
										timer = GAME_PAUSE_TIME;
										lives--;
										numBlockHitDuringThisLife = 0;

										//if this wasn't an additional ball, re-add the ball
										Ball newBall = new Ball();
										gameBalls.add(newBall);
										gameObjects.add(newBall);
									} 

									paint(gc);
									break;
							}
						}
			} 
		}
	}

	private void showMenu(GraphicsContext gc) {
		gc.setFill(Color.BLACK);
		gc.setFont(TITLE_FONT);
		gc.fillText("BREAKOUT", WINSIZE_X/2 - 400, WINSIZE_Y/2);

		gc.setFont(Font.font("Helvetica", FontPosture.ITALIC, 20));
		gc.fillText("by Ivan Hornung", WINSIZE_X/2 - 400, WINSIZE_Y/2+100);

		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		gc.fillText("Click Anywhere to Play", WINSIZE_X/2 - 390, WINSIZE_Y/2+200);

		gc.setFont(Font.font("Helvetica", 10));
		gc.fillText("CSE 205 Honors Contract", WINSIZE_X/2 - 400, WINSIZE_Y/2+20);
	}

	public void initializeScene() {
		gameObjects = new ArrayList<BreakoutObject>();
		gameBalls = new ArrayList<Ball>();

		// add player/paddle
		player = new Paddle();
		gameObjects.add(player);

		// add ball
		Ball ball = new Ball();
		gameObjects.add(ball);
		gameBalls.add(ball);

		// add walls/borders
		for(int i = 1; i <= 4; i++) {
			Wall wall = new Wall(i);
			gameObjects.add(wall);
		}
		

		// add blocks
		for (int r = 0; r < BLOCK_ROW_COUNT; r++)
			for (int c = 0; c < BLOCK_COLUMN_COUNT; c++) {
				int xvalue = BLOCK_WALL_SPACE + (r * (BLOCK_WIDTH + BLOCK_SPACING));
				int yvalue = BLOCK_WALL_SPACE + (c * (BLOCK_HEIGHT + BLOCK_SPACING));
				//randomly add special blocks
				if((int)(Math.random()*((BLOCK_ROW_COUNT*BLOCK_COLUMN_COUNT)/SPECIAL_BLOCK_COUNT)) == 0) {
					BlinkingBlock blinkingBlock = new BlinkingBlock(xvalue, yvalue, c);
					gameObjects.add(blinkingBlock);
				}
				//else add a normal block
				else {
					Block block = new Block(xvalue, yvalue, c);
					gameObjects.add(block);
				}
			}
			
	}

	private void showEndGame(GraphicsContext gc, boolean lost) {
		paint(gc);
		gc.setFill(Color.WHITE);
		gc.setFont(END_GAME_FONT);
		gc.fillText((lost) ? "You lost" : "You Won!!" , WINSIZE_X/2 - 150, player.position.yvalue - BLOCK_WALL_SPACE);
	}

	public void readjustPaddle() {
		int xvalue = (int)((WINSIZE_X - this.player.objectWidth)/2), yvalue = (int)(WINSIZE_Y*0.9);
        this.player.position = new Vector(xvalue, yvalue);
        this.player.velocity = new Vector();

        this.player.hitbox = new Hitbox(this.player.position.xvalue, this.player.position.yvalue,
											this.player.objectWidth, this.player.objectHeight);
	}

	/**
	 * Technically this is not needed for JavaFX applications. Added just in case.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

}