/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.animation;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import javax.imageio.ImageIO;


/**
 *
 * @author DELL
 */
public class LoadData {
    private static LoadData instance;
    
    private String animationFile="data/animation.txt";
    private String frameFile="data/newx.txt";
    private String frameImageFile="new.png"; 
    
    private String physicalMapFile="data/level1.txt";
    private String backGroundMapFile="data/level1.txt";
    private String soundfile = "data/sounds.txt";
    
    private Hashtable<String,FrameImage> frameImages;
    private Hashtable<String,Animation> animations;
    private Hashtable<String, AudioClip> sounds;
    
    private String[][] physical_map;
    private String[][] background_map;
    
    
    public static LoadData getInstance(){
        if(instance==null){
            instance=new LoadData();
        }
        return instance;
    }
    
    public void loadAllData() throws IOException{
        loadFrame();
        loadAnimation();
       LoadBackgroundMap();
       LoadPhysicalMap();
       LoadSounds();
    }
    
    public AudioClip getSound(String name){
        return instance.sounds.get(name);
    }
    
    public void LoadSounds() throws IOException{
        sounds = new Hashtable<String, AudioClip>();
        
        FileReader fr = new FileReader(soundfile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        if(br.readLine()==null) { // no line = "" or something like that
            System.out.println("No data");
            throw new IOException();
        }
        else {
            
            fr = new FileReader(soundfile);
            br = new BufferedReader(fr);
            
            while((line = br.readLine()).equals(""));
            
            int n = Integer.parseInt(line);
            
            for(int i = 0;i < n; i ++){
                
                AudioClip audioClip = null;
                while((line = br.readLine()).equals(""));

                String[] str = line.split(" ");
                String name = str[0];
                
                String path = str[1];

                try {
                   audioClip =  Applet.newAudioClip(new URL("file","",str[1]));

                } catch (MalformedURLException ex) {}
                
                instance.sounds.put(name, audioClip);
            }
            
        }
        
        br.close();
        
    }
    
    public String[][] getPhysicalMap(){
        return instance.physical_map;
    }
    
    public void LoadPhysicalMap() throws IOException{
        
        FileReader fr = new FileReader(physicalMapFile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
            
        
        instance.physical_map = new String[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split("");
            for(int j = 0;j<numberOfColumns;j++)
                instance.physical_map[i][j] = str[j];
        }
     
        
        
        br.close();
        
    }
    
    public String[][] getBackGroundMap(){
        return background_map;
    }
    
    public void LoadBackgroundMap() throws IOException{
        
        FileReader fr = new FileReader(backGroundMapFile);
        BufferedReader br = new BufferedReader(fr);
        
        String line = null;
        
        line = br.readLine();
        int numberOfRows = Integer.parseInt(line);
        line = br.readLine();
        int numberOfColumns = Integer.parseInt(line);
            
        
        instance.background_map = new String[numberOfRows][numberOfColumns];
        
        for(int i = 0;i < numberOfRows;i++){
            line = br.readLine();
            String [] str = line.split("");
            for(int j = 0;j<numberOfColumns;j++)
                instance.background_map[i][j] = str[j];
        }
        
      
        
        br.close();
        
    }
    
    
    public void loadFrame() throws  IOException{
        frameImages=new Hashtable<String,FrameImage>();
        FileReader fr=new FileReader(frameFile);
        BufferedReader br=new BufferedReader(fr);
        
        String line=null;
        if(br.readLine()==null){
            System.out.println("no data");
            throw  new IOException();
        }
        
        else {
            fr =new FileReader(frameFile);
            br=new BufferedReader(fr);
            line=br.readLine();
            int n=Integer.parseInt(line);
            
           
            for(int i=0;i<n;i++){
               FrameImage frame=new FrameImage();
               
                
                while((line=br.readLine()).equals(""));
                frame.setName(line);
                while((line=br.readLine()).equals(""));
                String path=line;
               
                
                while((line=br.readLine()).equals(""));
                String[] str=line.split(" ");
                int x=Integer.parseInt(str[3]);
                int y=Integer.parseInt(str[4]);
                
                
               while((line=br.readLine()).equals(""));
                str=line.split(" ");
                int w=Integer.parseInt(str[3]);
                int h=Integer.parseInt(str[4]);
                
               
                
                BufferedImage imageData = ImageIO.read(new File(path));
                BufferedImage image = imageData.getSubimage(x, y, w, h);
                
                
                frame.setImage(image);
                instance.frameImages.put(frame.getName(), frame);
                
                line=br.readLine();
                line=br.readLine();
                line=br.readLine();
               
            }
        }
        br.close();
    }
    
    
   public FrameImage getFrameImage(String name){
       FrameImage frameImage=new FrameImage(instance.frameImages.get(name));
       return frameImage;
   }
   
    public void loadAnimation() throws  IOException{
        animations =new Hashtable<String,Animation>();
        FileReader fr=new FileReader(animationFile);
        BufferedReader br=new BufferedReader(fr);
        String line=null;
        if(br.readLine()==null){
            System.out.println("no animation data");
            throw new IOException();
        }
        
        else {
             fr=new FileReader(animationFile);
            br=new BufferedReader(fr);
            line =br.readLine();
           int n=Integer.parseInt(line);
           for(int i=0;i<n;i++){
               Animation animation=new Animation();
               line=br.readLine();
               animation.setName(line);
               while((line=br.readLine()).equals(""));
               String str[]=line.split(" ");
               for(int j=0;j<str.length;j+=2)
                   animation.add(getFrameImage(str[j]), Double.parseDouble(str[j+1]));
               instance.animations.put(animation.getName(), animation);
              
           }
        }
        br.close();
    }
    
    public Animation getAnimation(String name){
        Animation animation=new Animation(instance.animations.get(name));
        return animation;
    }
    
}
