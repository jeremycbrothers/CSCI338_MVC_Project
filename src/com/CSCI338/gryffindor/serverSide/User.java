package com.CSCI338.gryffindor.serverSide;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;

public class User implements Runnable{
	
	private Thread thread;
	private boolean threadRunning;
	
	private Socket clientfd;
	private PrintWriter out;
	private BufferedReader in;
	
	private Model model;
	private Player player;
	
	public User(Model model) {
		//TODO
		this.model = model;
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
	
	private void setupIOStreams() {
		//TODO
	}
	
	private void stopIOStreams() {
		//TODO
	}
	
	private void readClientRequest() {
		//TODO
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
		
	}
	
}
