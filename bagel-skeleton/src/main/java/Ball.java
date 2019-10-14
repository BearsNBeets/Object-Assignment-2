/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

public class Ball extends Sprite {
    private Vector2 velocity;
    private static final double GRAVITY = 0.15;
    private static final double SPEED = 10;
    private String type;

    public Ball(Point point, Vector2 direction, String type) {
        super(point, imagesFolder + type + "ball.png");
        velocity = direction.mul(SPEED);
        this.type = type;
    }

    // Check whether rectangle of ball is past the bottom of the screen
    public boolean outOfScreen() {
        return super.getRect().top() > Window.getHeight();
    }

    @Override
    public void update() {
        velocity = velocity.add(Vector2.down.mul(GRAVITY));
        super.move(velocity);

        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }

        super.draw();
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public String getType() {
        return type;
    }
}
