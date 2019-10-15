import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.Random;

/**
 * The type Powerup.
 */
public class Powerup extends Sprite {
    private Vector2 velocity;
    private Point destination;
    private Point start;
    private static final double SPEED = 3;

    /**
     * Instantiates a new Powerup.
     *
     * @param point       the point
     * @param destination the destination
     */
    public Powerup(Point point, Point destination) {
        super(point, "res/powerup.png");
        this.destination = destination;
        this.start = point;
        calculateVelocity();
    }

    // Calculate velocity of powerup
    private void calculateVelocity(){
        velocity = destination.asVector().sub(start.asVector()).mul(SPEED).normalised();
    }

    // Generate random point within bounds of window
    private Point randomPoint(){
        Random rand = new Random();
        int x = rand.nextInt(Window.getWidth());
        int y = rand.nextInt(Window.getHeight());
        return new Point(x,y);
    }

    /**
     * On collision powerup.
     *
     * @param balls      the balls
     * @param ballNumber the ball number
     * @return the powerup as removed
     */
    //When ball collides with powerup, change ball to fireball and add to arraylist of balls
    public Powerup onCollision(ArrayList<Ball> balls, int ballNumber) {
        Ball ball = balls.get(ballNumber);
        Point ballPoint = ball.getRect().centre();
        Vector2 ballVelocity = ball.getVelocity();
        balls.remove(ballNumber);
        Ball fireBall = new Ball(ballPoint, ballVelocity.normalised(), "fire");
        balls.add(ballNumber, fireBall);
        //Delete powerup when hit
        return null;
    }

    @Override
    public void update() {
        if (inRange(5, super.getRect().centre(), destination)){
            this.start = destination;
            this.destination = randomPoint();
            calculateVelocity();
        }
        super.move(velocity);
        super.draw();
    }
}
