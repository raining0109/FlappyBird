package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.app.Game;
import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Abstract Birds Class, realize the flying logic and drawing of birds
 * This abstract class can be extended to achieve multiple bird roles
 * @author sunzhichao
 */
public abstract class AbstractBird extends AbstractBaseElement {

    public static final int IMG_COUNT = 8;
    public static final int STATE_COUNT = 4;
    private final BufferedImage[][] birdImages;
    private int wingState;

    //the state of bird
    private int state;
    public static final int BIRD_NORMAL = 0;
    public static final int BIRD_UP = 1;
    public static final int BIRD_FALL = 2;
    public static final int BIRD_DEAD_FALL = 3;
    public static final int BIRD_DEAD = 4;

    private final ScoreCounter counter;
    private final GameOverAnimation gameOverAnimation;

    /**
     * Constructor
     */
    public AbstractBird(){
        counter = ScoreCounter.getInstance();
        gameOverAnimation = new GameOverAnimation();
        //Read bird image resources
        birdImages = new BufferedImage[STATE_COUNT][IMG_COUNT];
        for(int i = 0;i<STATE_COUNT;i++){
            for(int j=0;j<IMG_COUNT;j++){
                birdImages[i][j] = Util.loadBufferedImage(Constant.BIRD_IMG_PATH[i][j]);
            }
        }
        //Initialize the height and width of the bird
        assert birdImages[0][0] != null;
        this.width = birdImages[0][0].getWidth();
        this.height = birdImages[0][0].getHeight();
        this.x = Constant.FRAME_WIDTH/4;//Initial position center coordinates
        this.y = Constant.FRAME_HEIGHT/2;//Initial position center coordinates
        this.image = birdImages[0][0];
        //Initialize the collision rectangle
        this.rectangle = new Rectangle(this.x-width/2,this.y-height/2,this.width,this.height);//初始化碰撞矩形
    }

    /**
     * the function to draw the bird
     * @param g "brush"
     */
    public void draw(Graphics g) {
        movement();
        /**
         * birdFall and deadBirdFall are the same photo
         */
        int state_index = Math.min(state,BIRD_DEAD_FALL);
        int halfImgWidth = birdImages[state_index][0].getWidth() / 2;
        int halfImgHeight = birdImages[state_index][0].getHeight() / 2;
        if(this.ySpeed < 0)
            image = birdImages[BIRD_UP][0];
        g.drawImage(image,x- halfImgWidth,y-halfImgHeight,null);

        //bird is dead
        if(state == BIRD_DEAD)
            gameOverAnimation.draw(g,this);
        //
        if(state != BIRD_DEAD_FALL)
            drawScore(g);
    }

    //Constants related to speed and acceleration
    public static final int VEL_FLAP = 14;//Speed when flapping wings
    public static final double ACC_Y = 2;//Free fall acceleration
    public static final int MAX_VEL_Y = 15;//Maximum free fall speed

    /**
     * The y coordinate of the ground,
     * used to judge the bird to reach the ground and stop it
     */
    private final int BOTTOM_BOUNDARY = Constant.FRAME_HEIGHT -GameBackground.GROUND_HEIGHT-this.height/2;


    //the method to describe the movement of the bird
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

    //Bird free fall
    public void freeFall(){
        /**
         * Simulate acceleration,
         * each call, speed plus ACC_Y, the maximum is MAX_VEL_Y
         */
        if(ySpeed<MAX_VEL_Y)
            ySpeed+=ACC_Y;
        y = Math.min((y+ySpeed),BOTTOM_BOUNDARY);//The bird can't fall below the ground
        this.rectangle.y += ySpeed;//Update collision rectangle
    }

    //Bird is died
    public void die() {
        counter.saveScore();
        state = BIRD_DEAD;
        Game.setGameState(Constant.STATE_OVER);
    }

    //Bird flap
    public void birdFlap() {
        if(keyIsReleased()){
            if(isDead())
                return;
            state = BIRD_UP;
            if(this.rectangle.y > Constant.TOP_BAR_HEIGHT){
                ySpeed = -VEL_FLAP;//Update speed after flapping wings, upper is negative
                wingState = 0;//Reset wings status
            }
            keyPressed();
        }
    }

    //bird fall
    public void birdFall() {
        if(isDead())
            return;
        state = BIRD_FALL;
    }
    //dead bird fall
    public void deadBirdFall() {
        state = BIRD_DEAD_FALL;
        ySpeed = 0;
    }

    //Determine if the bird is dead
    public boolean isDead() {
        return state == BIRD_DEAD_FALL || state == BIRD_DEAD;
    }

    //Plot real-time scores (not final)
    public void drawScore(Graphics g) {
        g.setColor(Color.white);
        g.setFont(Constant.CURRENT_SCORE_FONT);
        String str = Long.toString(counter.getCurrentScore());
        int x = Constant.FRAME_WIDTH/2;
        int y = Constant.FRAME_HEIGHT/10;
        g.drawString(str,x,y);
    }

    //reset bird
    public void reset() {
        state = BIRD_NORMAL;
        y = Constant.FRAME_HEIGHT/2;
        ySpeed = 0;

        int imgHeight = birdImages[state][0].getHeight();
        this.rectangle.y = y - imgHeight/2;
        counter.reset();
    }

    /**
     * The logic of the button:
     * First, the initial value of keyFlag is true, and then the first time you press it,
     * go to the birdFlap method, because the initial value is true, so flap the wings
     * Once the flapping is completed, the method keyPressed() sets the keyFlag to false,
     * so there will be no flapping again.
     * After releasing the hand, the keyReleased method will be called again to make keyFlag true.
     * So far, all the states are the same as before the button is pressed,
     * and the cycle is re-entered.
     * The following are some operations on the buttons:
     */

    private boolean keyFlag = true;

    public void keyPressed(){
        keyFlag = false;
    }

    public void keyReleased(){
        keyFlag = true;
    }

    public boolean keyIsReleased(){ return keyFlag;}

    public long getCurrentScore(){
        return counter.getCurrentScore();
    }

    public long getBestScore(){
        return counter.getBestScore();
    }
}
