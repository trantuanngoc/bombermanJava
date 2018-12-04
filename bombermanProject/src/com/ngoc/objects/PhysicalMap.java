/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.objects;

import com.ngoc.animation.LoadData;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author DELL
 */
public class PhysicalMap extends GameObject{
    public String[][] physical_map;
    private int tileSize;
    
    public PhysicalMap(float x, float y, GameWorld gameWorld) {
        super(x,y,gameWorld);
        this.tileSize = 100;
        physical_map=LoadData.getInstance().getPhysicalMap();
       
    }
   
    public String[][] getPhysical_map() {
        return physical_map;
    }

    public void setPhysical_map(String[][] physical_map) {
        this.physical_map = physical_map;
    }

    

    public int getWidth() {
        return Width;
    }

    public void setWidth(int Width) {
        this.Width = Width;
    }
    private int Width;
    
    
    
    public int getTileSize(){
        return tileSize;
    }
    
    public void draw(Graphics2D g2){
        Camera camera = getGameWorld().camera;
        
        g2.setColor(Color.GRAY);
        for(int i = 0;i< physical_map.length;i++)
            for(int j = 0;j<physical_map[0].length;j++)
                if(physical_map[i][j].equals("#")) 
                    g2.fillRect(  (int) getPos_x() + j*tileSize - (int) camera.getPos_x(), (int) getPos_y() + i*tileSize - (int) camera.getPos_y(), tileSize, tileSize);
        
    }
    
    public Rectangle haveCollisionWithLand(Rectangle rect){

        int posX1 = rect.x/tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width)/tileSize;
        posX2 += 2;

        int posY1 = (rect.y + rect.height)/tileSize;

        if(posX1 < 0) posX1 = 0;
        
        if(posX2 >= physical_map[0].length) posX2 = physical_map[0].length - 1;
        for(int y = posY1; y < physical_map.length;y++){
            for(int x = posX1; x <= posX2; x++){
                
                if(physical_map[y][x].equals("#")){
                    Rectangle r = new Rectangle((int) getPos_x() + x * tileSize, (int) getPos_y() + y * tileSize, tileSize, tileSize);
                    if(rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
    }
    
    public Rectangle haveCollisionWithTop(Rectangle rect){

        int posX1 = rect.x/tileSize;
        posX1 -= 2;
        int posX2 = (rect.x + rect.width)/tileSize;
        posX2 += 2;

        //int posY = (rect.y + rect.height)/tileSize;
        int posY = rect.y/tileSize;

        if(posX1 < 0) posX1 = 0;
        
        if(posX2 >= physical_map[0].length) posX2 = physical_map[0].length - 1;
        
        for(int y = posY; y >= 0; y--){
            for(int x = posX1; x <= posX2; x++){
                
               if(physical_map[y][x].equals("#")){
                    Rectangle r = new Rectangle((int) getPos_x() + x * tileSize, (int) getPos_y() + y * tileSize, tileSize, tileSize);
                    if(rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
    }
    
    public Rectangle haveCollisionWithRightWall(Rectangle rect){
   
        
        int posY1 = rect.y/tileSize;
        posY1-=2;
        int posY2 = (rect.y + rect.height)/tileSize;
        posY2+=2;
        
        int posX1 = (rect.x + rect.width)/tileSize;
        int posX2 = posX1 + 3;
        if(posX2 >= physical_map[0].length) posX2 = physical_map[0].length - 1;
        
        if(posY1 < 0) posY1 = 0;
        if(posY2 >= physical_map.length) posY2 = physical_map.length - 1;
        
        
        for(int x = posX1; x <= posX2; x++){
            for(int y = posY1; y <= posY2;y++){
                if(physical_map[y][x].equals("#")){
                    Rectangle r = new Rectangle((int) getPos_x() + x * tileSize, (int) getPos_y() + y * tileSize, tileSize, tileSize);
                    if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
        
    }
    
    public Rectangle haveCollisionWithLeftWall(Rectangle rect){
        
   
        
        int posY1 = rect.y/tileSize;
        posY1-=2;
        int posY2 = (rect.y + rect.height)/tileSize;
        posY2+=2;
        
        int posX1 = (rect.x + rect.width)/tileSize;
        int posX2 = posX1 - 3;
        if(posX2 < 0) posX2 = 0;
        
        if(posY1 < 0) posY1 = 0;
        if(posY2 >= physical_map.length) posY2 = physical_map.length - 1;
        
        
        for(int x = posX1; x >= posX2; x--){
            for(int y = posY1; y <= posY2;y++){
               if(physical_map[y][x].equals("#")){
                    Rectangle r = new Rectangle((int) getPos_x() + x * tileSize, (int) getPos_y() + y * tileSize, tileSize, tileSize);
                    if(r.y < rect.y + rect.height - 1 && rect.intersects(r))
                        return r;
                }
            }
        }
        return null;
        
    }

    
    @Override
    public void update() {}
    
}
