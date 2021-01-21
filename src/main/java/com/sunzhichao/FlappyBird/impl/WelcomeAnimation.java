package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Welcome Animation
 * @author sunzhichao
 */
public class WelcomeAnimation {

    private final BufferedImage titleImg;
    private final BufferedImage noticeImg;

    private int flashCount = 0;//Image flicker parameters

    public WelcomeAnimation(){
        titleImg = Util.loadBufferedImage(Constant.TITLE_IMG_PATH);
        noticeImg = Util.loadBufferedImage(Constant.NOTICE_IMG_PATH);
    }

    public void draw(Graphics g){
        int x = (Constant.FRAME_WIDTH - titleImg.getWidth()) / 2;
        int y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(titleImg,x,y,null);

        //flash
        final int CYCLE = 30;//Flashing period
        if(flashCount++>CYCLE){
            g.drawImage(noticeImg,(Constant.FRAME_WIDTH - noticeImg.getWidth())/2,Constant.FRAME_HEIGHT/5*3,null);
        }
        //Make a cycle after setting to zero
        if(flashCount == CYCLE * 2){
            flashCount = 0;
        }
    }
}
