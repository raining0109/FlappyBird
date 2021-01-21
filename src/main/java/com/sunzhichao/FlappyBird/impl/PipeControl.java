package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Pipe control logic class,
 * Pipe born and draw
 * @author sunzhichao
 */
public class PipeControl {

    private final List<Pipe> pipes;//Pipe container

    public PipeControl(){
        pipes = new ArrayList<>();
    }

    public void draw(Graphics g,Bird bird){
        //Traverse the pipe container, draw if it is visible,
        // return it if it is not visible
        for(int i= 0;i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            if(pipe.isVisible()){
                pipe.draw(g,bird);
            } else {
                Pipe remove = pipes.remove(i);
                PipePool.giveBack(remove);
                i--;//Otherwise one will not be traversed
            }
        }
        isCollideBird(bird);
        pipeBornLogic(bird);
    }

    public static final int VERTICAL_INTERVAL = Constant.FRAME_HEIGHT/5;
    public static final int HORIZONTAL_INTERVAL = Constant.FRAME_HEIGHT/4;

    //The shortest height and the longest height
    public static final int MIN_HEIGHT = Constant.FRAME_HEIGHT/8;
    public static final int MAX_HEIGHT = Constant.FRAME_HEIGHT/8*5;

    private void pipeBornLogic(Bird bird) {
        if (bird.isDead()) {
            //No more pipes if the bird dies
            return;
        }
        if (pipes.size() == 0) {
            //If the container is empty, add a pair of pipes
            int topHeight = Util.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); //Randomly generate pipe height

            Pipe top = PipePool.get("Pipe");
            top.setAttribute(Constant.FRAME_WIDTH, -Constant.TOP_PIPE_LENGTHENING,
                    topHeight + Constant.TOP_PIPE_LENGTHENING, Pipe.TYPE_TOP_NORMAL, true);

            Pipe bottom = PipePool.get("Pipe");
            bottom.setAttribute(Constant.FRAME_WIDTH, topHeight + VERTICAL_INTERVAL,
                    Constant.FRAME_HEIGHT - topHeight - VERTICAL_INTERVAL, Pipe.TYPE_BOTTOM_NORMAL, true);

            pipes.add(top);
            pipes.add(bottom);
        } else {
            Pipe lastPipe = pipes.get(pipes.size() - 1);

            if (lastPipe.isInFrame()) {
                /**
                 * If the pipes in the window are full, 
                 * it means that the bird has flown to the position of the first pair of pipes and starts to score
                 */
                if (pipes.size() >= PipePool.FULL_PIPE - 2)
                    ScoreCounter.getInstance().score(bird);
                try {
                    int currentScore = (int) ScoreCounter.getInstance().getCurrentScore() + 1;
                    /**
                     * The probability of the moving pipe being refreshed increases with the current score.
                     * When the score is greater than 19, all moving pipes are refreshed.
                     */
                    if (Util.isInProbability(currentScore, 20)) {
                        if (Util.isInProbability(1, 4)) // Probability of generating moving pipes and moving hover pipes
                            addMovingHoverPipe(lastPipe);
                        else
                            addMovingNormalPipe(lastPipe);
                    } else {
                        if (Util.isInProbability(1, 2)) // Probability of generating normal pipes and normal hover pipes
                            addNormalPipe(lastPipe);
                        else
                            addHoverPipe(lastPipe);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*The following is how to add various pipes
    *****************************************
     */
    private void addNormalPipe(Pipe lastPipe){
        int topHeight = Util.getRandomNumber(MIN_HEIGHT,MAX_HEIGHT+1);//Randomly generate pipe height
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL;//The x coordinate of the new pipe = the x coordinate of the last pair of pipes + the interval between the pipes

        Pipe top = PipePool.get("Pipe");

        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING,topHeight+Constant.TOP_PIPE_LENGTHENING,Pipe.TYPE_TOP_NORMAL,true);

        Pipe bottom = PipePool.get("Pipe");
        bottom.setAttribute(x,topHeight+VERTICAL_INTERVAL,Constant.FRAME_HEIGHT-topHeight-VERTICAL_INTERVAL,Pipe.TYPE_BOTTOM_NORMAL,true);

        pipes.add(top);
        pipes.add(bottom);
    }

    private void addHoverPipe(Pipe lastPipe){

        //Randomly generate the height of the pipe, [1/4,1/6] of the screen height
        int topHoverHeight = Util.getRandomNumber(Constant.FRAME_HEIGHT/6,Constant.FRAME_HEIGHT/4);
        int x =lastPipe.getX() + HORIZONTAL_INTERVAL;
        int y = Util.getRandomNumber(Constant.FRAME_HEIGHT/12,Constant.FRAME_HEIGHT/6);

        //Generate the top hover pipe
        Pipe topHover = PipePool.get("Pipe");
        topHover.setAttribute(x,y,topHoverHeight,Pipe.TYPE_HOVER_NORMAL,true);

        //Generate the bottom hover pipe
        int bottomHoverHeight = Constant.FRAME_HEIGHT -2*y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("Pipe");
        bottomHover.setAttribute(x,y+topHoverHeight+VERTICAL_INTERVAL,bottomHoverHeight,Pipe.TYPE_HOVER_NORMAL,true);
        pipes.add(topHover);
        pipes.add(bottomHover);
    }

    private void addMovingHoverPipe(Pipe lastPipe){

        int topHoverHeight = Util.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; //The x coordinate of the new pipe = the x coordinate of the last pair of pipes + the interval between the pipes
        int y = Util.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6); // 随机水管的y坐标，窗口的[1/6,1/12]


        //Generate the top hover pipe
        Pipe topHover = PipePool.get("MovingPipe");
        topHover.setAttribute(x, y, topHoverHeight, Pipe.TYPE_HOVER_MOVING, true);

        //Generate the bottom hover pipe
        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("MovingPipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, Pipe.TYPE_HOVER_MOVING, true);

        pipes.add(topHover);
        pipes.add(bottomHover);
    }

    private void addMovingNormalPipe(Pipe lastPipe){
        int topHeight = Util.getRandomNumber(MIN_HEIGHT,MAX_HEIGHT+1);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL;

        Pipe top = PipePool.get("MovingPipe");

        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING,topHeight+Constant.TOP_PIPE_LENGTHENING,Pipe.TYPE_TOP_MOVING,true);

        Pipe bottom = PipePool.get("MovingPipe");
        bottom.setAttribute(x,topHeight+VERTICAL_INTERVAL,Constant.FRAME_HEIGHT-topHeight-VERTICAL_INTERVAL,Pipe.TYPE_BOTTOM_MOVING,true);

        pipes.add(top);
        pipes.add(bottom);
    }

    /*
    *********************************
    * The above are the ways to add various pipes
    * */

    public void isCollideBird(Bird bird){
        if(bird.isDead()){
            return;
        }
        //Traverse the pipe container (complete quickly, in an instant)
        for(Pipe pipe:pipes){
            if(pipe.getRectangle().intersects(bird.getRectangle())){
                bird.deadBirdFall();
                return;
            }
        }
    }
    
    public void reset(){
        for(Pipe pipe:pipes){
            PipePool.giveBack(pipe);
        }
        pipes.clear();
    }
}
