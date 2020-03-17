package com.CSCI338.gryffindor;

public class Controller {
	
	private ClientModel model;
	private View view;
	
	private JoinButtonListener joinButtonListener;
	private KeyboardReader keyReader;
	private MouseReader mouseReader;
	private ExitListener exitListener;
	
	public Controller(ClientModel model, View view) {
		this.model = model;
		this.view = view;
		joinButtonListener = new JoinButtonListener(view, model);
		keyReader = new KeyboardReader(model);
		mouseReader = new MouseReader(model);
		exitListener = new ExitListener(this);
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
	
	public ExitListener getExitListener() {
		return exitListener;
	}
	
	public void returnToJoinMenu() {
		model.endConnection();
		view.switchToJoinMenu();
	}
	
}
