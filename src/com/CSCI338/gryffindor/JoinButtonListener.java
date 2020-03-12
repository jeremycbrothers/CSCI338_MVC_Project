package com.CSCI338.gryffindor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class JoinButtonListener implements ActionListener{
	
	private JoinMenu joinMenu;
	
	public void setJoinButton(JButton button, JoinMenu menu) {
		joinMenu = menu;
		button.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Connect to server and start game
		System.out.println("IP address is: " + joinMenu.getIPAddress());
		System.out.println("Player color is: " + joinMenu.getPlayerColor());
		
	}

}
