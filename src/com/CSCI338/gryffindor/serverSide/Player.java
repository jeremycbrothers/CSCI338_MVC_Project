package com.CSCI338.gryffindor.serverSide;

import java.awt.Color;

public class Player extends GameObject{

	private static final int DEFAULTPLAYERRADIUS = 50;
	private static final int MAXVELOCITY = 10;
	
	/**
	 * indexs for the keysPressed array
	 */
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	/**
	 * true if the key is pressed
	 */
	private boolean[] keysPressed = new boolean[4];
	private boolean dead = false;
	
	public Player(Model model) {
		super(model, 200, 200, DEFAULTPLAYERRADIUS, true, Color.GREEN);
	}
	
	/**
	 * Tell the player when a key was pressed or released
	 * @param key Use the constants UP, RIGHT, etc... defined above
	 * @param pressed
	 */
	public void acceptKeyInput(int key, boolean pressed) {
		keysPressed[key] = pressed;
	}
	
	public void acceptMouseInput(int mx, int my) {
		ServerMain.myPrint("Mouse clicked at (" + mx + " , " + my + ")");
		ServerMain.myPrint("Player is at     (" + x + " , " + y + ")");
		int xDiff = mx - x;
		int yDiff = y - my;
		//TODO fix bug with angle, currently not exactly the right angle, but close enough
		//Angle is in radians
		double angle = Math.atan(yDiff / xDiff);
		
		if(xDiff < 0) {//Otherwise player always shoots right
			angle += Math.PI;
		}
		
		int xVel = (int) (Projectile.MAXVELOCITY * Math.cos(angle));
		int yVel = (int) (Projectile.MAXVELOCITY * Math.sin(-angle));
		
		ServerMain.myPrint("Angle is: " + angle + ", xDiff is: " + xDiff + ", yDiff is: " + yDiff);
		
		shootProjectile(x, y, xVel, yVel);
	}
	
	private void shootProjectile(int x, int y, int velX, int velY) {
		model.addGameObject(new Projectile(model, x, y, velX, velY, this));
	}

	@Override
	public void tick() {
		int newVelX = 0, newVelY = 0;

		if(keysPressed[UP])
			newVelY -= MAXVELOCITY;
		if(keysPressed[DOWN])
			newVelY += MAXVELOCITY;
		
		if(keysPressed[RIGHT])
			newVelX += MAXVELOCITY;
		if(keysPressed[LEFT])
			newVelX -= MAXVELOCITY;
		
		setVelX(newVelX);
		setVelY(newVelY);
		
		
		super.tick();
	}
	
	@Override
	public void kill() {
		dead = true;
		model.removeGameObject(this);
		ServerMain.myPrint("Player removed");
	}
	
	public boolean isDead() {
		return dead;
	}
	
}
