package com.CSCI338.gryffindor.serverSide;

import java.util.Scanner;

public class ServerMain {
	
	private static final int MAXPLAYERS = 4;
	
	public static boolean SHOWTPS = false;
	
	private static Model MODEL;
	private static Server SERVER;
	
	public static void main(String[] args) {
		
		MODEL = createModel();
		SERVER = createServer(MODEL);
		
		Scanner scan = new Scanner(System.in);
		myPrint("Type 'help' for a list of server commands");
		
		
		while(true) {//command line input listener
			String input = scan.nextLine();
			
			if(input.equals("quit")) {
				closeAllServerSide(0);
				scan.close();
				break;
				
			}else if(input.equals("toggleTPS")) {
				SHOWTPS = !SHOWTPS;
				
			}else if(input.equals("help")) {
				myPrint("Available commands:");
				myPrint("===================");
				myPrint("  quit         Gracefully closes the server");
				myPrint("  toggleTPS    Stop/start tps messages");
			}
		}
		
	}
	
	public static Model createModel() {
		myPrint("Preparing to create the model...");
		Model model = new Model(800, 600);
		model.startThread();
		
		return model;
	}
	
	public static Server createServer(Model model) {
		myPrint("Preparing to create the server...");
		Server server = new Server(model, MAXPLAYERS);
		server.startThread();
		
		return server;
	}
	
	public static synchronized void closeAllServerSide(int status) {
		SERVER.stopServer();
		SERVER.stopThread();
		
		MODEL.stopThread();
		
		System.exit(status);
	}
	
	/**
	 * Print which displays the name of the calling thread
	 * and flushes just in case due to multiple threads running
	 */
	public static void myPrint(String str) {
		System.out.println(Thread.currentThread().getName() + " : " + str);
		System.out.flush();
	}
	
}
