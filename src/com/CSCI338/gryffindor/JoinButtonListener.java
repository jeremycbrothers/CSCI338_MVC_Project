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
		joinMenu.setFeedbackText("Connecting...");
		boolean connected = model.connectToServer(joinMenu.getIPAddress(), joinMenu.getPlayerColor());
		
		if(!connected) {
			joinMenu.setFeedbackText("Failed to connect to server. Bad IP?");
			return;
		}

		joinMenu.setFeedbackText("Connected");
		view.switchToDisplay();
		
		
	}

}
