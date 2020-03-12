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
		//TODO set player color
		if(running) {
			return;
		}
		try {
			clientSocket = new Socket(ip, PORT);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			running = true;
		} catch (IOException e) {
			// TODO make exit gracefully
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
		
		sendMessage("BYE");
		
		try {
			in.close();
			out.close();
			clientSocket.close();
			in = null;
			out = null;
			clientSocket = null;
		} catch (IOException e) {
			// TODO make exit gracefully
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
		sendMessage("MCA" + x + "," + y);
	}
	
	/**
	 * Controller calls this when a key is pressed
	 * Accepts keys: "UP", "DOWN", "LEFT", "RIGHT"
	 * @param key
	 */
	public void keyPressed(String key) {
		sendMessage("KEY" + key + "," + true);
	}
	
	/**
	 * Controller calls this when a key is released
	 * Accepts keys: "UP", "DOWN", "LEFT", "RIGHT"
	 * @param key
	 */
	public void keyReleased(String key) {
		sendMessage("KEY" + key + "," + false);
	}
	
	/**
	 * View calls this to get data to render the game
	 * @return
	 */
	public String requestRenderData() {
		//TODO watch for "DEAD" prepended to data string
		return sendMessage("GRD");
	}
	
	private synchronized String sendMessage(String message) {
		if(!running)
			return "";
		
		System.out.println("Sending message: " + message);
		
		out.println(message);
		String response = "";
		try {
			response = in.readLine();
		} catch (IOException e) {
			// TODO make exit gracefully
			e.printStackTrace();
		}
		
		System.out.println("Recieved response: " + response);
		
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
		
		clientTest.mouseClicked(400, 350);
		clientTest.keyPressed("UP");
		clientTest.keyReleased("UP");
		clientTest.sendMessage("Testing 1 ... 2 ... 3");
		
		clientTest.endConnection();
	}
	
}
