package com.example.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import com.example.objects.ParentsOfObject;
import com.example.window.GameWindow;

public class EnemyBullet extends ParentsOfObject{

	private Enemy enemy;
	public int power;
	private int id;
	private int column;
	
	private Color[] bulletColor={new Color(255,252,0),new Color(0,255,18),new Color(255,192,0),new Color(44,6,255),new Color(255,6,6)};
	private Color[] BuletColor={new Color(255,3,11),new Color(0,255,234),new Color(255,48,0),new Color(6,255,65),new Color(255,6,223)};
	
	private int[] wid ={2,2,3,3,4};
	private int[] bossWid={3,3,4,4,5};
	private double[] speed={5,5.5,6,6.5,7};
	
	private int[] dicipleRadius = {0,0,34,22,17};
	private int[] bossRadius = {0,25,34,36,24};
	private int radius;
	private int xDistance;
	private int yDistance;
	private Color color;
	
	public EnemyBullet(int id,int column,Enemy e){
		this.id = id;
		this.column = column;
		this.enemy = e;
		
		init();
		
	}
	public void init() {
		if(column == 0){
			color = bulletColor[id];
			width = height = wid[id];
			power = 5+(2*id);
			radius = dicipleRadius[id];
		}else{
			color = BuletColor[id];
			width = height = bossWid[id];
			power = 8+(3*id);
			radius = bossRadius[id];
		}
		xDistance = enemy.width/2;
		yDistance = enemy.height/2;
		moveSpeed = speed[id];
		xyPositionSet(enemy.angle, radius);
	}
	
	private void xyPositionSet(double theta,double radius) {
		double tTheta;
		theta %= 360;
		
		tTheta = Math.abs(theta);
		tTheta %= 90;
		
		
		if( theta >= 0 && theta <90 ){
			x = xDistance+(Math.cos(Math.toRadians(tTheta))*radius);
			y = yDistance+(Math.sin(Math.toRadians(tTheta))*radius);

			dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
			dy = Math.sin(Math.toRadians(tTheta))*moveSpeed;
		}else if( theta >= 90 && theta <180 ){
			x =xDistance- (Math.sin(Math.toRadians(tTheta))*radius);
			y =yDistance +(Math.cos(Math.toRadians(tTheta))*radius);

			dx = -Math.sin(Math.toRadians(tTheta))*moveSpeed;
			dy = Math.cos(Math.toRadians(tTheta ))*moveSpeed;
		}else if( theta >= 180 && theta <270 ){
			x =xDistance-( Math.cos(Math.toRadians(tTheta))*radius);
			y =yDistance-(Math.sin(Math.toRadians(tTheta))*radius);

			dx =-( Math.cos(Math.toRadians(tTheta))*moveSpeed);
			dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
		}else if( theta >= 270 && theta <360 ){
			x = xDistance+Math.sin(Math.toRadians(tTheta))*radius;
			y = yDistance-(Math.cos(Math.toRadians(tTheta ))*radius);

			dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
			dy = -(Math.cos(Math.toRadians(tTheta))*moveSpeed);
		}
		
			x = enemy.x+ x;
			y = enemy.y+ y;

	}
	public void update() {
		x += dx;
		y += dy;
		
		if(x < -enemy.width || x> GameWindow.WIDTH+enemy.width || y < -enemy.height || y > GameWindow.HEIGHT+enemy.height){
			enemy.bullet.remove(this);
		}
	}
	public void draw(Graphics2D g) {
		g.setColor(color);
		g.fillOval((int)x, (int)y, width, height);
	}
	public Shape getBounds(int i) {
		return new Rectangle((int)x,(int)y,width,height);
	}
}
