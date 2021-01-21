package com.sunzhichao.FlappyBird.util;

import java.awt.*;

/**
 * Game constant class,
 * used to store constants used in the game, file paths, etc.
 * @author sunzhichao
 */
public class Constant {

    //Game state
    public static final int GAME_READY = 0;
    public static final int GAME_START = 1;
    public static final int STATE_OVER = 2;

    // Window size
    public static final int FRAME_WIDTH = 420;
    public static final int FRAME_HEIGHT = 640;

    // Window position
    public static final int FRAME_X = 600;
    public static final int FRAME_Y = 100;

    // Title bar height
    public static final int TOP_BAR_HEIGHT = 20;

    // Game refresh rate
    public static final int FPS = 1000 / 30;

    //Game title
    public static final String GAME_TITLE = "Flappy Bird written by sunzhichao";

    //Game speed (moving speed of pipe and background layer)
    public static final int GAME_SPEED = 4;

    public static final String BG_IMG_PATH = "src/main/resources/image/background.png"; // 背景图片

    //background color
    public static final Color BG_COLOR = new Color(0x4bc4cf);

    //Ground height
    public static final int GROUND_HEIGHT = 35;

    // The top pipe lengthening because the top pipe has to move downward (MovingPipe class)
    public static final int TOP_PIPE_LENGTHENING = 100;

    //cloud
    public static final int CLOUD_IMAGE_COUNT = 2;//Number of cloud pictures
    public static final int MAX_CLOUD_COUNT = 7;//Maximum number of screen clouds
    public static final int CLOUD_BORN_PERCENT = 6;//Probability of cloud born
    //Cloud picture path
    public static final String[] CLOUDS_IMG_PATH = { "src/main/resources/image/cloud_0.png", "src/main/resources/image/cloud_1.png" };

    // Pipe picture path
    public static final String[] PIPE_IMG_PATH = { "src/main/resources/image/pipe.png", "src/main/resources/image/pipe_top.png",
            "src/main/resources/image/pipe_bottom.png" };

    //Bird Picture Path
    public static final String[][] BIRD_IMG_PATH = {
            { "src/main/resources/image/0.png", "src/main/resources/image/1.png", "src/main/resources/image/2.png", "src/main/resources/image/3.png",
                    "src/main/resources/image/4.png", "src/main/resources/image/5.png", "src/main/resources/image/6.png", "src/main/resources/image/7.png" },
            { "src/main/resources/image/up.png", "src/main/resources/image/up.png", "src/main/resources/image/up.png", "src/main/resources/image/up.png",
                    "src/main/resources/image/up.png", "src/main/resources/image/up.png", "src/main/resources/image/up.png", "src/main/resources/image/up.png" },
            { "src/main/resources/image/down_0.png", "src/main/resources/image/down_1.png", "src/main/resources/image/down_2.png",
                    "src/main/resources/image/down_3.png", "src/main/resources/image/down_4.png", "src/main/resources/image/down_5.png",
                    "src/main/resources/image/down_6.png", "src/main/resources/image/down_7.png" },
            { "src/main/resources/image/dead.png", "src/main/resources/image/dead.png", "src/main/resources/image/dead.png", "src/main/resources/image/dead.png",
                    "src/main/resources/image/dead.png", "src/main/resources/image/dead.png", "src/main/resources/image/dead.png",
                    "src/main/resources/image/dead.png", }
    };

    //Score file path
    public static final String SCORE_FILE_PATH = "src/main/resources/score";

    //Animation path
    public static final String TITLE_IMG_PATH = "src/main/resources/image/title.png";
    public static final String NOTICE_IMG_PATH = "src/main/resources/image/start.png";
    public static final String SCORE_IMG_PATH = "src/main/resources/image/score.png";
    public static final String OVER_IMG_PATH = "src/main/resources/image/over.png";
    public static final String AGAIN_IMG_PATH = "src/main/resources/image/again.png";

    //Game score font attributes
    public static final Font SCORE_FONT = new Font("Times New Roman",Font.BOLD,24);
    public static final Font CURRENT_SCORE_FONT = new Font("Times New Roman", Font.BOLD, 32);

}
