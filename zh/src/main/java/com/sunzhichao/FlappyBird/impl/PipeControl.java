package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;
import com.sunzhichao.FlappyBird.util.Util;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 水管控制逻辑类，水管的生成与绘制
 * @author sunzhichao
 */
public class PipeControl {

    private final List<Pipe> pipes;//水管的容器

    //构造方法
    public PipeControl(){
        pipes = new ArrayList<>();
    }

    //绘制方法
    public void draw(Graphics g,Bird bird){
        //遍历水管容器，如果可见则绘制，不可见则归还
        //可见性还需要另行描述
        for(int i= 0;i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            if(pipe.isVisible()){
                pipe.draw(g,bird);
                //System.out.println("pipe.draw(g,bird)方法被调用了");
            } else {
                Pipe remove = pipes.remove(i);
                PipePool.giveBack(remove);
                i--;//继续检测，不然少一个未遍历
            }
        }
        //碰撞检测
        isCollideBird(bird);
//        for(Pipe pipe:pipes){
//            if(pipe.getX() == bird.x){
//                ScoreCounter.getInstance().score(bird);
//            }
//        }
        pipeBornLogic(bird);
    }

    public static final int VERTICAL_INTERVAL = Constant.FRAME_HEIGHT/5;//垂直间隔
    public static final int HORIZONTAL_INTERVAL = Constant.FRAME_HEIGHT/4;//水平间隔

    //最短高度与最长高度
    public static final int MIN_HEIGHT = Constant.FRAME_HEIGHT/8;
    public static final int MAX_HEIGHT = Constant.FRAME_HEIGHT/8*5;

    private void pipeBornLogic(Bird bird) {
        if (bird.isDead()) {
            //小鸟死后不再添加水管
            return;
        }
        if (pipes.size() == 0) {
            // 若容器为空，则添加一对水管
            int topHeight = Util.getRandomNumber(MIN_HEIGHT, MAX_HEIGHT + 1); // 随机生成水管高度

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
                if (pipes.size() >= PipePool.FULL_PIPE - 2)// 若窗口中可容纳的水管已满，说明小鸟已飞到第一对水管的位置，开始记分
                    ScoreCounter.getInstance().score(bird);
                try {
                    int currentScore = (int) ScoreCounter.getInstance().getCurrentScore() + 1; // 获取当前分数
                    // 移动水管刷新的概率随当前分数递增，当得分大于19后全部刷新移动水管
                    if (Util.isInProbability(currentScore, 20)) {
                        if (Util.isInProbability(1, 4)) // 生成移动水管和移动悬浮水管的概率
                            addMovingHoverPipe(lastPipe);
                        else
                            addMovingNormalPipe(lastPipe);
                    } else {
                        if (Util.isInProbability(1, 2)) // 生成静止普通水管和静止悬浮水管的概率
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

    /*下面为各种水管的添加方式
    *****************************************
     */
    private void addNormalPipe(Pipe lastPipe){
        int topHeight = Util.getRandomNumber(MIN_HEIGHT,MAX_HEIGHT+1);//随机生成水管高度
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL;// 新水管的x坐标 = 最后一对水管的x坐标 + 水管的间隔

        Pipe top = PipePool.get("Pipe");

        //设置属性
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING,topHeight+Constant.TOP_PIPE_LENGTHENING,Pipe.TYPE_TOP_NORMAL,true);

        Pipe bottom = PipePool.get("Pipe");
        bottom.setAttribute(x,topHeight+VERTICAL_INTERVAL,Constant.FRAME_HEIGHT-topHeight-VERTICAL_INTERVAL,Pipe.TYPE_BOTTOM_NORMAL,true);

        //加入容器中
        pipes.add(top);
        pipes.add(bottom);
    }

    //添加悬浮水管
    private void addHoverPipe(Pipe lastPipe){

        //随机生成水管的高度，屏幕高度的[1/4,1/6]
        int topHoverHeight = Util.getRandomNumber(Constant.FRAME_HEIGHT/6,Constant.FRAME_HEIGHT/4);
        int x =lastPipe.getX() + HORIZONTAL_INTERVAL;
        int y = Util.getRandomNumber(Constant.FRAME_HEIGHT/12,Constant.FRAME_HEIGHT/6);

        //生成上部的悬浮水管
        Pipe topHover = PipePool.get("Pipe");
        topHover.setAttribute(x,y,topHoverHeight,Pipe.TYPE_HOVER_NORMAL,true);

        //生成下部的悬浮水管
        int bottomHoverHeight = Constant.FRAME_HEIGHT -2*y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("Pipe");
        bottomHover.setAttribute(x,y+topHoverHeight+VERTICAL_INTERVAL,bottomHoverHeight,Pipe.TYPE_HOVER_NORMAL,true);
        //添加进入容器
        pipes.add(topHover);
        pipes.add(bottomHover);
    }

    //添加移动的悬浮水管
    private void addMovingHoverPipe(Pipe lastPipe){

        int topHoverHeight = Util.getRandomNumber(Constant.FRAME_HEIGHT / 6, Constant.FRAME_HEIGHT / 4);
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL; // 新水管的x坐标 = 最后一对水管的x坐标 + 水管的间隔
        int y = Util.getRandomNumber(Constant.FRAME_HEIGHT / 12, Constant.FRAME_HEIGHT / 6); // 随机水管的y坐标，窗口的[1/6,1/12]


        // 生成上部的悬浮水管
        Pipe topHover = PipePool.get("MovingPipe");
        topHover.setAttribute(x, y, topHoverHeight, Pipe.TYPE_HOVER_MOVING, true);

        // 生成下部的悬浮水管
        int bottomHoverHeight = Constant.FRAME_HEIGHT - 2 * y - topHoverHeight - VERTICAL_INTERVAL;
        Pipe bottomHover = PipePool.get("MovingPipe");
        bottomHover.setAttribute(x, y + topHoverHeight + VERTICAL_INTERVAL, bottomHoverHeight, Pipe.TYPE_HOVER_MOVING, true);

        pipes.add(topHover);
        pipes.add(bottomHover);
    }

    //添加移动的普通水管
    private void addMovingNormalPipe(Pipe lastPipe){
        int topHeight = Util.getRandomNumber(MIN_HEIGHT,MAX_HEIGHT+1);//随机生成水管高度
        int x = lastPipe.getX() + HORIZONTAL_INTERVAL;// 新水管的x坐标 = 最后一对水管的x坐标 + 水管的间隔

        Pipe top = PipePool.get("MovingPipe");

        //设置属性
        top.setAttribute(x, -Constant.TOP_PIPE_LENGTHENING,topHeight+Constant.TOP_PIPE_LENGTHENING,Pipe.TYPE_TOP_MOVING,true);

        Pipe bottom = PipePool.get("MovingPipe");
        bottom.setAttribute(x,topHeight+VERTICAL_INTERVAL,Constant.FRAME_HEIGHT-topHeight-VERTICAL_INTERVAL,Pipe.TYPE_BOTTOM_MOVING,true);

        //加入容器中
        pipes.add(top);
        pipes.add(bottom);
    }

    /*
    *********************************
    * 以上为各种水管的添加方式
    * */

    public void isCollideBird(Bird bird){
        //如果鸟已经死亡则不再判断
        if(bird.isDead()){
            return;
        }
        //遍历水管容器（很快完成，一瞬间）
        for(Pipe pipe:pipes){
            if(pipe.getRectangle().intersects(bird.getRectangle())){
                bird.deadBirdFall();
                return;
            }
        }
    }

    //重置水管
    public void reset(){
        for(Pipe pipe:pipes){
            PipePool.giveBack(pipe);
        }
        pipes.clear();
    }
}
