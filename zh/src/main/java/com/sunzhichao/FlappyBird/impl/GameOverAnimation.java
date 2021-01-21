package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 游戏结束动画类
 * @author sunzhichao
 */
public class GameOverAnimation {

    private final BufferedImage scoreImg;//计分牌
    private final BufferedImage overImg;//结束标志
    private final BufferedImage againImg;//继续标志

    public GameOverAnimation(){
        //载入图片
        overImg = Util.loadBufferedImage(Constant.OVER_IMG_PATH);
        scoreImg = Util.loadBufferedImage(Constant.SCORE_IMG_PATH);
        againImg = Util.loadBufferedImage(Constant.AGAIN_IMG_PATH);
    }

    private static final int SCORE_LOCATE = 5;//计分牌位置
    private int flash = 0;//图片闪烁参数

    public void draw(Graphics g, AbstractBird bird){

        int x = (Constant.FRAME_WIDTH - overImg.getWidth())/2;
        int y = Constant.FRAME_WIDTH/4;
        g.drawImage(overImg,x,y,null);

        //绘制计分牌
        x = (Constant.FRAME_WIDTH - scoreImg.getWidth())/2;
        y = Constant.FRAME_HEIGHT / 3;
        g.drawImage(scoreImg,x,y,null);

        //绘制本局的分数
        g.setColor(Color.white);
        g.setFont(Constant.SCORE_FONT);
        x = (Constant.FRAME_WIDTH - scoreImg.getWidth()/2)/2;
        y += scoreImg.getHeight()/2;
        String str = Long.toString(bird.getCurrentScore());
        x -= Util.getStringWidth(Constant.SCORE_FONT,str);
        y += Util.getStringHeight(Constant.SCORE_FONT,str)/2;
        g.drawString(str,x,y);

        //绘制最高分数
        if(bird.getBestScore() > 0){
            str = Long.toString(bird.getBestScore());
            x = (Constant.FRAME_WIDTH + scoreImg.getWidth()/2)/2;
            y += Util.getStringWidth(Constant.SCORE_FONT,str)/2;
            g.drawString(str,x,y);
        }
        //绘制继续游戏，图像闪烁
        final int COUNT = 30; // 闪烁周期
        if (flash++ > COUNT)
            g.drawImage(againImg,(Constant.FRAME_WIDTH - againImg.getWidth()) / 2, Constant.FRAME_HEIGHT / 5 * 3,null);
        if (flash == COUNT * 2) // 重置闪烁参数
            flash = 0;
    }
}
