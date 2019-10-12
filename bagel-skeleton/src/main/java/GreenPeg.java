import bagel.Image;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;

public class GreenPeg extends Peg{

    public GreenPeg(Point point, String shape) {
        super(point, imagesFolder + "green" + shape + srcEnd, shape);
    }

    @Override
    public Peg onCollision(ArrayList<Ball> balls, int ballNumber) {
        Ball ball = balls.get(ballNumber);
        calculateNewVelocity(ball);

        makeBalls(balls);

        //Delete peg from list of pegs on board
        return null;
    }

    public void makeBalls(ArrayList<Ball> balls){
        String type = balls.get(0).getType();
        Vector2 ballDirectionLeft = new Vector2(-1,1);
        balls.add(new Ball(this.getPoint(), ballDirectionLeft.normalised(), type));
        Vector2 ballDirectionRight = new Vector2(1,1);
        balls.add(new Ball(this.getPoint(), ballDirectionRight.normalised(), type));
    }

}