/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author DELL
 */
public abstract class ParticularObject extends GameObject {

    private int intergerX, intergerY;

    public static final int LEAGUE_TEAM = 1;
    public static final int ENEMY_TEAM = 2;
    public static final int TREE_TEAM=3;
    public static final int BOMB_TEAM=4;
    public static final int ITEM_TEAM=5;
    public static final int FLAME_ITEM=6;
    public static final int SPEED_ITEM=7;
     public static final int BOMB_ITEM=8;
     public static final int PORTAL=9;
    
    

    public static final int LEFT_DIR = 0;
    public static final int RIGHT_DIR = 1;
    public static final int UP_DIR = 2;
    public static final int DOWN_DIR = 3;

    public static final int ALIVE = 0;
    public static final int DEATH = 1;
    private int state = ALIVE;

    private float width;
    private float height;
    private float speedX;
    private float speedY;

    private int direction;

    private int teamType;

    public ParticularObject(float x, float y, float width, float height, GameWorld gameWorld) {

        super(x, y, gameWorld);
        setWidth(width);
        setHeight(height);

        direction = RIGHT_DIR;
    }

    public int getIntergerX() {
        return intergerX;
    }

    public void setIntergerX(int intergerX) {
        this.intergerX = intergerX;
    }

    public int getIntergerY() {
        return intergerY;
    }

    public void setIntergerY(int intergerY) {
        this.intergerY = intergerY;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getSpeedX() {
        return speedX;
    }

    public void setSpeedX(float speedX) {
        this.speedX = speedX;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getTeamType() {
        return teamType;
    }

    public void setTeamType(int teamType) {
        this.teamType = teamType;
    }

    public boolean isObjectOutOfCameraView() {
        if (getPos_x() - getGameWorld().camera.getPos_x() > getGameWorld().camera.getWidthView()
                || getPos_x() - getGameWorld().camera.getPos_x() < -50
                || getPos_y() - getGameWorld().camera.getPos_y() > getGameWorld().camera.getHeightView()
                || getPos_y() - getGameWorld().camera.getPos_y() < -50) {
            return true;
        } else {
            return false;
        }
    }

    public int getDirectionForCollisionWithMap() {
        
        if (getDirection() == RIGHT_DIR && getGameWorld().physicalMap.haveCollisionWithRightWall(getBoundForCollisionWithMap()) != null) {
            return RIGHT_DIR;

        }
        
        if (getDirection() == LEFT_DIR && getGameWorld().physicalMap.haveCollisionWithLeftWall(getBoundForCollisionWithMap()) != null) {
            return LEFT_DIR;

        }
        

        if (getDirection() == UP_DIR && getGameWorld().physicalMap.haveCollisionWithTop(getBoundForCollisionWithMap()) != null) {
            return UP_DIR;
        }

        if (getDirection() == DOWN_DIR && getGameWorld().physicalMap.haveCollisionWithLand(getBoundForCollisionWithMap()) != null) {
            return DOWN_DIR;

        }
        
        return 4;
        
    }

    @Override
    public void update() {
        switch (state) {
            case ALIVE:
                break;
            case DEATH:
                break;

        }
    }

    public Rectangle getBoundForCollisionWithMap() {
        Rectangle bound = new Rectangle();
        bound.x = (int) (getPos_x() - (getWidth() / 2));
        bound.y = (int) (getPos_y() - (getHeight() / 2));
        bound.width = (int) getWidth();
        bound.height = (int) getHeight();
        return bound;
    }

    public void drawBoundForCollisionWithMap(Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithMap();
        g2.setColor(Color.BLUE);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPos_x(), rect.y - (int) getGameWorld().camera.getPos_y(), rect.width, rect.height);

    }

    public void drawBoundForCollisionWithEnemy(Graphics2D g2) {
        Rectangle rect = getBoundForCollisionWithEnemy();
        g2.setColor(Color.RED);
        g2.drawRect(rect.x - (int) getGameWorld().camera.getPos_x(), rect.y - (int) getGameWorld().camera.getPos_y(), rect.width, rect.height);
    }

    public abstract Rectangle getBoundForCollisionWithEnemy();

    public abstract void draw(Graphics2D g2);


}
