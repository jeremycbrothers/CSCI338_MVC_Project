package com.CSCI338.gryffindor;

public class Controller {
	
	private ClientModel model;
	private View view;
	
	private JoinButtonListener joinButtonListener;
	
	public Controller(ClientModel model, View view) {
		// TODO Auto-generated constructor stub
		this.model = model;
		this.view = view;
		joinButtonListener = new JoinButtonListener();
	}
	
	public JoinButtonListener getJoinButtonListener() {
		return joinButtonListener;
	}
	
}
