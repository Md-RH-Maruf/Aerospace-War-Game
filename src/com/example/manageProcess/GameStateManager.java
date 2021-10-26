package com.example.manageProcess;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import com.example.enemy.Asteroids;
import com.example.gameStates.GameStatesParents;
import com.example.gameStates.HelpState;
import com.example.gameStates.HighScoreState;
import com.example.gameStates.MenuState;
import com.example.gameStates.PlayState;
import com.example.gameStates.SettingState;
import com.example.objects.Background;
import com.example.objects.ParentsOfObject;
import com.example.objects.Sound;
import com.example.player.Player;

public class GameStateManager {
	
	private Background bg;
	private ArrayList<Asteroids> asteroids;
	private Player player;
	
	private GameStatesParents[] states;
	public static final int MENUSTATE = 0 ;
	public static final int PLAYSTATE = 1 ;
	public static final int HIGHSCORESTATE = 2;
	public static final int HELPSTATE = 3;
	public static final int SETTINGSTATE = 4;
	
	public static int currentState;
	
	public static Sound sound;
	
	public GameStateManager(){
		init();
	}
	
	public void init(){
		sound = new Sound();
		states = new GameStatesParents[5];
		states[MENUSTATE] = new MenuState(this);
		states[PLAYSTATE] = new PlayState(this);
		states[SETTINGSTATE] = new SettingState(this);
		states[HELPSTATE] = new HelpState(this);
		states[HIGHSCORESTATE] = new HighScoreState(this);
		
		currentState = MENUSTATE;
		setState(currentState);
		if(currentState != PLAYSTATE){
			player = new Player(this);
			bg = new Background(this);
			asteroids = new ArrayList<Asteroids>();
			for(int i=0;i<8;i++){
				asteroids.add(new Asteroids(this,player));
			}
		}
		sound.makeMenuSound();
		
	}
	
	public void setState(int state){
		
		loadState(state);
	}
	
	public void loadState(int state){
		
		currentState  = state;
		if(currentState == MENUSTATE){
			states[currentState] = new MenuState(this);
		}else if(currentState == PLAYSTATE){
			System.out.println("Initialize="+currentState);
			states[currentState] = new PlayState(this);
		}else if(currentState == SETTINGSTATE){
			states[currentState] = new SettingState(this);
		}else if(currentState == HIGHSCORESTATE){
			states[currentState] = new HighScoreState(this);
		}else if(currentState == HELPSTATE){
			states[currentState] = new HelpState(this);
		}
	}
	
	public void update(){
		
		states[currentState].update();
		if(currentState != PLAYSTATE){
			bg.update();
			for(int i=0;i<8;i++){
			asteroids.get(i).update();
			}
		}
		
	}
	
	public void draw(Graphics2D g){
		
		if(currentState != PLAYSTATE){
			bg.draw(g);
			for(int i=0;i<8;i++){
				asteroids.get(i).draw(g);
			}
		}
		states[currentState].draw(g);
		
	}
	
	public void keyPressed(int k){
		states[currentState].keyPressed(k);
		
	}
	
	public void keyReleased(int k){
		states[currentState].keyReleased(k);
	}
	
	public void mouseMoved(Point k){
		states[currentState].mouseMoved(k);
	}
	
	public void mouseClick(int k){
		states[currentState].mouseClick(k);
	}

}
