import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Ball extends BreakoutObject implements GameConstants {
    
    public Ball() {
        this.objectWidth = BALL_DIAMETER;
        this.objectHeight = BALL_DIAMETER;
        this.objectColor = Color.WHITE;


        this.position = new Vector((WINSIZE_X-this.objectWidth)/2, (WINSIZE_Y * 0.9) - this.objectHeight - 5);
        this.velocity = new Vector(BALL_INIT_VELOCITY_X, BALL_INIT_VELOCITY_Y);
        this.velocity.setMagnitude(BALL_INIT_VELOCITY);

        this.hitbox = new Hitbox(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);
    }

    /**
     * public void fillOval(double x,
                     double y,
                     double w,
                     double h)
     *  x - the X coordinate of the upper left bound of the oval.
        y - the Y coordinate of the upper left bound of the oval.
        w - the width at the center of the oval.
        h - the height at the center of the oval.
     */

    public void draw(GraphicsContext gc) {
		gc.setFill(this.objectColor);
		gc.fillOval(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);
	}

    public InteractionType isOverlapping(BreakoutObject c) {
        // slice em up 
        Hitbox objectHitbox = c.hitbox;
        double ballCenterX = this.hitbox.centerPosition.xvalue;
        double ballCenterY = this.hitbox.centerPosition.yvalue;

        //TODO: could shorten logic into one giant if statement since all the executions inside the if's are the same
        //if object c is divided into 9 cells like a phone dialing pad, this tests the middle column
        if((ballCenterX >= c.position.xvalue) && (ballCenterX <= c.position.xvalue + c.objectWidth)) {
            //number pad #2 and #8
            if((Math.abs(ballCenterY - c.position.yvalue) <= this.hitbox.verticalRadius)
                || (Math.abs(ballCenterY - (c.position.yvalue + c.objectHeight)) <= this.hitbox.verticalRadius) ) {
                    double currentMagnitude = this.velocity.getMagnitude();
                    this.velocity.yvalue = -1 * Math.signum(this.velocity.yvalue) /** (Math.random()*0.2 + 0.9)*/;
                    this.velocity.setMagnitude(currentMagnitude);
                    //if the object hit isn't a wall, we want the ball's x-val for velocity to flip if it hits the half of the 
                    //object from the side that it came from
                    if(c instanceof Paddle) {
                        //if ball hit object from the bottom/top on the left side, flip the x-value vector too
                        double topLeftCornerX = c.position.xvalue;
                        double topRightCornerX = c.position.xvalue + c.objectWidth;
                        //var to help determine which direction the ball came from by seeing where it was 5 frames ago
                        if(
                            //if where it was 10 frames ago is closer to top left corner than top right corner and it's still closer to that corner
                            //(meaning that it hit the right side of the object), we flip the x-velocity value
                            // OR the other way around for the other corner 
                            (this.velocity.xvalue > 0
                                && (Math.abs(this.position.xvalue - topLeftCornerX) < Math.abs(this.position.xvalue - topRightCornerX)))
                            || (this.velocity.xvalue < 0
                            && (Math.abs(this.position.xvalue - topRightCornerX) < Math.abs(this.position.xvalue - topLeftCornerX)))
                        ) {
                            this.velocity.xvalue *= -1 /** (1/Math.abs(this.position.xvalue - c.hitbox.centerPosition.xvalue))*/;
                            this.velocity.setMagnitude(currentMagnitude);
                        }
                    }
                    return determineObjectHit(c);
                }
        } //checks for middle row of cells
        else if((ballCenterY >= c.position.yvalue) && (ballCenterY <= c.position.yvalue + c.objectHeight)) {
            //number pad #4 and #6
            if((Math.abs(ballCenterX - c.position.xvalue) <= this.hitbox.horizontalRadius)
                || (Math.abs(ballCenterX - (c.position.xvalue + c.objectWidth)) <= this.hitbox.horizontalRadius)) {
                    this.velocity.xvalue *= -1;
                    return determineObjectHit(c);
                }
        } // checks for top corners
        else if(ballCenterY <= c.position.yvalue) {
            // first check for top left corner
            if(ballCenterX <= c.position.xvalue) {
                //see if distance between center of circle and top left corner of object is <= ball radius
                if(this.hitbox.centerPosition.compareTo(c.position) <= BALL_DIAMETER/2) {
                    determineVelocityFlip();
                    return determineObjectHit(c);
                } // end of distance less than radius check
            } // end of top left corner check
            // second check for top right corner
            else if(ballCenterX >= c.position.xvalue + c.objectWidth) {
                Vector objectTopRightCorner = new Vector(c.position.xvalue + c.objectWidth, c.position.yvalue);
                if(this.hitbox.centerPosition.compareTo(objectTopRightCorner) <= BALL_DIAMETER/2) {
                    determineVelocityFlip();
                    return determineObjectHit(c);
                } // end of distance less than radius check
            }// end of top right corner check
        } // checks for bottom corners
        else if(ballCenterY >= c.position.yvalue + c.objectHeight) {
            // third check for bottom left corner
            if(ballCenterX <= c.position.xvalue) {
                Vector objectBottomLeftCorner = new Vector(c.position.xvalue, c.position.yvalue + c.objectHeight);
                if(this.hitbox.centerPosition.compareTo(objectBottomLeftCorner) <= BALL_DIAMETER/2) {
                    determineVelocityFlip();
                    return determineObjectHit(c);
                } // end of distance less than radius check
            } // end of bottom left corner check
            else if(ballCenterX >= c.position.xvalue + c.objectWidth) {
                Vector objectBottomRightCorner = new Vector(c.position.xvalue + c.objectWidth, c.position.yvalue + c.objectHeight);
                if(this.hitbox.centerPosition.compareTo(objectBottomRightCorner) <= BALL_DIAMETER/2) {
                    determineVelocityFlip();
                    return determineObjectHit(c);
                }
            }
        }
        
        return InteractionType.NO_HIT;
    } //end of isOverlapping() method


    // used for corners
    private void determineVelocityFlip() {
        double determiner = Math.random() - 0.5;
        if(determiner >= 0)
            this.velocity.xvalue *= -1;
        else
            this.velocity.yvalue *= -1;
    }

    private InteractionType determineObjectHit(BreakoutObject c) {
        if(!(c instanceof Paddle) && !(c instanceof Wall)) {
            // BreakoutGame.score++;
            c.visible = false;
            return (c instanceof BlinkingBlock) ? InteractionType.HIT_SPECIAL_BLOCK : InteractionType.HIT_BLOCK;
        } else if(c instanceof Wall) {
            //check if its the bottom wall and remove the ball and subtract score
            Wall wallObjectC = (Wall)(c);
            if(wallObjectC.wallType == 4) {
                ballOut();
                return InteractionType.OUT_OF_BOUNDS;
            }
            // if it reaches this point, the object hit a wall but it isn't the bottom one
        } 
        return InteractionType.HIT_PADDLE;
    }

    private void ballOut() {
        this.position = new Vector((WINSIZE_X-this.objectWidth)/2, (WINSIZE_Y * 0.9) - this.objectHeight - 5);
        this.velocity = new Vector(-0.5, -1);
        this.hitbox = new Hitbox(this.position.xvalue, this.position.yvalue, this.objectWidth, this.objectHeight);
        
    }

    @Override
    public void render() {
        this.position.add(this.velocity);
        this.hitbox.centerPosition.add(this.velocity);
    }
}
