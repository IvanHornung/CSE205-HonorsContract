public class Hitbox {
    public Vector centerPosition;
    public double horizontalRadius, verticalRadius;

    public Hitbox(double xvalue, double yvalue, int width, int height) {
        // JavaFX shape properties are defined by the coordinates of the upper left 
        // corner of the shape. For my hitbox class, I am choosing to track the 
        // center of the object. {vertical, horizontal} radius for a BreakoutObject
        // is just 1/2 of the {height, width}.

        double centerPosX = xvalue + width/2;
        double centerPosY = yvalue + height/2;
        this.centerPosition = new Vector(centerPosX, centerPosY);

        this.horizontalRadius = width/2;
        this.verticalRadius = height/2;
    }
}
