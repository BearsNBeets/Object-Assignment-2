import bagel.Image;
import bagel.util.Point;

public class RedPeg extends Peg{

    private Image image;

    public RedPeg(Point point, String shape) {
        super(point, imagesFolder + "red"  + shape + srcEnd, shape);
    }

}
