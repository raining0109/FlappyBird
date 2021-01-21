package com.sunzhichao.FlappyBird.app;

import com.sunzhichao.FlappyBird.impl.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import static com.sunzhichao.FlappyBird.util.Constant.*;

/**
 * The main logic of the game, used to control the game flow
 */
public class Game extends Frame {

    private static int gameState;

    private GameBackground background;
    private CloudControl cloudControl;
    private Bird bird;
    private PipeControl pipeControl;
    private WelcomeAnimation welcomeAnimation;

    /**
     * Constructor
     */
    public Game() {
        initFrame();
        setVisible(true);//The window is invisible by default, set to visible
        initGame();
    }

    /**
     * Initialize the game frame
     */
    private void initFrame() {
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setTitle(GAME_TITLE);
        setLocation(FRAME_X, FRAME_Y);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);//End the program
            }
        });
        addKeyListener(new BirdKeyListener());
    }

    /**
     * Initialize the game object
     */
    private void initGame() {
        background = new GameBackground();
        cloudControl = new CloudControl();
        pipeControl = new PipeControl();
        welcomeAnimation = new WelcomeAnimation();
        bird = new Bird();
        setGameState(GAME_READY);

        //Start a new thread to refresh the window
        new Thread(() -> {
            while (true) {
                repaint();//The system will call the update method
                try {
                    Thread.sleep(FPS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private final BufferedImage bufImg = new BufferedImage(FRAME_WIDTH, FRAME_HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

    public void update(Graphics g) {
        Graphics bufG = bufImg.getGraphics();//Get “picture brush”
        //Use the picture brush to draw the content that needs to be drawn to the picture
        background.draw(bufG, bird);
        cloudControl.draw(bufG, bird);
        if (gameState == GAME_READY) {
            welcomeAnimation.draw(bufG);
        } else {
            pipeControl.draw(bufG, bird);
        }
        bird.draw(bufG);
          g.drawImage(bufImg, 0, 0, null);//Draw the picture on the screen at once
    }

    public static void setGameState(int gameState) {
        Game.gameState = gameState;
    }

    class BirdKeyListener implements KeyListener {

        /**
         * Press the button, call different methods
         * according to the current state of the game
         */
        @Override
        public void keyPressed(KeyEvent e) {
            int keycode = e.getKeyChar();
            switch (gameState) {
                case GAME_READY:
                    if(keycode ==KeyEvent.VK_SPACE){
                        bird.birdFlap();
                        bird.birdFall();
                        setGameState(GAME_START);
                    }
                    break;
                case GAME_START:
                    if (keycode ==KeyEvent.VK_SPACE){
                        bird.birdFlap();
                        bird.birdFall();
                    }
                    break;
                case STATE_OVER:
                    if(keycode == KeyEvent.VK_SPACE){
                        resetGame();
                    }
                    break;
            }
        }

        private void resetGame(){
            setGameState(GAME_READY);
            pipeControl.reset();
            bird.reset();
        }

        /**
         * When the space button is released,
         * the state needs to be changed manually
         */
        @Override
        public void keyReleased(KeyEvent e) {
            int keycode = e.getKeyChar();
            if(keycode == KeyEvent.VK_SPACE){
                bird.keyReleased();
            }
        }

        /**
         * When other buttons are pressed,no action happens
         */
        @Override
        public void keyTyped(KeyEvent e) {
        }
    }
}
