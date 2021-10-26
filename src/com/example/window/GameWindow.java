package com.example.window;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.example.manageProcess.GameStateManager;

public class GameWindow extends JPanel implements Runnable,KeyListener,MouseMotionListener,MouseListener{

	
	public static int WIDTH;
	public static int HEIGHT;
	
	private Thread thread;
	
	public static boolean isRunning;
	
	private BufferedImage image;
	private Graphics2D g2d;
	
	
	private GameStateManager GSManager;

	private Dimension size = Toolkit.getDefaultToolkit().getScreenSize();

	private long start;
	private long elapsed;
	private long wait;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	public GameWindow(){
		setPreferredSize(setDimention(4,3,1400,1050));
		setFocusable(true);
		//requestFocus();
		setBackground(Color.black);
		if(thread == null){
			thread =new Thread(this);
			init();
			addKeyListener(this);
			addMouseMotionListener(this);
			addMouseListener(this);
			isRunning = true;
			thread.start();
			
		}
		
	}

	
	public void run() {
		
		requestFocus();
		
		
		while(isRunning){
			
			start = System.nanoTime();
			
			update();
			repaint();
			
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			
			if(wait < 0) wait = 0;
			
			try{
				thread.sleep(wait);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	private void init(){
		
		GSManager = new GameStateManager();
		
	}
	
	private void update(){
	
		GSManager.update();
	}
	
	public void paintComponent(Graphics g ){
		
		
		super.paintComponent(g);
		g2d = (Graphics2D) g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, WIDTH, HEIGHT);
		
		GSManager.draw(g2d);
		g2d.dispose();
		
	}
	

	//Method of getScreenSize on fixed ratio
	public Dimension setDimention(double ratioX,double ratioY,double maximumX,double maximumY){

		if((ratioX/ratioY) < (double)size.width/size.height){
			if(maximumY< size.getHeight()){
				HEIGHT=(int) maximumY;
			}else
				HEIGHT =(int) size.getHeight();

			WIDTH =(int)( (ratioX * HEIGHT) / ratioY );

		}else if((ratioX/ratioY) >  (double)size.width/size.height){
			if(maximumX < size.getWidth()){
				WIDTH=(int) maximumX;
			}else
				WIDTH = (int)size.getWidth();

			HEIGHT =(int)( (ratioY * WIDTH) / ratioX );


		}else{
			if(maximumX> size.getWidth() || maximumY > size.getHeight()){

			}
			WIDTH = (int)size.getWidth();
			HEIGHT = (int)size.getHeight();

		}
		System.out.println("width="+	WIDTH +" Height=" + HEIGHT);
		return new Dimension((int)WIDTH,(int)HEIGHT-40);
	}

	public void keyPressed(KeyEvent k) {
		GSManager.keyPressed(k.getKeyCode());
	}
	public void keyReleased(KeyEvent k) {

		GSManager.keyReleased(k.getKeyCode());
	}
	public void keyTyped(KeyEvent arg0) {

	}


	public void mouseDragged(MouseEvent arg0) {
		
	}


	public void mouseMoved(MouseEvent k) {
		GSManager.mouseMoved(k.getPoint());
	}


	@Override
	public void mouseClicked(MouseEvent k) {
		GSManager.mouseClick(k.getButton());
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}




}
