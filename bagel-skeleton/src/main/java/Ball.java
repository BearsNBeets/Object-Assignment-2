import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

/**
 * The object Ball which bounces around the screen and hits pegs.
 * Adapted class from sample solution for SWEN20003 Object Oriented Software Development
 * (Project 1, Semester 2, 2019)
 */
public class Ball extends Sprite {
    private Vector2 velocity;
    private static final double GRAVITY = 0.15;
    private static final double SPEED = 10;
    private String type;
    private boolean bucketHit = false;

    /**
     * Instantiates a new Ball.
     *
     * @param point     the point
     * @param direction the direction
     * @param type      the type
     */
    public Ball(Point point, Vector2 direction, String type) {
        super(point, imagesFolder + type + "ball.png");
        velocity = direction.mul(SPEED);
        this.type = type;
    }

    /**
     * Check whether rectangle of ball is past the bottom of the screen
     *
     * @return the boolean
     */
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

    /**
     * Sets velocity.
     *
     * @param velocity the velocity
     */
    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }

    /**
     * Gets velocity.
     *
     * @return the velocity
     */
    public Vector2 getVelocity() {
        return velocity;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * Return whether ball has hit bucket already
     *
     * @return the boolean
     */
    public boolean isBucketHit() {
        return bucketHit;
    }

    /**
     * Sets hit bucket boolean value if ball has hit bucket on way out of screen
     *
     * @param bucketHit the hit bucket
     */
    public void setBucketHit(boolean bucketHit) {
        this.bucketHit = bucketHit;
    }
}
