import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * The type Green peg.
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

    /**
     * On peg collision calculates new ball velocity, creates new balls and returns state of peg as destroyed.
     *
     * @param balls      the balls
     * @param ballNumber the ball number
     * @param pegs       array of pegs
     * @return the peg
     */
    //Make new balls and destory peg when hit
    @Override
    public Peg onCollision(ArrayList<Ball> balls, int ballNumber, Peg[] pegs) {
        Ball ball = balls.get(ballNumber);
        calculateNewVelocity(ball);
        makeBalls(balls);

        //Delete peg from list of pegs on board
        return null;
    }

    /**
     * Make new balls at green peg location.
     *
     * @param balls the balls
     */
    // Generate 2 balls going in opposite directions of same type as collided ball
    public void makeBalls(ArrayList<Ball> balls){
        String type = balls.get(0).getType();
        Vector2 ballDirectionLeft = Vector2.up.add(Vector2.left);
        balls.add(new Ball(this.getPoint(), ballDirectionLeft.normalised(), type));
        Vector2 ballDirectionRight = Vector2.up.add(Vector2.right);
        balls.add(new Ball(this.getPoint(), ballDirectionRight.normalised(), type));
    }
}