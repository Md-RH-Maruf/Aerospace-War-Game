package com.example.gameStates;

import java.awt.Graphics2D;
import java.awt.Point;

import com.example.manageProcess.GameStateManager;

public abstract class GameStatesParents {
	
	public GameStateManager gsm;
	
	public GameStatesParents(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public abstract void init();
	
	public abstract void update();
	
	public abstract void draw(Graphics2D g);
	
	public abstract void keyPressed(int k);
	
	public abstract void keyReleased(int k);
	
	public abstract void mouseMoved(Point k);
	
	public abstract void mouseClick(int k);
	
	

}
