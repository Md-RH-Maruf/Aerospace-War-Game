package com.example.gameStates;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.example.enemy.Asteroids;
import com.example.manageProcess.GameStateManager;
import com.example.objects.Background;
import com.example.player.Player;

public class MenuState extends GameStatesParents{



	private int currentSelection;
	private Point point;
	private int[] xSpace = {35,15,0,35,35};

	private String[] menu= {"Play","Settings","High Score","Help","Quit"};
	private String title="Aerospace War";
	private Font  titleFont= new Font("Cerif",Font.BOLD,100);

	private Font  menuFont = new Font("cerif",Font.BOLD,30);

	private BufferedImage image;
	public MenuState(GameStateManager gsm){
		super(gsm);
	}

	public void init() {

		try {
			image = ImageIO.read(getClass().getResourceAsStream("/Menu/menu.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update() {

	}


	private void selectMenu(){

		if(currentSelection == 0){
			gsm.setState(gsm.PLAYSTATE);
		}else if(currentSelection == 1){
			gsm.setState(gsm.SETTINGSTATE);
		}else if(currentSelection == 2){
			gsm.setState(gsm.HIGHSCORESTATE);
		}else if(currentSelection == 3){
			gsm.setState(gsm.HELPSTATE);
		}else if(currentSelection == 4){
			System.exit(0);
		}
	}
	public void draw(Graphics2D g) {
		//		bg.draw(g);
		//		for(int i=0;i<8;i++){
		//			asteroids.get(i).draw(g);
		//		}
		g.setFont(titleFont);
		g.setColor(Color.blue);
		g.drawString(title, 180, 200);

		g.drawImage(image, 380, 275+(currentSelection*35),null);
		for(int i=0 ;i<menu.length;i++){
			if(i==currentSelection){
				g.setColor(Color.red);

			}else{
				g.setColor(Color.cyan);

			}
			g.setFont(menuFont);
			g.drawString(menu[i], 420+xSpace[i], 300+(i*35));
		}
	}


	public void keyPressed(int k) {
		if(KeyEvent.VK_UP == k){
			gsm.sound.makeClickSound();
			--currentSelection;
			if(currentSelection < 0){
				currentSelection = menu.length-1;
			}

		}
		if(KeyEvent.VK_DOWN == k){
			gsm.sound.makeClickSound();
			++currentSelection;
			if(currentSelection >= menu.length){
				currentSelection = 0;
			}
		}

		if(KeyEvent.VK_ENTER == k){
			gsm.sound.makeClickSound();
			selectMenu();
		}
	}

	public void keyReleased(int k) {
	}


	public void mouseMoved(Point k) {
		this.point = k;
		for(int i=0;i < menu.length;i++){
			if(new Rectangle(420, 275+(i*35), 240, 34).contains(k)){
				if(currentSelection!=i){
					gsm.sound.makeClickSound();
					currentSelection = i;
				}

			}
		}
	}

	@Override
	public void mouseClick(int k) {
		System.out.println("Clicked");
		if(k == MouseEvent.BUTTON1){
			for(int i=0;i < menu.length;i++){
				if(new Rectangle(420, 275+(i*35), 240, 34).contains(point)){

					gsm.sound.makeClickSound();
					selectMenu();
				}
			}
		}
	}

}
