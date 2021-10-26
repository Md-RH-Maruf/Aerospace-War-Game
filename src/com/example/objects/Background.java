package com.example.objects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.manageProcess.GameStateManager;
import com.example.player.Player;
import com.example.window.GameWindow;

public class Background extends ParentsOfObject{


	private BufferedImage bgImage;
	private BufferedImage rayImage;
	
	public double tempDX;
	public double tempDY;
	


	private final int IMAGE_WIDTH = 1400;
	private final int IMAGE_HEIGHT = 1050;
	public Background( GameStateManager gsm){
		this.gsm = gsm;
		init();
	}


	public void init() {
		moveSpeed = -.5;
		angle = 0;
		dx =  Math.cos(Math.toRadians(angle))*moveSpeed;
		dy =  Math.sin(Math.toRadians(angle))*moveSpeed;
		
				
		try {
			
			bgImage = ImageIO.read(getClass().getResourceAsStream("/Background/background.png"));
			rayImage = ImageIO.read(getClass().getResourceAsStream("/Background/rayImage.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tempDX = 0;
		tempDY = 0;
	}
	public void update() {
		angle =Player.angle;
		dx =  Math.cos(Math.toRadians(angle))*moveSpeed;
		dy =  Math.sin(Math.toRadians(angle))*moveSpeed;
		
		
		x += dx + tempDX;
		y += dy + tempDY;
		
		if(x < -IMAGE_WIDTH){
			x += IMAGE_WIDTH;
		}else if( x > IMAGE_WIDTH){
			x -= IMAGE_WIDTH;
		}
		
		if( y < -IMAGE_HEIGHT){
			y += IMAGE_HEIGHT;
		}else if( y > IMAGE_HEIGHT){
			y -= IMAGE_HEIGHT;
		}
	}
	public void draw(Graphics2D g) {
		
		g.drawImage(bgImage,(int)x	,(int) y, null);
		
		if(x > 0 ){
			
			g.drawImage(bgImage,(int) -(IMAGE_WIDTH- x), (int)y,null);
			
		}else if(x < -(IMAGE_WIDTH - GameWindow.WIDTH)){
			g.drawImage(bgImage, (int)(GameWindow.WIDTH - (-(IMAGE_WIDTH - GameWindow.WIDTH)-x)),(int) y, null);
		}
		
		if(y > 0){
			g.drawImage(bgImage, (int)x,(int)-(IMAGE_HEIGHT - y), null );
		}else if( y < -(IMAGE_HEIGHT - GameWindow.HEIGHT)){
			g.drawImage(bgImage, (int) x, (int)(GameWindow.HEIGHT - (-(IMAGE_HEIGHT - GameWindow.HEIGHT)-y)), null);
		}
		
		if(x > 0 && y > 0){
		g.drawImage(bgImage, (int) - (IMAGE_WIDTH - x), (int)-(IMAGE_HEIGHT- y), null);
		}else if( x > 0 && y < -(IMAGE_HEIGHT - GameWindow.HEIGHT)){
			g.drawImage(bgImage, (int)-(IMAGE_WIDTH - x),(int)(GameWindow.HEIGHT - (-(IMAGE_HEIGHT - GameWindow.HEIGHT)-y)) , null);
		}else if( x < -(IMAGE_WIDTH - GameWindow.WIDTH) && y > 0){
			g.drawImage(bgImage, (int)(GameWindow.WIDTH - (-(IMAGE_WIDTH - GameWindow.WIDTH)-x)), (int)-(IMAGE_HEIGHT - y), null);
		}else if(x < -(IMAGE_WIDTH - GameWindow.WIDTH) && y < -(IMAGE_HEIGHT - GameWindow.HEIGHT)){
			g.drawImage(bgImage, (int)(GameWindow.WIDTH - (-(IMAGE_WIDTH - GameWindow.WIDTH)-x)), (int)(GameWindow.HEIGHT - (-(IMAGE_HEIGHT - GameWindow.HEIGHT)-y)), null);
		}
		
		g.drawImage(rayImage, 0, 0, null);
		
	}


	@Override
	public Shape getBounds(int i) {
		
		return null;
	}
	

}
