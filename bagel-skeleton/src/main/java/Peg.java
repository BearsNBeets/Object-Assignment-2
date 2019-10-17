import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Side;
import bagel.util.Vector2;

import java.util.ArrayList;

/**
 * The parent type of all the pegs available in Shadow Bounce game
 * Adapted class from sample solution for SWEN20003 Object Oriented Software Development
 * (Project 1, Semester 2, 2019)
 */
public class Peg extends Sprite {
    /**
     * The constant ending string of each peg file-name.
     */
    public static final String srcEnd = "peg.png";

    private String shape;

    /**
     * Instantiates a new Peg.
     *
     * @param point    the point
     * @param imageSrc the image source
     * @param shape    the shape
     */
    public Peg(Point point, String imageSrc, String shape) {
        super(point, imageSrc);
        this.shape = shape;
    }

    /**
     * On peg collision calculates new ball velocity, destroys affected pegs and returns state of peg.
     *
     * @param balls      the balls
     * @param ballNumber the ball number
     * @param pegs       array of pegs
     * @return the peg
     */
    // Default behaviour when collision occurs to bounce ball and destroy peg
    public Peg onCollision(ArrayList<Ball> balls, int ballNumber, Peg[] pegs) {
        Ball ball = balls.get(ballNumber);
        calculateNewVelocity(ball);
        // When fireball hits, calculate other balls also hit
        if (ball.getType().equals("fire")) {
            splashDamage(balls, pegs);
        }
        // Delete peg from list of pegs on board
        return null;
    }

    /**
     *
     * @param balls balls on screen
     * @return destoryed peg as null
     */
    public Peg indirectCollision(ArrayList<Ball> balls){
        return null;
    }

    /**
     * Calculate pegs surrounding hit peg by fireball to see whether they are within damage zone 70 pixels
     * and destroy them.
     *
     * @param pegs array of pegs
     */
    public void splashDamage(ArrayList<Ball> balls, Peg[] pegs){
        int damageRange = 70;
        Point centralPoint = super.getRect().centre();
        for (int i = 0; i < pegs.length; i++){
            //Skip non-existent pegs and grey pegs
            if (pegs[i] != null && !(pegs[i] instanceof GreyPeg)) {
                Point pegPoint = pegs[i].getPoint();
                //Check distance from hit peg
                if (inRange(damageRange, centralPoint, pegPoint)) {
                    pegs[i] = pegs[i].indirectCollision(balls);
                }
            }
        }
    }

    /**
     * Calculate new velocity.
     *
     * @param ball the ball
     */
    // Calculate new velocity of ball depending on side of collision into peg
    public void calculateNewVelocity(Ball ball){
        Vector2 velocity = ball.getVelocity();
        Rectangle pegRectangle = this.getRect();
        Rectangle ballRectangle = ball.getRect();
        // Check for which side the ball intersected with the peg and bounce accordingly using corners of rectangle
        if (pegRectangle.intersectedAt(ballRectangle.bottomLeft(), velocity).equals(Side.TOP)
                || pegRectangle.intersectedAt(ballRectangle.bottomRight(), velocity).equals(Side.TOP)
                || pegRectangle.intersectedAt(ballRectangle.topLeft(), velocity).equals(Side.BOTTOM)
                || pegRectangle.intersectedAt(ballRectangle.topRight(), velocity).equals(Side.BOTTOM)
                // Following checks are for when ball travels into peg to try and counter unusual bounce
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

    /**
     * Gets shape of peg.
     *
     * @return the shape
     */
    public String getShape() {
        return shape;
    }
}
