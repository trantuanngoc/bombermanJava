/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.GUI;

import com.ngoc.animation.Animation;
import com.ngoc.animation.FrameImage;
import com.ngoc.animation.LoadData;
import com.ngoc.objects.GameWorld;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class Board extends JPanel implements Runnable, KeyListener {

    private Thread thread;
    private static boolean runGame;

    FrameImage frame;
    private Animation animation;

    private KeyboardManager keyboardManager;

    private BufferedImage buffimage;
    private Graphics2D buffGraphics2D;

    private GameWorld gameWorld;

    public Board() {
//        try {
//            BufferedImage image=ImageIO.read(new File("animation2.png"));
//             BufferedImage image1=image.getSubimage(124, 202, 69, 194);
//            frame1 =new FrameImage("frame1",image1);
//            BufferedImage image2=image.getSubimage(72, 408, 64, 197);
//            frame2 =new FrameImage("frame2",image2);
//            BufferedImage image3=image.getSubimage(1, 205, 61, 202);
//            frame3 =new FrameImage("frame3",image3);
//            animation=new Animation();
//            animation.add(frame1, 500*1000000);
//            animation.add(frame2, 500*1000000);
//            animation.add(frame3, 500*1000000);
//        } catch (IOException ex) {
//        }
        frame = LoadData.getInstance().getFrameImage("run_01");
        animation = LoadData.getInstance().getAnimation("rightDirection");
        gameWorld = new GameWorld();

        keyboardManager = new KeyboardManager(gameWorld);

        buffimage = new BufferedImage(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffimage, 0, 0, this);
    }

    public void renderGame() {
        if (buffimage == null) {
            buffimage = new BufferedImage(Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        }

        if (buffimage != null) {
            buffGraphics2D = (Graphics2D) buffimage.getGraphics();
        }

        if (buffimage != null) {

            gameWorld.Render(buffGraphics2D);

        }
    }

    public void updateGame() {
        gameWorld.update();

    }

    public synchronized void startGame() {
        if (thread == null) {
            runGame = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public synchronized void stopGame() {
        try {
            thread.join();
            runGame = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        long FPS = 40;
        long period = 1000 * 1000000 / FPS;
        long beginTime;
        long sleepTime;

        beginTime = System.nanoTime();

        while (runGame) {

            updateGame();
            renderGame();
            repaint();

            long deltaTime = System.nanoTime() - beginTime;
            sleepTime = period - deltaTime;

            try {
                if (sleepTime > 0) {
                    Thread.sleep(sleepTime / 1000000);
                } else {
                    Thread.sleep(period / 2000000);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            beginTime = System.nanoTime();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyboardManager.processKeyPress(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyboardManager.processKeyRelease(e.getKeyCode());
    }

}
