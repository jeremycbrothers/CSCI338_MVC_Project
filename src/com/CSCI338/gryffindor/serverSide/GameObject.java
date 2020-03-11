package com.CSCI338.gryffindor.serverSide;

public abstract class GameObject implements Tickable{
	
	/**
	 * coords of the center of the object
	 */
	protected int x, y;

	private int velX, velY;
	private int radius;
	private boolean damagable;
	
	protected Model model;
	
	public GameObject(Model model, int x, int y, int radius, boolean isDamagable) {
		this.model = model;
		this.x = x;
		this.y = y;
		this.radius = radius;
		this.damagable = isDamagable;
	}
	
	/**
	 * Simple physics update of position
	 */
	protected void updatePosition() {
		x += velX;
		y += velY;
	}
	
	/**
	 * Prevent game objects from leaving the screen
	 */
	private void forceIntoBounds() {
		if(x < 0) {
			x = 0;
		}else if(x > model.getWidth()) {
			x = model.getWidth();
		}
		
		if(y < 0) {
			y = 0;
		}else if(y > model.getHeight()) {
			y = model.getHeight();
		}
	}
	
	/**
	 * Use the pythagorean theorem and the fact that both objects are circles to check collision
	 * @param other The GameObject to check against
	 * @return true if they collide
	 */
	public boolean collides(GameObject other) {
		int xDiff = x - other.x;
		int yDiff = y - other.y;
		double distance = Math.sqrt( (xDiff * xDiff) + (yDiff * yDiff) );
		
		double collisionDistance = radius + other.radius;
		
		return distance < collisionDistance;
	}
	
	@Override
	public void tick() {
		updatePosition();
		forceIntoBounds();
	}
	
	public void setVelX(int velX) {
		this.velX = velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public boolean isDamagable() {
		return damagable;
	}
	
	/**
	 * Call to properly remove the object from the game
	 */
	public abstract void kill();
	
}
