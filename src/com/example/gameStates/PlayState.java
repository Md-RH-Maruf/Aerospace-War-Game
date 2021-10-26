package com.example.gameStates;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.io.WriteAbortedException;
import java.util.LinkedList;

import com.example.enemy.Asteroids;
import com.example.enemy.Enemy;
import com.example.manageProcess.Animation;
import com.example.manageProcess.GameStateManager;
import com.example.objects.Background;
import com.example.objects.Bonus;
import com.example.objects.Fire;
import com.example.objects.PointShow;
import com.example.objects.Welcome;
import com.example.player.Player;
import com.example.player.Ray;
import com.example.window.GameWindow;

public class PlayState extends GameStatesParents{
	private Animation enemyAddAnim;

	public static Background bg;
	public static Player player;

	private int  asteroidsQuantity;
	private int  enemyQuantity;

	private int level;

	public static int tempPoint;

	private boolean start;
	private boolean isWrite;
	private boolean isGameOver;

	private int hitPoint;
	private int damagePoint;
	private int attackPoint;
	private int levelCompletePoint;

	private Area a1,a2;
	private LinkedList<Enemy> enemy;
	private Welcome welcome;
	private Ray[] ray;
	public LinkedList<Asteroids> asteroids;
	public static LinkedList<Fire> fire;
	public PointShow point;
	public LinkedList<Bonus> bonus;
	
	private int removeNumber;

	private int enemyCount;
	private int enemyKillCount;

	public PlayState(GameStateManager gsm) {

		super(gsm);
	}


	public void init() {
		start = false;
		enemyAddAnim = new Animation();
		enemyAddAnim.setDelay(30000);
		enemyAddAnim.setTime();
		ray = new Ray[6];
		bg = new Background(gsm);
		int[] setPoint = {10,100,200,500};	
		//setPoint = new int(5);
		asteroidsQuantity = 5+(level*2);
		enemyQuantity = 5+(level*5);
		hitPoint = setPoint[0]+(setPoint[0]*level);
		damagePoint = setPoint[1]+(setPoint[1]*level);
		attackPoint = setPoint[2]+(setPoint[2]*level);
		levelCompletePoint = setPoint[3]+(setPoint[3]*level);

		isGameOver = false;
		isWrite = true;
		asteroids= new LinkedList<Asteroids>();
		enemy = new LinkedList<Enemy>();
		player = new Player(gsm);
		fire =new LinkedList<Fire>();
		bonus = new LinkedList<Bonus>();
		for( int i=0 ;i<6;i++){
			
			ray[i] = new Ray(player,i);
			ray[i].x = player.x - (i* ray[i].width);
			ray[i].y = player.y+(player.width/2-ray[i].width/2);
		}
		for(int i=0; i<asteroidsQuantity;i++){
			asteroids.add(new Asteroids(gsm,player));
		}
		enemyKillCount=0;
		enemyCount = 1;
		enemy.add(new Enemy(level, 0,player));
		point = new PointShow(player,level);
		welcome = new Welcome(level,this);
		welcome.setWelcome(true);
		gsm.sound.makeCongratulationSound();
	}


	public void update() {
		try{
			bg.update();
			
			if(!isGameOver){
				
			if(start){
				
				player.update();
				for(int i = 0 ;i< 6; i++){
					ray[i].update();
				}
				for(int i=0; i< asteroids.size();i++){
					asteroids.get(i).update();
				}

				for( int i = 0 ;i< fire.size();i++){
					fire.get(i).update();
				}

				for(int i=0 ;i<player.bullet.size();i++){
					player.bullet.get(i).update();
				}

				for(int i= 0;i<bonus.size();i++){
					bonus.get(i).update();
				}
				if(enemyAddAnim.updateTime()){
					addEnemy();
				}
				for(int i = 0 ;i<enemy.size();i++){

					enemy.get(i).update();
				}
				point.update();
				collisionDetection();
			}
			}
			welcome.update();

			levelAndLifeCheck();
		}catch(Exception e){
			
			init();
			update();
		}
		
	}

	private void levelAndLifeCheck(){
		if(enemyKillCount >=enemyQuantity){
			setTempPoint();
			level++;
			welcome.setIsCongratulation(true);
			init();
		}
		
		if(player.life <= 0){
			isGameOver = true;
			
			welcome.setIsGameOver(true);
			if(isWrite){
				gsm.sound.makeGameOverSound();
				isWrite = false;
				HighScoreState.setHighScore(point.getPoint());
			}
			
		}
	}

	private void collisionDetection() {
		areaDetection();
		collideWithAsteroids();

		collideWithEnemy();

		bulletCollide();

		bonusCollision();


	}

	private void setTempPoint(){
		tempPoint = point.getPoint();
	}
	private void bonusCollision(){
		for(int i=0 ;i<bonus.size();i++){
			for(int j=0;j<6;j++){
				a1 = new Area(player.getBounds(j));
				a2 = new Area(bonus.get(i).getBounds(i));
				a1.intersect(a2);
				if(!a1.isEmpty()){
					addBonus(i);
					break;
				}
			}
		}

	}
	private void bulletCollide(){

		for(int i=0; i < player.bullet.size();i++){
			for(int j =0 ;j<asteroids.size();j++){
				if(player.bullet.get(i).id == 0){
					a1 = new Area(player.bullet.get(i).getBounds());
					a2 = new Area(asteroids.get(j).getBounds(j));
					a1.intersect(a2);
					if(!a1.isEmpty()){
						gsm.sound.makeAsteroidHit();
						damageByPlayer(i,j);
						player.bullet.remove(i);
						break;
					}
				}else{
					a1 = new Area(player.bullet.get(i).getBounds(1));
					a2 = new Area(asteroids.get(j).getBounds(j));
					a1.intersect(a2);
					if(!a1.isEmpty()){
						gsm.sound.makeAsteroidHit();
						damageByPlayer(i,j);
						player.bullet.get(i).bullet1isActive = false;
					}

					a1 = new Area(player.bullet.get(i).getBounds(2));
					a2 = new Area(asteroids.get(j).getBounds(j));
					a1.intersect(a2);
					if(!a1.isEmpty()){
						gsm.sound.makeAsteroidHit();
						damageByPlayer(i,j);
						player.bullet.get(i).bullet2isActive = false;
					}
					if(!player.bullet.get(i).bullet1isActive && !player.bullet.get(i).bullet2isActive){
						player.bullet.remove(i);
						break;
					}
				}

			}
		}


		for(int i=0; i < player.bullet.size();i++){
			for(int j =0 ;j<enemy.size();j++){
				if(player.bullet.get(i).id == 0){
					a1 = new Area(player.bullet.get(i).getBounds());
					a2 = new Area(enemy.get(j).getBounds(j));
					a1.intersect(a2);
					if(!a1.isEmpty()){
						gsm.sound.makeBulletHit();
						attack(i,j);
						player.bullet.remove(i);
						break;
					}
				}else{
					a1 = new Area(player.bullet.get(i).getBounds(1));
					a2 = new Area(enemy.get(j).getBounds(j));
					a1.intersect(a2);
					if(!a1.isEmpty()){
						gsm.sound.makeBulletHit();
						attack(i,j);
						player.bullet.get(i).bullet1isActive = false;
					}
					if(enemy.size()>0){
						a1 = new Area(player.bullet.get(i).getBounds(2));
						if(enemy.get(j) != null)
							a2 = new Area(enemy.get(j).getBounds(j));
						a1.intersect(a2);
						if(!a1.isEmpty()){
							gsm.sound.makeBulletHit();
							attack(i,j);
							player.bullet.get(i).bullet2isActive = false;
						}
					}
					if(!player.bullet.get(i).bullet1isActive && !player.bullet.get(i).bullet2isActive){
						player.bullet.remove(i);
						break;
					}
				}

			}
		}


		for(int i=0;i < enemy.size();i++){
			for(int j=0;j<enemy.get(i).bullet.size();j++){
				for(int k=0;k<asteroids.size();k++){
					a1 = new Area(enemy.get(i).bullet.get(j).getBounds(0));
					a2 = new Area(asteroids.get(k).getBounds(k));
					a1.intersect(a2);
					if(!a1.isEmpty()){
						gsm.sound.makeAsteroidHit();
						damageByEnemy(i,j,k);
						enemy.get(i).bullet.remove(j);
						break;
					}
				}
			}
		}

		for(int i=0;i < enemy.size();i++){
			for(int j=0;j<enemy.get(i).bullet.size();j++){
				for(int k=0;k<6;k++){
					a1 = new Area(player.getBounds(k));
					a2 = new Area(enemy.get(i).bullet.get(j).getBounds(0));
					a1.intersect(a2);
					if(!a1.isEmpty()){
						gsm.sound.makeBulletHit();
						hitByEnemyBullet(i,j);
						break;
					}
				}
			}
		}

	}
	private void collideWithEnemy(){
		for(int i=0;i<6;i++){
			for(int j=0;j<enemy.size();j++){

				a1 = new Area(player.getBounds(i));

				a2 = new Area(enemy.get(j).getBounds(j));
				a1.intersect(a2);
				if(!a1.isEmpty()){
					gsm.sound.makeFlinchingSound();
					gsm.sound.makeBulletHit();
					hitByEnemy(j);
				}
			}
		}
	}
	private void collideWithAsteroids(){
		for(int j=0;j<asteroids.size();j++){
			for(int i=0;i<6;i++){
				a1 = new Area(player.getBounds(i));
				a2 = new Area(asteroids.get(j).getBounds(j));
				a1.intersect(a2);
				if(!a1.isEmpty()){
					gsm.sound.makeFlinchingSound();
					gsm.sound.makeAsteroidHit();
					hitByAsteroids();
				}
			}
		}

	}

	private void attack(int bulletNo,int enemyNo){
		fire.add(new Fire(a1.getBounds().x+(a1.getBounds().width/2), a1.getBounds().y+(a1.getBounds().height/2),1));
		enemy.get(enemyNo).setLife(-player.bullet.get(bulletNo).power);
		point.setPoint(hitPoint);
		if(enemy.get(enemyNo).getLife() <=0){
			enemy.remove(enemyNo);
			enemyKillCount++;
			point.setPoint(attackPoint);
			if(enemy.size()==0){
				enemyAddAnim.setTime();
				addEnemy();
			}

		}


	}

	private void damageByPlayer(int bulletNo,int asteroidsNo){

		fire.add(new Fire(a1.getBounds().x+(a1.getBounds().width/2), a1.getBounds().y+(a1.getBounds().height/2),0));
		if(asteroids.get(asteroidsNo).getLife() <=0){
			bonus.add( new Bonus(this, (int)(asteroids.get(asteroidsNo).x+ (asteroids.get(asteroidsNo).width/2)),(int)(asteroids.get(asteroidsNo).y+ (asteroids.get(asteroidsNo).height/2))));
			asteroids.get(asteroidsNo).init();
			point.setPoint(damagePoint);
		}else{
			point.setPoint(hitPoint);
			asteroids.get(asteroidsNo).setLife(-player.bullet.get(bulletNo).power);
		}


	}

	private void damageByEnemy(int enemyNo,int bulletNo,int asteroidsNo){
		fire.add(new Fire(a1.getBounds().x+(a1.getBounds().width/2), a1.getBounds().y+(a1.getBounds().height/2), 0));
		if(asteroids.get(asteroidsNo).getLife() <=0){
			bonus.add( new Bonus(this, (int)(asteroids.get(asteroidsNo).x+ (asteroids.get(asteroidsNo).width/2)),(int)(asteroids.get(asteroidsNo).y+ (asteroids.get(asteroidsNo).height/2))));
			asteroids.get(asteroidsNo).init();
		}else{
			asteroids.get(asteroidsNo).setLife(-enemy.get(enemyNo).bullet.get(bulletNo).power);
		}
	}

	private void hitByAsteroids(){
		if(!player.animation.getFlinching()){
			fire.add(new Fire(a1.getBounds().x+(a1.getBounds().width/2), a1.getBounds().y+(a1.getBounds().height/2),1));
			player.animation.setFlinching(true);
			player.animation.setFlinchingTimer();
			player.playerXYReset();
			player.setLife (-20);
		}

	}

	private void hitByEnemy(int enemyNo){
		fire.add(new Fire(a1.getBounds().x+(a1.getBounds().width/2), a1.getBounds().y+(a1.getBounds().height/2), 1));
		player.animation.setFlinching(true);
		player.animation.setFlinchingTimer();
		player.playerXYReset();
		player.setLife(-10);
		enemy.get(enemyNo).setLife(-10);
		if(enemy.get(enemyNo).getLife() <= 0){
			enemy.remove(enemyNo);
			enemyKillCount++;
			enemyAddAnim.setTime();
			addEnemy();

		}
	}

	private void hitByEnemyBullet(int enemyNo,int BulletNo){
		fire.add(new Fire(a1.getBounds().x+(a1.getBounds().width/2), a1.getBounds().y+(a1.getBounds().height/2),1));

		player.setLife(-enemy.get(enemyNo).bullet.get(BulletNo).power);

		enemy.get(enemyNo).bullet.remove(BulletNo);
	}

	private void areaDetection(){
		if(player.getLeftRotationX() < 0){

			player.rightRotationX -= player.leftRotationX;

			bg.tempDX = -player.leftRotationX;
			asteroidsTempXset(- player.leftRotationX);
			player.setLeftRotationX(0);
		}else if(player.getLeftRotationX() > (GameWindow.WIDTH-player.width)){


			player.rightRotationX -= (player.leftRotationX - (GameWindow.WIDTH-player.width));

			bg.tempDX = -(player.leftRotationX - (GameWindow.WIDTH-player.width));
			asteroidsTempXset(- (player.leftRotationX - (GameWindow.WIDTH-player.width)));
			player.setLeftRotationX((GameWindow.WIDTH-player.width));
		}else if(player.getRightRotationX() < 0){

			player.leftRotationX -= player.rightRotationX;

			bg.tempDX = -player.rightRotationX;
			asteroidsTempXset(- player.rightRotationX);
			player.setRightRotationX(0);
		}else if(player.getRightRotationX() >(GameWindow.WIDTH-player.width)){

			player.rightRotationX -= (player.rightRotationX -(GameWindow.WIDTH-player.width));

			bg.tempDX = -(player.rightRotationX - (GameWindow.WIDTH-player.width));
			asteroidsTempXset(- (player.rightRotationX -(GameWindow.WIDTH-player.width)));
			player.setRightRotationX((GameWindow.WIDTH-player.width));
		}else{

			bg.tempDX = 0;
			asteroidsTempXset(0);
		}



		if(player.getLeftRotationY() < 0){

			player.rightRotationY = player.rightRotationY-player.leftRotationY;

			bg.tempDY = -player.leftRotationY;
			asteroidsTempYset(- player.leftRotationY);
			player.setLeftRotationY(0);
		}else if(player.getLeftRotationY() > (GameWindow.HEIGHT-player.height)){

			player.rightRotationY = player.rightRotationY-(player.leftRotationY - (GameWindow.HEIGHT-player.height));

			bg.tempDY = -(player.leftRotationY - (GameWindow.HEIGHT-player.height));
			asteroidsTempYset(- (player.leftRotationY - (GameWindow.HEIGHT-player.height)));
			player.setLeftRotationY((GameWindow.HEIGHT-player.height));
		}else if(player.getRightRotationY() < 0){

			player.leftRotationY = player.leftRotationY-player.rightRotationY;

			bg.tempDY = -player.rightRotationY;
			asteroidsTempYset(- player.rightRotationY);
			player.setRightRotationY(0);
		}else if(player.getRightRotationY() > (GameWindow.HEIGHT-player.height)){

			player.rightRotationY = player.leftRotationY-(player.rightRotationY - (GameWindow.HEIGHT-player.height));

			bg.tempDY = -(player.rightRotationY - (GameWindow.HEIGHT-player.height));
			asteroidsTempYset(- (player.rightRotationY - (GameWindow.HEIGHT-player.height)));
			player.setRightRotationY((GameWindow.HEIGHT-player.height));
		}else{

			bg.tempDY = 0;
			asteroidsTempYset(0);
		}


	}

	private void addEnemy(){
		enemyCount++;
		point.setEnemy(-1);
		if(enemyCount < enemyQuantity){
			enemy.add(new Enemy(level, 0,player));
		}else if(enemyCount == enemyQuantity){
			enemy.add(new Enemy(level, 1,player));
		}

	}
	private void asteroidsTempXset(double dx){
		for(int i=0;i<asteroids.size();i++){
			asteroids.get(i).tempDX = dx;

		}
		for(int i=0;i< bonus.size();i++){
			bonus.get(i).tempDX = dx;
		}
		for(int i=0;i<enemy.size();i++){
			enemy.get(i).tempDX = dx;
		}
	}
	private void asteroidsTempYset(double dy){
		for(int i=0;i<asteroids.size();i++){
			asteroids.get(i).tempDY = dy;
		}
		for(int i=0;i< bonus.size();i++){
			bonus.get(i).tempDY = dy;
		}
		for(int i=0;i<enemy.size();i++){
			enemy.get(i).tempDY = dy;
		}
	}

	private void addBonus(int bonusNo){
		if(bonus.get(bonusNo).id == 0){
			point.setPoint(100);
		}else if(bonus.get(bonusNo).id == 1){
			player.setBuletOQuantity(100);
		}else if(bonus.get(bonusNo).id == 2){
			player.setBulet1Quantity(10);
		}else if(bonus.get(bonusNo).id == 3){
			player.setLife(100);
		}

		bonus.remove(bonusNo);
	}

	public void setStrat(boolean start){
		this.start = start;
	}



	public void draw(Graphics2D g) {
		bg.draw(g);


		for(int i=0; i<asteroids.size();i++){
			asteroids.get(i).draw(g);
		}
		for(int i= 0 ; i< 6 ; i++){
			ray[i].draw(g);
		}
		player.draw(g);

		for(int i = 0 ;i<enemy.size();i++){
			enemy.get(i).draw(g);
		}

		for(int i=0 ;i<player.bullet.size();i++){
			player.bullet.get(i).draw(g);
		}

		for(int i= 0;i<bonus.size();i++){
			bonus.get(i).draw(g);
		}
		for(int i=0; i<fire.size();i++){
			fire.get(i).draw(g);
		}

		point.draw(g);

		welcome.draw(g);

	}


	public void keyPressed(int k) {
		if(k == KeyEvent.VK_UP){
			player.setUp(true);
		}

		if(k == KeyEvent.VK_DOWN){
			player.setDown(true);
		}

		if(k == KeyEvent.VK_LEFT){
			player.setLeft(true);
		}

		if(k == KeyEvent.VK_RIGHT){
			player.setRight(true);
		}

		if(k == KeyEvent.VK_CONTROL){
			player.setRotate(true);
		}

		if( k == KeyEvent.VK_SPACE){
			gsm.sound.makeShootSound();
			player.setFire(true);
		}
		
		if( k == KeyEvent.VK_ENTER){
			
			if(start){
				welcome.setIspause(true);
				start = false;
			}else{
				welcome.setIsStart(true);
				welcome.setIspause(false);
			}
		}
		
		if( k == KeyEvent.VK_BACK_SPACE || k== KeyEvent.VK_ESCAPE){
			gsm.setState(gsm.MENUSTATE);
		}
		
		if( k == KeyEvent.VK_R){
			if(isGameOver){
				gsm.sound.makeClickSound();
				level = 0;
				welcome.setIsStart(true);
				player.life = 100;
				init();
				
			}
		}

		if( k == KeyEvent.VK_B){
			gsm.sound.makeClickSound();
			player.setBullet();
		}
	}


	public void keyReleased(int k) {
		if(k == KeyEvent.VK_UP){
			player.setUp(false);
		}

		if(k == KeyEvent.VK_DOWN){
			player.setDown(false);
		}

		if(k == KeyEvent.VK_LEFT){
			player.setLeft(false);
		}

		if(k == KeyEvent.VK_RIGHT){
			player.setRight(false);
		}

		if(k == KeyEvent.VK_CONTROL){
			player.setRotate(false);
		}

		if( k == KeyEvent.VK_SPACE){
			player.setFire(false);
		}
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
