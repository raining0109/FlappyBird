package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Cloud class, realize the drawing method of clouds
 * @author sunzhichao
 */
public class Cloud extends AbstractBaseElement {

    /**
     * The scale of clouds,
     * used to draw clouds of different sizes
     */
    private final int scaleImageWidth;
    private final int getScaleImageHeight;

    /**
     * Constructor
     * @param x The x coordinate of the upper left of the cloud
     * @param y The y coordinate of the upper left of the cloud
     * @param img Cloud photo
     */
    public Cloud(int x,int y,BufferedImage img){
        this.x = x;
        this.y = y;
        this.image = img;
        this.xSpeed = Constant.GAME_SPEED*2;//The speed of clouds is twice the speed of the game
        this.ySpeed = 0;//The vertical velocity of the cloud is 0
        //Scale of cloud picture 1.0~2.0
        double scale = 1 + Math.random();
        scaleImageWidth = (int)(scale * img.getWidth());
        getScaleImageHeight = (int)(scale * img.getHeight());
    }

    @Override
    public void draw(Graphics g, Bird bird) {
        int speed = this.xSpeed;
        //If the bird dies, the cloud slows down
        if(bird.isDead())
            speed =1;
        x -= speed;
        g.drawImage(this.image, x, y, scaleImageWidth,getScaleImageHeight,null);
    }

    /**
     * Determine if the clouds fly off the screen
     * @return true:fly off; false:not
     */
    public boolean isOutFrame(){
        return x < -1 * scaleImageWidth;
    }
}