import bagel.Image;
import bagel.util.Point;

public class GreenPeg extends Peg{

    public GreenPeg(Point point) {
        super(point, "res/green-peg.png");
    }

    @Override
    public Peg onCollision(Ball ball) {
        calculateNewVelocity(ball);

        //Delete peg from list of pegs on board
        return null;
    }

}