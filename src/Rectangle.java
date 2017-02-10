
import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * 
 */
public class Rectangle extends Figure implements Serializable{
    private int width;
    private int height;

    /**
     *
     * @param width
     * @param height
     * @param color
     * @param x
     * @param y
     */
    public Rectangle(int width, int height, Color color, int x, int y) {
        super(color, x, y);
        this.width = width;
        this.height = height;
    }
    public void draw(Graphics graphics) {
        super.draw(graphics);
        graphics.fillRect(x, y,width, height);        
    }

    @Override
    public String toString() {
        return "Rectangle{x=" + x + ", y=" + y + ",width=" + width +
                ", height=" + height + ",color=" + color+'}';
    }  
    
    
    
    
    
}
