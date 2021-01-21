package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;

import java.awt.*;

/**
 * 移动水管类，指悬浮的会自动上下移动的水管，使游戏难度更大
 * @author sunzhichao
 */
public class MovingPipe extends Pipe{

    private int dealtY;//移动水管的下移距离
    public static final int MAX_DELTA = 50;//最大移动距离
    private int direction;//上下移动的方向,

    //方向参数
    public static final int DIR_UP = 0;
    public static final int DIR_DOWN = 1;

    //构造方法
    public MovingPipe(){ super();}

    //设置水管的类型，包括是上还是下
    public void setAttribute(int x,int y,int height,int type,boolean visible){
        super.setAttribute(x,y,height,type,visible);
        dealtY = 0;
        //对于上面的水管，direction为下，初始方向向下
        //对于下面的水管，direction为下，初始方向也是向下
        direction = DIR_DOWN;
    }

    //绘制方法
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
        //小鸟死后水管停止移动
        if(bird.isDead()) {
            return;
        }
        movement();
    }

 //绘制移动的悬浮水管
protected void drawHover(Graphics g) {
    // 拼接的个数
    int count = (height - 2 * PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
    // 绘制水管的上顶部
    g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
    // 绘制水管的主体
    for (int i = 0; i < count; i++) {
        g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT + PIPE_HEAD_HEIGHT, null);
    }
    // 绘制水管的下底部
    int y = this.y + height - PIPE_HEAD_HEIGHT;
    g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) >> 1), y + dealtY, null);
}

    // 绘制从上往下的移动水管
    protected void drawTop(Graphics g) {
        // 拼接的个数
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1; // 取整+1
        // 绘制水管的主体
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, y + dealtY + i * PIPE_HEIGHT, null);
        }
        // 绘制水管的顶部
        g.drawImage(imgs[1], x - ((PIPE_HEAD_WIDTH - width) / 2),
                height - Constant.TOP_PIPE_LENGTHENING - PIPE_HEAD_HEIGHT + dealtY, null);
    }

    // 绘制从下往上的移动水管
    protected void drawBottom(Graphics g) {
        // 拼接的个数
        int count = (height - PIPE_HEAD_HEIGHT) / PIPE_HEIGHT + 1;
        // 绘制水管的主体
        for (int i = 0; i < count; i++) {
            g.drawImage(imgs[0], x, Constant.FRAME_HEIGHT - PIPE_HEIGHT - i * PIPE_HEIGHT + dealtY, null);
        }
        // 绘制水管的顶部
        g.drawImage(imgs[2], x - ((PIPE_HEAD_WIDTH - width) / 2), Constant.FRAME_HEIGHT - height + dealtY, null);
    }


    public void movement(){
        //左右移动，判断是否出界,同时改变x的碰撞矩形值
        x -= xSpeed;
        rectangle.x -= xSpeed;
        if(x< -1*PIPE_HEAD_WIDTH) {
            visible = false;
        }

        //移动水管类自身的上下移动方法
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
