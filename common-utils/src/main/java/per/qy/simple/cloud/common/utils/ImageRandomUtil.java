package per.qy.simple.cloud.common.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class ImageRandomUtil {

    /**
     * 干扰线数量
     */
    private static final int LINE_COUNT = 50;

    /**
     * 生成随机图片，建议宽高及验证码长度大概关系：width=(height-6)*randomCode.length()+4
     *
     * @param width      图片宽度
     * @param height     图片高度
     * @param randomCode 验证码
     * @return
     */
    public static BufferedImage createImage(int width, int height, String randomCode) {
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        // 设定图像背景色(因为是做背景，所以偏淡)
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        // 创建字体，字体的大小应该根据图片的高度来定。
        int fontSize = height - 10;
        g.setFont(new Font("Times New Roman", Font.ITALIC, fontSize));

        // 绘制干扰线
        for (int i = 0; i < LINE_COUNT; i++) {
            g.setColor(getRandColor(130, 200));
            Random random = new Random();
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g.drawLine(x1, y1, x2, y2);
        }

        // 绘制验证码
        for (int i = 0; i < randomCode.length(); i++) {
            // 生成随机颜色(因为是做前景，所以偏深)
            g.setColor(getRandColor(20, 130));
            // 坐标从左上角开始为(0,0)
            g.drawString(String.valueOf(randomCode.charAt(i)), fontSize * i + 4, height - 5);
        }
        // 图象生效
        g.dispose();
        return buffImg;

    }

    /**
     * 随机颜色
     */
    private static Color getRandColor(int fc, int bc) {
        Random random = new Random();
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
