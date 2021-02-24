package terry.practice;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConvert {
    private static final String DIR = System.getProperty("user.dir") + "/src/java/terry/practice/";

    public static void JpegConvertToPng(String inputImage, String outputImage) {
        long start = System.currentTimeMillis();
        try {
            File jpegFile = new File(inputImage);
            BufferedImage in = ImageIO.read(jpegFile);
            ImageIO.write(in, "png", new File(outputImage));

            long end = System.currentTimeMillis();
            System.out.println("convert jpeg to png succeed.");
            System.out.printf("File size: %.4fMb, time: %.4fs\n", jpegFile.length()/1000000.0, (end-start)/1000.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void pngConvertToJpeg(String inputImage, String outputImage) {
        BufferedImage in = null;
        long start = System.currentTimeMillis();
        try {
            File pngFile = new File(inputImage);
            in = ImageIO.read(pngFile);
            BufferedImage newImage = new BufferedImage(in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_RGB);
            newImage.createGraphics().drawImage(in, 0, 0, Color.white, null);
            ImageIO.write(newImage, "jpg", new File(outputImage));
            long end = System.currentTimeMillis();
            System.out.println("convert png to jpeg succeed.");
            System.out.printf("File size: %.4fMb, time: %.4fs\n", pngFile.length()/1000000.0, (end-start)/1000.0);
        } catch (IOException e) {
            System.out.println("convert png to jpeg failed.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ImageConvert.pngConvertToJpeg(DIR+"img.png", DIR+"img.jpg");
        ImageConvert.JpegConvertToPng(DIR+"img.jpg", DIR+"img1.png");
    }
}
