package com.CSCI338.gryffindor.serverSide;

import java.util.LinkedList;

public class Model implements Tickable, Runnable{
	
	private String renderString;
	
	private Thread thread;
	private boolean running = false;
	
	private LinkedList<GameObject> gameObjects;
	private int width, height;
	
	public Model(int width, int height) {
		gameObjects = new LinkedList<GameObject>();
		renderString = "";
		this.width = width;
		this.height = height;
	}
	
	public synchronized void startThread() {
		if(running) {
			return;
		}
		
		ServerMain.myPrint("Model thread created");
		running = true;
		thread = new Thread(this);
		thread.setName("Model thread");
		thread.start();
	}
	
	public synchronized void stopThread() {
		if(!running) {
			return;
		}

		ServerMain.myPrint("Model thread stopped");
		running = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String createRenderData() {
		//TODO createRenderData
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
		long lastTime = System.nanoTime();
		double ticksPerSecond = 20.0;
		double ns = 1000000000 / ticksPerSecond;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int ticks = 0;
		long now;
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				tick();
				ticks++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				ServerMain.myPrint("TPS is: " + ticks + ", current GameObjects: " + gameObjects.size());
				ticks = 0;
			}
		}
		
	}

	@Override
	public void tick() {
		
		for(int i = 0; i < gameObjects.size(); i++) {
			gameObjects.get(i).tick();
		}
		
		renderString = createRenderData();
		
	}
	
	public String getRenderString() {
		return renderString;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public LinkedList<GameObject> getGameObjects(){
		return gameObjects;
	}
	
}
