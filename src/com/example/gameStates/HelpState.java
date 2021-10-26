package com.example.gameStates;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.manageProcess.GameStateManager;

public class HelpState extends GameStatesParents{

	private BufferedImage image;
	
	
	public HelpState(GameStateManager gsm){
		
		super(gsm);
		
	}
	public void init() {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Menu/help.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void update() {
		
	}
	public void draw(Graphics2D g) {
		g.drawImage(image, 100, 10, null);
	}
	public void keyPressed(int k) {
		if(KeyEvent.VK_ESCAPE == k || KeyEvent.VK_BACK_SPACE == k){
			gsm.sound.makeClickSound();
			gsm.setState(gsm.MENUSTATE);
		}
	}
	public void keyReleased(int k) {
		
	}
	@Override
	public void mouseMoved(Point k) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseClick(int k) {
		// TODO Auto-generated method stub
		
	}
}
