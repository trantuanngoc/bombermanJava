/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.objects;

import com.ngoc.GUI.Game;
import com.ngoc.animation.LoadData;
import java.awt.Graphics2D;

/**
 *
 * @author DELL
 */
public class BackGroundMap extends GameObject {

    public String[][] map;
    private int tile_size;
    private String backGroundBrick = "brick";
    private String backGroundGrass = "grass";

    public BackGroundMap(float x, float y, GameWorld gameWorld) {
        super(x, y, gameWorld);
        map = LoadData.getInstance().getBackGroundMap();
        tile_size = 100;

    }

    @Override
    public void update() {
    }

    public void draw(Graphics2D g2) {

        Camera camera = getGameWorld().camera;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (j * tile_size - camera.getPos_x() > -100 && j * tile_size - camera.getPos_x() < Game.SCREEN_WIDTH && i * tile_size - camera.getPos_y() > -100 && i * tile_size - camera.getPos_y() < Game.SCREEN_HEIGHT) {
                    if (map[i][j].equals("#")) {
                        g2.drawImage(LoadData.getInstance().getFrameImage(backGroundBrick).getImage(), (int) getPos_x() + j * tile_size - (int) camera.getPos_x(),
                                (int) getPos_y() + i * tile_size - (int) camera.getPos_y(), null);
                    }
                    if (!map[i][j].equals("#")) {
                        g2.drawImage(LoadData.getInstance().getFrameImage(backGroundGrass).getImage(), (int) getPos_x() + j * tile_size - (int) camera.getPos_x(),
                                (int) getPos_y() + i * tile_size - (int) camera.getPos_y(), null);
                    }

                }
            }
        }

    }
}
