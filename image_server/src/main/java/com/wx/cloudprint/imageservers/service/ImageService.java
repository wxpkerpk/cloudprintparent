package com.wx.cloudprint.imageservers.service;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Service
public class ImageService {
    public static final int A4_width = 210;
    public static final int A4_height = 297;
    public static final int A3_width = 297;
    public static final int A3_height = 420;
    public static final int A5_width = 210;
    public static final int A5_height = 148;

    public static float scala = 4;
    private static ColorConvertOp colorConvertOp = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);

    public static BufferedImage scalaImage(BufferedImage sourceImg, int toWidth, int toHeight, boolean isMono) {
        BufferedImage result = new BufferedImage(toWidth, toHeight,
                BufferedImage.TYPE_INT_RGB);

        result.getGraphics().drawImage(
                sourceImg.getScaledInstance(toWidth, toHeight,
                        Image.SCALE_SMOOTH), 0, 0, null);
        if (isMono) {
            result = colorConvertOp.filter(result, null);
        }
        return result;
    }

    public byte[][] combineImg(String sourceFile, int row, int col, String paperType, Integer page, boolean isDirection, boolean isMono) {

        int width = 0;
        int height = 0;


        switch (paperType) {
            case "A4": {
                width = (int) (A4_width * scala);
                height = (int) (A4_height * scala);
                break;

            }
            case "A3": {
                width = (int) (A3_width * scala);
                height = (int) (A3_height * scala);
                break;
            }
            case "A5": {

                width = (int) (A5_width * scala);
                height = (int) (A5_height * scala);

            }


        }

        if ((col >= row && col >= 2) ||(col==row&&row==1&!isDirection)) {
            int temp = width;
            width = height;
            height = temp;
        }
        File file = new File(sourceFile);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            int len = files.length;
            int perLen = row * col;
            int destLen = (int) Math.ceil(((float) len) / perLen);

            int count = 0;
            Arrays.sort(files, (o1, o2) -> {
                int i1 = Integer.parseInt(o1.getName().split("\\.")[0]);
                int i2 = Integer.parseInt(o2.getName().split("\\.")[0]);
                return Integer.compare(i1, i2);
            });
            destLen = page == null ? destLen : page + 1;
            if(page==null) page=0;

            byte[][] images = new byte[destLen - page][];
            count = page * perLen;
            for (int i = page; i < destLen; i++) {

                BufferedImage result = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics graphics = result.getGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, width, height);
                for (int j = 0; j < perLen && count < len; j++) {
                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(files[count]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (image != null) {
                        int toWidth = width / col;
                        int toHeight = height / row;
                        BufferedImage bufferedImage = null;
                        bufferedImage = scalaImage(image, toWidth, toHeight, isMono);

                        graphics.drawImage(bufferedImage, ((j % col) * toWidth), (j / col) * toHeight, null);


                    }
                    count++;

                }
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(result, "png", out);
                    images[i - page] = out.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return images;

        }
        return null;


    }
}
