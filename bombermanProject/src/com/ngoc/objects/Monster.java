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
import java.util.Random;

/**
 *
 * @author DELL
 */
public class Monster extends Human {

    private Animation rightMonster;
    private Animation leftMonster;
    private Animation behindMonster;
    private Animation frontMonster;
    private static final int RUN_SPEED = 3;

    public Monster(float x, float y, float width, float height, GameWorld gameWorld) {
        super(x, y, width, height, gameWorld);
        Random rd=new Random();
        int number=rd.nextInt(4);
        setTeamType(ENEMY_TEAM);
        
        setDirection(number);
        rightMonster = LoadData.getInstance().getAnimation("rightMonster");
        leftMonster = LoadData.getInstance().getAnimation("leftMonster");
        behindMonster = LoadData.getInstance().getAnimation("behindMonster");
        frontMonster = LoadData.getInstance().getAnimation("frontMonster");

    }

    @Override
    public Rectangle getBoundForCollisionWithEnemy() {
        // TODO Auto-generated method stub
        Rectangle rect = getBoundForCollisionWithMap();

        rect.x = (int) getPos_x() - 22;
        rect.y = (int) getPos_y() - 30;
        rect.width = 44;
        rect.height = 60;

        return rect;
    }

    public void goBackWhenCollision() {
        switch (getDirectionForCollisionWithMap()) {
            case LEFT_DIR:
                setDirection(RIGHT_DIR);
                break;
            case RIGHT_DIR:
                setDirection(LEFT_DIR);
                break;
            case UP_DIR:
                setDirection(DOWN_DIR);
                break;
            case DOWN_DIR:
                setDirection(UP_DIR);
                break;
        }

        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if (object != null) {
            switch (getDirection()) {
                case LEFT_DIR:
                    setDirection(RIGHT_DIR);
                    break;
                case RIGHT_DIR:
                    setDirection(LEFT_DIR);
                    break;
                case UP_DIR:
                    setDirection(DOWN_DIR);
                    break;
                case DOWN_DIR:
                    setDirection(UP_DIR);
                    break;
            }
        }
        run();
    }
    public void processingCollisionWithPlayer(){
        ParticularObject object = getGameWorld().particularObjectManager.getCollisionWidthEnemyObject(this);
        if ((object != null && object.getState() == ALIVE) && (object.getTeamType() == LEAGUE_TEAM)) {
            object.setState(DEATH);

        }
    }
    @Override
    public void update() {
        super.update();
        goBackWhenCollision();
        processingCollisionWithPlayer();
        

    }

    @Override
    public void draw(Graphics2D g2) {
        switch (getState()) {
            case ALIVE:

                if (getDirection() == RIGHT_DIR) {
                    rightMonster.Update(System.nanoTime());

                    rightMonster.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (rightMonster.getCurrentFrame() == 1) {
                        rightMonster.setIgnoreFrame(0);
                    }
                }
                if (getDirection() == LEFT_DIR) {
                    leftMonster.Update(System.nanoTime());

                    leftMonster.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (leftMonster.getCurrentFrame() == 1) {
                        leftMonster.setIgnoreFrame(0);
                    }
                }
                if (getDirection() == UP_DIR) {
                    behindMonster.Update(System.nanoTime());

                    behindMonster.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (behindMonster.getCurrentFrame() == 1) {
                        behindMonster.setIgnoreFrame(0);
                    }
                }
                if (getDirection() == DOWN_DIR) {
                    frontMonster.Update(System.nanoTime());

                    frontMonster.draw((int) (getPos_x() - getGameWorld().camera.getPos_x()), (int) getPos_y() - (int) getGameWorld().camera.getPos_y(), g2);
                    if (frontMonster.getCurrentFrame() == 1) {
                        frontMonster.setIgnoreFrame(0);
                    }
                }
                break;

        }

        
    }

    @Override
    public void stopRun() {
        setSpeedX(0);
        setSpeedY(0);

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

}
