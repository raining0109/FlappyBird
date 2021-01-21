package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 游戏背景类，包括地面和背景天空的绘制与移动
 * @author sunzhichao
 */
public class GameBackground {
    private static final BufferedImage backgroundImg;//背景图片

    private final int speed;//背景层的速度
    private int layerX;//背景层的坐标

    public static final int GROUND_HEIGHT;//地面高度

    static {
        backgroundImg = Util.loadBufferedImage(Constant.BG_IMG_PATH);
        assert backgroundImg != null;//断言backgroundImg 不为空
        GROUND_HEIGHT = backgroundImg.getHeight()/2;
    }

    /**
     * 构造方法
     * 初始化背景层运动速度及绘制起点
     */
    public GameBackground(){
        this.speed = Constant.GAME_SPEED;
        this.layerX = 0;
    }

    /**
     * 绘制方法
     * @param g 画笔
     * @param bird 小鸟实例
     */
    public void draw(Graphics g,Bird bird){
        //绘制背景颜色
        g.setColor(Constant.BG_COLOR);
        g.fillRect(0,0,Constant.FRAME_WIDTH,Constant.FRAME_HEIGHT);

        //获取背景图片的尺寸
        int imgWidth = backgroundImg.getWidth();
        int imgHeight = backgroundImg.getHeight();

        int count = Constant.FRAME_WIDTH/imgWidth +2;//需要绘制的次数
        for(int i=0;i<count;i++){
            g.drawImage(backgroundImg,imgWidth*i-layerX,Constant.FRAME_HEIGHT-imgHeight,null);
        }

        if(bird.isDead()){
            return;//小鸟死亡则不再绘制
        }
        movement();
    }

    /**
     * 背景层的运动逻辑
     * 改变绘制的起点，造成的视觉效果就是在移动
     */
    private void movement(){
        layerX +=speed;
        if(layerX > backgroundImg.getWidth()){
            layerX = 0;
        }
    }
}
