package com.CSCI338.gryffindor.serverSide;

import java.awt.Color;
import java.util.LinkedList;

public class Projectile extends GameObject{
	
	
	public static final int MAXVELOCITY = 20;
	private static final int DEFAULTPROJECTILERADIUS = 5;
	
	private GameObject owner;
	
	public Projectile(Model model, int x, int y, int velX, int velY, GameObject owner) {
		super(model, x, y, DEFAULTPROJECTILERADIUS, false, Color.RED);
		this.setVelX(velX);
		this.setVelY(velY);
		this.owner = owner;
	}
	
	@Override
	public void tick() {
		updatePosition();
		
		//once off screen, projectiles cease to exist
		if(x < 0 || x > model.getWidth() || y < 0 || y > model.getHeight()) {
			this.kill();
			return;
		}
		
		LinkedList<GameObject> list = model.getGameObjects();
		
		for(int i = 0; i < list.size(); i++) {
			GameObject current = list.get(i);
			
			//no need to check if invincible objects are being shot
			if(!current.isDamagable())
				continue;
			//can't shoot yourself
			if(current == owner)
				continue;
			
			if(current.collides(this)) {
				current.kill();
				this.kill();
			}
			
		}
	}
	
	@Override
	public void kill() {
		model.removeGameObject(this);
		ServerMain.myPrint("Projectile removed");
	}

}
