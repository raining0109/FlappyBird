package com.sunzhichao.FlappyBird.app;

import com.sunzhichao.FlappyBird.impl.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static com.sunzhichao.FlappyBird.util.Constant.*;

/**
 * 游戏主逻辑类，用于游戏流程的控制
 */
public class Game extends Frame {

    private static int gameState;//游戏状态

    private GameBackground background;//游戏背景对象
    private CloudControl cloudControl;//游戏云朵控制类
    private Bird bird;//小鸟对象
    private PipeControl pipeControl;//游戏水管控制类
    private WelcomeAnimation welcomeAnimation;//游戏欢迎界面对象

    //构造方法
    public Game() {
        initFrame();//初始化游戏窗口
        setVisible(true);//窗口默认为不可见，设置为可见
        initGame();//初始化游戏对象
    }

    //初始化游戏窗口
    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(GAME_TITLE);
        setLocation(FRAME_X, FRAME_Y);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);//结束程序
            }
        });
        addKeyListener(new BirdKeyListener());//添加按键监听


    }

    //初始化游戏的各个对象
    private void initGame() {
        background = new GameBackground();
        cloudControl = new CloudControl();
        pipeControl = new PipeControl();
        welcomeAnimation = new WelcomeAnimation();
        bird = new Bird();
        setGameState(GAME_READY);

        //启动一个新的线程，用来刷新窗口lambda写法
        new Thread(() -> {
            while (true) {
                repaint();//系统会调用update方法
                try {
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private final BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    //刷新方法
    public void update(Graphics g) {
        Graphics bufG = bufImg.getGraphics();//获得图片画笔
        //使用图片画笔将需要绘制的内容绘制到图片
        background.draw(bufG, bird);//背景
        cloudControl.draw(bufG, bird);//云朵
        if (gameState == GAME_READY) {
            welcomeAnimation.draw(bufG);
        } else {
            pipeControl.draw(bufG, bird);
        }
        bird.draw(bufG);//绘制小鸟
          g.drawImage(bufImg, 0, 0, null);//一次性将图片绘制到屏幕上
    }

    public static void setGameState(int gameState) {
        Game.gameState = gameState;
    }

    //游戏按键监听类
    class BirdKeyListener implements KeyListener {

        //按键按下，根据游戏当前状态调用不同的方法
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyChar();
            switch (gameState) {
                case GAME_READY:
                    if(keycode ==KeyEvent.VK_SPACE){
                        //游戏启动界面按下空格，振翅+重力
                        bird.birdFlap();//振翅
                        bird.birdFall();//重力
                        setGameState(GAME_START);//游戏状态开始
                    }
                    break;
                case GAME_START:
                    if (keycode ==KeyEvent.VK_SPACE){
                        //游戏过程中按下空格则振翅一次+重力
                        bird.birdFlap();
                        bird.birdFall();
                    }
                    break;
                case STATE_OVER:
                    if(keycode == KeyEvent.VK_SPACE){
                        //游戏结束时按下空格，重新开始游戏
                        resetGame();
                    }
                    break;
            }
        }

        //重新开始游戏
        private void resetGame(){
            setGameState(GAME_READY);
            pipeControl.reset();
            bird.reset();
        }

        //按键松开，要手动更改状态
        @Override
        public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyChar();
            if(keycode == KeyEvent.VK_SPACE){
                bird.keyReleased();
            }
        }

        //其他按键没有动作发生
        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
}
