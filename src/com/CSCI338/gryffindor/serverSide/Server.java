package com.CSCI338.gryffindor.serverSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

public class Server implements Runnable{
	
	private static final int PORT = 8082;
	
	private Thread thread;
	private boolean threadRunning = false;
	
	private ServerSocket serverfd;
	private boolean serverRunning = false;
	private Model model;
	private LinkedList<User> users;
	private int maxConnections;
	
	public Server(Model model, int maxConnections) {
		this.model = model;
		users = new LinkedList<User>();
		this.maxConnections = maxConnections;
	}
	
	public synchronized void startThread() {
		if(threadRunning) {
			return;
		}
		
		ServerMain.myPrint("Server thread created");
		threadRunning = true;
		thread = new Thread(this);
		thread.setName("Server thread");
		thread.start();
	}
	
	public synchronized void stopThread() {
		if(!threadRunning) {
			return;
		}

		ServerMain.myPrint("Server thread stopped");
		threadRunning = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		startServer();
		try {
			acceptUserConnections();
		} catch (SocketException e) {
			ServerMain.myPrint("Listener socket closed");
			
			for(User user : users) {
				user.stopThread();
			}
			
			stopThread();
		}
	}
	
	public synchronized void startServer() {
		if(serverRunning) {
			return;
		}

		ServerMain.myPrint("Server starting");
		serverRunning = true;
		try {
			serverfd = new ServerSocket(PORT);
			
		} catch (Exception e) {
			e.printStackTrace();
			ServerMain.closeAllServerSide(-1);
		}
	}
	
	public synchronized void stopServer() {
		if(!serverRunning) {
			return;
		}

		ServerMain.myPrint("Server stopping");
		serverRunning = false;
		try {
			serverfd.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServerMain.myPrint("Stopping user threads");
		for(User user : users) {
			user.stopThread();
		}
		stopThread();
	}
	
	private void acceptUserConnections() throws SocketException {
		while(serverRunning) {
			
			if(users.size() < maxConnections) {//can accept more players
				try {
					ServerMain.myPrint("Server ready for connection...");
					
					Socket clientfd = serverfd.accept();
					
					ServerMain.myPrint("Server accepted client: " + clientfd.getInetAddress());
					User user = new User(model, clientfd, this);
					users.add(user);
					user.startThread();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}
	
}
