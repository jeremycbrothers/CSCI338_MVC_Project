package com.CSCI338.gryffindor;

public class MVCMain {
	
	public static void main(String[] args) {
		ClientModel model = new ClientModel();
		View view = new View(model);
		Controller controller = new Controller(model, view);
		
		view.registerListener(controller);
	}
	
}
