package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Game background class
 * draw method and movement of the ground and sky
 * @author sunzhichao
 */
public class GameBackground {
    private static final BufferedImage backgroundImg;

    private final int speed;//Background layer speed
    private int layerX;//Background layer coordinates

    public static final int GROUND_HEIGHT;

    static {
        backgroundImg = Util.loadBufferedImage(Constant.BG_IMG_PATH);
        assert backgroundImg != null;
        GROUND_HEIGHT = backgroundImg.getHeight()/2;
    }

    /**
     * Constructor
     * Initialize the movement speed of the background layer
     * and the starting point to draw it
     */
    public GameBackground(){
        this.speed = Constant.GAME_SPEED;
        this.layerX = 0;
    }

    /**
     * draw method
     * @param g "brush"
     * @param bird bird instance
     */
    public void draw(Graphics g,Bird bird){
        //Draw background color
        g.setColor(Constant.BG_COLOR);
        g.fillRect(0,0,Constant.FRAME_WIDTH,Constant.FRAME_HEIGHT);

        //Get the size of the background image
        int imgWidth = backgroundImg.getWidth();
        int imgHeight = backgroundImg.getHeight();

        int count = Constant.FRAME_WIDTH/imgWidth +2;//Number of times to draw
        for(int i=0;i<count;i++){
            g.drawImage(backgroundImg,imgWidth*i-layerX,Constant.FRAME_HEIGHT-imgHeight,null);
        }

        if(bird.isDead()){
            return;//Don't draw if the bird dies
        }
        movement();
    }

    /**
     * the movement logic of the background layer
     * Change the starting point of drawing and as a result that the visual effect is moving
     */
    private void movement(){
        layerX +=speed;
        if(layerX > backgroundImg.getWidth()){
            layerX = 0;
        }
    }
}
