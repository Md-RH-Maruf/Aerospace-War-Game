package com.example.objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.example.gameStates.PlayState;
import com.example.manageProcess.Animation;

public class Welcome {



	private int timeCount;

	private int level;
	private Font titleFont =new Font("Hyperspace Bold.ttf",Font.BOLD,40);
	private Font secondFont =new Font("Hyperspace Bold.ttf",Font.BOLD,50);
	private Font pointFont =new Font("Hyperspace Bold.ttf",Font.BOLD,20);
	private Font hintsFont= new Font("Hyperspace Bold.ttf",Font.PLAIN,10);
	private Font puseFont =new Font("Hyperspace Bold.ttf",Font.BOLD,50);

	private boolean isWelcome;
	private boolean isPause;
	private boolean isStart;
	private boolean isGameOver;
	private boolean isCongratulation;

	private Animation animation;

	private PlayState playState;

	public Welcome(int level,PlayState playstat){
		this.level = level;
		this.playState = playstat;
		init();
	}

	private void init(){


		timeCount = 3;
		animation = new Animation();
		animation.setDelay(1000);
	}

	public void update(){

		if(isWelcome  || isCongratulation || isStart){
			if(animation.updateTime()){
				timeCount--;
			}
			if(timeCount==-1){
				isWelcome = false;
				isCongratulation = false;
				isStart= false;
				isPause = false;
				playState.setStrat(true);
			}
		}
	}

	public void setWelcome(boolean welcome){

		this.isWelcome = welcome;
		animation.setTime();
	}

	public void setIsCongratulation(boolean cong){
		this.isCongratulation = cong;
		animation.setTime();
	}

	public void setIsGameOver(boolean gameOver){
		this.isGameOver = gameOver;
	}

	public void setIspause(boolean pause){
		this.isPause = pause;

		if(this.isPause) playState.setStrat(false);

	}

	public void setIsStart(boolean isStart){
		this.isStart = isStart;
	}

	public void draw(Graphics2D g){

		g.setColor(Color.cyan);

		if(isCongratulation){

			g.setFont(titleFont);
			g.drawString("Congratulation!", 410, 250);

			g.setFont(pointFont);
			g.drawString("You have reached level="+(1+level), 420, 300);


			if(timeCount!=0){
				g.setFont(secondFont);
				g.drawString(String.valueOf(timeCount), 510, 350);
			}
			else{
				g.setFont(puseFont);
				g.drawString("Start", 490, 350);
			}
		}
		else if(isWelcome){

			g.setFont(titleFont);
			g.drawString("Welcome", 450, 250);

			g.setFont(pointFont);
			g.drawString("level = "+" "+(1+level), 480, 300);


			if(timeCount!=0){
				g.setFont(secondFont);
				g.drawString(String.valueOf(timeCount), 500, 350);
			}
			else{
				g.setFont(puseFont);
				g.drawString("Start", 480, 350);
			}

		}else if(isPause){
			timeCount = 3;
			g.setFont(puseFont);
			g.drawString("Pause", 440	, 380);

			g.setFont(hintsFont);
			g.drawString("Press 'Enter' key For start.", 450, 400);
		}else if(isGameOver){
			g.setFont(titleFont);
			g.drawString("Game Over!", 410	, 350);

			g.setFont(pointFont);
			g.drawString("Your Score is= "+playState.point.getPoint(), 425, 380);

			g.setFont(hintsFont);
			g.drawString("Press 'R' key for restart or 'Esc' key for Exit", 420, 410);
		}else if(isStart){
			if(timeCount!=0){
				g.setFont(secondFont);
				g.drawString(String.valueOf(timeCount), 510, 350);
			}
			else{
				g.setFont(puseFont);
				g.drawString("Start", 490, 350);
			}
		}
	}
}
