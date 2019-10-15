import bagel.util.Point;

import java.util.ArrayList;

/**
 * The type Grey peg.
 */
public class GreyPeg extends Peg{

    /**
     * Instantiates a new Grey peg.
     *
     * @param point the point
     * @param shape the shape
     */
    public GreyPeg(Point point, String shape) {
        super(point, imagesFolder + "grey"  + shape + srcEnd, shape);
    }

    /**
     * On peg collision calculates new ball velocity and peg as same peg.
     *
     * @param balls      the balls
     * @param ballNumber the ball number
     * @param pegs       array of pegs
     * @return the peg
     */
    @Override
    // Indestructable peg; bounce ball only
    public Peg onCollision(ArrayList<Ball> balls, int ballNumber, Peg[] pegs) {
        Ball ball = balls.get(ballNumber);
        calculateNewVelocity(ball);
        //Delete peg from list of pegs on board
        update();
        return this;
    }

}
