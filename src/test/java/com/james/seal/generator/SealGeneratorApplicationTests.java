package com.james.seal.generator;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SealGeneratorApplicationTests {

    @Test
    void contextLoads() {
        try {
            BufferedImage image = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            // 设置背景色为白色
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, 500, 500);

            // 绘制红色圆圈
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(5));
            g2d.draw(new Ellipse2D.Float(50, 50, 400, 400));

            // 绘制五角星
            drawStar(g2d, 250, 250, 150);

            // 添加文字
            addText(g2d);

            // 做旧处理
            applyAgedEffect(image);

            // 保存图像
            File output = new File("seal.png");
            ImageIO.write(image, "png", output);

            System.out.println("Seal generated successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void drawStar(Graphics2D g2d, float centerX, float centerY, float radius) {
        int numPoints = 5;
        double angle = Math.PI / numPoints;
        double startX = centerX + radius * Math.cos(angle);
        double startY = centerY + radius * Math.sin(angle);

        g2d.setStroke(new BasicStroke(5));
        g2d.drawLine((int) startX, (int) startY, (int) centerX, (int) centerY);
        for (int i = 1; i < numPoints; i++) {
            angle += 2 * Math.PI / numPoints;
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            g2d.drawLine((int) startX, (int) startY, (int) x, (int) y);
            startX = x;
            startY = y;
        }
    }

    private static void addText(Graphics2D g2d) {
        g2d.setFont(new Font("SimSun", Font.PLAIN, 36)); // 宋体字体
        g2d.setColor(Color.BLACK); // 文字颜色改为黑色
        g2d.drawString("北京荣耀终端有限公司", 100, 150);
        g2d.drawString("人事专用章", 180, 350);
        g2d.drawString("1101020440608", 170, 400);
    }

    private static void applyAgedEffect(BufferedImage image) {
        // 这里可以添加一些做旧处理的代码，例如调整亮度、对比度等
        // 示例：降低亮度
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color color = new Color(image.getRGB(x, y));
                int red = (int) (color.getRed() * 0.9);
                int green = (int) (color.getGreen() * 0.9);
                int blue = (int) (color.getBlue() * 0.9);
                image.setRGB(x, y, new Color(red, green, blue).getRGB());
            }
        }
    }
}