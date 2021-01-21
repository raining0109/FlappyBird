package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * normal water pipes,
 * realize the drawing and movement logic of water pipes
 * @author sunzhichao
 */
public class Pipe extends AbstractBaseElement{
    /**
     * the photo of pipe,
     * static guarantees that the image is only loaded once
     */
    static BufferedImage[] imgs;

    static {
        final int PIPE_IMAGE_COUNT = 3;
        imgs = new BufferedImage[PIPE_IMAGE_COUNT];
        for(int i = 0;i<PIPE_IMAGE_COUNT;i++){
            imgs[i] = Util.loadBufferedImage(Constant.PIPE_IMG_PATH[i]);
        }
    }

    // Width and height of all water pipes
    public static final int PIPE_WIDTH = imgs[0].getWidth();//Width of middle water pipe
    public static final int PIPE_HEIGHT = imgs[0].getHeight();//Height of the middle water pipe
    public static final int PIPE_HEAD_WIDTH = imgs[1].getWidth();//Width of the wider part of the pipe head
    public static final int PIPE_HEAD_HEIGHT = imgs[1].getHeight();//Height of the wider part of the pipe head
    /**
     * The visible state of the water pipe, true means visible,
     * false means it can be returned to the object pool
     */
    boolean visible;

    // There are six types of water pipes
    //Including top, hover, bottom,
    // each category is divided into moving and static (normal)
    int type;
    public static final int TYPE_TOP_NORMAL = 0;
    public static final int TYPE_TOP_MOVING = 1;
    public static final int TYPE_BOTTOM_NORMAL = 2;
    public static final int TYPE_BOTTOM_MOVING = 3;
    public static final int TYPE_HOVER_NORMAL = 4;
    public static final int TYPE_HOVER_MOVING = 5;

    public Pipe(){

        this.xSpeed = Constant.GAME_SPEED;
        this.width = PIPE_WIDTH;
        this.rectangle = new Rectangle();
        rectangle.width = PIPE_WIDTH;
    }

    /**
     *Set parameters of the water pipe
     * @param x  x coordinate (upper left corner)
     * @param y  y coordinate (upper left corner)
     * @param height Pipe height
     * @param type  Pipe type
     * @param visible  Visibility of pipes
     */
    public void setAttribute(int x,int y,int height,int type,boolean visible){
        this.x = x;
        this.y = y;
        this.height = height;
        this.type = type;
        this.visible = visible;

        setRectangle(this.x,this.y,this.height);
    }

    private void setRectangle(int x,int y,int height) {
        rectangle.x = x;
        rectangle.y = y;
        rectangle.height = height;
    }

    /**
     * Determine whether the pipe are visible
     * @return true：visible false：not
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
        movement();
    }

    //Draw normal pipes from top to bottom
    protected void drawTop(Graphics g){
        //Count the number of splicing
        int count = (height-PIPE_HEAD_HEIGHT)/PIPE_HEIGHT+1;
        //Draw the main body of the pipe
        for(int i  =0; i < count; i++) {
            g.drawImage(imgs[0],x,y+i*PIPE_HEIGHT,null);
        }
        //Draw the bottom of the pipe
        g.drawImage(imgs[1],x -  ((PIPE_HEAD_WIDTH-width) / 2),height-Constant.TOP_PIPE_LENGTHENING-PIPE_HEAD_HEIGHT,null);
    }

    //Draw normal pipes from bottom to top
    protected void drawBottom(Graphics g){
        //Count the number of splicing
        int count = (height - PIPE_HEAD_HEIGHT-Constant.GROUND_HEIGHT)/PIPE_HEIGHT+1;
        //Draw the main body of the pipe
        for(int i = 0;i<count;i++){
            g.drawImage(imgs[0],x,Constant.FRAME_HEIGHT - PIPE_HEIGHT - Constant.GROUND_HEIGHT - i * PIPE_HEIGHT,null);
        }
        //Draw the top of the pipe
        g.drawImage(imgs[2],x -  ((PIPE_HEAD_WIDTH-width) / 2),Constant.FRAME_HEIGHT - height,null);
    }

    //Draw normal pipes suspended,
    // just make one of them
    protected void drawHover(Graphics g){
        //Count the number of splicing
        int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        //Draw the top of the pipe
        g.drawImage(imgs[2],x - ((PIPE_HEAD_WIDTH - width) / 2), y,null);
        //Draw the main body of the pipe
        for (int i = 0;i<count;i++){
            g.drawImage(imgs[0],x,y+i*PIPE_HEIGHT+PIPE_HEAD_HEIGHT,null);
        }
        //Draw the bottom of the pipe
        int y = this.y+height - PIPE_HEAD_HEIGHT;
        g.drawImage(imgs[1],x-((PIPE_HEAD_WIDTH-width) / 2),y,null);
    }

    //Movement logic of normal water pipes
    public void movement(){
        x -= xSpeed;
        rectangle.x -= xSpeed;
        if(x<-1*PIPE_HEAD_WIDTH){
            //The pipe completely left the window
            visible = false;
        }
    }

    //Determine whether the current pipe completely appears in the window
    public boolean isInFrame(){
        return x+width<Constant.FRAME_WIDTH;
    }

    //Get the X coordinate of the pipe
    public int getX(){
        return this.x;
    }

}
