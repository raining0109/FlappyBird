package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Cloud control class,
 * control the generation logic of clouds and draw the clouds in the container
 */
public class CloudControl {
    private final List<Cloud> clouds;//Cloud container, traverse the container every time when draw
    private final BufferedImage[] cloudImages;
    private long time;//Record system time
    public static final int CLOUD_INTERVAL = 100;//Logical operation cycle of cloud refresh

    public CloudControl(){
        clouds = new ArrayList<>();//Initialize the container
        cloudImages = new BufferedImage[Constant.CLOUD_IMAGE_COUNT];
        for(int i=0;i<Constant.CLOUD_IMAGE_COUNT;i++){
            cloudImages[i] = Util.loadBufferedImage(Constant.CLOUDS_IMG_PATH[i]);
        }
        time = System.currentTimeMillis();//Get current time
    }

    public void draw(Graphics g,Bird bird){
        cloudBornLogic();
        //Traverse each cloud and draw
        for(Cloud cloud:clouds){
            cloud.draw(g,bird);
        }
    }

    //Cloud born logic
    private void cloudBornLogic(){
        //100ms born once
        if(System.currentTimeMillis() - time > CLOUD_INTERVAL){
            time = System.currentTimeMillis();//reset time
            /**
             * If the number of clouds on the screen is less than the maximum number allowed,
             * add clouds randomly according to the given probability
             */
            if(clouds.size() < Constant.MAX_CLOUD_COUNT){
                try{
                    if(Util.isInProbability(Constant.CLOUD_BORN_PERCENT,100)){
                        //select cloud image randomly
                        int index = Util.getRandomNumber(0,Constant.CLOUD_IMAGE_COUNT);

                        //The coordinates of the cloud refresh, refresh from the right side of the screen
                        int x = Constant.FRAME_WIDTH;
                        //The coordinates of the cloud refresh, the y coordinate has a range
                        int y = Util.getRandomNumber(Constant.TOP_BAR_HEIGHT,Constant.FRAME_HEIGHT/3);

                        //Add clouds to the container
                        Cloud cloud = new Cloud(x,y,cloudImages[index]);
                        clouds.add(cloud);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //Remove the cloud from the container if it flies off the screen
            clouds.removeIf(Cloud::isOutFrame);
        }
    }
}
