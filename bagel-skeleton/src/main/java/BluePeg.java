import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Side;
import bagel.util.Vector2;

public class BluePeg extends Peg{



    public BluePeg(Point point, String shape) {
        super(point, imagesFolder + "blue" + shape + srcEnd, shape);
    }


}
