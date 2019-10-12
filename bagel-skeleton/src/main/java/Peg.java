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

    public Peg onCollision(ArrayList<Ball> balls, int ballNumber, Peg[] pegs) {
        Ball ball = balls.get(ballNumber);
        calculateNewVelocity(ball);
        // When fireball hits, calculate other balls also hit
        if (ball.getType().equals("fire")) {
            splashDamage(pegs);
        }
        // Delete peg from list of pegs on board
        return null;
    }

    // Calculate pegs surrounding hit peg to see whether they are within damage zone 70 pixels
    public void splashDamage(Peg[] pegs){
        int damageRange = 70;
        Point centralPoint = super.getRect().centre();
        for (int i = 0; i < pegs.length; i++){
            if (pegs[i] != null && !(pegs[i] instanceof GreyPeg)) {
                Point pegPoint = pegs[i].getPoint();
                if (inRange(damageRange, centralPoint, pegPoint)) {
                    pegs[i] = null;
                }
            }
        }
    }

    // Calculate new velocity of ball depending on side of collision into peg
    public void calculateNewVelocity(Ball ball){
        Vector2 velocity = ball.getVelocity();
        Rectangle pegRectangle = this.getRect();
        Rectangle ballRectangle = ball.getRect();
        // Check for which side the ball intersected with the peg and bounce accordingly
        if (pegRectangle.intersectedAt(ballRectangle.bottomLeft(), velocity).equals(Side.TOP)
                || pegRectangle.intersectedAt(ballRectangle.bottomRight(), velocity).equals(Side.TOP)
                || pegRectangle.intersectedAt(ballRectangle.topLeft(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.topRight(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.bottomLeft(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.bottomRight(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.topLeft(), velocity).equals(Side.TOP)
                || pegRectangle.intersectedAt(ballRectangle.topRight(), velocity).equals(Side.TOP)
        ){
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
