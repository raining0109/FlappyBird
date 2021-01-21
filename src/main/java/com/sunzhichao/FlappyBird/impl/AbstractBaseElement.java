package com.sunzhichao.FlappyBird.impl;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The abstract class for basic game elements
 * @author sunzhichao
 */
public abstract class AbstractBaseElement{
    protected int x,y;//The coordinates of the center point of the element
    /**
     * The moving speed of the element in the x-axis and y-axis directions,
     * the left is a positive number, and the right is a negative number
     */
    protected int xSpeed,ySpeed;
    protected int width,height;
    protected BufferedImage image;
    protected Rectangle rectangle;//Rectangle object, used to detect collision

    /**
     * draw method
     * @param g "Picture brush"
     * @param bird bird object
     */
    public void draw(Graphics g, Bird bird){
            g.drawImage(this.image,this.x,this.y,this.width,
                    this.height,null);
    }

    /**
     * Get the current rectangle instance
     * @return rectangle instance
     */
    public Rectangle getRectangle(){
        return this.rectangle;
    }


}
