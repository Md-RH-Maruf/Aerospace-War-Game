package com.example.enemy;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.spec.EllipticCurve;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.management.loading.PrivateClassLoader;
import javax.swing.Box.Filler;

import com.example.gameStates.PlayState;
import com.example.manageProcess.GameStateManager;
import com.example.objects.ParentsOfObject;
import com.example.player.Player;
import com.example.window.GameWindow;

public class Asteroids extends ParentsOfObject{

	private BufferedImage image;
	private AffineTransform at;
	
	private Random rand;
	public double tempDX;
	public double tempDY;
	
	private int id;
	private Shape shape;

	private Player player;
	private int life;

	
	public Asteroids(GameStateManager gsm,Player p){
		this.gsm = gsm;
		this.player = p;
		init();
		
	}
	
	public void init() {
		rand = new Random();
		width = 50+rand.nextInt(80);
		height = 50+rand.nextInt(80);
		id = rand.nextInt(4)+1;
		try {
			
				image = scale(ImageIO.read(getClass().getResourceAsStream("/Asteroids/astorids"+id+".png")));
			
			
		} catch (IOException e) {

			e.printStackTrace();
		}
	
		
		
		
		moveSpeed = rand.nextInt(1)+1;
		tempDX = 0;
		tempDY = 0;
		xySelection();
		dXdYSelection();
		
		life = 100;
		
	}
	
	private BufferedImage scale(BufferedImage image){
		Image temp=image.getScaledInstance(width, height, Image.SCALE_FAST);
		BufferedImage dimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d= dimage.createGraphics();
		g2d.drawImage(temp, 0, 0, null);
		g2d.dispose();
		
		return dimage;
	}
	
	private void dXdYSelection(){
		
		angle=(Math.abs(Math.toDegrees(Math.atan2(player.y - y , player.x - x)))+rand.nextInt(5)) % 90;
		
		if(player.x > x){
			dx = moveSpeed * Math.cos(Math.toRadians(angle)) ;
		}else if(player.x < x){
			dx = -moveSpeed * Math.cos(Math.toRadians(angle)) ;
		}else
			dx = 0;
		
		if(player.y > y){
			dy = moveSpeed * Math.sin(Math.toRadians(angle)) ;
		}else if(player.y < y){
			dy = -moveSpeed * Math.sin(Math.toRadians(angle)) ;
		}else
			dy = 0;
	}
	
	public void setLife(int life){
		this.life += life;
	}
	
	public int getLife(){
		return this.life;
	}
	
	public void xySelection(){
		life = 100;
		x = rand.nextInt(width)- rand.nextInt(width);
		y = rand.nextInt(height) - rand.nextInt(height);
	
		x = (x >= 0)? x+GameWindow.WIDTH : x-width;
		y = (y >= 0)? y+GameWindow.HEIGHT : y-height;
	}
	
	private void reverseDxDySelection(){
		if(((x+width+5) < -GameWindow.WIDTH && dx < 0) ||(( x -5)>(2*GameWindow.WIDTH) && dx > 0)){
			
			xySelection();
			dXdYSelection();
			moveSpeed = rand.nextInt(1)+1;
		}else if(((y+height+5) < -GameWindow.HEIGHT && dy < 0) ||(( y -5)>(2*GameWindow.HEIGHT) && dy > 0)){
			xySelection();
			dXdYSelection();
			moveSpeed = rand.nextInt(1)+1;
		}
	}

	public void update() {
		if(gsm.currentState == gsm.PLAYSTATE){
			x += dx + PlayState.bg.dx + tempDX;
			y += dy + PlayState.bg.dy + tempDY;
		}else{
			x += dx;
			y += dy;
		}
		//System.out.println("x= "+x+" y="+y+" dx="+dx+" dy="+dy);		
		reverseDxDySelection();
		
	}

	public void draw(Graphics2D g) {
		at = new AffineTransform();
		
		at.translate(x, y);
		
		g.drawImage(image,at,null);
		
	}
	
	
	public Shape getBounds(int i) {
		
		at = new AffineTransform();
		at.translate(x, y);
		at.rotate(Math.toRadians(angle), width/2, height/2);
		RoundRectangle2D  rr = new RoundRectangle2D.Float((int)0,(int) 0, width, height, width, height);
		
		return at.createTransformedShape(rr);
	}


}
