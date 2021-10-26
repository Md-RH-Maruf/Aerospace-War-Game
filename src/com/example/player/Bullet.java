package com.example.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import com.example.gameStates.PlayState;
import com.example.manageProcess.GameStateManager;
import com.example.objects.ParentsOfObject;
import com.example.window.GameWindow;

public class Bullet extends ParentsOfObject{


	public int id;
	private double x1;
	private double x2;
	private double y1;
	private double y2;
	
	public boolean bullet1isActive;
	public boolean bullet2isActive;
	
	private Player player;
	
	public int power;
	
	private int xDistanace;
	public Bullet(Player player,int id){
		this.player = player;
		this.id = id;
		init();
	}



	public void init() {
		xDistanace = 30;
		if (id == 0) {
			moveSpeed = 6;
			xyPositionSet(player.angle, player.height/2);
			power = 10;
		}else if(id == 1){
			moveSpeed = 8;
			bullet1isActive = true;
			bullet2isActive = true;
			power = 20;
			xyPositionSet(player.angle	, player.height/3.6 ,player.height/1.385);
			xyPositionSet(player.angle, player.height/2);
		}
		
		
			
	}
	
	private void xyPositionSet(double theta ,double radius1,double radius2) {
		double tTheta;
		theta %= 360;
		
		tTheta = Math.abs(theta);
		tTheta %= 90;
		
		if(player.site.equals("isRight")){

			if( theta >= 0 && theta <90 ){
				x1 = xDistanace+(Math.sin(Math.toRadians(tTheta))*radius1);
				y1 = radius2+(radius1-(Math.cos(Math.toRadians(tTheta ))*radius1));
				
				x2 = xDistanace+(Math.sin(Math.toRadians(tTheta))*radius2);
				y2 = radius1+(radius2-(Math.cos(Math.toRadians(tTheta ))*radius2));

				dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = Math.sin(Math.toRadians(tTheta))*moveSpeed;

			}else if( theta >= 90 && theta <180 ){
				x1 = xDistanace+(Math.cos(Math.toRadians(tTheta))*radius1);
				y1 = (player.height)+(Math.sin(Math.toRadians(tTheta))*radius1);
				
				x2 = xDistanace+(Math.cos(Math.toRadians(tTheta))*radius2);
				y2 = (player.height)+(Math.sin(Math.toRadians(tTheta))*radius2);

				dx = -Math.sin(Math.toRadians(tTheta))*moveSpeed;
				dy = Math.cos(Math.toRadians(tTheta ))*moveSpeed;
			}else if( theta >= 180 && theta <270 ){
				x1 =xDistanace-( Math.sin(Math.toRadians(tTheta))*radius1);
				y1 = (player.height)+(Math.cos(Math.toRadians(tTheta ))*radius1);
				
				x2 =xDistanace-( Math.sin(Math.toRadians(tTheta))*radius2);
				y2 = (player.height)+(Math.cos(Math.toRadians(tTheta ))*radius2);

				dx =-( Math.cos(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta >= 270 && theta <360 ){
				x1 = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius1);
				y1 = (player.height)-(Math.sin(Math.toRadians(tTheta))*radius1);
				
				x2 = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius2);
				y2 = (player.height)-(Math.sin(Math.toRadians(tTheta))*radius2);

				dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.cos(Math.toRadians(tTheta ))*moveSpeed);
			}else if( theta <= -0 && theta >-90 ){
				x1 =xDistanace-( Math.sin(Math.toRadians(tTheta))*radius1);
				y1 = radius2+(radius1-(Math.cos(Math.toRadians(tTheta))*radius1));
				
				x2 =xDistanace-( Math.sin(Math.toRadians(tTheta))*radius2);
				y2 = radius1+(radius2-(Math.cos(Math.toRadians(tTheta))*radius2));

				dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -90 && theta >-180 ){
				x1 = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius1);
				y1 = (player.height)+(Math.sin(Math.toRadians(tTheta))*radius1);
				
				x2 = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius2);
				y2 = (player.height)+(Math.sin(Math.toRadians(tTheta))*radius2);

				dx = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -180 && theta >-270 ){
				x1 =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius1);
				y1 = (player.height)+ (Math.cos(Math.toRadians(tTheta))*radius1);
				
				x2 =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius2);
				y2 = (player.height)+ (Math.cos(Math.toRadians(tTheta))*radius2);

				dx = -Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -270 && theta >-360 ){
				x1 =xDistanace+ (Math.cos(Math.toRadians(tTheta))*radius1);
				y1 = (player.height)-(Math.sin(Math.toRadians(tTheta ))*radius1);
				
				x2 =xDistanace+ (Math.cos(Math.toRadians(tTheta))*radius2);
				y2 = (player.height)-(Math.sin(Math.toRadians(tTheta ))*radius2);

				dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = (Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}

			x1 = player.rightRotationX +x1;
			y1 = player.rightRotationY +y1;
			
			x2 = player.rightRotationX + x2;
			y2 = player.rightRotationY + y2;

		}
		

		if(player.site.equals("isLeft")){
			if( theta >= 0 && theta <90 ){
				x1 = xDistanace-(Math.sin(Math.toRadians(tTheta))*radius1);
				y1 = (Math.cos(Math.toRadians(tTheta))*radius1);
				
				x2 = xDistanace-(Math.sin(Math.toRadians(tTheta))*radius2);
				y2 = (Math.cos(Math.toRadians(tTheta))*radius2);

				dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = Math.sin(Math.toRadians(tTheta))*moveSpeed;
			}else if( theta >= 90 && theta <180 ){
				x1 =xDistanace- (Math.cos(Math.toRadians(tTheta))*radius1);
				y1 = -(Math.sin(Math.toRadians(tTheta))*radius1);
				
				x2 =xDistanace- (Math.cos(Math.toRadians(tTheta))*radius2);
				y2 = -(Math.sin(Math.toRadians(tTheta))*radius2);

				dx = -Math.sin(Math.toRadians(tTheta))*moveSpeed;
				dy = Math.cos(Math.toRadians(tTheta ))*moveSpeed;
			}else if( theta >= 180 && theta <270 ){
				x1 =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius1);
				y1 =- (Math.cos(Math.toRadians(tTheta))*radius1);
				
				x2 =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius2);
				y2 =- (Math.cos(Math.toRadians(tTheta))*radius2);

				dx =-( Math.cos(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta >= 270 && theta <360 ){
				x1 = xDistanace+Math.cos(Math.toRadians(tTheta))*radius1;
				y1 = (Math.sin(Math.toRadians(tTheta ))*radius1);
				
				x2 = xDistanace+Math.cos(Math.toRadians(tTheta))*radius2;
				y2 = (Math.sin(Math.toRadians(tTheta ))*radius2);

				dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -0 && theta >-90 ){
				x1 =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius1);
				y1 =(Math.cos(Math.toRadians(tTheta))*radius1);
				
				x2 =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius2);
				y2 =(Math.cos(Math.toRadians(tTheta))*radius2);

				dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -90 && theta >-180 ){
				x1 = xDistanace+(Math.cos(Math.toRadians(tTheta))*radius1);
				y1 = -(Math.sin(Math.toRadians(tTheta))*radius1);
				
				x2 = xDistanace+(Math.cos(Math.toRadians(tTheta))*radius2);
				y2 = -(Math.sin(Math.toRadians(tTheta))*radius2);

				dx = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -180 && theta >-270 ){
				x1 = xDistanace-(Math.sin(Math.toRadians(tTheta))*radius1);
				y1 = -(Math.cos(Math.toRadians(tTheta))*radius1);
				
				x2 = xDistanace-(Math.sin(Math.toRadians(tTheta))*radius2);
				y2 = -(Math.cos(Math.toRadians(tTheta))*radius2);

				dx = -Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -270 && theta >-360 ){
				x1 = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius1);
				y1 =  (Math.sin(Math.toRadians(tTheta))*radius1);
				
				x2 = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius2);
				y2 =  (Math.sin(Math.toRadians(tTheta))*radius2);

				dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = (Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}

			x1 = player.leftRotationX+ x1;
			y1 = player.leftRotationY+ y1;
			
			x2 = player.leftRotationX+ x2;
			y2 = player.leftRotationY+ y2;
		}

	}

	private void xyPositionSet(double theta,double radius) {
		double tTheta;
		theta %= 360;
		
		tTheta = Math.abs(theta);
		tTheta %= 90;
		
		if(player.site.equals("isRight")){

			if( theta >= 0 && theta <90 ){
				x = xDistanace+(Math.sin(Math.toRadians(tTheta))*radius);
				y = radius+(radius-(Math.cos(Math.toRadians(tTheta ))*radius));

				dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = Math.sin(Math.toRadians(tTheta))*moveSpeed;

			}else if( theta >= 90 && theta <180 ){
				x = xDistanace+(Math.cos(Math.toRadians(tTheta))*radius);
				y = (radius*2)+(Math.sin(Math.toRadians(tTheta))*radius);

				dx = -Math.sin(Math.toRadians(tTheta))*moveSpeed;
				dy = Math.cos(Math.toRadians(tTheta ))*moveSpeed;
			}else if( theta >= 180 && theta <270 ){
				x =xDistanace-( Math.sin(Math.toRadians(tTheta))*radius);
				y = (radius*2)+(Math.cos(Math.toRadians(tTheta ))*radius);

				dx =-( Math.cos(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta >= 270 && theta <360 ){
				x = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius);
				y = (radius*2)-(Math.sin(Math.toRadians(tTheta))*radius);

				dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.cos(Math.toRadians(tTheta ))*moveSpeed);
			}else if( theta <= -0 && theta >-90 ){
				x =xDistanace-( Math.sin(Math.toRadians(tTheta))*radius);
				y = radius+(radius-(Math.cos(Math.toRadians(tTheta))*radius));

				dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -90 && theta >-180 ){
				x = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius);
				y = (radius*2)+(Math.sin(Math.toRadians(tTheta))*radius);

				dx = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -180 && theta >-270 ){
				x =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius);
				y = (radius *2)+ (Math.cos(Math.toRadians(tTheta))*radius);

				dx = -Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -270 && theta >-360 ){
				x =xDistanace+ (Math.cos(Math.toRadians(tTheta))*radius);
				y = (radius*2)-(Math.sin(Math.toRadians(tTheta ))*radius);

				dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = (Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}

			x = player.rightRotationX +x;
			y = player.rightRotationY +y;

		}

		if(player.site.equals("isLeft")){
			if( theta >= 0 && theta <90 ){
				x = xDistanace-(Math.sin(Math.toRadians(tTheta))*radius);
				y = (Math.cos(Math.toRadians(tTheta))*radius);

				dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = Math.sin(Math.toRadians(tTheta))*moveSpeed;
			}else if( theta >= 90 && theta <180 ){
				x =xDistanace- (Math.cos(Math.toRadians(tTheta))*radius);
				y = -(Math.sin(Math.toRadians(tTheta))*radius);

				dx = -Math.sin(Math.toRadians(tTheta))*moveSpeed;
				dy = Math.cos(Math.toRadians(tTheta ))*moveSpeed;
			}else if( theta >= 180 && theta <270 ){
				x =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius);
				y =- (Math.cos(Math.toRadians(tTheta))*radius);

				dx =-( Math.cos(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta >= 270 && theta <360 ){
				x = xDistanace+Math.cos(Math.toRadians(tTheta))*radius;
				y = (Math.sin(Math.toRadians(tTheta ))*radius);

				dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -0 && theta >-90 ){
				x =xDistanace+( Math.sin(Math.toRadians(tTheta))*radius);
				y =(Math.cos(Math.toRadians(tTheta))*radius);

				dx = Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -90 && theta >-180 ){
				x = xDistanace+(Math.cos(Math.toRadians(tTheta))*radius);
				y = -(Math.sin(Math.toRadians(tTheta))*radius);

				dx = -(Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = -(Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -180 && theta >-270 ){
				x = xDistanace-(Math.sin(Math.toRadians(tTheta))*radius);
				y = -(Math.cos(Math.toRadians(tTheta))*radius);

				dx = -Math.cos(Math.toRadians(tTheta))*moveSpeed;
				dy = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
			}else if( theta <= -270 && theta >-360 ){
				x = xDistanace-(Math.cos(Math.toRadians(tTheta))*radius);
				y =  (Math.sin(Math.toRadians(tTheta))*radius);

				dx = (Math.sin(Math.toRadians(tTheta))*moveSpeed);
				dy = (Math.cos(Math.toRadians(tTheta))*moveSpeed);
			}

			x = player.leftRotationX+ x;
			y = player.leftRotationY+ y;
		}

	}

	public void update() {
		
		x += dx;
		y += dy;
		
		x1 += dx;
		y1 += dy;
		
		x2 += dx;
		y2 += dy;
		
		if(x < -player.width || x> GameWindow.WIDTH+player.width || y < -player.height || y > GameWindow.HEIGHT+player.height){
			player.bullet.remove(this);
		}
	}
	
	public int getPower(){
		return power;
	}

	public void draw(Graphics2D g) {
		
		if(id==0){
			g.setColor(Color.magenta);
			g.fillOval((int)x, (int)y, 3, 3);
		}else{
			g.setColor(Color.blue);
			if(bullet1isActive){
				g.fillOval((int)x1, (int)y1, 4, 4);
			}
			if(bullet2isActive) {
				g.fillOval((int)x2, (int)y2, 4, 4);
			}
			
		}
		

	}

	public Shape getBounds(int i) {
		if(i == 1)
			return new Rectangle((int)x1,(int)y1,4,4);
		else 
			return new Rectangle((int)x2,(int)y2,4,4);
	}
	
	public Shape getBounds(){
		return new Rectangle((int)x,(int)y,3,3);
	}


}
