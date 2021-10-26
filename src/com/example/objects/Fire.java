package com.example.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.example.gameStates.PlayState;
import com.example.manageProcess.Animation;

public class Fire {

	private int x;
	private int y;

	private int id;

	private int weight;
	private int height;

	private Animation animation;

	private int frameCount;

	private BufferedImage[] image;

	public Fire(int x,int y,int id){
		init();
		this.x = (x - weight/2);
		this.y = (y - height/2);
		this.id = id;
	}

	private void init(){
		image =new BufferedImage[2];

		try {
			image[1] = ImageIO.read(getClass().getResourceAsStream("/Fire/fire.png"));
			for(int i = 0 ;i< 2;i++){
				image[i] = image[1].getSubimage(i*100, 0, 100, 100);
			}

		} catch (IOException e) {
			
			e.printStackTrace();
		}

		weight = height = 5;
		animation = new Animation();
		animation.setDelay(50);
		animation.setTime();

		frameCount = 0;
	}

	public void update(){
		if(animation.updateTime()){
			if(frameCount <=5){
				weight+=2;
				height+=2;
				x--;
				y--;
				frameCount++;
			}else{
				weight--;
				height--;
				x +=.5;
				y +=.5;

			}

			if(weight==0){
				PlayState.fire.remove(this);
			}

		}

	}

	public void draw(Graphics2D g){
		if( id == 1){
			g.drawImage(image[0], (int)x	, (int)y,weight,height, null);

		}else
			g.drawImage(image[1], (int)x	, (int)y,weight,height, null);

	}
}
