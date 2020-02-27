package com.CSCI338.gryffindor.serverSide;

import java.util.LinkedList;

public class Model implements Tickable, Runnable{
	
	private Thread thread;
	private boolean running = false;
	
	private LinkedList<GameObject> gameObjects;
	
	public Model() {
		gameObjects = new LinkedList<GameObject>();
	}
	
	public synchronized void startThread() {
		if(running) {
			return;
		}
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stopThread() {
		if(!running) {
			return;
		}
		
		running = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String createRenderData() {
		//TODO
		return "";
	}
	
	public void addGameObject(GameObject obj) {
		gameObjects.add(obj);
	}
	
	public void removeGameObject(GameObject obj) {
		gameObjects.remove(obj);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
