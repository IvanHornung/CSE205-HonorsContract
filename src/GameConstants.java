import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public interface GameConstants {

    public static final int GAME_TICK_SPEED = 8; //millis - default: 10
    public static final int GAME_PAUSE_TIME = 3000; //millis - 3 seconds

    public static final int WINSIZE_X = 1200, WINSIZE_Y = 800;
    public final String WINTITLE = "Breakout Game - Hornung";
    public final Font SCORE_FONT = Font.font("Helvetica", 20);
    public final Font END_GAME_FONT = Font.font("Helvetica", 40);
    public final Font TITLE_FONT = Font.font("Helvetica", 100);
    public final int SCORE_LOSS = 5;
    public final int INITIAL_LIVES = 3;

    public static final int PADDLE_WIDTH = 80, PADDLE_HEIGHT = 8;
    public static final Color PADDLE_COLOR = Color.rgb(239, 255, 198);

    public static final int BALL_DIAMETER = 10;

    public static int BLOCK_ROW_COUNT = 10, BLOCK_COLUMN_COUNT = 10;

    public static final int BLOCK_WALL_SPACE = WINSIZE_X / 15;
    public static final int BLOCK_SPACING = 6;
    public static final int BLOCK_WIDTH = (int)((WINSIZE_X - (BLOCK_WALL_SPACE * 2))/(BLOCK_ROW_COUNT)) - BLOCK_SPACING;
    public static final int BLOCK_HEIGHT = (int)((WINSIZE_Y*0.9 - BLOCK_WALL_SPACE*4)/(BLOCK_COLUMN_COUNT)) - BLOCK_SPACING;

    public static final int WALL_THICKESS = 10;

    public static final int BLOCK_STROKE_THICKNESS = 5;

    public static final double VELOCITY_INCREASE_PERCENT = 0.015;
    public static final int VELOCITY_INCREASE_FREQUENCY = 5;

    public static final double BALL_INIT_VELOCITY_X = Math.signum(Math.random()-0.5)*0.5, BALL_INIT_VELOCITY_Y = -(Math.random()*0.2+0.9);
    public static final double BALL_INIT_VELOCITY = 2;

    public static final int SPECIAL_BLOCK_COUNT = 4;

}

/**
 * TODO:
 *  - see what can be constant
 *  - add comments to each method and class with @
 *  - set instance variables to private and getters/mutators
 *  - organize imports by library + rid unnused imports
 *  - compare var names with example
 *  - change color for blocks
 *  - add levels?
 *  - change speeds
 */