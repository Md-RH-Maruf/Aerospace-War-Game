package com.example.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import com.example.gameStates.PlayState;
import com.example.manageProcess.GameStateManager;
import com.example.window.GameWindow;

public class Bonus extends ParentsOfObject{
	
	public int id;
	private PlayState ps;
	private BufferedImage image;
	
	private Random rand;
	
	public double tempDX;
	public double tempDY;

	public Bonus(PlayState ps,int x,int y){
		this.x = x;
		this.y = y;
		this.ps = ps;
		this.gsm = gsm;
		init();
	}

	public void init() {
		rand = new Random();
		dx = rand.nextDouble()%2-.8;
		dy = rand.nextDouble()%2-.8;
		id = rand.nextInt(4);
		width = 20 ;
		height = 20;
		tempDX = 0;
		tempDY = 0;
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/point/Bonus.png"));
			
			image = image.getSubimage(id*20, 0, width, height);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	public void update() {
		x += dx + ps.bg.dx + tempDX;
		y += dy + ps.bg.dy + tempDY;
		
		if(x < -GameWindow.WIDTH || x > (2*GameWindow.WIDTH) || y< -GameWindow.HEIGHT || y >(2*GameWindow.HEIGHT)){
			ps.bonus.remove(this);
			
		}
	}

	public void draw(Graphics2D g) {
		
		g.drawImage(image,(int) x,(int) y,width,height, null);
	}

	public Shape getBounds(int i) {
	
		return new Rectangle((int)x,(int)y,width,height);
	}
}
