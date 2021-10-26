package com.example.enemy;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

import javax.imageio.ImageIO;

import com.example.gameStates.PlayState;
import com.example.manageProcess.Animation;
import com.example.objects.ParentsOfObject;
import com.example.player.Player;
import com.example.window.GameWindow;

public class Enemy extends ParentsOfObject{

	private BufferedImage image;
	private AffineTransform affineT;
	private int id;
	private int column;
	private int life;
	public LinkedList<EnemyBullet> bullet;
	
	private Animation moveAnim;
	private Animation bulletAnim;
	private Animation angleAnim;
	
	private Player player;
	
	public double tempDX;
	public double tempDY;
	
	private Random rand;
	
	private double tAngle;
	private double dAngle;
	private double startSpeed;
	private double moveSpeed;
	private double stopSpeed;
	private double speed;
	
	private double rotateStart;
	private double rotateSpeed;
	private double rotateStop;
	
	private boolean isDecrement;
	private boolean xSelection,ySelection;
	
	private int[] discipleWidth= {55,50,80,80,80};
	private int[] discipleHeight= {55,50,40,55,50};
	private int[] bossWidth= {70,80,80,80,80};
	private int[] bossHeight = {70,60,40,55,60};
	
	private int[] moveBoss={};
	public Enemy(int id,int column,Player p){
		this.id= id;
		this.column = column;
		this.player = p;
		init();
	}

	public void init() {
		
		if(column == 0){
			width= discipleWidth[id];
			height = discipleHeight[ id];
			life = 100;
		}else{
			width = bossWidth[id];
			height = bossHeight[id];
			life = 200;
		}
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Enemy/enemy.png"));
				
			image = image.getSubimage(80*column, 80*id,width, height);
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		angle= 0;
		moveAnim = new Animation();
		moveAnim.setDelay(5000);
		moveAnim.setActive(4000);
		moveAnim.setTime();
		moveAnim.setFlinching(true);
		moveAnim.setFlinchingTimer();
		
		bulletAnim = new Animation();
		bulletAnim.setDelay(4000);
		bulletAnim.setActive(3000);
		bulletAnim.setTime();
		
		angleAnim = new Animation();
		angleAnim.setDelay(5000);
		angleAnim.setActive(2000);
		angleAnim.setTime();
		
		startSpeed = .5;
		moveSpeed = 3+(id*.5);
		stopSpeed = .08;
		
		rotateStart = 1;
		rotateSpeed= 3;
		rotateStop = 1;
		rand = new Random();
		xySelection();
		
		bullet = new LinkedList<EnemyBullet>();
	}
	

	public void update() {
		if(angleAnim.checkTime()){
			angleSet();
		}else{
			decreseAngle();
		}
		if(bulletAnim.checkBullet() ){
			gsm.sound.makeEnemyShootSound();
			bulletAdd();
		}
		if(moveAnim.checkTime()){
			incrementMovement();
		}else{
			decrementMovement();
		}
		
		dXdYSelection();
		
		if(xSelection){
			x -= dx;
		}else{
			x += dx;
		}
		if(ySelection){
			y -= dy;
		}else{
			y += dy;
		}
		x += PlayState.bg.dx+tempDX;
		y += PlayState.bg.dy+tempDY;
		for(int i=0;i<bullet.size();i++){
			bullet.get(i).update();
		}
		areaDetection();
	}
	private void bulletAdd(){
		gsm.sound.makeEnemyShootSound();
		bullet.add(new EnemyBullet(id, column, this));
	}
	private void areaDetection(){
		if(x < 0){
			x = 0;
			xSelection = !xSelection;
		}else if( x> GameWindow.WIDTH-width){
			x = GameWindow.WIDTH-width;
			xSelection = !xSelection;
		}
		
		if(y < 0){
			y = 0;
			ySelection = !ySelection;
		}else if(y > GameWindow.HEIGHT-height){
			y = GameWindow.HEIGHT-height;
			ySelection = !ySelection;
		}
	}
	
	private void dXdYSelection(){
		if(tAngle >=0 ){
			dx = Math.sin(Math.toRadians(90 - tAngle))*speed;
			dy = Math.sin(Math.toRadians(tAngle))*speed;
		}else{
			dx = Math.sin(Math.toRadians(-90 - tAngle))*speed;
			dy = Math.sin(Math.toRadians(tAngle))*speed;
		}
		
	}
	
	private void incrementMovement(){
		isDecrement = true;
		speed += startSpeed;
		if(speed> moveSpeed) speed = moveSpeed;
	}
	
	private void decrementMovement(){
		if(isDecrement){
			if(speed != 0){
				speed -= stopSpeed;
			}
			if(speed > -stopSpeed && speed < stopSpeed){
				speed = 0;
				tAngle = rand.nextInt(180)-180;
				isDecrement = false;
			}
		}
		
	}
	
	private void decreseAngle(){
		if(dAngle > -rotateStop && dAngle < rotateStop){
			dAngle = 0;
		}else if(dAngle > 0){
			 dAngle -= rotateStop;
		}else if(dAngle < 0){
			dAngle += rotateStop;
		}
		
		angle += dAngle;
	}
	private void angleSet(){
		double tDAngle = Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		
		if(tDAngle < 0) tDAngle = 360+tDAngle;
		dAngleSet(tDAngle);
		
	}
	private void dAngleSet(double tDAngle){
		
		if(tDAngle > angle){
			tDAngle = tDAngle - angle;
			
			if(tDAngle < 180) {
				dAngle += rotateStart;
				if(dAngle>rotateSpeed){
					dAngle = rotateSpeed;
				}
				
				if(tDAngle < Math.abs(dAngle)){
					dAngle = tDAngle;
				}
			}else{
				dAngle -= rotateStart;
				if(dAngle<-rotateSpeed){
					dAngle = -rotateSpeed;
				}
				
				if(tDAngle < Math.abs(dAngle)){
					dAngle = -tDAngle;
				}
			}
			
		}else if(tDAngle < angle){
			tDAngle = angle - tDAngle;
			
			if(tDAngle < 180) {
				dAngle -= rotateStart;
				if(dAngle<-rotateSpeed){
					dAngle = -rotateSpeed;
				}
				
				if(tDAngle < Math.abs(dAngle)){
					dAngle = -tDAngle;
				}
				
			}else{
				dAngle += rotateStart;
				if(dAngle>rotateSpeed){
					dAngle = rotateSpeed;
				}
				
				if(tDAngle < Math.abs(dAngle)){
					dAngle = tDAngle;
				}
			}
			
			
			
		}else{
			 dAngle = 0;
		}
		
		angle += dAngle;
		
		angle %= 360;
		
		if(angle < 0) angle = (360 - angle);

	}
	
	public void setLife(int life){
		this.life += life;
	}
	
	public int getLife(){
		return life;
	}

	public void xySelection(){
		
		x = rand.nextInt(width)- rand.nextInt(width);
		y = rand.nextInt(height) - rand.nextInt(height);
	
		x = (x >= 0)? x+GameWindow.WIDTH : x-width;
		y = (y >= 0)? y+GameWindow.HEIGHT : y-height;
	}

	public void draw(Graphics2D g) {
		for(int i=0;i<bullet.size();i++){
			bullet.get(i).draw(g);
		}
		
		affineT = new AffineTransform();
		affineT.translate(x, y);
		affineT.rotate(Math.toRadians(angle),width/2,height/2);
		RoundRectangle2D  rr = new RoundRectangle2D.Float((int)0,(int) 0, width-3, height-3, width-3, height-3);
		if(moveAnim.isFlinching())
			g.drawImage(image,affineT, null);
		//g.draw(affineT.createTransformedShape(rr));
	}

	public Shape getBounds(int i) {
		affineT = new AffineTransform();
		affineT.translate(x, y);
		affineT.rotate(Math.toRadians(angle), width/2, height/2);
		RoundRectangle2D  rr = new RoundRectangle2D.Float((int)0,(int) 0, width-3, height-3, width-3, height-3);
		
		return affineT.createTransformedShape(rr);
	}
}
