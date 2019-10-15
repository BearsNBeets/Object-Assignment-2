import bagel.util.Point;

/**
 * The type Blue peg.
 */
public class BluePeg extends Peg{

    /**
     * Instantiates a new Blue peg.
     *
     * @param point the point of peg
     * @param shape the shape of peg
     */
    public BluePeg(Point point, String shape) {
        super(point, imagesFolder + "blue" + shape + srcEnd, shape);
    }


}
