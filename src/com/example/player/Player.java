package com.example.player;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JProgressBar;

import com.example.manageProcess.Animation;
import com.example.manageProcess.GameStateManager;
import com.example.objects.ParentsOfObject;
import com.example.window.GameWindow;

public class Player extends ParentsOfObject{

	private AffineTransform affineT;
	private BufferedImage[] frames;

	private double moveBackSpeed;
	private double backStopSpeed;
	private double moveSpeed;
	private double sideSpeed;
	private double sideStartSpeed;
	private double sideStopSpeed;
	private double startSpeed;
	private double stopSpeed;
	private double XSpeed;
	private double YSpeed;

	public double life;

	private JProgressBar lifeBar;

	private double rotateSpeed;
	private int rotateXPoint;
	public static double angle;
	private double dAngle;
	private double rotateStartSpeed;
	private double rotateStopSpeed;
	private double maxRotateSpeed;

	private double tAngle;

	//variable for rotation destination x,y
	private double rectWidth[] = {11.32,7.602,9.93,3.93,6.89,4.789};
	private double rectHeight[] = {1.76,3.28,1.1,1.93,4.45,12.15};

	private double rectX[] = {0,11.32,4.55,3.12,1.74,1.39};
	private double rectY[] = {4.6, 2.87,31.6,4.15,2.58,2.17};


	public double leftRotationX;
	public double leftRotationY;
	public double rightRotationX;
	public double rightRotationY;
	public String site;
	private double radius;

	private boolean isUp;
	public static boolean isDown;
	private boolean isLeft;
	private boolean isRight;
	private boolean isRotate;
	private boolean isFire;

	public int bullet0Quantity;
	public int bullet1Quantity;
	public int currentBulletId;

	private final int IDLE = 1;
	private final int LEFTROTATE = 2;
	private final int RIGHTROTAATE = 3;
	private final int FONTMOVE = 4;
	private final int BACKMOVE = 5;
	private int currentAction;

	public Animation animation;
	private Animation buletAnim;

	public LinkedList<Bullet> bullet;

	public Player(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}

	public void init() {

		startSpeed = 2;
		stopSpeed = .05;
		moveSpeed = 4.5 ;
		XSpeed = 0;

		sideSpeed = 3;
		sideStartSpeed = 1.5;
		sideStopSpeed = .08;

		rotateSpeed = 5;
		rotateStartSpeed = 2;
		rotateStopSpeed = .3;
		angle = 0;
		tAngle=0;
		moveBackSpeed = 0.5;
		backStopSpeed = 0.05;

		width = GameWindow.WIDTH/20;
		height = (GameWindow.HEIGHT/22);

		x = GameWindow.WIDTH / 2;
		y = GameWindow.HEIGHT / 2;

		leftRotationX = GameWindow.WIDTH / 2;
		leftRotationY = GameWindow.HEIGHT / 2;

		site = "isLeft";
		radius = height ;

		bullet0Quantity = 100;
		bullet1Quantity = 10;
		currentBulletId = 0;

		life = 100;
		lifeBar = new JProgressBar();

		try {

			BufferedImage sheet = ImageIO.read(getClass().getResourceAsStream("/Player/aircraft.png"));
			frames = new BufferedImage[2 * 19];
			for(int i= 0 ;i<2 ;i++){
				for(int j = 0; j< 19 ; j++){
					frames[(i*19)+j] = scale(sheet.getSubimage(j*102, i*71, 102, 71)) ;
				}
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

		for(int i=0 ;i< 6;i++){
			rectWidth[i] = width / rectWidth[i] ;
			rectHeight[i] = height / rectHeight[i];
			if(i==0)
				rectX[i]=0;
			else
				rectX[i] = width/ rectX[i];
			rectY[i] = height / rectY[i];
		}

		rotateXPoint = 30;

		bullet = new LinkedList<Bullet>();
		animation = new Animation();
		animation.setFrames(frames);
		
		buletAnim = new Animation();
		buletAnim.setDelay(1000);
	}

	private BufferedImage scale(BufferedImage image){
		Image temp=image.getScaledInstance(width, height, Image.SCALE_FAST);
		BufferedImage dimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2d= dimage.createGraphics();
		g2d.drawImage(temp, 0, 0, null);
		g2d.dispose();

		return dimage;
	}

	public void update() {

		setNextXYPosition();
		animation.update(tAngle);
		buletAdd();
	}

	private void setNextXYPosition(){

		if(isUp){
			gsm.sound.makeMoveSound();
			XSpeed += startSpeed;
			if(XSpeed > moveSpeed){
				XSpeed = moveSpeed;
			}

		}else if(isDown){
			gsm.sound.makeMoveSound();
			XSpeed -= moveBackSpeed;
			if(XSpeed < -moveBackSpeed){
				XSpeed = -moveBackSpeed;
			}

		}else{
			if(XSpeed > 0){
				XSpeed -= stopSpeed;
			}else if(XSpeed < 0){

				XSpeed += backStopSpeed;
			}
			if((XSpeed > 0 && XSpeed <0.05 ) ||(XSpeed < 0 && XSpeed > -0.05 )){
				XSpeed = 0;
			}
		}


		if((isRotate && isLeft) || (isRotate && isRight)){
			//dx = 0;
			//dy = 0;
			gsm.sound.makeMoveSound();
			if(isRight){
				dAngle += rotateStartSpeed;

				if(dAngle > rotateSpeed){
					dAngle = rotateSpeed;
				}else if(dAngle < -rotateSpeed){
					dAngle = -rotateSpeed;
				}

				site= "isRight";



			}
			if(isLeft){
				dAngle -= rotateStartSpeed; 
				if(dAngle > rotateSpeed){
					dAngle = rotateSpeed;
				}else if(dAngle < -rotateSpeed){
					dAngle = -rotateSpeed;
				}


				site = "isLeft";
			}

			tAngle += dAngle;

			if(tAngle > 90 ) tAngle = 90;
			else if(tAngle < -90) tAngle = -90;

		}else if(isRight){
			YSpeed += sideStartSpeed;
			if(YSpeed > sideSpeed){
				YSpeed = sideSpeed;
			}
			tAngle += 5;
			if(tAngle>35) tAngle = 35;
			decreseAngle();
		}
		else if(isLeft){
			//System.out.println("Left Pressed="+YSpeed);
			YSpeed -= sideStartSpeed;
			if(YSpeed < -sideSpeed){
				YSpeed = -sideSpeed;
			}
			tAngle -= 5;
			if(tAngle<-35) tAngle = -35;
			decreseAngle();
		}else {
			decreseAngle();
			if(YSpeed > 0){
				YSpeed -= sideStopSpeed;
			}else if(YSpeed < 0){
				YSpeed += sideStopSpeed;
			}

			if(( YSpeed > 0 && YSpeed < .08 ) || ( YSpeed < 0 && YSpeed > -.08 )){
				YSpeed = 0;
			}
			if(tAngle> 0){
				tAngle -= 5;
			}else if(tAngle < 0){
				tAngle += 5;
			}

			if((tAngle > 0 && tAngle< 5)||(tAngle<0 && tAngle> -5)){
				tAngle = 0;
			}
		}


		angle += dAngle;

		rotationXYPositionSet(angle);

		//setRotateDxDy(angle);

		dx = Math.abs(Math.cos(Math.toRadians(angle)))*  XSpeed;
		dy = Math.abs(Math.sin(Math.toRadians(angle)))*  XSpeed;
		//setRotateDxDy(angle);


		if(((angle / 90) >= 0 && (angle / 90) <1) || ((angle / 90) <= -3 && (angle / 90) > -4)){
			//System.out.println("Method Use-1");


			leftRotationX += dx;
			leftRotationY += dy;

			rightRotationX += dx;
			rightRotationY += dy;

		}
		else if(((angle / 90) >= 1 &&  (angle / 90) <2) || ((angle / 90) <= -2 && (angle / 90) > -3)){
			//System.out.println("Method Use-2");


			leftRotationX -= dx;
			leftRotationY += dy;

			rightRotationX -= dx;
			rightRotationY += dy;
		}else if(((angle / 90) >= 2 && (angle / 90) <3) || ((angle / 90) <= -1 &&  (angle / 90) > -2)){
			//System.out.println("Method Use-3");


			leftRotationX -= dx;
			leftRotationY -= dy;

			rightRotationX -= dx;
			rightRotationY -= dy;
		}else if(((angle / 90) >= 3 && (angle / 90) <4) || ((angle / 90) <= 0 && (angle / 90) > -1)){
			//System.out.println("Method Use-4");


			leftRotationX += dx;
			leftRotationY -= dy;

			rightRotationX += dx;
			rightRotationY -= dy;
		}


		dy = Math.abs(Math.cos(Math.toRadians(angle)))*  YSpeed;
		dx = Math.abs(Math.sin(Math.toRadians(angle)))*  YSpeed;

		angle = angle % 360;


		if(((angle / 90) >= 0 && (angle / 90) <1) || ((angle / 90) <= -3 && (angle / 90) > -4)){


			leftRotationX -= dx;
			leftRotationY += dy;

			rightRotationX -= dx;
			rightRotationY += dy;

		}
		else if(((angle / 90) >= 1 &&  (angle / 90) <2) || ((angle / 90) <= -2 && (angle / 90) > -3)){
			//System.out.println("Method Use-2");


			leftRotationX -= dx;
			leftRotationY -= dy;

			rightRotationX -= dx;
			rightRotationY -= dy;
		}else if(((angle / 90) >= 2 && (angle / 90) <3) || ((angle / 90) <= -1 &&  (angle / 90) > -2)){
			//System.out.println("Method Use-3");


			leftRotationX += dx;
			leftRotationY -= dy;

			rightRotationX += dx;
			rightRotationY -= dy;

		}else if(((angle / 90) >= 3 && (angle / 90) <4) || ((angle / 90) <= 0 && (angle / 90) > -1)){


			leftRotationX += dx;
			leftRotationY += dy;

			rightRotationX += dx;
			rightRotationY += dy;
		}



	}




	private void rotationXYPositionSet(double tAngle) {


		double theta;
		tAngle %= 360;
		if(tAngle<0){
			theta = - tAngle;
		}else
			theta = tAngle;

		if(site.equals("isRight")){

			if( tAngle >= 0 && tAngle <90 ){
				dx = Math.sin(Math.toRadians(theta%90))*radius;
				dy = radius - (Math.cos(Math.toRadians(theta % 90))*radius);
			}else if( tAngle >= 90 && tAngle <180 ){
				dx = Math.cos(Math.toRadians(theta%90))*radius;
				dy = radius + (Math.sin(Math.toRadians(theta % 90))*radius);
			}else if( tAngle >= 180 && tAngle <270 ){
				dx =-( Math.sin(Math.toRadians(theta%90))*radius);
				dy = radius + (Math.cos(Math.toRadians(theta % 90))*radius);
			}else if( tAngle >= 270 && tAngle <360 ){
				dx = -(Math.cos(Math.toRadians(theta%90))*radius);
				dy = radius - (Math.sin(Math.toRadians(theta % 90))*radius);
			}else if( tAngle <= -0 && tAngle >-90 ){
				dx =-( Math.sin(Math.toRadians(theta%90))*radius);
				dy = radius - (Math.cos(Math.toRadians(theta % 90))*radius);
			}else if( tAngle <= -90 && tAngle >-180 ){
				dx = -(Math.cos(Math.toRadians(theta%90))*radius);
				dy = radius + (Math.sin(Math.toRadians(theta % 90))*radius);
			}else if( tAngle <= -180 && tAngle >-270 ){
				dx = Math.sin(Math.toRadians(theta%90))*radius;
				dy = radius + (Math.cos(Math.toRadians(theta % 90))*radius);
			}else if( tAngle <= -270 && tAngle >-360 ){
				dx = (Math.cos(Math.toRadians(theta%90))*radius);
				dy = radius - (Math.sin(Math.toRadians(theta % 90))*radius);
			}

			leftRotationX = rightRotationX +dx;
			leftRotationY = rightRotationY +dy;

			//System.out.println("leftrx="+leftRotationX+"leftRY= "+leftRotationY);
		}

		if(site.equals("isLeft")){
			if( tAngle >= 0 && tAngle <90 ){
				dx = -(Math.sin(Math.toRadians(theta%90))*radius);
				dy = -(radius - (Math.cos(Math.toRadians(theta % 90))*radius));
			}else if( tAngle >= 90 && tAngle <180 ){
				dx =- (Math.cos(Math.toRadians(theta%90))*radius);
				dy = -(radius + (Math.sin(Math.toRadians(theta % 90))*radius));
			}else if( tAngle >= 180 && tAngle <270 ){
				dx =( Math.sin(Math.toRadians(theta%90))*radius);
				dy =-( radius + (Math.cos(Math.toRadians(theta % 90))*radius));
			}else if( tAngle >= 270 && tAngle <360 ){
				dx = Math.cos(Math.toRadians(theta%90))*radius;
				dy = -(radius - (Math.sin(Math.toRadians(theta % 90))*radius));
			}else if( tAngle <= -0 && tAngle >-90 ){
				dx =( Math.sin(Math.toRadians(theta%90))*radius);
				dy = -(radius - (Math.cos(Math.toRadians(theta % 90))*radius));
			}else if( tAngle <= -90 && tAngle >-180 ){
				dx = (Math.cos(Math.toRadians(theta%90))*radius);
				dy = -(radius + (Math.sin(Math.toRadians(theta % 90))*radius));
			}else if( tAngle <= -180 && tAngle >-270 ){
				dx = -(Math.sin(Math.toRadians(theta%90))*radius);
				dy = -(radius + (Math.cos(Math.toRadians(theta % 90))*radius));
			}else if( tAngle <= -270 && tAngle >-360 ){
				dx = -(Math.cos(Math.toRadians(theta%90))*radius);
				dy = -(radius - (Math.sin(Math.toRadians(theta % 90))*radius));
			}

			rightRotationX = leftRotationX+ dx;
			rightRotationY = leftRotationY+ dy;
		}

	}

	private void decreseAngle(){
		if(dAngle > 0){
			dAngle -= rotateStopSpeed;
		}else if(dAngle < 0){
			dAngle += rotateStopSpeed;
		}

		if(( dAngle > 0 && dAngle < .3 ) || ( dAngle < 0 && dAngle > -.3 )){
			dAngle = 0;
		}

		dx = Math.sin(Math.toRadians(dAngle)) * radius;
		dy = Math.cos(Math.toRadians(dAngle)) * radius;
	}

	public void playerXYReset(){
		leftRotationX= GameWindow.WIDTH/2;
		leftRotationY = GameWindow.HEIGHT/2;
		site = "isLeft";

		dx = 0;
		dy = 0;
		angle = 0;
		tAngle = 0;
		XSpeed = 0 ;
	}



	public double getX(){
		return x;
	}

	public double getY(){
		return y;
	}

	public double getLeftRotationX(){
		return leftRotationX;
	}

	public double getLeftRotationY(){
		return leftRotationY;
	}

	public double getRightRotationX(){
		return rightRotationX;
	}

	public double getRightRotationY(){
		return rightRotationY;
	}

	public void setLeftRotationX(double leftRotationX) {
		this.leftRotationX = leftRotationX;
	}

	public void setLeftRotationY(double leftRotationY) {
		this.leftRotationY = leftRotationY;
	}

	public void setRightRotationX(double rightRotationX) {
		this.rightRotationX = rightRotationX;
	}

	public void setRightRotationY(double rightRotationY) {
		this.rightRotationY = rightRotationY;
	}

	public void setUp(boolean isUp) {
		this.isUp = isUp;
	}

	public void setLife(double life){
		this.life += life;
		
		if(this.life > 100) this.life = 100;
		if(this.life < 0) this.life = 0;
	}

	public double getLife(){
		return life;
	}

	public void setDown(boolean isDown) {
		this.isDown = isDown;
	}

	public void setLeft(boolean isLeft) {
		this.isLeft = isLeft;
	}

	public void setRight(boolean isRight) {
		this.isRight = isRight;
	}

	public void setRotate(boolean isRotate) {
		this.isRotate = isRotate;
	}

	public void setFire(boolean isFire) {


		if(currentBulletId == 0){
			if(bullet0Quantity > 0){
				gsm.sound.makeShootSound();
				bullet.add(new Bullet(this, currentBulletId));
				bullet0Quantity--;
			}
		}else {
			if(bullet1Quantity > 0){
				gsm.sound.makeShootSound();
				bullet.add(new Bullet(this, currentBulletId));
				bullet1Quantity--;
			}else{
				currentBulletId = 0;
			}
		}

		this.isFire = isFire;
	}

	public void setBuletOQuantity(int i){
		this.bullet0Quantity += i;
		if(bullet0Quantity > 100) bullet0Quantity = 100;
		if(bullet0Quantity < 0) bullet0Quantity = 0;
	}
	
	public void setBulet1Quantity(int i){
		this.bullet1Quantity += i;
		if(bullet1Quantity > 10) bullet1Quantity = 10;
		if(bullet1Quantity < 0) bullet1Quantity = 0;
	}
	
	public void setBullet(){
		currentBulletId = (currentBulletId == 0?1: 0);
	}

	
	public String getSite(){
		return site;
	}
	public int getHeight(){
		return height;
	}

	private void buletAdd(){
		

		if(buletAnim.updateTime()){
			bullet0Quantity +=3;
			
			if(bullet0Quantity > 100)
				bullet0Quantity = 100;
		}
	}

	public void draw(Graphics2D g) {

		affineT =new AffineTransform();


		if(site.equals("isRight")){
			x = rightRotationX;
			y = rightRotationY;
			affineT.translate(x, y);
			affineT.rotate(Math.toRadians(angle),rotateXPoint,radius );
		}else if(site.equals("isLeft")){

			x = leftRotationX;
			y = leftRotationY;
			affineT.translate(x, y);
			affineT.rotate(Math.toRadians(angle),rotateXPoint,0);
		}

		if(animation.isFlinching())
			g.drawImage(animation.getImage(),affineT,null);

		g.setColor(Color.yellow);

	}

	public Shape getBounds(int rectNumber) {
		affineT = new AffineTransform();
		affineT.translate(x, y);
		if(site.equals("isLeft")){
			affineT.rotate(Math.toRadians(angle),rotateXPoint,0);
		}else if(site.equals("isRight")){
			affineT.rotate(Math.toRadians(angle),rotateXPoint,radius);
		}
		return affineT.createTransformedShape(new Rectangle((int)(rectX[rectNumber]), (int)(rectY[rectNumber]), (int)rectWidth[rectNumber], (int)rectHeight[rectNumber]));
	}



}
