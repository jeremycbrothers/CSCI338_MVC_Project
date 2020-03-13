package com.CSCI338.gryffindor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JoinButtonListener implements ActionListener{
	
	private JoinMenu joinMenu;
	private View view;
	private ClientModel model;
	
	public JoinButtonListener(View view, ClientModel model) {
		this.view = view;
		this.model = model;
	}
	
	public void setJoinButton(JButton button, JoinMenu menu) {
		joinMenu = menu;
		button.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("IP address is: " + joinMenu.getIPAddress());
		System.out.println("Player color is: " + joinMenu.getPlayerColor());
		
		boolean connected = model.connectToServer(joinMenu.getIPAddress(), joinMenu.getPlayerColor());
		System.out.println("Connected : " + connected);
		
		if(!connected) {
			return;
		}
		
		view.switchToDisplay();
		System.out.println("Switched to display");
		
		
	}

}
