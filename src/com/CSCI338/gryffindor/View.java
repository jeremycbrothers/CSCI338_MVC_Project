package com.CSCI338.gryffindor;

import java.awt.Dimension;

import javax.swing.JFrame;

public class View extends JFrame{
	
	private static final long serialVersionUID = 4807560364159855075L;
	
	public static final Dimension DIMENSIONS = new Dimension(800, 600);
	
	private ClientModel model;
	private Controller controller;
	
	private JoinMenu joinMenu;

	public View(ClientModel model) {
		// TODO Auto-generated constructor stub
		this.model = model;
		joinMenu = new JoinMenu();
		
		setTitle("Gryffindor MVC Project");
		getContentPane().add(joinMenu);
		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void registerListener(Controller controller) {
		// TODO Plug controller into appropriate GUI components
		this.controller = controller;
		joinMenu.registerListener(controller);
	}

	public void start() {
		// TODO Auto-generated method stub
		
	}

}
