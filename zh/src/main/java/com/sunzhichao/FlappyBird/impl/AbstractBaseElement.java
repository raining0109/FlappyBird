package com.sunzhichao.FlappyBird.impl;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *抽象类，游戏基本元素
 * @author sunzhichao
 */
public abstract class AbstractBaseElement{
    protected int x,y;//元素中心点的坐标
    protected int xSpeed,ySpeed;//元素在x轴与y轴方向的移动速度，左为正数，右为负数
    protected int width,height;//元素的宽度与高度
    protected BufferedImage image;//绘制图像
    protected Rectangle rectangle;//矩形对象，用来检测碰撞

    /**
     * 绘制方法
     * @param g 图片画笔
     * @param bird 小鸟实例
     */
    public void draw(Graphics g, Bird bird){
            g.drawImage(this.image,this.x,this.y,this.width,
                    this.height,null);
    }

    /**
     * 获得当前实例的矩形类
     * @return 矩形
     */
    public Rectangle getRectangle(){
        return this.rectangle;
    }


}
