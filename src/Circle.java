
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * 
 */
public class Circle extends Figure {
    private int radius;

    public void draw(Graphics graphics) {
        super.draw(graphics);
        graphics.fillOval(x-radius, y-radius, 2*radius, 2*radius);        
    }

    /**
     *
     * @param radius
     * @param color
     * @param x
     * @param y
     */
    public Circle(int radius, Color color, int x, int y) {
        super(color, x, y);
        this.radius = radius;
    }
    @Override
    public String toString() {
        return "Circle(" + x + ", y=" + y +", radius=" + radius+
                ",color=" + color+'}';
    }  
    
    

}
