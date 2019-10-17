import bagel.util.Point;

import java.util.ArrayList;

/**
 * The object Grey peg.
 */
public class GreyPeg extends Peg{

    /**
     * Instantiates a new Grey peg.
     *
     * @param point the location of peg
     * @param shape the peg
     */
    public GreyPeg(Point point, String shape) {
        super(point, imagesFolder + "grey"  + shape + srcEnd, shape);
    }

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
