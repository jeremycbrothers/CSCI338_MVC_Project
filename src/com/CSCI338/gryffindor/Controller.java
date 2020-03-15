package com.CSCI338.gryffindor;

public class Controller {
	
	private ClientModel model;
	private View view;
	
	private JoinButtonListener joinButtonListener;
	private KeyboardReader keyReader;
	private MouseReader mouseReader;
	
	public Controller(ClientModel model, View view) {
		this.model = model;
		this.view = view;
		joinButtonListener = new JoinButtonListener(view, model);
		keyReader = new KeyboardReader(model);
		mouseReader = new MouseReader(model);
	}
	
	public JoinButtonListener getJoinButtonListener() {
		return joinButtonListener;
	}
	
	public KeyboardReader getKeyReader() {
		return keyReader;
	}
	
	public MouseReader getMouseReader() {
		return mouseReader;
	}
	
	public void returnToJoinMenu() {
		model.endConnection();
		view.switchToJoinMenu();
	}
	
}
