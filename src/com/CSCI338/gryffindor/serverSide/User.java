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
		this.model = model;
		this.clientfd = clientfd;
		this.server = server;
		
		player = new Player(model);
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
		} catch (IOException e) {
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}
	
	private synchronized void shutdownUser() {
		model.removeGameObject(player);
		stopIOStreams();
		server.removeUser(this);
		stopThread();
	}
	
	private void readClientRequest() {
		String request = "";
		String data;
		String response = "";
		
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
			
			

			data = request.substring(3);
			
			if(request.equals("Testing 1 ... 2 ... 3")) {
				out.println("Testing response");
				shutdownUser();
				
			}else if(request.equals("GRD")) {//GRD == Get Render Data
				out.println(fetchRenderData());
				
			}else if(request.substring(0, 3).equals("MCA")) {//MCA == Mouse Clicked At
				//TODO read mouse location from request
				int mx, my;
				int splitter = data.indexOf(',');
				mx = Integer.parseInt(data.substring(0, splitter));
				my = Integer.parseInt(data.substring(splitter + 1));
				player.acceptMouseInput(mx, my);
				response = "POP, BANG!";
				
			}//TODO else for key input
			
			out.println(response);
			
		}
	}
	
	private String fetchRenderData() {
		return model.getRenderString();
	}
	
	@Override
	public void run() {
		setupIOStreams();
		readClientRequest();
	}
	
}
