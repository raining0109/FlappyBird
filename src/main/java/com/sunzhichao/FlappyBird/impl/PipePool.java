package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Water pipe object pool
 * In order to avoid repeated creation and destruction of objects, use the object pool to create some objects in advance
 * Take it away from here when using it and return it after use
 */
public class PipePool {
    private static final List<Pipe> pool = new ArrayList<>();
    private static final List<MovingPipe> movingPool = new ArrayList<>();
    public static final int MAX_PIPE_COUNT = 30;//The maximum number of objects in the object pool
    public static final int FULL_PIPE = (Constant.FRAME_WIDTH
            / (Pipe.PIPE_HEAD_WIDTH + PipeControl.HORIZONTAL_INTERVAL) + 2) * 2;

    static {
        for(int i = 0;i<PipePool.FULL_PIPE;i++){
            pool.add(new Pipe());
        }
        for(int i = 0;i<PipePool.FULL_PIPE;i++){
            movingPool.add(new MovingPipe());
        }
    }

    /**
     * Get an object from the object pool
     * @param className class name
     * @return Pile instance
     */
    public static Pipe get(String className){
        if("Pipe".equals(className)){
            int size = pool.size();
            if(size > 0){
                return pool.remove(size - 1);
            } else {
                return new Pipe();
            }
        } else {
            int size = movingPool.size();
            if(size > 0){
                return movingPool.remove(size - 1);
            } else {
                return new MovingPipe();
            }
        }
    }

    /**
     * give back the object to the container
     * @param pipe Objects to be given back
     */
    public static void giveBack(Pipe pipe){
        if(pipe.getClass() == Pipe.class){
            if(pool.size() <MAX_PIPE_COUNT){
                pool.add(pipe);
            }
        } else {
            if(movingPool.size() < MAX_PIPE_COUNT){
                movingPool.add((MovingPipe) pipe);
            }
        }
    }
}


