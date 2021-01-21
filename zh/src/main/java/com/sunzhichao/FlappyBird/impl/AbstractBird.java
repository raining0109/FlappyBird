package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.app.Game;
import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 抽象小鸟类，实现小鸟的飞行逻辑与绘制
 * 该抽象类可以拥有不同的子类，从而实现多种小鸟角色
 * @author sunzhichao
 */
public abstract class AbstractBird extends AbstractBaseElement {

    public static final int IMG_COUNT = 8;//图片数量
    public static final int STATE_COUNT = 4;//状态数
    private final BufferedImage[][] birdImages;//小鸟的图片数组对象
    private int wingState;//翅膀状态

    //小鸟的状态
    private int state;
    public static final int BIRD_NORMAL = 0;
    public static final int BIRD_UP = 1;
    public static final int BIRD_FALL = 2;
    public static final int BIRD_DEAD_FALL = 3;
    public static final int BIRD_DEAD = 4;

    private final ScoreCounter counter;//计分器
    private final GameOverAnimation gameOverAnimation;//游戏结束动画类对象

    /**
     * 构造方法
     */
    public AbstractBird(){
        counter = ScoreCounter.getInstance();
        gameOverAnimation = new GameOverAnimation();
        //读取小鸟图片资源
        birdImages = new BufferedImage[STATE_COUNT][IMG_COUNT];
        for(int i = 0;i<STATE_COUNT;i++){
            for(int j=0;j<IMG_COUNT;j++){
                birdImages[i][j] = Util.loadBufferedImage(Constant.BIRD_IMG_PATH[i][j]);
            }
        }
        //初始化小鸟的高度和宽度
        assert birdImages[0][0] != null;
        this.width = birdImages[0][0].getWidth();
        this.height = birdImages[0][0].getHeight();
        this.x = Constant.FRAME_WIDTH/4;//初始位置中心坐标
        this.y = Constant.FRAME_HEIGHT/2;//初始位置中心坐标
        this.image = birdImages[0][0];
        //初始化碰撞矩形
        this.rectangle = new Rectangle(this.x-width/2,this.y-height/2,this.width,this.height);//初始化碰撞矩形
    }

    /**
     * 小鸟的绘制方法
     * @param g 画笔
     */
    public void draw(Graphics g) {
        movement();
        int state_index = Math.min(state,BIRD_DEAD_FALL);//死亡与坠落同一个图片
        // 小鸟中心点计算
        int halfImgWidth = birdImages[state_index][0].getWidth() / 2;
        int halfImgHeight = birdImages[state_index][0].getHeight() / 2;
        //ySpeed小于0，上升
        if(this.ySpeed < 0)
            image = birdImages[BIRD_UP][0];
        g.drawImage(image,x- halfImgWidth,y-halfImgHeight,null);

        //小鸟死亡
        if(state == BIRD_DEAD)
            gameOverAnimation.draw(g,this);
        //
        if(state != BIRD_DEAD_FALL)
            drawScore(g);
    }

    //有关速度与加速度的常量
    public static final int VEL_FLAP = 14;//振翅时速度
    public static final double ACC_Y = 2;//自由降落加速度
    public static final int MAX_VEL_Y = 15;//最大自由降落速度

    //地面的y坐标，判断，小鸟到达地面停止，不再下降
    private final int BOTTOM_BOUNDARY = Constant.FRAME_HEIGHT -GameBackground.GROUND_HEIGHT-this.height/2;


    //小鸟的运动方法
    public void movement() {
        wingState++;
        this.image = birdImages[Math.min(state,BIRD_DEAD_FALL)][wingState/10%IMG_COUNT];
        if(state == BIRD_FALL||state == BIRD_DEAD_FALL){
            freeFall();
            if(this.rectangle.y > BOTTOM_BOUNDARY){
                die();
            }
        }
    }

    //小鸟自由下落
    public void freeFall(){
        //模拟加速度，每次调用，速度加ACC_Y,最大为MAX_VEL_Y
        if(ySpeed<MAX_VEL_Y)
            ySpeed+=ACC_Y;
        y = Math.min((y+ySpeed),BOTTOM_BOUNDARY);//小鸟不能低于地面，落地后停止下降
        this.rectangle.y += ySpeed;//更新碰撞矩形
    }

    //小鸟死亡
    public void die() {
        counter.saveScore();
        //state = BIRD_NORMAL;
        state = BIRD_DEAD;
        Game.setGameState(Constant.STATE_OVER);
    }

    //小鸟振翅上升
    public void birdFlap() {
        if(keyIsReleased()){
            if(isDead())
                return;
            state = BIRD_UP;
            if(this.rectangle.y > Constant.TOP_BAR_HEIGHT){
                ySpeed = -VEL_FLAP;//振翅后更新速度，上为负数
                wingState = 0;//重置翅膀状态
            }
            keyPressed();//
        }
    }

    //小鸟下降
    public void birdFall() {
        if(isDead())
            return;
        state = BIRD_FALL;
    }
    //小鸟死亡下降
    public void deadBirdFall() {
        state = BIRD_DEAD_FALL;
        ySpeed = 0;
    }

    //判断小鸟是否死亡
    public boolean isDead() {
        return state == BIRD_DEAD_FALL || state == BIRD_DEAD;
    }

    //绘制实时分数(不是最终)
    public void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
        String str = Long.toString(counter.getCurrentScore());
        int x = Constant.FRAME_WIDTH/2;
        int y = Constant.FRAME_HEIGHT/10;
        g.drawString(str,x,y);
    }

    //重置小鸟
    public void reset() {
        state = BIRD_NORMAL;//重置小鸟状态
        y = Constant.FRAME_HEIGHT/2; //小鸟坐标
        ySpeed = 0; //小鸟速度

        int imgHeight = birdImages[state][0].getHeight();
        this.rectangle.y = y - imgHeight/2;//重置矩形对象坐标
        counter.reset();//重置计分器
    }

    /**
     * 按键的逻辑
     *    首先，keyFlag初始值是true，然后第一次按下，走到birdFlap方法，因为初始值就是ture，所以振翅
     *    一次然后，振翅完毕，方法keyPressed()将keyFlag设置为false，所以之后就不会再振翅了，
     *    松开手之后，会再次调用keyReleased方法使得keyFlag为true。
     *    到目前为止，所有的状态与按键前一致，重新进入循环。
     * 下面是一些关于按键的操作方法
     */

    private boolean keyFlag = true;//按键状态，true为已经释放，避免重复调用方法

    public void keyPressed(){
        keyFlag = false;
    }

    public void keyReleased(){
        keyFlag = true;
    }
    //判断按键是否释放
    public boolean keyIsReleased(){ return keyFlag;}

    public long getCurrentScore(){
        return counter.getCurrentScore();
    }

    public long getBestScore(){
        return counter.getBestScore();
    }
}
