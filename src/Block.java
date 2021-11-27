import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Block extends BreakoutObject implements GameConstants {
    private static ArrayList<Color> rowColors = new ArrayList<Color>();

    static {
        for(int i = 0; i < BLOCK_COLUMN_COUNT; i++) {
            int redValue = (int)(Math.random()*(235)+20);
            int greenValue = (int)(Math.random()*(235)+20);
            int blueValue = (int)(Math.random()*(235)+20);
            rowColors.add(Color.rgb(redValue, greenValue, blueValue).brighter());
        }
    }

    public Block(int xPos, int yPos, int column) {
        this.objectWidth = BLOCK_WIDTH;
        this.objectHeight = BLOCK_HEIGHT;

        this.position = new Vector(xPos, yPos);
        this.velocity = new Vector();

        this.hitbox = new Hitbox(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);

        //determine objectColor given the row
        this.objectColor = rowColors.get(column);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(this.objectColor);
        gc.fillRect(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);

        //add decorative border on blocks
        gc.setStroke(this.objectColor.darker());
        gc.setLineWidth(BLOCK_STROKE_THICKNESS);
        gc.strokeRect(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);
    }
}