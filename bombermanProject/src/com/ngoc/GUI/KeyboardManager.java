/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.GUI;

import com.ngoc.objects.GameWorld;
import java.awt.event.KeyEvent;

/**
 *
 * @author DELL
 */
public class KeyboardManager {
    private GameWorld gameWorld;
    public KeyboardManager(GameWorld gameWorld){
        this.gameWorld=gameWorld;
    }
    public void processKeyPress(int keyCode){
        switch(keyCode){
            case KeyEvent.VK_UP:
               gameWorld.player.setDirection(gameWorld.player.UP_DIR);
               gameWorld.player.run();
                break;
                
            case KeyEvent.VK_DOWN:
               gameWorld.player.setDirection(gameWorld.player.DOWN_DIR);
               gameWorld.player.run();
                break;
                
            case KeyEvent.VK_LEFT:
                gameWorld.player.setDirection(gameWorld.player.LEFT_DIR);
                gameWorld.player.run();
                break;
                
            case  KeyEvent.VK_RIGHT:
               gameWorld.player.setDirection(gameWorld.player.RIGHT_DIR);
                gameWorld.player.run();
                break;
                
            case KeyEvent.VK_SPACE:
              gameWorld.player.attack();
                break;
                
            case KeyEvent.VK_ENTER:
                if(gameWorld.state == GameWorld.PAUSE_GAME){
                    if(gameWorld.previousState == GameWorld.GAME_PLAY)
                        gameWorld.switchState(GameWorld.GAME_PLAY);
                    else gameWorld.switchState(GameWorld.GAME_PLAY);
                    gameWorld.bgMusic.loop();
                    gameWorld.bgMusic.play();
                }
                break;
        }
        
    }
    
    public void processKeyRelease(int keyCode){
        switch(keyCode){
            case KeyEvent.VK_UP:
                if(gameWorld.player.getSpeedY()<0)
                gameWorld.player.stopRun();
                break;
                
            case KeyEvent.VK_DOWN:
              if(gameWorld.player.getSpeedY()>0)
                gameWorld.player.stopRun();
                break;
                
            case KeyEvent.VK_LEFT:
                if(gameWorld.player.getSpeedX()<0)
                gameWorld.player.stopRun();
                break;
                
            case  KeyEvent.VK_RIGHT:
                if(gameWorld.player.getSpeedX()>0)
                gameWorld.player.stopRun();
                break;
                
            case KeyEvent.VK_SPACE:
                
                break;
                
            case KeyEvent.VK_ENTER:
                break;
        }
        
    }
}
