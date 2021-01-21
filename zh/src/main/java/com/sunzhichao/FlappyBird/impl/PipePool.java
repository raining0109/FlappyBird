package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * 水管对象池
 * 为了避免反复创建与销毁对象，使用对象池提前创建好一些对象
 * 使用时从这里取走，使用后归还
 */
public class PipePool {
    private static final List<Pipe> pool = new ArrayList<>();
    private static final List<MovingPipe> movingPool = new ArrayList<>();
    public static final int MAX_PIPE_COUNT = 30;//对象池中的最大对象数量
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
     * 从对象池中获取一个对象
     * @param className 传入类名
     * @return 水管对象
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
     * 归还对象给容器
     * @param pipe 需要归还的对象
     */
    public static void giveBack(Pipe pipe){
        //判断对象的类型
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


