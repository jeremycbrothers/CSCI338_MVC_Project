package com.CSCI338.gryffindor;

import java.awt.Dimension;

import javax.swing.JFrame;

public class View extends JFrame{
	
	private static final long serialVersionUID = 4807560364159855075L;
	
	public static final Dimension DIMENSIONS = new Dimension(800, 600);
	
	private JoinMenu joinMenu;
	private Display display;

	public View(ClientModel model) {
		joinMenu = new JoinMenu();
		display = new Display(model);
		
		setTitle("Gryffindor MVC Project");
		switchToJoinMenu();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public void registerListener(Controller controller) {
		this.addWindowListener(controller.getExitListener());
		joinMenu.registerListener(controller);
		display.registerListener(controller);
	}
	
	public void switchToDisplay() {
		getContentPane().removeAll();
		getContentPane().add(display);
		pack();
		display.startThread();
	}
	
	public void switchToJoinMenu() {
		joinMenu.setFeedbackText(null);
		getContentPane().removeAll();
		getContentPane().add(joinMenu);
		pack();
		display.stopThread();
	}

}
