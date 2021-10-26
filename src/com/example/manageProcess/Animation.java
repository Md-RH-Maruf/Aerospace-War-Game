package com.example.manageProcess;

import java.awt.Image;
import java.awt.image.BufferedImage;

import com.example.player.Player;

public class Animation {

	private BufferedImage[] frames;
	private int currentFrame;

	private static double angle;

	private long startTime;
	private long delay;
	private long flinchingTimer;
	private long active;
	private long bulletTime;
	
	private boolean isFlinching;

	public void update(double angle){
		if(angle >= 0)	currentFrame = (int) angle/5;
		else if(angle<= 0)	currentFrame =(int)( 19-(angle/5));

	}

	public void update(){
		if(delay == -1) return ;

		long elapsed = (System.nanoTime() - startTime) / 1000000;

		if(elapsed > delay){
			currentFrame++;
			startTime = System.nanoTime();
		}

		if(currentFrame == frames.length){
			currentFrame = 0;

		}
	}

	public boolean updateTime(){

		if(delay == -1) return false;

		long elapsed = (System.nanoTime() - startTime) / 1000000;

		if(elapsed > delay){
			startTime = System.nanoTime();
			return true;
		}
		return false;
	}
	
	public boolean isFlinching(){
		if(isFlinching){
			double elapsed = (System.nanoTime() - flinchingTimer) / 1000000;

			if( elapsed > 2100){
				isFlinching = false;
				flinchingTimer= 0;
			}
			if((elapsed> 0 && elapsed< 300) || (elapsed> 600 && elapsed< 900) ||(elapsed> 1200 && elapsed< 1500)||(elapsed> 1800 && elapsed< 2100)){				
				return false;
			}

		}
		return true;
	}

	public boolean checkTime(){

		if(delay == -1) return false;

		long elapsed = (System.nanoTime() - startTime) / 1000000;
		
		if(elapsed > (delay)){
			startTime = System.nanoTime();
		}
		if(elapsed > active){
			return true;
		}
		return false;
	}

	public boolean checkBullet(){

		if(delay == -1) return false;

		long elapsed = (System.nanoTime() - startTime) / 1000000;

		if(elapsed > (delay)){
			startTime = System.nanoTime();
			bulletTime= System.nanoTime();
		}
		if(elapsed > active){
			if(((System.nanoTime() - bulletTime) / 1000000) >100){
				bulletTime = System.nanoTime();
				return true;
				
			}
				
		}
		return false;
	}
	
	public void setFrames(BufferedImage[] frames){
		this.frames = frames;
		currentFrame = 0;
		startTime = System.nanoTime();

	}

	public void setTime(){
		startTime=System.nanoTime();
	}

	public void setDelay(long delay){
		this.delay  = delay;
	}
	
	public void setActive(long active){
		this.active = active;
	}
	
	public void setFlinching(boolean isFlinching){
		this.isFlinching = isFlinching;
	}
	
	public boolean getFlinching(){
		return this.isFlinching;
	}

	public void setFlinchingTimer(){
		this.flinchingTimer = System.nanoTime();
	}
	public BufferedImage getImage(){return frames[currentFrame];}

	public void setCurrentFrame(int currentFrame){
		this.currentFrame = currentFrame;
	}

	public int getCurrentFrame(){
		return currentFrame;
	}
}
