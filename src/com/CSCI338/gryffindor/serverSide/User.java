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
		player.kill();
		stopIOStreams();
		server.removeUser(this);
		stopThread();
	}
	
	private void readClientRequest() {
		
		while(threadRunning) {
			
			String request = "";
			String requestCode;
			String data;
			String response = "";
			
			try {
				
				if(!in.ready()) {//nothing to read
					continue;
				}
				
				request = in.readLine();
				
				if(ServerMain.LISTCLIENTREQUESTS) {
					ServerMain.myPrint("Client request: " + request);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
				continue;
			}
			
			
			requestCode = request.substring(0, 3);
			data = request.substring(3);
			
			if(request.equals("Testing 1 ... 2 ... 3")) {//Testing, not for actual game use
				response = "Testing response";
				
			}else if(requestCode.equals("GRD")) {//GRD == Get Render Data
				response = fetchRenderData();
				
			}else if(requestCode.equals("MCA")) {//MCA == Mouse Clicked At
				int mx, my;
				int splitter = data.indexOf(',');
				mx = Integer.parseInt(data.substring(0, splitter));
				my = Integer.parseInt(data.substring(splitter + 1));
				player.acceptMouseInput(mx, my);
				response = "POP, BANG!";
				
			}else if(requestCode.equals("KEY")) {//A key was pressed or released
				int splitter = data.indexOf(',');
				String key = data.substring(0, splitter);
				boolean isPressed = Boolean.parseBoolean(data.substring(splitter + 1));
				int keyCode = 0;
				if(key.equals("UP")) {
					keyCode = Player.UP;
				}else if(key.equals("DOWN")) {
					keyCode = Player.DOWN;
				}else if(key.equals("LEFT")) {
					keyCode = Player.LEFT;
				}else if(key.equals("RIGHT")) {
					keyCode = Player.RIGHT;
				}
				
				player.acceptKeyInput(keyCode, isPressed);
				response = key + "=" + keyCode + ", " + isPressed;
				
			}else if(requestCode.equals("BYE")) {//Client is closing its connection
				out.println("Good bye!");
				shutdownUser();
				
			}
			
			out.println(response);
			
		}
	}
	
	private String fetchRenderData() {
		String data = model.getRenderString();
		if(player.isDead()) {
			data = "DEAD" + data;
		}
		
		return data;
	}
	
	@Override
	public void run() {
		setupIOStreams();
		readClientRequest();
	}
	
}
