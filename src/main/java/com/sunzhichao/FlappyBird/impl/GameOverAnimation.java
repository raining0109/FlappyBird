package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * GameOver Animation Class
 * @author sunzhichao
 */
public class GameOverAnimation {

    private final BufferedImage scoreImg;//Scoreboard picture
    private final BufferedImage overImg;//End picture
    private final BufferedImage againImg;//Continue picture

    public GameOverAnimation(){
        //Load picture
        overImg = Util.loadBufferedImage(Constant.OVER_IMG_PATH);
        scoreImg = Util.loadBufferedImage(Constant.SCORE_IMG_PATH);
        againImg = Util.loadBufferedImage(Constant.AGAIN_IMG_PATH);
    }

    private int flash = 0;//Photo flashing parameters

    public void draw(Graphics g, AbstractBird bird){

        int x = (Constant.FRAME_WIDTH - overImg.getWidth())/2;
        int y = Constant.FRAME_WIDTH/4;
        g.drawImage(overImg,x,y,null);

        //Draw the scoreboard
        x = (Constant.FRAME_WIDTH - scoreImg.getWidth())/2;
        y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(scoreImg,x,y,null);

        //Draw the score
        g.setColor(Color.white);
        g.setFont(Constant.SCORE_FONT);
        x = (Constant.FRAME_WIDTH - scoreImg.getWidth()/2)/2;
        y += scoreImg.getHeight()/2;
        String str = Long.toString(bird.getCurrentScore());
        x -= Util.getStringWidth(Constant.SCORE_FONT,str);
        y += Util.getStringHeight(Constant.SCORE_FONT,str)/2;
        g.drawString(str,x,y);

        //Draw the highest score image
        if(bird.getBestScore() > 0){
            str = Long.toString(bird.getBestScore());
            x = (Constant.FRAME_WIDTH + scoreImg.getWidth()/2)/2;
            y += Util.getStringWidth(Constant.SCORE_FONT,str)/2;
            g.drawString(str,x,y);
        }
        //Draw the continue image
        final int COUNT = 30;
        if (flash++ > COUNT)
            g.drawImage(againImg,(Constant.FRAME_WIDTH - againImg.getWidth()) / 2, Constant.FRAME_HEIGHT / 5 * 3,null);
        if (flash == COUNT * 2) // reset
            flash = 0;
    }
}
