import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

import java.util.ArrayList;
import java.util.Random;

public class Powerup extends Sprite {
    private Vector2 velocity;
    private Point destination;
    private Point start;
    private static final double SPEED = 3;


    public Powerup(Point point, Point destination) {
        super(point, "res/powerup.png");
        this.destination = destination;
        this.start = point;
        calculateVelocity();
    }

    private void calculateVelocity(){
        velocity = destination.asVector().sub(start.asVector()).mul(SPEED).normalised();
    }

    private Point randomPoint(){
        Random rand = new Random();
        int x = rand.nextInt(Window.getWidth());
        int y = rand.nextInt(Window.getHeight());
        return new Point(x,y);
    }

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

    private boolean inRange(int range, Point currentLocation, Point compareToLocation){
        double xCheck = compareToLocation.x - currentLocation.x;
        double yCheck = compareToLocation.y - currentLocation.y;
        if (xCheck < 0){
            xCheck = xCheck * -1;
        }
        if (yCheck < 0){
            yCheck = yCheck * -1;
        }
        if (xCheck < range && yCheck < range) {
            return true;
        }
        return false;
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
