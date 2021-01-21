package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * 云朵控制类，控制云朵的生成逻辑并绘制容器中的云朵
 */
public class CloudControl {
    private final List<Cloud> clouds;//云朵的容器，每次绘制时遍历容器
    private final BufferedImage[] cloudImages;//图片资源
    private long time;//控制云朵的逻辑运算周期
    public static final int CLOUD_INTERVAL = 100;//云朵刷新的逻辑运算周期

    public CloudControl(){
        clouds = new ArrayList<>();//新建容器
        //读取图片资源
        cloudImages = new BufferedImage[Constant.CLOUD_IMAGE_COUNT];
        for(int i=0;i<Constant.CLOUD_IMAGE_COUNT;i++){
            cloudImages[i] = Util.loadBufferedImage(Constant.CLOUDS_IMG_PATH[i]);
        }
        time = System.currentTimeMillis();//获取当前时间
    }

    //绘制方法
    public void draw(Graphics g,Bird bird){
        //生成云朵Cloud对象
        cloudBornLogic();
        //遍历每一个云朵，进行绘制
        for(Cloud cloud:clouds){
            cloud.draw(g,bird);
        }
    }

    //云朵的生成逻辑
    private void cloudBornLogic(){
        //100ms运算一次
        if(System.currentTimeMillis() - time > CLOUD_INTERVAL){
            time = System.currentTimeMillis();//重置time
            //如果屏幕的云朵小于允许的最大数量，则根据给定的概率随机添加云朵
            if(clouds.size() < Constant.MAX_CLOUD_COUNT){
                try{
                    if(Util.isInProbability(Constant.CLOUD_BORN_PERCENT,100)){
                        //随机选取云朵图片
                        int index = Util.getRandomNumber(0,Constant.CLOUD_IMAGE_COUNT);

                        //云朵刷新的坐标，从屏幕右侧开始刷新
                        int x = Constant.FRAME_WIDTH;
                        //云朵刷新的坐标，y坐标有一个范围
                        int y = Util.getRandomNumber(Constant.TOP_BAR_HEIGHT,Constant.FRAME_HEIGHT/3);

                        //向容器中添加云朵
                        Cloud cloud = new Cloud(x,y,cloudImages[index]);
                        clouds.add(cloud);//新建Cloud实例后添加至clouds容器
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            //如果云朵飞出屏幕则从容器中移除
            clouds.removeIf(Cloud::isOutFrame);

        }
    }
}
