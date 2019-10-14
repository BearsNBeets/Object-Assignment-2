/**
 * Sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 *
 * @author Eleanor McMurtry
 */

import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;

public abstract class Sprite {
    public static final String imagesFolder = "res/";
    private Image image;
    private Rectangle rect;
    private Point point;

    public Sprite(Point point, String imageSrc) {
        image = new Image(imageSrc);
        rect = image.getBoundingBoxAt(point);
        this.point = point;
    }

    public Rectangle getRect() {
        return rect;
    }

    public Point getPoint() {
        return point;
    }

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

    // Check whether rectangles intersect
    public boolean intersects(Sprite other) {
        return rect.intersects(other.rect);
    }

    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    public void draw() {
        image.draw(rect.centre().x, rect.centre().y);
    }

    public abstract void update();
}
