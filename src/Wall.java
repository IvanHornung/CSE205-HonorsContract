import javafx.scene.canvas.GraphicsContext;

public class Wall extends BreakoutObject implements GameConstants {

    public int wallType;

    public Wall(int wallType) {
        this.wallType = wallType;

        // constructor parameter wallType determines which wall (left, top, right, or bottom) the Wall instance is
        switch(wallType) {
            case 1: // case 1 is the left wall
                this.objectWidth = WALL_THICKESS;
                this.objectHeight = WINSIZE_Y;
                this.position = new Vector(-this.objectWidth, 0);
                break;
            case 2: // case 2 is the top wall
                this.objectWidth = WINSIZE_X;
                this.objectHeight = WALL_THICKESS;
                this.position = new Vector(0, -this.objectHeight);
                break;
            case 3: // case 3 is the right wall
                this.objectWidth = WALL_THICKESS;
                this.objectHeight = WINSIZE_Y;
                this.position = new Vector(WINSIZE_X, 0);
                break;
            case 4:
                this.objectWidth = WINSIZE_X;
                this.objectHeight = WALL_THICKESS;
                this.position = new Vector(0, WINSIZE_Y);
                break;
            default:
                break;
        }
        this.velocity = new Vector(); // (0, 0) since walls are stationary
        this.hitbox = new Hitbox(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);
    }

    @Override
    public void draw(GraphicsContext gc) {
        // no need to draw the wall components. they are meant for calculation
    }
}
