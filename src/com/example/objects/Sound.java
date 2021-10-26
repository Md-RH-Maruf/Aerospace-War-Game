package com.example.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.example.manageProcess.Audio;

public class Sound {

	
	private Audio shootSound;
	private Audio moveSound;
	private Audio enemyShootSound;
	private Audio clickSound;
	private Audio bulletHit;

	private Audio gameOverSound;
	private Audio congratulationSound;
	private Audio menuSound;
	private Audio flinchingSound;
	
	private boolean isMusic;
	private boolean isSfx;
	
	private Scanner scan;
	private PrintWriter pw;
	private StringTokenizer tkn;
	private String s;
	public Sound(){
		
		read();
		shootSound = new Audio("/Sound/bulletwhizzing.mp3");
		moveSound = new Audio("/Sound/thrust.wav");
		enemyShootSound = new Audio("/Sound/enemyshoot.wav");
		clickSound = new Audio("/Sound/click.mp3");
		bulletHit = new Audio("/Sound/bulletHitSounds.mp3");
		gameOverSound = new Audio("/Sound/game-over03.wav");
		congratulationSound = new Audio("/Sound/gamestart.mp3");
		menuSound = new Audio("/Sound/Game-Menu.mp3");
		flinchingSound = new Audio("/Sound/flinchingSound.mp3");
		
	}
	
	public void makeShootSound(){
		if(isSfx){
			shootSound.play();
		}
	}
	
	public void makeMoveSound(){
		if(isSfx){
			moveSound.play();
		}
	}
	
	public void makeEnemyShootSound(){
		if(isSfx){
			enemyShootSound.play();
		}
	}
	
	public void makeClickSound(){
		if(isSfx){
			clickSound.play();
		}
	}
	
	public void makeBulletHit(){
		if(isSfx){
			bulletHit.play();
		}
	}
	
	public void makeAsteroidHit(){
		if(isSfx){
			bulletHit.play();
		}
	}
	
	public void makeGameOverSound(){
		if(isSfx){
			gameOverSound.play();
		}
	}
	
	public void makeCongratulationSound(){
		if(isSfx){
			congratulationSound.play();
		}
	}
	
	public void makeMenuSound(){
		if(isMusic){
			menuSound.play();
		}else{
			menuSound.stop();
		}
	}
	
	public void makeFlinchingSound(){
		if(isSfx){
			flinchingSound.play();
		}
	}
	
	public void read(){
		try {
			scan = new Scanner(new File("Resource/TextFile/Sound.txt"));
			while(scan.hasNext()){
				tkn = new StringTokenizer(scan.nextLine(),":");
				
				
				
				if(tkn.nextToken().equals("Music")){
					//System.out.println("scan="+tkn.nextToken()+"hj");
					isMusic = Boolean.valueOf(tkn.nextToken());
				}else{
					//System.out.println("sca="+tkn.nextToken()+"hj");
					isSfx = Boolean.valueOf(tkn.nextToken());
				}
				
				System.out.println("out="+isMusic+"  "+isSfx);
				
			}
			
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}
	

	
	public void write(){
		try {
			pw = new PrintWriter(new File("Resource/TextFile/Sound.txt"));
			pw.write("Music:"+String.valueOf(isMusic)+"\n"+"Sfx:"+String.valueOf(isSfx));
			pw.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
	}
	public boolean getIsMusic(){
		return this.isMusic;
	}
	
	public boolean getIsSfx(){
		return this.isSfx;
	}
	
	public void setIsMusic(boolean isMusic){
		this.isMusic = isMusic;
		write();
	} 
	
	public void setIsSfx(boolean isSfx){
		this.isSfx = isSfx;
		write();
	}
	
	
}
