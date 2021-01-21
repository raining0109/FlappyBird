package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;

import java.awt.*;

/**
 * MovingPipe refer to pipes that automatically move up and down, making the game more difficult
 * @author sunzhichao
 */
public class MovingPipe extends Pipe{

    private int dealtY;//the downward distance of the pipe
    public static final int MAX_DELTA = 50;//max downward distance
    private int direction;//The direction to move: up or down

    //Direction parameter
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;

    //Constructor
    public MovingPipe(){ super();}

    public void setAttribute(int x,int y,int height,int type,boolean visible){
        super.setAttribute(x,y,height,type,visible);
        dealtY = 0;
        //For the upper water pipe, the direction is down, and the initial direction is down
        //For the water pipe below, the direction is down, and the initial direction is also down
        direction = DIR_DOWN;
    }

    public void draw(Graphics g,Bird bird){
        switch (type){
            case TYPE_HOVER_MOVING:
                drawHover(g);
                break;
            case TYPE_TOP_MOVING:
                drawTop(g);
                break;
            case TYPE_BOTTOM_MOVING:
                drawBottom(g);
                break;
        }
        //The water pipe stopped moving after the bird died
        if(bird.isDead()) {
            return;
        }
        movement();
    }

 //Draw moving hover pipe
protected void drawHover(Graphics g) {
    //Count the number of splicing
    int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
    //Draw the top of the pipe
    g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
    //Draw the main body of the pipe
    for (int i = 0; i < count; i++) {
        g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
    }
    //Draw the bottom of the pipe
    int y = this.y + height - PIPE_HEAD_HEIGHT;
    g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
}

    //Draw moving pipes from top to bottom
    protected void drawTop(Graphics g) {
        //Count the number of splicing
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // 取整+1
        //Draw the main body of the pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT, null);
        }
        //Draw the bottom of the pipe
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) / 2),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT + dealtY, null);
    }

    //Draw moving pipes from bottom to top
    protected void drawBottom(Graphics g) {
        //Count the number of splicing
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        //Draw the main body of the pipe
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT + dealtY, null);
        }
        //Draw the top of the pipe
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) / 2), Constant.FRAME_HEIGHT - height + dealtY, null);
    }


    public void movement(){
        x -= xSpeed;
        rectangle.x -= xSpeed;
        if(x< -1*PIPE_HEAD_WIDTH) {
            visible = false;
        }

        //Up and down movement method of moving pipe
        if(direction == DIR_DOWN){
            dealtY++;
            if(dealtY > MAX_DELTA){
                direction = DIR_UP;
            }
        } else {
            dealtY--;
            if(dealtY <= 0){
                direction =DIR_DOWN;
            }
        }
        rectangle.y = this.y + dealtY;
    }
}
