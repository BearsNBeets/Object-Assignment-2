/**
 * Adapted class from sample solution for SWEN20003 Object Oriented Software Development
 * (Project 1, Semester 2, 2019)
 *
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

/**
 * The type Sprite.
 */
public abstract class Sprite {
    /**
     * The constant imagesFolder.
     */
    public static final String imagesFolder = "res/";
    private Image image;
    private Rectangle rect;
    private Point point;

    /**
     * Instantiates a new Sprite.
     *
     * @param point    the point
     * @param imageSrc the image src
     */
    public Sprite(Point point, String imageSrc) {
        image = new Image(imageSrc);
        rect = image.getBoundingBoxAt(point);
        this.point = point;
    }

    /**
     * Gets rect.
     *
     * @return the rect
     */
    public Rectangle getRect() {
        return rect;
    }

    /**
     * Gets point.
     *
     * @return the point
     */
    public Point getPoint() {
        return point;
    }

    /**
     * In range boolean.
     *
     * @param range             the range
     * @param currentLocation   the current location
     * @param compareToLocation the compare to location
     * @return the boolean
     */
    // Check distance from one point to another
    public boolean inRange(int range, Point currentLocation, Point compareToLocation){
        double xCheck = compareToLocation.x - currentLocation.x;
        double yCheck = compareToLocation.y - currentLocation.y;
        if (xCheck < 0){
            xCheck = xCheck * -1;
        }
        if (yCheck < 0){
            yCheck = yCheck * -1;
        }
        return xCheck < range && yCheck < range;
    }

    /**
     * Intersects boolean.
     *
     * @param other the other
     * @return the boolean
     */
    // Check whether rectangles intersect
    public boolean intersects(Sprite other) {
        return rect.intersects(other.rect);
    }

    /**
     * Move.
     *
     * @param dx the dx
     */
    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    /**
     * Draw.
     */
    public void draw() {
        image.draw(rect.centre().x, rect.centre().y);
    }

    /**
     * Update.
     */
    public abstract void update();
}
