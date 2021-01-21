package com.sunzhichao.FlappyBird.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

public class Util {

    /**
     * 载入图片
     * @param imgPath 图片地址
     * @return BufferedImage
     */
    public static BufferedImage loadBufferedImage(String imgPath){
        try{
            return ImageIO.read(new FileInputStream(imgPath));
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断任意概率的概率性事件是否发生
     *
     * @param numerator   分子，不小于0的值
     * @param denominator 分母，不小于0的值
     * @return 概率性事件发生返回true，否则返回false
     */
    public static boolean isInProbability(int numerator, int denominator) throws Exception {
        // 分子分母不小于0
        if (numerator <= 0 || denominator <= 0) {
            throw new Exception("传入了非法的参数");
        }
        //分子大于分母，一定发生
        if (numerator >= denominator) {
            return true;
        }
        return getRandomNumber(1, denominator + 1) <= numerator;
    }

    public static int getRandomNumber(int min,int max){
        return (int)(Math.random()*(max-min)+min);
    }

    /**
     * 获得指定字符串在指定字体的宽
     */
    public static int getStringWidth(Font font, String str) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(str, frc).getWidth());
    }
    /**
     * 获得指定字符串在指定字体的高
     */
    public static int getStringHeight(Font font, String str) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(str, frc).getHeight());
    }
}
