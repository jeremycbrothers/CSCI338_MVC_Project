package com.CSCI338.gryffindor.serverSide;

import java.util.Scanner;

public class ServerMain {
	
	private static final int MAXPLAYERS = 4;
	
	public static Model MODEL;
	public static Server SERVER;
	
	public static void main(String[] args) {
		
		MODEL = createModel();
		SERVER = createServer(MODEL);
		
		Scanner scan = new Scanner(System.in);
		
		
		
		while(true) {//command line input listener
			String input = scan.nextLine();
			
			if(input.equals("quit")) {
				closeAllServerSide(0);
				scan.close();
				break;
				
			}else if(input.equals("help")) {
				myPrint("Available commands:");
				myPrint("===================");
				myPrint("  quit      Gracefully closes the server");
			}
		}
		
	}
	
	public static Model createModel() {
		//TODO
		myPrint("Preparing to create the model...");
		Model model = new Model();
		//model.startThread();
		
		return model;
	}
	
	public static Server createServer(Model model) {
		//TODO
		myPrint("Preparing to create the server...");
		Server server = new Server(model, MAXPLAYERS);
		server.startThread();
		
		return server;
	}
	
	public static synchronized void closeAllServerSide(int status) {
		//TODO
		
		SERVER.stopServer();
		SERVER.stopThread();
		
		//MODEL.stopThread();
		
		System.exit(status);
	}
	
	/**
	 * print with a flush to deal with multi-threaded output
	 */
	public static void myPrint(String str) {
		System.out.println(str);
		System.out.flush();
	}
	
}
