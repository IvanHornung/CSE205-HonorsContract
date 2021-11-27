import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Paddle extends BreakoutObject implements GameConstants {

    public Paddle() {
        this.objectWidth = PADDLE_WIDTH;
        this.objectHeight = PADDLE_HEIGHT;
        this.objectColor = PADDLE_COLOR;

        int xvalue = (int)((WINSIZE_X-this.objectWidth)/2), yvalue = (int)(WINSIZE_Y*0.9);
        this.position = new Vector(xvalue, yvalue);
        this.velocity = new Vector();

        this.hitbox = new Hitbox(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);
    }

    public void draw(GraphicsContext gc) {
        gc.setFill(this.objectColor);
		gc.fillRect(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);
    }

}
