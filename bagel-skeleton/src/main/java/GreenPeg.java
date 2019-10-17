import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * The object Green peg which generates two balls at location of peg if hit by ball.
 */
public class GreenPeg extends Peg{

    /**
     * Instantiates a new Green peg.
     *
     * @param point the point
     * @param shape the shape
     */
    public GreenPeg(Point point, String shape) {
        super(point, imagesFolder + "green" + shape + srcEnd, shape);
    }

    //On peg collision calculates new ball velocity, creates new balls and returns state of peg as destroyed.
    @Override
    public Peg onCollision(ArrayList<Ball> balls, int ballNumber, Peg[] pegs) {
        Ball ball = balls.get(ballNumber);
        calculateNewVelocity(ball);
        makeBalls(balls);
        //Delete peg from list of pegs on board
        return null;
    }

    /**
     * Generate 2 balls going in opposite directions of same type as collided ball
     *
     * @param balls arraylist of ball that destroyed green peg
     */
    public void makeBalls(ArrayList<Ball> balls){
        String type = balls.get(0).getType();
        Vector2 ballDirectionLeft = Vector2.up.add(Vector2.left);
        balls.add(new Ball(this.getPoint(), ballDirectionLeft.normalised(), type));
        Vector2 ballDirectionRight = Vector2.up.add(Vector2.right);
        balls.add(new Ball(this.getPoint(), ballDirectionRight.normalised(), type));
    }

    @Override
    public Peg indirectCollision(ArrayList<Ball> balls){
        this.makeBalls(balls);
        return null;
    }
}