/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.objects;

import com.ngoc.GUI.Game;
import com.ngoc.animation.LoadData;

import java.applet.AudioClip;
import java.awt.Color;

import java.awt.Graphics2D;
import java.util.List;

/**
 *
 * @author DELL
 */
public class GameWorld {

    public Player player;
    public Camera camera;
    public Bomb bomb;
  
    
    public static int MONSTER_NUMBER =0;

    public static final int PAUSE_GAME = 0;
    public static final int GAME_OVER = 1;
    public static final int GAME_WIN = 2;
    public static final int GAME_PLAY=3;
    
    public int state = PAUSE_GAME;
    public int previousState = state;

    public PhysicalMap physicalMap;
    public BackGroundMap backgroundMap;
    public ParticularObjectManager particularObjectManager;
    public List<Monster> monsters;
    
    public AudioClip bgMusic;
    public AudioClip loseSound;
    public AudioClip winSound;

    public GameWorld() {
        player = new Player(300, 300, 50, 60, this);
        player.setTeamType(ParticularObject.LEAGUE_TEAM);
        
        physicalMap = new PhysicalMap(0, 0, this);
        backgroundMap = new BackGroundMap(0, 0, this);

        camera = new Camera(0, 50, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT, this);

        particularObjectManager = new ParticularObjectManager(this);
        particularObjectManager.addObject(player);
        
        bgMusic=LoadData.getInstance().getSound("bgmusic");
        loseSound=LoadData.getInstance().getSound("loseSound");
        winSound=LoadData.getInstance().getSound("winSound");
        init();
    }

    private void init() {
        for (int i = 0; i < physicalMap.physical_map.length; i++) {
            for (int j = 0; j < physicalMap.physical_map[0].length; j++) {
                if (physicalMap.physical_map[i][j].equals("1")) {
                    particularObjectManager.addObject(new Monster(j * 100 + 100 / 2, i * 100 + 100 / 2, 44, 40, this));
                    MONSTER_NUMBER++;
                }
                if ((physicalMap.physical_map[i][j].equals("*") || physicalMap.physical_map[i][j].equals("s")) || physicalMap.physical_map[i][j].equals("f")||physicalMap.physical_map[i][j].equals("b")||physicalMap.physical_map[i][j].equals("x")) {
                    particularObjectManager.addObject(new Tree(j * 100 + 100 / 2, i * 100 + 100 / 2, 100, 100, this));
                }
                if (physicalMap.physical_map[i][j].equals("s")) {
                    particularObjectManager.addObject(new SpeedItem(j * 100 + 100 / 2, i * 100 + 100 / 2, 44, 40, this));
                }
                if (physicalMap.physical_map[i][j].equals("f")) {
                    particularObjectManager.addObject(new FlameItem(j * 100 + 100 / 2, i * 100 + 100 / 2, 44, 40, this));
                }
                if (physicalMap.physical_map[i][j].equals("b")) {
                    particularObjectManager.addObject(new BombItem(j * 100 + 100 / 2, i * 100 + 100 / 2, 44, 40, this));
                }
                if (physicalMap.physical_map[i][j].equals("x")) {
                    particularObjectManager.addObject(new Portal(j * 100 + 100 / 2, i * 100 + 100 / 2, 44, 40, this));
                }

            }
        }
    }
    
    public void switchState(int state){
        previousState = this.state;
        this.state = state;
    }

    public void update() {
        particularObjectManager.updateObjects();
        camera.update();
        
        switch(state){
            case PAUSE_GAME:
                break;
            case GAME_WIN:
                break;
            case GAME_OVER:
                break;
            case GAME_PLAY:
                if(MONSTER_NUMBER==0&&player.checkToBePortal()==true){
                    bgMusic.stop();
                    winSound.play();
                    switchState(GAME_WIN);
                }
                if(player.getState()==player.DEATH){
                    bgMusic.stop();
                    loseSound.play();
                    switchState(GAME_OVER);
                }
                
                break;
        }

    }

    public void Render(Graphics2D g2) {

        
        
        switch(state){
            case PAUSE_GAME:
                    g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("PRESS ENTER TO CONTINUE", 400, 300);
                    break;
            case GAME_PLAY:
                backgroundMap.draw(g2);
                particularObjectManager.draw(g2);
                break;
            
            case GAME_OVER:
                g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("GAME OVER!", 450, 300);
                break;
            case GAME_WIN:
                g2.setColor(Color.BLACK);
                    g2.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
                    g2.setColor(Color.WHITE);
                    g2.drawString("YOU WIN!", 450, 300);
                break;
        }

    }
}
