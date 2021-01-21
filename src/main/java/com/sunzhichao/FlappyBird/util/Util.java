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
     * load photo
     * @param imgPath the path of the photo
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
     * Determine whether a probabilistic event with any probability occurs
     * @return occurs:true ; not:false
     */
    public static boolean isInProbability(int numerator, int denominator) throws Exception {
        // The numerator and denominator are not less than 0
        if (numerator <= 0 || denominator <= 0) {
            throw new Exception("An illegal parameter was passed in");
        }
        //The numerator is greater than the denominator, it must happen
        if (numerator >= denominator) {
            return true;
        }
        return getRandomNumber(1, denominator + 1) <= numerator;
    }

    public static int getRandomNumber(int min,int max){
        return (int)(Math.random()*(max-min)+min);
    }

    /**
     * Get the width of the specified string in the specified font
     */
    public static int getStringWidth(Font font, String str) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(str, frc).getWidth());
    }
    /**
     * Get the height of the specified string in the specified font
     */
    public static int getStringHeight(Font font, String str) {
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        return (int) (font.getStringBounds(str, frc).getHeight());
    }
}
