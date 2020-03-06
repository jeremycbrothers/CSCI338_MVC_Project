package com.CSCI338.gryffindor.serverSide;

public class Player extends GameObject{

	private static final int DEFAULTPLAYERRADIUS = 50;
	private static final int MAXPLAYERVELOCITY = 10;
	
	/**
	 * indexs for the keysPressed array
	 */
	public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;
	/**
	 * true if the key is pressed
	 */
	private boolean[] keysPressed = new boolean[4];
	
	public Player(Model model) {
		super(model, DEFAULTPLAYERRADIUS);
	}
	
	/**
	 * Tell the player when a key was pressed or released
	 * @param key Use the constants UP, RIGHT, etc... defined above
	 * @param pressed
	 */
	public void acceptKeyInput(int key, boolean pressed) {
		//TODO
	}
	
	public void acceptMouseInput() {
		//TODO
		
		shootProjectile();
	}
	
	private void shootProjectile() {
		//TODO
	}

	@Override
	protected void classSpecificTicking() {
		int newVelX = 0, newVelY = 0;

		if(keysPressed[UP])
			newVelY -= MAXPLAYERVELOCITY;
		if(keysPressed[DOWN])
			newVelY += MAXPLAYERVELOCITY;
		
		if(keysPressed[RIGHT])
			newVelX += MAXPLAYERVELOCITY;
		if(keysPressed[LEFT])
			newVelX -= MAXPLAYERVELOCITY;
		
		setVelX(newVelX);
		setVelY(newVelY);
		
	}
	
}
