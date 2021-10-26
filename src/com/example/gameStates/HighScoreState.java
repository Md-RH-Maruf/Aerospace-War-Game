package com.example.gameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.example.manageProcess.GameStateManager;

public class HighScoreState extends GameStatesParents{

	private static Scanner scan;
	private static String[] showScore;
	private static int[] score;
	private static StringTokenizer tkn;
	private static String title="High Score";
	
	private static PrintWriter pw;
	public HighScoreState(GameStateManager gsm){
		super(gsm);
	}

	public void init() {
		
		loadScore();
	}

	public static void loadScore(){
		try {
			scan= new Scanner(new File("Resource/TextFile/HighScore.txt"));
			showScore=new String[10];
			score= new int[10];
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
		for(int i=0;scan.hasNext();i++){
			showScore[i]=(scan.nextLine());
			tkn= new StringTokenizer(showScore[i]);
			tkn.nextToken();

			score[i]= (tkn.hasMoreTokens()?Integer.valueOf(tkn.nextToken()):0);
			//System.out.println(score[i]);
		}
	}
	public static boolean setHighScore(int tempScore){
		try{
			loadScore();
			for(int i=0;i< 10;i++){
				
				if(score[i] < tempScore){
					for(int j=9;j >i;j--){
						score[j] = score[j-1];
					}
					score[i]=tempScore;

					pw = new PrintWriter("Resource/TextFile/HighScore.txt");

					for(int k=0;k<10;k++){
						pw.write((k+1)+": "+score[k]+"\n");
					}
					pw.close();
					return true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	public void update() {
		
	}

	public void draw(Graphics2D g) {
		g.setFont(new Font("Traditional Arabic",Font.BOLD,40));
		g.setColor(Color.BLUE);
		g.drawString(title, 360, 150);

		g.setColor(Color.cyan);
		g.setFont(new Font("Traditional Arabic",Font.BOLD,20));
		for(int i=0 ; i< 10;i++){
			g.drawString(showScore[i], 410, 190+(i*30));
		}
	}

	
	public void keyPressed(int k) {
		if(k == KeyEvent.VK_ENTER || k == KeyEvent.VK_BACK_SPACE || k == KeyEvent.VK_ESCAPE){
			gsm.sound.makeClickSound();
			gsm.setState(gsm.MENUSTATE);
		}
	}


	public void keyReleased(int k) {
		
	}

	@Override
	public void mouseMoved(Point k) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClick(int k) {
		// TODO Auto-generated method stub
		
	}
}
