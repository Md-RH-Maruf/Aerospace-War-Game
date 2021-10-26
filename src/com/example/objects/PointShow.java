package com.example.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.gameStates.PlayState;
import com.example.player.Player;
import com.example.window.GameWindow;

public class PointShow {
	
	private int lX;
	private int lY;
	private int rX;
	private int rY;
	
	private int width;
	private int height;
	
	private int point;
	
	private int life;
	private int enemy;
	
	private int level;
	
	private int id;
	
	private BufferedImage[] image;
	
	private Font font =new Font("cerif",Font.BOLD,20);
	
	private String enemyS="Enemy= ";
	
	private Player player;
	
	public PointShow(Player p,int level){
		this.player = p;
		this.level = level;
		init();
	}
	
	public void init(){
		image =new BufferedImage[4];
		try {
			image[3] = ImageIO.read(getClass().getResourceAsStream("/point/bullet.png"));
			for(int i=0;i< image.length;i++){
				image[i] = image[3].getSubimage(i*20,0, 20, 20);
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		enemy = 5+(5*level);
		width = 20;
		height = 20;
		life = 80;
		point = PlayState.tempPoint;
		id = player.currentBulletId;
		
		lX = 10;
		lY = 10;
		rX = GameWindow.WIDTH-130;
		rY = 20;
		
	}
	
	public void update(){
		id = player.currentBulletId;
		
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setPoint(int p){
		this.point += p;
	}

	public int getPoint(){
		return point;
	}
	
	public void setEnemy(int enemy){
		this.enemy += enemy;
	}
	
	public void draw(Graphics2D g){
		g.setColor(Color.red);
		g.drawRoundRect(lX+10, lY, 200, 20, 10, 10);
		g.fillRoundRect(lX+10, lY,(int) player.getLife()*2, 20,10,10);
		
		g.drawImage(image[3], lX, lY, null);
		g.drawRoundRect(lX+10, lY+25, 200, 20, 10, 10);
		if(id == 0){
			g.setColor(Color.magenta);
			g.drawImage(image[0], lX, lY+25, null);
			g.fillRoundRect(lX+10, lY+25, player.bullet0Quantity*2, 20, 10, 10);
			
		}else{
			g.setColor(Color.blue);
			g.drawImage(image[1], lX, lY+25, null);
			g.fillRoundRect(lX+10, lY+25, player.bullet1Quantity*20, 20, 10, 10);
		}
			
			
		g.drawImage(image[2], lX, lY+50, null);
		g.setFont(font);
		g.setColor(Color.white);
		g.drawString(String.valueOf(point),lX+30, lY+68);
		
		g.drawString(enemyS	, rX, rY);
		
		g.drawString(String.valueOf(enemy), rX+80, rY);
		
	}

}
