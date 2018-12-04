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
public class Camera extends GameObject {

    private float widthView;
    private float heightView;

    private boolean isLocked = false;

    public Camera(float x, float y, float widthView, float heightView, GameWorld gameWorld) {
        super(x, y, gameWorld);
        this.widthView = widthView;
        this.heightView = heightView;
    }

    public void lock() {
        isLocked = true;
    }

    public void unlock() {
        isLocked = false;
    }

    @Override
    public void update() {

        // NOTE: WHEN SEE FINAL BOSS, THE CAMERA WON'T CHANGE THE POSITION,
        // AFTER THE TUTORIAL, CAMERA WILL SET THE NEW POS
        if (!isLocked) {

            Player mainCharacter = getGameWorld().player;

            if (mainCharacter.getPos_x() - getPos_x() > 800) {
                setPos_x(mainCharacter.getPos_x() - 800);
            }
            if (mainCharacter.getPos_x() - getPos_x() < 100) {
                setPos_x(mainCharacter.getPos_x() - 100);
            }

            if (mainCharacter.getPos_y() - getPos_y() > 600) {
                setPos_y(mainCharacter.getPos_y() - 600); // bottom
            }
            if (mainCharacter.getPos_y() - getPos_y() < 100) {
                setPos_y(mainCharacter.getPos_y() - 100);// top 
            }
        }

    }

    public float getWidthView() {
        return widthView;
    }

    public void setWidthView(float widthView) {
        this.widthView = widthView;
    }

    public float getHeightView() {
        return heightView;
    }

    public void setHeightView(float heightView) {
        this.heightView = heightView;
    }

}
