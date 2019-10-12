/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Side;
import bagel.util.Vector2;

import java.util.ArrayList;

public class Peg extends Sprite {
    public static final String srcEnd = "peg.png";

    private String shape;

    public Peg(Point point, String imageSrc, String shape) {
        super(point, imageSrc);
        this.shape = shape;
    }

    public Peg onCollision(ArrayList<Ball> balls, int ballNumber) {
        Ball ball = balls.get(ballNumber);
        calculateNewVelocity(ball);
        //Delete peg from list of pegs on board
        return null;
    }

    //Calculate new velocity of ball depending on side of collision into peg
    public void calculateNewVelocity(Ball ball){
        Vector2 velocity = ball.getVelocity();
        Rectangle pegRectangle = this.getRect();
        Rectangle ballRectangle = ball.getRect();
        //Check for which side the ball intersected with the peg and bounce accordingly
        if ((pegRectangle.intersectedAt(ballRectangle.bottomLeft(), velocity)).equals(Side.TOP)
                || pegRectangle.intersectedAt(ballRectangle.bottomRight(), velocity).equals(Side.TOP)
                || pegRectangle.intersectedAt(ballRectangle.bottomLeft(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.bottomRight(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.topLeft(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.topRight(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.topLeft(), velocity).equals(Side.TOP)
                || pegRectangle.intersectedAt(ballRectangle.topRight(), velocity).equals(Side.TOP)){
            ball.setVelocity(new Vector2(velocity.x, -velocity.y));
        } else {
            ball.setVelocity(new Vector2(-velocity.x, velocity.y));
        }
    }

    @Override
    public void update() {
        super.draw();
    }

    public String getShape() {
        return shape;
    }
}
