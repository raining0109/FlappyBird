package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;

import java.io.*;

/**
 * 分数计数器
 * 由于ScoreCounter全局唯一，不能有多个，所以使用了单例模式
 * 简单来说，单例模式就是保证一个类仅有一个实例，并提供一个访问它的全局访问点。
 * @author sunzhichao
 */
public class ScoreCounter {

    private static class ScoreCounterHolder{
        private static final ScoreCounter scoreCounter = new ScoreCounter();
    }
    public static ScoreCounter getInstance(){
        return ScoreCounterHolder.scoreCounter;
    }

    private long score = 0;//分数
    private long bestScore;//最高分数

    /**
     * 私有构造方法
     */
    private ScoreCounter(){
        bestScore = -1;
        try {
            loadBestScore();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 从文件中读取最高分数，并放在bestScore变量中
     */
    private void loadBestScore() throws IOException {
        File file = new File(Constant.SCORE_FILE_PATH);
        if(file.exists()){
            DataInputStream dis = new DataInputStream(new FileInputStream(file));
            bestScore = dis.readLong();
            dis.close();
        }
    }

    /**
     * 将最高成绩保存在文件中
     */
    public void saveScore(){
        bestScore = Math.max(bestScore,getCurrentScore());
        try {
            File file = new File(Constant.SCORE_FILE_PATH);
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(file));
            dos.writeLong(bestScore);
            dos.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 分数加一
     * @param bird 小鸟实例
     */
    public void score(Bird bird){
        if(!bird.isDead()){
            score += 1;
        }
    }

    public long getBestScore(){
        return bestScore;
    }

    public long getCurrentScore(){
        return score;
    }

    public void reset(){
        score = 0;
    }
}
