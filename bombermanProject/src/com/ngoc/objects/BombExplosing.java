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

/**
 *
 * @author DELL
 */
public class BombExplosing extends Human {

    private Animation rightExplosing;
    private Animation leftExplosing;
    private Animation upExplosing;
    private Animation downExplosing;
    
    public static int FLAME_EXPLOSING=2;
    
    private float initialPosx;
    private float initialPosy;
    private long  explosingTime;

    public static final int RUN_SPEED = 20;

    public BombExplosing(float x, float y, float width, float height, int direction, int speedx, int speedy, GameWorld gameWorld) {
        super(x, y, width, height, gameWorld);

        initialPosx = x;
        initialPosy = y;

        setDirection(direction);
        setSpeedX(speedx);
        setSpeedY(speedy);
        explosingTime = System.currentTimeMillis();
        rightExplosing = LoadData.getInstance().getAnimation("rightExplosion");
        leftExplosing = LoadData.getInstance().getAnimation("leftExplosion");
        upExplosing = LoadData.getInstance().getAnimation("upExplosion");
        downExplosing = LoadData.getInstance().getAnimation("downExplosion");
    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        Rectangle rect = new Rectangle();
        rect.x = (int) getPos_x() - 22;
        rect.y = (int) getPos_y() - 43;
        rect.width = 44;
        rect.height = 40;

        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {
        switch (getState()) {
            case ALIVE:
                if (getDirection() == RIGHT_DIR) {
                    rightExplosing.Update(System.nanoTime());

                    rightExplosing.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (rightExplosing.getCurrentFrame() == 1) {
                        rightExplosing.setIgnoreFrame(0);
                    }
                }
                if (getDirection() == LEFT_DIR) {
                    leftExplosing.Update(System.nanoTime());

                    leftExplosing.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (leftExplosing.getCurrentFrame() == 1) {
                        leftExplosing.setIgnoreFrame(0);
                    }
                }
                if (getDirection() == UP_DIR) {
                    upExplosing.Update(System.nanoTime());

                    upExplosing.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (upExplosing.getCurrentFrame() == 1) {
                        upExplosing.setIgnoreFrame(0);
                    }
                }
                if (getDirection() == DOWN_DIR) {
                    downExplosing.Update(System.nanoTime());

                    downExplosing.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (downExplosing.getCurrentFrame() == 1) {
                        downExplosing.setIgnoreFrame(0);
                    }
                }
                break;
                
        }
        drawBoundForCollisionWithMap(g2);
    }

    @Override
    public void update() {
        super.update();

        if (getDirectionForCollisionWithMap() == LEFT_DIR) {
            setState(DEATH);
        } else if (getDirectionForCollisionWithMap() == RIGHT_DIR) {
            setState(DEATH);
        }

        if (getDirectionForCollisionWithMap() == UP_DIR) {
            setState(DEATH);
        } else if (getDirectionForCollisionWithMap() == DOWN_DIR) {
            setState(DEATH);
        }

        if (System.currentTimeMillis()- explosingTime > 500 || (Math.abs(getPos_x() - initialPosx) / 100) > FLAME_EXPLOSING || (Math.abs(getPos_y() - initialPosy) / 100) > FLAME_EXPLOSING) {
            setState(DEATH);
        }
        
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if((object!=null && object.getState() == ALIVE)&&(object.getTeamType()!=SPEED_ITEM && object.getTeamType()!=FLAME_ITEM)&&(object.getTeamType()!=BOMB_TEAM)&&(object.getTeamType()!=BOMB_ITEM)&&(object.getTeamType()!=BOMB_TEAM)&&(object.getTeamType()!=PORTAL)){
            
            object.setState(DEATH);
            if(object.getTeamType()==ENEMY_TEAM){
                getGameWorld().MONSTER_NUMBER--;
            }
        }

    }

    @Override
    public void run() {
        if (getDirection() == LEFT_DIR) {
            setSpeedX(-RUN_SPEED);
        }
        if (getDirection() == RIGHT_DIR) {
            setSpeedX(RUN_SPEED);
        }
        if (getDirection() == UP_DIR) {
            setSpeedY(-RUN_SPEED);
        }
        if (getDirection() == DOWN_DIR) {
            setSpeedY(RUN_SPEED);
        }
    }

    @Override
    public void stopRun() {
        setSpeedX(0);
        setSpeedY(0);
    }

}
