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
public abstract class Human extends ParticularObject {

    public Human(float x, float y, float width, float height, GameWorld gameWorld) {
        super(x, y, width, height, gameWorld);
        setState(ALIVE);

    }

    public abstract void run();

    public abstract void stopRun();

    @Override
    public void update() {
        super.update();

        if (getState() == ALIVE) {

            setPos_x(getPos_x() + getSpeedX());
            setPos_y(getPos_y() + getSpeedY());

        }
    }

}
