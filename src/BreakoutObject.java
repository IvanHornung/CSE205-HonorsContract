import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class BreakoutObject {
    public Color objectColor;
    public int objectWidth, objectHeight;
    public Vector position, velocity;
    public boolean visible = true;
    public Hitbox hitbox;

    public abstract void draw(GraphicsContext gc);

    public void render() {
        /**
         * useless method that overidden if it's Ball. Should I?
         *  - have render defined in this class and add a check inside of for-each
         *    loop that draws (instanceOf Ball) to see if it should render
         *  - have render defined in this class and just run it no matter what
         *    (unnecessary "+= 0" calculations)
         *  - have this blank as is and @Override it in Ball
         * 
         */
    }

}
