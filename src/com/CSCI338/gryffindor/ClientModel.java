package com.CSCI338.gryffindor;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientModel {
	
	private static final int PORT = 8082;
	
	private boolean running = false;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	/**
	 * Connect to the specified server.
	 * Does nothing if client is already running.
	 * @param ip Server to connect to
	 * @param color Color of the player
	 * @throws UnknownHostException When server not found
	 */
	public void connectToServer(String ip, Color color) throws UnknownHostException{
		if(running) {
			return;
		}
		try {
			clientSocket = new Socket(ip, PORT);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			running = true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Closes all resources used to talk with server.
	 * If an error occurs closing, will exit forcibly.
	 * Does nothing if client is not running.
	 */
	public void endConnection() {
		if(!running) {
			return;
		}
		try {
			in.close();
			out.close();
			clientSocket.close();
			in = null;
			out = null;
			clientSocket = null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
	/**
	 * Controller calls this with the location on the window
	 * the player clicked.
	 * @param x
	 * @param y
	 */
	public void mouseClicked(int x, int y) {
		//TODO
	}
	
	/**
	 * Controller calls this when a key is pressed
	 * @param key
	 */
	public void keyPressed(String key) {
		//TODO
	}
	
	/**
	 * Controller calls this when a key is released
	 * @param key
	 */
	public void keyReleased(String key) {
		//TODO
	}
	
	/**
	 * View calls this to get data to render the game
	 * @return
	 */
	public String requestRenderData() {
		//TODO
		return "";
	}
	
	private synchronized String sendMessage(String message) {
		out.println(message);
		String response = "";
		try {
			response = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return response;
	}
	
	/**
	 * A main method for testing server connection
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) throws UnknownHostException {
		ClientModel clientTest = new ClientModel();
		clientTest.connectToServer("127.0.0.1", Color.black);
		
		System.out.print(clientTest.sendMessage("Testing 1 ... 2 ... 3"));
		
		clientTest.endConnection();
	}
	
}
