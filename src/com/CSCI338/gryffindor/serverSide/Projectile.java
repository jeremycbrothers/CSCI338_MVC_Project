package com.CSCI338.gryffindor.serverSide;

import java.util.LinkedList;

public class Projectile extends GameObject{

	private static final int DEFAULTPROJECTILERADIUS = 5;
	
	public Projectile(Model model, int x, int y, int velX, int velY) {
		super(model, x, y, DEFAULTPROJECTILERADIUS, false);
		this.setVelX(velX);
		this.setVelY(velY);
	}
	
	@Override
	public void tick() {
		updatePosition();
		
		//once off screen, projectiles cease to exist
		if(x < 0 || x > model.getWidth()) {
			if(y < 0 || y > model.getHeight()) {
				this.kill();
				model.removeGameObject(this);
				return;
			}
		}
		
		LinkedList<GameObject> list = model.getGameObjects();
		
		for(int i = 0; i < list.size(); i++) {
			GameObject current = list.get(i);
			
			//no need to check if invincible objects are being shot
			if(!current.isDamagable())
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
	}

}
