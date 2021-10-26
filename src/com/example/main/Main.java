package com.example.main;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.example.window.GameWindow;

// main Class
public class Main extends JFrame{

	GameWindow gwindow;
	
	// main Method
	public static void main(String [] args){
		Main main=new Main();
		main.gwindow = new GameWindow();
		main.setTitle("Aerospace War");
		main.add(main.gwindow);
		main.pack();
		main.setVisible(true);
		main.setResizable(false);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
