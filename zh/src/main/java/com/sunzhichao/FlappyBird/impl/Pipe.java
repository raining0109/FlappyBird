package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 普通水管类，实现水管的绘制与运动逻辑
 * @author sunzhichao
 */
public class Pipe extends AbstractBaseElement{
    static BufferedImage[] imgs;//水管的图片，static保证图片只加载一次

    //static代码块初始化图片加载
    static {
        final int PIPE_IMAGE_COUNT = 3;//一共三张水管图片
        imgs = new BufferedImage[PIPE_IMAGE_COUNT];
        for(int i = 0;i<PIPE_IMAGE_COUNT;i++){
            imgs[i] = Util.loadBufferedImage(Constant.PIPE_IMG_PATH[i]);
        }
    }

    // 所有水管的宽高
    public static final int PIPE_WIDTH = imgs[0].getWidth();//中间水管的宽度
    public static final int PIPE_HEIGHT = imgs[0].getHeight();//中间水管的高度
    public static final int PIPE_HEAD_WIDTH = imgs[1].getWidth();//水管头部较宽部分的宽度
    public static final int PIPE_HEAD_HEIGHT = imgs[1].getHeight();//水管头部较宽部分的高度

    boolean visible; // 水管可见状态，true为可见，false表示可归还至对象池

    // 水管的类型，共六种
    //包括顶部，悬浮，底部，每个类别又分为移动的和静止的（normal）
    int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_TOP_MOVING = 1;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_BOTTOM_MOVING = 3;
    public static final int TYPE_HOVER_NORMAL = 4;
    public static final int TYPE_HOVER_MOVING = 5;

    //构造函数
    public Pipe(){

        this.xSpeed = Constant.GAME_SPEED;//水管的移动速度
        this.width = PIPE_WIDTH;//水管的高度，是中间水管的高度
        this.rectangle = new Rectangle();//新建碰撞矩形的实例
        rectangle.width = PIPE_WIDTH;//将碰撞矩形的宽度设为中间水管的宽度
    }

    /**
     *设置水管的各个参数
     * @param x  x坐标（左上角)
     * @param y  y坐标（左上角）
     * @param height 水管高度
     * @param type  水管类型
     * @param visible  水管可见性
     */
    public void setAttribute(int x,int y,int height,int type,boolean visible){
        this.x = x;
        this.y = y;
        this.height = height;
        this.type = type;
        this.visible = visible;
        //设置矩形对象的参数
        setRectangle(this.x,this.y,this.height);
    }

    //设置矩形对象的参数
    private void setRectangle(int x,int y,int height) {
        rectangle.x = x;
        rectangle.y = y;
        rectangle.height = height;
    }

    /**
     * 判断水管参数是否可见
     * @return true：可见 false：不可见
     */
    public boolean isVisible(){ return visible; }

    public void draw(Graphics g,Bird bird){
        switch (type) {
            case TYPE_TOP_NORMAL:
                drawTop(g);
                break;
            case TYPE_BOTTOM_NORMAL:
                drawBottom(g);
                break;
            case TYPE_HOVER_NORMAL:
                drawHover(g);
                break;
        }
        if(bird.isDead()){
            return;
        }
        movement();//移动
    }

    //绘制从上往下的普通水管
    protected void drawTop(Graphics g){
        //计算拼接的个数
        int count = (height-PIPE_HEAD_HEIGHT)/PIPE_HEIGHT+1;
        //绘制水管的主体
        for(int i  =0; i < count; i++) {
            g.drawImage(imgs[0],x,y+i*PIPE_HEIGHT,null);
        }
        //绘制水管的底部
        g.drawImage(imgs[1],x -  ((PIPE_HEAD_WIDTH-width) / 2),height-Constant.TOP_PIPE_LENGTHENING-PIPE_HEAD_HEIGHT,null);
    }

    //绘制从下往上的普通水管
    protected void drawBottom(Graphics g){
        //拼接的个数
        int count = (height - PIPE_HEAD_HEIGHT-Constant.GROUND_HEIGHT)/PIPE_HEIGHT+1;
        //绘制水管的主体
        for(int i = 0;i<count;i++){
            g.drawImage(imgs[0],x,Constant.FRAME_HEIGHT - PIPE_HEIGHT - Constant.GROUND_HEIGHT - i * PIPE_HEIGHT,null);
        }
        //绘制水管的底部
        g.drawImage(imgs[2],x -  ((PIPE_HEAD_WIDTH-width) / 2),Constant.FRAME_HEIGHT - height,null);
    }

    //绘制悬浮的普通水管,只是制造其中的一个
    protected void drawHover(Graphics g){
        //拼接的个数
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        //绘制水管的上顶部
        g.drawImage(imgs[2],x - ((PIPE_HEAD_WIDTH - width) / 2), y,null);
        //绘制水管的主体
        for (int i = 0;i<count;i++){
            g.drawImage(imgs[0],x,y+i*PIPE_HEIGHT+PIPE_HEAD_HEIGHT,null);
        }
        //绘制水管的下底部
        int y = this.y+height - PIPE_HEAD_HEIGHT;
        g.drawImage(imgs[1],x-((PIPE_HEAD_WIDTH-width) / 2),y,null);
    }

    //普通水管的运动逻辑
    public void movement(){
        x -= xSpeed;
        rectangle.x -= xSpeed;
        if(x<-1*PIPE_HEAD_WIDTH){
            //水管完全离开了窗口
            visible = false;
        }
    }

    //判断当前水管是否完全出现在窗口
    public boolean isInFrame(){
        return x+width<Constant.FRAME_WIDTH;
    }

    //获取水管的X坐标
    public int getX(){
        return this.x;
    }

}
