package com.example.player;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.manageProcess.Animation;
import com.example.manageProcess.GameStateManager;
import com.example.objects.ParentsOfObject;

public class Ray extends ParentsOfObject{

	private BufferedImage bi;
	private BufferedImage[] frames;
	private AffineTransform afineT;
	private Animation animation;
	private String site;
	private Player player;

	private int lrX;
	private int lrY;
	private int rrX;
	private int rrY;
	public Ray(Player player, int state){

		this.player = player;
		init();
		animation = new Animation();
		animation.setFrames(frames);

		animation.setCurrentFrame(state);
		animation.setDelay(50);
	}

	public void init() {
		frames = new BufferedImage[6];

		try {
			bi = ImageIO.read(getClass().getResourceAsStream("/Player/ray.png"));

			for(int i = 0 ;i < 6;i++){
				frames[i] = bi.getSubimage(i*25, 0, 25, 40);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		angle = player.angle;

		height = player.getHeight() / 6 ;

		width = (height * 25)/ 40;

		site = player.getSite();

		lrX=rrX = 30;
		rrY = (player.height/2)+(height/2);
		lrY =-((player.height/2)-(height/2));

	}
	private void dXdYPositionUpdate(double tAngle) {

		angle= tAngle %= 360; 

		if(tAngle < 0){
			tAngle = -tAngle;
		}

		tAngle %= 90;
		if(angle >= 0){
			if((angle >= 0 && angle <90) ){
				dx = -(Math.cos(Math.toRadians(tAngle)) * width);
				dy = -(Math.sin(Math.toRadians(tAngle)) * width);
			}else if((angle >= 90 && angle <180)){
				dx = (Math.sin(Math.toRadians(tAngle)) * width);
				dy = -(Math.cos(Math.toRadians(tAngle)) * width);
			}else if((angle >= 180 && angle <270) ){
				dx = (Math.cos(Math.toRadians(tAngle)) * width);
				dy = (Math.sin(Math.toRadians(tAngle)) * width);
			}else if((angle >= 270 && angle <= 360) ){
				dx = -(Math.sin(Math.toRadians(tAngle)) * width);
				dy = (Math.cos(Math.toRadians(tAngle)) * width);
			}
		}else if(angle < 0){
			if((angle <= -270 && angle >=-360)){
				dx = -(Math.sin(Math.toRadians(tAngle)) * width);
				dy = -(Math.cos(Math.toRadians(tAngle)) * width);
			}else if(  (angle <= -180 && angle >-270)){
				dx = (Math.cos(Math.toRadians(tAngle)) * width);
				dy = -(Math.sin(Math.toRadians(tAngle)) * width);
			}else if( (angle <= -90 && angle >-180)){
				dx = (Math.sin(Math.toRadians(tAngle)) * width);
				dy = (Math.cos(Math.toRadians(tAngle)) * width);
			}else if( (angle <= -0 && angle >-90)){
				dx = -(Math.cos(Math.toRadians(tAngle)) * width);
				dy = (Math.sin(Math.toRadians(tAngle)) * width);
			}
		}


	}

	public void update() {


		animation.update();
		if(animation.getCurrentFrame()==0){

			x = player.x;
			y = player.y+(player.height/2 - height/2);

			angle = player.angle;
			site = player.site;
			dXdYPositionUpdate(angle);
		}else{
			x += dx;
			y += dy;
		}

	}

	public void setX( double x){
		this.x = x;
	}
	public void setY( double y){
		this.y = y;
	}
	public void draw(Graphics2D g) {
		afineT = new AffineTransform();

		if(site.equals("isLeft")){
			afineT.translate(x, y);
			afineT.rotate(Math.toRadians(angle), lrX, lrY);
		}else if(site.equals("isRight")){  
			afineT.translate(x, y);
			afineT.rotate(Math.toRadians(angle), rrX, rrY);
		}

		afineT.scale((double) width/animation.getImage().getWidth(), (double)height/animation.getImage().getHeight());

		g.drawImage( animation.getImage(),afineT, null);
	}

	
	@Override
	public Shape getBounds(int i) {
		// TODO Auto-generated method stub
		return null;
	}

}
