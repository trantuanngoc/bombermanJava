/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.objects;

/**
 *
 * @author DELL
 */
public abstract class GameObject {
    private  float pos_x;
    private float pos_y;
    private GameWorld gameWorld;

    public GameObject(float pos_x, float pos_y, GameWorld gameWorld) {
        this.pos_x = pos_x;
        this.pos_y = pos_y;
        this.gameWorld = gameWorld;
    }

    public float getPos_x() {
        return pos_x;
    }

    public void setPos_x(float pos_x) {
        this.pos_x = pos_x;
    }

    public float getPos_y() {
        return pos_y;
    }

    public void setPos_y(float pos_y) {
        this.pos_y = pos_y;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
    
    public abstract void update();
}
