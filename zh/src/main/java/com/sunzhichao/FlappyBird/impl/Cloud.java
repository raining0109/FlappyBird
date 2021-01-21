package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 云朵类，实现云朵的绘制方法
 * @author sunzhichao
 */
public class Cloud extends AbstractBaseElement {

    //云朵的缩放比例
    private final int scaleImageWidth;
    private final int getScaleImageHeight;

    /**
     * 云朵类的构造方法
     * @param x 云朵左上方的 x 坐标
     * @param y 云朵左上方的 y 坐标
     * @param img 云朵图片
     */
    public Cloud(int x,int y,BufferedImage img){
        this.x = x;
        this.y = y;
        this.image = img;
        this.xSpeed = Constant.GAME_SPEED*2;//云朵的速度为游戏速度的二倍
        this.ySpeed = 0;//云朵的垂直速度为0
        //云朵图片缩放的比例1.0~2.0
        double scale = 1 + Math.random();
        scaleImageWidth = (int)(scale * img.getWidth());
        getScaleImageHeight = (int)(scale * img.getHeight());
    }

    @Override
    public void draw(Graphics g, Bird bird) {
        int speed = this.xSpeed;
        //如果小鸟死亡，云朵速度减缓
        if(bird.isDead())
            speed =1;
        x -= speed;
        g.drawImage(this.image, x, y, scaleImageWidth,getScaleImageHeight,null);
    }

    /**
     * 判断云朵是否飞出屏幕
     * @return true:飞出;false:未飞出
     */
    public boolean isOutFrame(){
        return x < -1 * scaleImageWidth;
    }
}