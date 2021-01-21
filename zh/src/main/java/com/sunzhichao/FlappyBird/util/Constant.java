package com.sunzhichao.FlappyBird.util;

import java.awt.*;

/**
 * 游戏常量类，用于存储游戏中会用到的常量，文件路径等
 * @author sunzhichao
 */
public class Constant {

    //游戏状态
    public static final int GAME_READY = 0;//游戏未开始
    public static final int GAME_START = 1;//游戏开始
    public static final int STATE_OVER = 2;//游戏结束

    // 窗口尺寸
    public static final int FRAME_WIDTH = 420;
    public static final int FRAME_HEIGHT = 640;

    // 窗口位置
    public static final int FRAME_X = 600;
    public static final int FRAME_Y = 100;

    // 标题栏高度
    public static final int TOP_BAR_HEIGHT = 20;

    // 游戏刷新率
    public static final int FPS = 1000 / 30;

    //游戏标题
    public static final String GAME_TITLE = "Flappy Bird written by sunzhichao";

    //游戏速度（水管及背景层的移动速度）
    public static final int GAME_SPEED = 4;

    public static final String BG_IMG_PATH = "src/main/resources/image/background.png"; // 背景图片

    //背景颜色
    public static final Color BG_COLOR = new Color(0x4bc4cf);

    //地面高度
    public static final int GROUND_HEIGHT = 35;

    // 上方管道加长，因为上方水管要下降移动（MovingPipe类）
    public static final int TOP_PIPE_LENGTHENING = 100;

    //云朵
    public static final int CLOUD_IMAGE_COUNT = 2;//云朵图片的个数
    public static final int MAX_CLOUD_COUNT = 7;//屏幕云朵的最大数量
    public static final int CLOUD_BORN_PERCENT = 6;//云朵生成的概率
    //云朵图片路径
    public static final String[] CLOUDS_IMG_PATH = { "src/main/resources/image/cloud_0.png", "src/main/resources/image/cloud_1.png" };

    // 水管图片路径
    public static final String[] PIPE_IMG_PATH = { "src/main/resources/image/pipe.png", "src/main/resources/image/pipe_top.png",
            "src/main/resources/image/pipe_bottom.png" };

    //小鸟图片路径
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


    public static final String SCORE_FILE_PATH = "resources/score";//分数文件路径

    //动画路径
    public static final String TITLE_IMG_PATH = "src/main/resources/image/title.png";
    public static final String NOTICE_IMG_PATH = "src/main/resources/image/start.png";
    public static final String SCORE_IMG_PATH = "src/main/resources/image/score.png";
    public static final String OVER_IMG_PATH = "src/main/resources/image/over.png";
    public static final String AGAIN_IMG_PATH = "src/main/resources/image/again.png";

    //游戏分数字体属性
    public static final Font SCORE_FONT = new Font("Times New Roman",Font.BOLD,24);
    public static final Font CURRENT_SCORE_FONT = new Font("Times New Roman", Font.BOLD, 32);// 字体

}
