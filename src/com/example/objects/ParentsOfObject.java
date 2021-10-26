package com.example.objects;

import java.awt.Graphics2D;
import java.awt.Shape;

import com.example.manageProcess.GameStateManager;

public abstract class ParentsOfObject {
	
	public GameStateManager gsm;

	public double x;
	public double y;
	
	public double dx;
	public double dy;
	
	public int width;
	public int height;
	
	public double moveSpeed;
	public double angle;
	
	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract Shape getBounds(int i);
}
