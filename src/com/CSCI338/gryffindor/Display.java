package com.CSCI338.gryffindor;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Display extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -4970779383268067892L;
	
	private ClientModel model;
	
	private boolean threadRunning = false;
	private Thread thread;
	
	public Display(ClientModel model) {
		setPreferredSize(View.DIMENSIONS);
		this.model = model;
	}
	
	@Override
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfFrames = 60.0;
		double ns = 1000000000 / amountOfFrames;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		long now;
		while(threadRunning) {
			now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1) {
				render();
				frames++;
				delta--;
			}
			
			if(System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println("FPS is: " + frames);
				frames = 0;
			}
		}
		
	}
	
	public synchronized void startThread() {
		if(threadRunning) {
			return;
		}
		
		System.out.println("Display thread created");
		threadRunning = true;
		thread = new Thread(this);
		thread.setName("Display thread");
		thread.start();
	}
	
	public synchronized void stopThread() {
		if(!threadRunning) {
			return;
		}
		

		System.out.println("Display thread stopped");
		threadRunning = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, View.DIMENSIONS.width, View.DIMENSIONS.height);
		//////////////
		{
			String renderData = model.requestRenderData();
			parseRenderData(g, renderData);
		}
		//////////////
		g.dispose();
		bs.show();
	}
	
	/**
	 * Render data format is:
	 * object1:object2:object3 .... etc
	 * 
	 * object format is:
	 * x,y,radius,rgb
	 * 
	 * @param g
	 * @param data
	 */
	private void parseRenderData(Graphics g, String data) {
		if(data.length() == 0)
			return;
		
		String[] gameObjects = data.split(":");
		
		String[] gOData;
		int x, y, radius, rgb;
		
		for(String gameObject : gameObjects) {
			gOData = gameObject.split(",");
			x = Integer.parseInt(gOData[0]);
			y = Integer.parseInt(gOData[1]);
			radius = Integer.parseInt(gOData[2]);
			rgb = Integer.parseInt(gOData[3]);
			
			g.setColor(new Color(rgb));
			g.fillOval(x + radius, y + radius, 2 * radius, 2 * radius);
			
		}
	}
	
	public void registerListener(Controller controller) {
		this.addKeyListener(controller.getKeyReader());
		this.addMouseListener(controller.getMouseReader());
	}
	
}
