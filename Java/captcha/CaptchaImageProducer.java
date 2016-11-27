package com.zing.util.captcha;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * Created by zing on 2016/11/27.
 */
public class CaptchaImageProducer {
    //文字字号
    public static final int FONT_SIZE = 40;
    //宽度
    public static final int WIDTH = 200;
    //高度
    public static final int HEIGHT = 50;
    //噪点数
    public static final int NOISE = 40;
    //扭曲度
    public static final int DEFORMATION = 20;
    //背景颜色
    public static final Color BACKGROUND_COLOR = Color.WHITE;
    //字体颜色
    public static final Color FONT_COLOR = new Color(0,145,255);
    //字体
    public static final Font[] FONT_STYLES = new Font[]{
            new Font("Arial", Font.BOLD, FONT_SIZE),
            new Font("Courier", Font.BOLD, FONT_SIZE),
            new Font("微软雅黑", Font.BOLD, FONT_SIZE)
    };
    //字间距
    public static final int LETTER_SPACE = 15;


    private Random random = new Random();


    private static CaptchaImageProducer imageProducer = new CaptchaImageProducer();

    private CaptchaImageProducer() {
    }

    public static CaptchaImageProducer getInstance() {
        return imageProducer;
    }

    public BufferedImage createCAPTCHAImage(String captchaText) {
        BufferedImage image = createFontImage(captchaText);
        image = twistImage(image);
        image = addFishEyeGimpy(image);
        image = createBackgroundImage(image);
        for (int i = 0; i < NOISE; i++) {
            image = makeNoise(image);
        }
        return image;
    }

    /**
     * 创建验证码图片
     *
     * @param captchaText
     * @return
     */
    public BufferedImage createFontImage(String captchaText) {
        char[] words = captchaText.toCharArray();
        Font[] fonts = new Font[words.length];
        int[] charWidth = new int[words.length];

        int startY = (HEIGHT - FONT_SIZE) / 15 + FONT_SIZE;


        BufferedImage bi = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);//创建图片
        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.setColor(FONT_COLOR);


        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        hints.add(
                new RenderingHints(
                        RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY
                )
        );

        graphics2D.setRenderingHints(hints);

        FontRenderContext frc = graphics2D.getFontRenderContext();

        int space = 0;
        for (int i = 0; i < words.length; i++) {
//                fonts[i] = fonts[(int) (Math.random()*FONT_STYLE.length)];
            fonts[i] = FONT_STYLES[random.nextInt(FONT_STYLES.length)];
            GlyphVector glyphVector = fonts[i].createGlyphVector(frc, new char[]{words[i]});
            charWidth[i] = (int) glyphVector.getVisualBounds().getWidth();
            if (i > 0) {
                space = space + LETTER_SPACE;
            }
            space = space + charWidth[i];


        }
        int startX = (WIDTH - space) / 2;
        for (int i = 0; i < words.length; i++) {
            graphics2D.setFont(fonts[i]);
            graphics2D.drawChars(new char[]{words[i]}, 0, 1, startX, startY);
            startX = startX + charWidth[i] + LETTER_SPACE;
        }
        return bi;
    }

    /**
     * 创建背景
     *
     * @param base
     * @return
     */
    public BufferedImage createBackgroundImage(BufferedImage base) {
        BufferedImage background = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = (Graphics2D) background.getGraphics();

        RenderingHints hints = new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_OFF
        );
        hints.add(
                new RenderingHints(
                        RenderingHints.KEY_COLOR_RENDERING,
                        RenderingHints.VALUE_COLOR_RENDER_QUALITY
                )
        );

        hints.add(
                new RenderingHints(
                        RenderingHints.KEY_ALPHA_INTERPOLATION,
                        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY
                )
        );

        hints.add(
                new RenderingHints(
                        RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY
                )
        );

        graphics2D.setRenderingHints(hints);

        GradientPaint paint = new GradientPaint(0, 0, BACKGROUND_COLOR, WIDTH, HEIGHT, BACKGROUND_COLOR);
        graphics2D.setPaint(paint);
        graphics2D.fill(new Rectangle2D.Double(0, 0, WIDTH, HEIGHT));

        graphics2D.drawImage(base, 0, 0, null);

        return background;

    }

    public BufferedImage makeNoise(BufferedImage base) {
        Color color = makeRandomColor();
        Graphics2D graph = (Graphics2D) base.getGraphics();
        graph.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));

        graph.setColor(color);

        // for the maximum 3 point change the stroke and direction
        graph.setStroke(new BasicStroke(random.nextFloat() * 2));
        int fromX = random.nextInt(WIDTH);
        int fromY = random.nextInt(HEIGHT);
        graph.drawLine(fromX, fromY, fromX + randomBetween(-10,10), fromY + randomBetween(-10,10));
        graph.dispose();
        return base;
    }


    /**
     * 鱼眼滤镜
     *
     * @param base
     * @return
     */
    public BufferedImage addFishEyeGimpy(BufferedImage base) {
        Graphics2D graphics = (Graphics2D) base.getGraphics();
//        int horizontalLines = HEIGHT / 7;
//        int verticalLines = WIDTH / 7;

//        int horizontalGaps = HEIGHT / (HEIGHT / 7 + 1);
//        int verticalGaps = WIDTH / (WIDTH / 7 + 1);
//
//        for (int i = horizontalGaps; i < HEIGHT; i += horizontalGaps) {
//            graphics.setColor(Color.BLUE);
//            graphics.drawLine(0, i, WIDTH, i);
//        }
//        for (int i = verticalGaps; i < WIDTH; i += verticalGaps) {
//            graphics.setColor(Color.GREEN);
//            graphics.drawLine(i, 0, i, HEIGHT);
//        }

        int[] pix = new int[WIDTH * HEIGHT];
        int point = 0;
        for (int X = 0; X < WIDTH; X++) {
            for (int Y = 0; Y < HEIGHT; Y++) {
                pix[point] = base.getRGB(X, Y);
                point++;
            }
        }

        double distance = randomBetween(WIDTH / 4, WIDTH / 3);
        int middleX = WIDTH / 2;
        int middleY = HEIGHT / 2;

        for (int X = 0; X < WIDTH; X++) {
            for (int Y = 0; Y < HEIGHT; Y++) {
                int realX = X - middleX;
                int realY = Y - middleY;
                double distanceTemp = Math.sqrt(realX * realX + realY * realY);
                if (distance > distanceTemp) {
                    int xTemp = middleX
                            + (int) (((fishEyeFormula(distanceTemp / distance) * distance) / distanceTemp) * (double) (X - middleX));
                    int yTemp = middleY
                            + (int) (((fishEyeFormula(distanceTemp / distance) * distance) / distanceTemp) * (double) (Y - middleY));
                    base.setRGB(X, Y, pix[xTemp * HEIGHT + yTemp]);
                }
            }
        }
        return base;
    }


    private double fishEyeFormula(double s) {
        if (s < 0.0D) {
            return 0.0D;
        }
        if (s > 1.0D) {
            return s;
        } else {
            return -0.75D * s * s * s + 1.5D * s * s + 0.25D * s;
        }
    }

    private int randomBetween(int from, int to) {
        int space = to - from + 1;
        return (int) (Math.floor(Math.random() * space) + from);
    }


    private Color makeRandomColor() {
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    /**
     *
     * @Description:正弦曲线Wave扭曲图片
     * @since 1.0.0
     * @Date:2012-3-1 下午12:49:47
     * @return BufferedImage
     */
    private BufferedImage twistImage(BufferedImage base) {
        double dMultValue = random.nextInt(7) + Math.random()*DEFORMATION*3;// 波形的幅度倍数，越大扭曲的程序越高，一般为3
        double dPhase = random.nextInt(6);// 波形的起始相位，取值区间（0-2＊PI）

        BufferedImage destBi = new BufferedImage(WIDTH,
                HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

        for (int i = 0; i < destBi.getWidth(); i++) {
            for (int j = 0; j < destBi.getHeight(); j++) {
                int nOldX = getXPosition4Twist(dPhase, dMultValue,
                        destBi.getHeight(), i, j);
                int nOldY = j;
                if (nOldX >= 0 && nOldX < destBi.getWidth() && nOldY >= 0
                        && nOldY < destBi.getHeight()) {
                    destBi.setRGB(nOldX, nOldY, base.getRGB(i, j));
                }
            }
        }
        return destBi;
    }
    /**
     *
     * @Description:获取扭曲后的x轴位置
     * @since 1.0.0
     * @Date:2012-3-1 下午3:17:53
     * @param dPhase
     * @param dMultValue
     * @param height
     * @param xPosition
     * @param yPosition
     * @return int
     */
    private int getXPosition4Twist(double dPhase, double dMultValue,
                                   int height, int xPosition, int yPosition) {
        double PI = 3.1415926535897932384626433832799; // 此值越大，扭曲程度越大
        double dx =  (PI * yPosition) / height + dPhase;
        double dy = Math.sin(dx);
        return xPosition + (int) (dy * dMultValue);
    }

}
