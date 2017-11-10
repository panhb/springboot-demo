package com.panhb.demo.utils;

import org.apache.commons.lang.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author panhb
 */
public class ImageUtils {


    /**
     * 按尺寸缩放图片
     *
     * @param imageFile
     * @param newPath
     * @param width
     * @param height
     * @throws IOException
     */
    public static void zoomImage(File imageFile, String newPath, int width, int height) throws IOException {
        if (imageFile != null && !imageFile.canRead()){
            return;
        }
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        if (null == bufferedImage){
            return;
        }
        zoomImageUtils(imageFile, newPath, bufferedImage, width, height);
    }

    /**
     * 按指定高度 等比例缩放图片
     *
     * @param imageFile
     * @param newPath
     * @param newWidth 新图的宽度
     * @throws IOException
     */
    public static void zoomImageScale(File imageFile, String newPath, int newWidth) throws IOException {
        if(!imageFile.canRead()){
            return;
        }
        BufferedImage bufferedImage = ImageIO.read(imageFile);
        if (null == bufferedImage){
            return;
        }
        int originalWidth = bufferedImage.getWidth();
        int originalHeight = bufferedImage.getHeight();
        // 缩放的比例
        double scale = (double)originalWidth / (double)newWidth;
        int newHeight =  (int)(originalHeight / scale);
        zoomImageUtils(imageFile, newPath, bufferedImage, newWidth, newHeight);
    }

    private static void zoomImageUtils(File imageFile, String newPath, BufferedImage bufferedImage, int width, int height)
            throws IOException {
        String suffix = StringUtils.substringAfterLast(imageFile.getName(), ".");
        boolean isPngOrGif = suffix != null && (suffix.trim().toLowerCase().endsWith("png") || suffix.trim().toLowerCase().endsWith("gif"));
        // 处理 png 背景变黑的问题
        if(isPngOrGif){
            BufferedImage to= new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = to.createGraphics();
            to = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
            g2d.dispose();
            g2d = to.createGraphics();
            Image from = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
            g2d.drawImage(from, 0, 0, null);
            g2d.dispose();
            ImageIO.write(to, suffix, new File(newPath));
        }else{
            BufferedImage newImage = new BufferedImage(width, height, bufferedImage.getType());
            Graphics g = newImage.getGraphics();
            g.drawImage(bufferedImage, 0, 0, width, height, null);
            g.dispose();
            ImageIO.write(newImage, suffix, new File(newPath));
        }
    }

    public static void main(String[] args) throws Exception{
        String dir = "C:\\hengheng";
        File[] list = new File(dir).listFiles();
        for(File f : list){
            String name = f.getName();
            String newPath = dir + File.separator + "new_" + name;
            int width = Integer.parseInt(name.substring(0,name.lastIndexOf("_")).split("-")[0]);
            int height = Integer.parseInt(name.substring(0,name.lastIndexOf("_")).split("-")[1]);
            zoomImage(f,newPath,width,height);
        }
    }
}
