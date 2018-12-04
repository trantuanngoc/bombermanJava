/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.objects;

import com.ngoc.GUI.Board;
import com.ngoc.GUI.Game;
import com.ngoc.animation.Animation;
import com.ngoc.animation.LoadData;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.List;


/**
 *
 * @author DELL
 */
public class Player extends Human {

    private static int RUN_SPEED = 7;
    private static int BOMB_NUMBER_ENABLE = 3;
    public static int BOMB_NUMBER_CURRENT = 0;
    private List<Bomb> bombs;
    
    private AudioClip itemSound;

    private Animation rightAnimation;
    private Animation frontAnimation;
    private Animation leftAnimation;
    private Animation behindAnimation;

    private Animation standRightAnimation;
    private Animation standFrontAnimation;
    private Animation standBehindAnimation;
    private Animation standLeftAnimation;

    public Player(float x, float y, float width, float height, GameWorld gameWorld) {
        super(x, y, width, height, gameWorld);

        //shooting1 = CacheDataLoader.getInstance().getSound("bluefireshooting");
        //hurtingSound = CacheDataLoader.getInstance().getSound("megamanhurt");
        setTeamType(LEAGUE_TEAM);

        rightAnimation = LoadData.getInstance().getAnimation("rightDirection");
        frontAnimation = LoadData.getInstance().getAnimation("frontDirection");
        behindAnimation = LoadData.getInstance().getAnimation("behindDirection");
        leftAnimation = LoadData.getInstance().getAnimation("leftDirection");

        standRightAnimation = LoadData.getInstance().getAnimation("standRight");
        standFrontAnimation = LoadData.getInstance().getAnimation("standFront");
        standBehindAnimation = LoadData.getInstance().getAnimation("standBehind");
        standLeftAnimation = LoadData.getInstance().getAnimation("standLeft");
        
        itemSound=LoadData.getInstance().getSound("itemSound");

    }

    public void processingCollisionWithMap() {
        if (getDirectionForCollisionWithMap() == LEFT_DIR) {
            Rectangle rectLeftWall = getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap());
            setPos_x(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);
        } else if (getDirectionForCollisionWithMap() == RIGHT_DIR) {
            Rectangle rectRightWall = getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap());
            setPos_x(rectRightWall.x - getWidth()/2 );
        }

        if (getDirectionForCollisionWithMap() == UP_DIR) {
            Rectangle recTopWall = getGameWorld().physicalMap.haveCollisionWithTop(getBoundForCollisionWithMap());
            setPos_y(recTopWall.y + recTopWall.width );
        } else if (getDirectionForCollisionWithMap() == DOWN_DIR) {
            Rectangle recDownWall = getGameWorld().physicalMap.haveCollisionWithLand(getBoundForCollisionWithMap());
            setPos_y(recDownWall.y - getHeight() );
        }
    }

    public void processingCollisionWithtree() {
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if ((object != null && object.getState() == ALIVE) && (object.getTeamType() == TREE_TEAM)) {

            if (getDirection() == LEFT_DIR) {
                Rectangle rectLeftWall = object.getBoundForCollisionWithEnemy();
                setPos_x(rectLeftWall.x + rectLeftWall.width + getWidth() / 2);
            } else if (getDirection() == RIGHT_DIR) {
                Rectangle rectRightWall = object.getBoundForCollisionWithEnemy();
                setPos_x(rectRightWall.x - getWidth() / 2);
            }

            if (getDirection() == UP_DIR) {
                Rectangle recTopWall = object.getBoundForCollisionWithEnemy();
                setPos_y(recTopWall.y + recTopWall.width );
            } else if (getDirection() == DOWN_DIR) {
                Rectangle recDownWall = object.getBoundForCollisionWithEnemy();
                setPos_y(recDownWall.y - getHeight());
            }

        }
    }

    public void processingCollisionWithSpeedItem() {
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if ((object != null && object.getState() == ALIVE) && (object.getTeamType() == SPEED_ITEM)) {
            object.setState(DEATH);
            RUN_SPEED = RUN_SPEED + 2;
            itemSound.play();

        }
    }
    
    public void processingCollisionWithFlameItem() {
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if ((object != null && object.getState() == ALIVE) && (object.getTeamType() == FLAME_ITEM)) {
            object.setState(DEATH);
            BombExplosing.FLAME_EXPLOSING++;
            itemSound.play();

        }
    }
    
    public void processingCollisionWithBombItem() {
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if ((object != null && object.getState() == ALIVE) && (object.getTeamType() == BOMB_ITEM)) {
            object.setState(DEATH);
            BOMB_NUMBER_ENABLE++;
            itemSound.play();

        }
    }
    
    public boolean checkToBePortal() {
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if ((object != null && object.getState() == ALIVE) && (object.getTeamType() == PORTAL)) {
            itemSound.play();
             return true;

        }
        return false;
    }

    @Override
    public void update() {
        super.update();

        processingCollisionWithMap();
        processingCollisionWithtree();
        processingCollisionWithSpeedItem();
        processingCollisionWithFlameItem();
        processingCollisionWithBombItem();
        

    }

    @Override
    public Rectangle getBoundForCollisionWithMap(){
        Rectangle rec=new Rectangle();
        rec.x=(int) getPos_x()-25;
        rec.y=(int) getPos_y();
        rec.width=50;
        rec.height=60;
        return rec;
    }
    @Override
    public Rectangle getBoundForCollisionWithEnemy() {

        Rectangle rect = getBoundForCollisionWithMap();

        return rect;
    }

    @Override
    public void draw(Graphics2D g2) {

        switch (getState()) {

            case ALIVE:

                if (getSpeedX() > 0) {
                    rightAnimation.Update(System.nanoTime());

                    rightAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (rightAnimation.getCurrentFrame() == 1) {
                        rightAnimation.setIgnoreFrame(0);
                    }
                } else if (getSpeedX() < 0) {
                    leftAnimation.Update(System.nanoTime());

                    leftAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (leftAnimation.getCurrentFrame() == 1) {
                        leftAnimation.setIgnoreFrame(0);
                    }
                } else {
                    if (getDirection() == RIGHT_DIR) {

                        standRightAnimation.Update(System.nanoTime());
                        standRightAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);

                    }
                    if (getDirection() == LEFT_DIR) {

                        standLeftAnimation.Update(System.nanoTime());
                        standLeftAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);

                    }
                }

                if (getSpeedY() > 0) {
                    frontAnimation.Update(System.nanoTime());

                    frontAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (frontAnimation.getCurrentFrame() == 1) {
                        frontAnimation.setIgnoreFrame(0);
                    }
                } else if (getSpeedY() < 0) {
                    behindAnimation.Update(System.nanoTime());

                    behindAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (behindAnimation.getCurrentFrame() == 1) {
                        behindAnimation.setIgnoreFrame(0);
                    }
                } else {

                    if (getDirection() == UP_DIR) {

                        standBehindAnimation.Update(System.nanoTime());
                        standBehindAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);

                    }
                    if (getDirection() == DOWN_DIR) {
                        standFrontAnimation.Update(System.nanoTime());
                        standFrontAnimation.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    }

                }

                break;

            case DEATH:

                break;

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
        rightAnimation.reset();
        leftAnimation.reset();
        behindAnimation.reset();
        frontAnimation.reset();

        rightAnimation.unIgnoreFrame(0);
        leftAnimation.unIgnoreFrame(0);
        behindAnimation.unIgnoreFrame(0);
        frontAnimation.unIgnoreFrame(0);
    }

    public void attack() {
        if (BOMB_NUMBER_CURRENT < BOMB_NUMBER_ENABLE) {
            Bomb bomb = new Bomb(((int) (getPos_x() / 100)) * 100 + 50, ((int) (getPos_y() / 100)) * 100 + 50, 100, 100, getGameWorld());
            BOMB_NUMBER_CURRENT++;
            getGameWorld().particularObjectManager.addObject(bomb);          
        }

    }

}
