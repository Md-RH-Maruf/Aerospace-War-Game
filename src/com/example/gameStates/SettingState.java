package com.example.gameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.manageProcess.GameStateManager;

public class SettingState extends GameStatesParents{

	private BufferedImage image;
//	
//	private boolean isMusic;
//	private boolean isSfx;
//	
	private String music="";
	private String sfx="";
	
	private int selection;
	
	private Font font=new Font("cerif",Font.PLAIN,25);
	public SettingState(GameStateManager gsm){
		super(gsm);
	}

	public void init() {
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Menu/menu.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void update() {
		//System.out.println("boolean="+gsm.sound.getIsMusic());
		if(gsm.sound.getIsMusic()){
			music= "ON";			
		}
		else{
			music = "OFF";
		}
		
		if(gsm.sound.getIsSfx()) sfx = "ON";
		else sfx = "OFF";
	}


	public void draw(Graphics2D g) {
		g.setColor(Color.cyan);
		g.drawImage(image, 440, 225+(selection*50), null);
		g.setFont(font);
		
		g.drawString("Music", 350, 250);
		g.drawString(music, 530, 250);
		
		g.drawString("SFX", 350, 300);
		g.drawString(sfx, 530, 300);
	}

	public void keyPressed(int k) {
		if(KeyEvent.VK_BACK_SPACE == k || KeyEvent.VK_ESCAPE == k){
			gsm.sound.makeClickSound();
			gsm.setState(gsm.MENUSTATE);
		}
		if(KeyEvent.VK_UP == k){
			gsm.sound.makeClickSound();
			--selection;
			if(selection<0) selection=1;
		}
		
		if(KeyEvent.VK_DOWN == k){
			gsm.sound.makeClickSound();
			++selection;
			if(selection>1) selection=0;
			
		}
		if(KeyEvent.VK_ENTER==k){
			gsm.sound.makeClickSound();
			if(selection == 0) {
				gsm.sound.setIsMusic(!gsm.sound.getIsMusic());
				gsm.sound.makeMenuSound();
			}
			else gsm.sound.setIsSfx(!gsm.sound.getIsSfx());
		}
		if(KeyEvent.VK_LEFT == k){
			gsm.sound.makeClickSound();
			if(selection == 0) {
				gsm.sound.setIsMusic(!gsm.sound.getIsMusic());
				gsm.sound.makeMenuSound();
			}
			else gsm.sound.setIsSfx(!gsm.sound.getIsSfx());
		}
		
		if(KeyEvent.VK_RIGHT == k){
			gsm.sound.makeClickSound();
			if(selection == 0){
				gsm.sound.setIsMusic(!gsm.sound.getIsMusic());
				gsm.sound.makeMenuSound();
			}
			else gsm.sound.setIsSfx(!gsm.sound.getIsSfx());
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
