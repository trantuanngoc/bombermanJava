/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.objects;

import com.ngoc.animation.Animation;
import com.ngoc.animation.LoadData;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import com.ngoc.objects.BombExplosing;
import java.applet.AudioClip;

/**
 *
 * @author DELL
 */
public class Bomb extends ParticularObject {

    private Animation bombAnimation;
    private long putTime;
    private long explosingTime;
    public AudioClip explosingAudioClip;

    public Bomb(float x, float y, float width, float height, GameWorld gameWorld) {

        super(x, y, width, height, gameWorld);
        setWidth(width);
        setHeight(height);
        setState(ALIVE);
        setTeamType(BOMB_TEAM);
        putTime = System.currentTimeMillis();

        bombAnimation = LoadData.getInstance().getAnimation("bomb");
        explosingAudioClip=LoadData.getInstance().getSound("bombExplosing");
    }

    public void checkBombExplosing() {
        if (System.currentTimeMillis() - putTime > 3000) {

            BombExplosing rightExplosing = new BombExplosing(getPos_x(), getPos_y(), 100, 100, RIGHT_DIR, BombExplosing.RUN_SPEED, 0, getGameWorld());//lỗi do để 100, tìm hiểu sau
            BombExplosing leftExplosing = new BombExplosing(getPos_x(), getPos_y(), 100, 100, LEFT_DIR, -BombExplosing.RUN_SPEED, 0, getGameWorld());
            BombExplosing upExplosing = new BombExplosing(getPos_x(), getPos_y(), 100, 100, UP_DIR, 0, -BombExplosing.RUN_SPEED, getGameWorld());
            BombExplosing downExplosing = new BombExplosing(getPos_x(), getPos_y(), 100, 100, DOWN_DIR, 0, BombExplosing.RUN_SPEED, getGameWorld());

            getGameWorld().particularObjectManager.addObject(rightExplosing);
            getGameWorld().particularObjectManager.addObject(leftExplosing);
            getGameWorld().particularObjectManager.addObject(upExplosing);
            getGameWorld().particularObjectManager.addObject(downExplosing);
            Player.BOMB_NUMBER_CURRENT--;
            explosingAudioClip.play();
            setState(DEATH);
            

        }

    }

    @Override
    public void update() {
        super.update();
        checkBombExplosing();

    }

    @Override
    public void draw(Graphics2D g2) {
        switch (getState()) {
            case ALIVE:
                bombAnimation.Update(System.nanoTime());

                bombAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                if (bombAnimation.getCurrentFrame() == 1) {
                    bombAnimation.setIgnoreFrame(0);
                }
                break;
        }

        

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = getBoundForCollisionWithMap();

        rect.x = (int) getPos_x() - 22;
        rect.y = (int) getPos_y() - 20;
        rect.width = 44;
        rect.height = 40;

        return rect;
    }

}
