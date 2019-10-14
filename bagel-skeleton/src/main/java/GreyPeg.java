import bagel.Image;
import bagel.util.Point;
import bagel.util.Side;
import bagel.util.Vector2;

import java.util.ArrayList;

public class GreyPeg extends Peg{

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
