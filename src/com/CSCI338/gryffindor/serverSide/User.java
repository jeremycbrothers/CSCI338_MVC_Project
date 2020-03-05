package com.CSCI338.gryffindor.serverSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User implements Runnable{
	
	private Thread thread;
	private boolean threadRunning;
	
	private Server server;
	private Socket clientfd;
	private PrintWriter out;
	private BufferedReader in;
	
	private Model model;
	private Player player;
	
	public User(Model model, Socket clientfd, Server server) {
		//TODO
		this.model = model;
		this.clientfd = clientfd;
		this.server = server;
		
		Player player = new Player();
		this.model.addGameObject(player);
	}
	
	public synchronized void startThread() {
		if(threadRunning) {
			return;
		}
		
		ServerMain.myPrint("Client thread started");
		threadRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stopThread() {
		if(!threadRunning) {
			return;
		}
		
		ServerMain.myPrint("Client socket closed");
		try {
			clientfd.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		ServerMain.myPrint("Client thread stopped");
		threadRunning = false;
		try {
			thread.join();
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private void setupIOStreams() {
		try {
			out = new PrintWriter(clientfd.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientfd.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			shutdownUser();
		}
	}
	
	private void stopIOStreams() {
		try {
			if(in != null)
				in.close();
			
			if(out != null)
				out.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private synchronized void shutdownUser() {
		stopIOStreams();
		server.removeUser(this);
		stopThread();
	}
	
	private void readClientRequest() {
		//TODO
		String request = "";
		
		while(threadRunning) {
			
			try {
				
				if(!in.ready()) {//nothing to read
					continue;
				}
				
				request = in.readLine();
				ServerMain.myPrint("Client request: " + request);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
			
			if(request.equals("Testing 1 ... 2 ... 3")) {
				out.println("Testing response");
				shutdownUser();
			}
			
		}
	}
	
	private void passPlayerControls() {
		//TODO
	}
	
	private void fetchRenderData() {
		//TODO
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		setupIOStreams();
		readClientRequest();
	}
	
}
