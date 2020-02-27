package com.CSCI338.gryffindor.serverSide;

import java.net.ServerSocket;
import java.util.LinkedList;

public class Server implements Runnable{

	private Thread thread;
	private boolean threadRunning = false;
	
	private ServerSocket serverfd;
	private boolean serverRunning = false;
	private Model model;
	private LinkedList<User> users;
	private int maxConnections;
	
	public Server(Model model, int maxConnections) {
		//TODO
		this.model = model;
		users = new LinkedList<User>();
		this.maxConnections = maxConnections;
	}
	
	public synchronized void startThread() {
		if(threadRunning) {
			return;
		}
		
		threadRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stopThread() {
		if(!threadRunning) {
			return;
		}
		
		threadRunning = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public void startServer() {
		
	}
	
	public void stopServer() {
		
	}
	
	public void acceptUserConnections() {
		
	}
	
}
