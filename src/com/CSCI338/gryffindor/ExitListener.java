package com.CSCI338.gryffindor;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExitListener extends WindowAdapter{
	
	private Controller controller;
	
	public ExitListener(Controller controller) {
		this.controller = controller;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		System.out.println("Window X'ed out");
		controller.returnToJoinMenu();
		
	}
	
}
