package com.CSCI338.gryffindor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardReader implements KeyListener{
	
	private ClientModel model;
	
	public KeyboardReader(ClientModel model) {
		this.model = model;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}//Intentionally not used

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			model.keyPressed("UP");
			break;

		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			model.keyPressed("DOWN");
			break;

		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			model.keyPressed("RIGHT");
			break;

		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			model.keyPressed("LEFT");
			break;
			
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			model.keyReleased("UP");
			break;

		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			model.keyReleased("DOWN");
			break;

		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			model.keyReleased("RIGHT");
			break;

		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			model.keyReleased("LEFT");
			break;
			
		}
		
	}

}
