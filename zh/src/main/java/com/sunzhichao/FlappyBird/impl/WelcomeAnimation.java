package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 游戏启动界面
 * @author sunzhichao
 */
public class WelcomeAnimation {

    private final BufferedImage titleImg;
    private final BufferedImage noticeImg;

    private int flashCount = 0;//图像闪烁参数

    public WelcomeAnimation(){
        titleImg = Util.loadBufferedImage(Constant.TITLE_IMG_PATH);
        noticeImg = Util.loadBufferedImage(Constant.NOTICE_IMG_PATH);
    }

    /**
     * 绘制图像
     * @param g 画笔
     */
    public void draw(Graphics g){
        int x = (Constant.FRAME_WIDTH - titleImg.getWidth()) / 2;
        int y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(titleImg,x,y,null);

        //使notice的头像闪烁
        final int CYCLE = 30;//闪烁周期
        if(flashCount++>CYCLE){
            g.drawImage(noticeImg,(Constant.FRAME_WIDTH - noticeImg.getWidth())/2,Constant.FRAME_HEIGHT/5*3,null);
        }
        //置零后形成循环周期
        if(flashCount == CYCLE * 2){
            flashCount = 0;
        }
    }
}
