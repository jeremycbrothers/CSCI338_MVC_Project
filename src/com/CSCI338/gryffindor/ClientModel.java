package com.CSCI338.gryffindor;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ClientModel {
	
	private static final int PORT = 8082;
	
	private Controller controller;
	
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
	 * @return true if connection successful
	 */
	public boolean connectToServer(String ip, Color color) {
		//TODO set player color
		if(running) {
			return false;
		}
		try {
			
			try {
				clientSocket = new Socket(ip, PORT);
			} catch (ConnectException e1) {
				clientSocket = null;
				return false;
			}
			
			
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			running = true;
		} catch (IOException e) {
			e.printStackTrace();
			controller.returnToJoinMenu();
		}
		
		setColor(color);
		
		return true;
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
			running = false;
		} catch (IOException e) {
			e.printStackTrace();
			controller.returnToJoinMenu();
		}
	}
	
	/**
	 * Used to set the player's color on initialization
	 * @param color
	 */
	private void setColor(Color color) {
		sendMessage("CLR" + color.getRGB());
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
		String response = sendMessage("GRD");
		
		if(response.length() >= 4) {
			if(response.substring(0, 4).equals("DEAD")) {
				response = response.substring(4);
				controller.returnToJoinMenu();
			}
		}
		
		return response;
	}
	
	private synchronized String sendMessage(String message) {
		if(!running)
			return "";
		
		System.out.println("Sending message: " + message);
		
		out.println(message);
		String response = "";
		try {
			
			try {
				response = in.readLine();
			} catch (SocketException e1) {
				controller.returnToJoinMenu();
				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
			controller.returnToJoinMenu();
		}
		
		System.out.println("Recieved response: " + response);
		
		return response;
	}
	
	/**
	 * A main method for testing server connection
	 * @param args
	 * @throws UnknownHostException 
	 */
	public static void main(String[] args) {
		ClientModel clientTest = new ClientModel();
		if(!clientTest.connectToServer("127.0.0.1", Color.black)) {
			System.out.println("Connection failed");
			return;
		}
		
		clientTest.mouseClicked(400, 350);
		clientTest.keyPressed("UP");
		clientTest.keyReleased("UP");
		clientTest.sendMessage("Testing 1 ... 2 ... 3");
		
		clientTest.endConnection();
	}

	public void addController(Controller controller) {
		this.controller = controller;
	}
	
}
