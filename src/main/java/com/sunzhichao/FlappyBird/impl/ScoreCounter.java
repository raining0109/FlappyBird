package com.sunzhichao.FlappyBird.impl;

import com.sunzhichao.FlappyBird.util.Constant;

import java.io.*;

/**
 * Score counter class
 * Since ScoreCounter is globally unique and cannot have more than one,
 * the Singleton Pattern is used
 * Simply put, the Singleton Pattern is to ensure that a class has only one instance
 * and provide a global access method to access it.
 * @author sunzhichao
 */
public class ScoreCounter {

    private static class ScoreCounterHolder{
        private static final ScoreCounter scoreCounter = new ScoreCounter();
    }
    public static ScoreCounter getInstance(){
        return ScoreCounterHolder.scoreCounter;
    }

    private long score = 0;
    private long bestScore;

    /**
     * private Constructor
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
     * Read the highest score from the file and put it in the bestScore variable
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
     * Save the highest score in a file
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
     * score add one
     * @param bird bird instance
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
