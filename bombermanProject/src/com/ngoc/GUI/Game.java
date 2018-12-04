/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ngoc.GUI;

import com.ngoc.animation.LoadData;
import java.awt.EventQueue;
import java.io.IOException;
import javax.swing.JFrame;

/**
 *
 * @author DELL
 */
public class Game extends JFrame {

    public static final int SCREEN_WIDTH = 1300;
    public static final int SCREEN_HEIGHT = 800;

    public Game() {
        initUI();
    }

    public void initUI() {

        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("bomber");
        setResizable(false);
        setLocationRelativeTo(null);

        try {
            LoadData.getInstance().loadAllData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Board board = new Board();
        board.startGame();
        add(board);
        addKeyListener(board);

    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Game ex = new Game();
            ex.setVisible(true);
        });
    }
}
