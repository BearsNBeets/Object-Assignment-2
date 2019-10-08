import bagel.Image;
import bagel.util.Point;
import bagel.util.Side;
import bagel.util.Vector2;

public class GreyPeg extends Peg{
    public GreyPeg(Point point) {
        super(point, "res/grey-peg.png");
    }


    @Override
    public Peg onCollision(Ball ball) {
        calculateNewVelocity(ball);
        //Delete peg from list of pegs on board
        update();
        return this;
    }
}
