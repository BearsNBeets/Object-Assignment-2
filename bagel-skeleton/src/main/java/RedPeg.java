import bagel.util.Point;

/**
 * The type Red peg.
 */
public class RedPeg extends Peg{

    /**
     * Instantiates a new Red peg.
     *
     * @param point the point of peg
     * @param shape the shape of peg
     */
    public RedPeg(Point point, String shape) {
        super(point, imagesFolder + "red"  + shape + srcEnd, shape);
    }

}
