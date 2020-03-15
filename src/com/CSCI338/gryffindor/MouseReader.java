package com.CSCI338.gryffindor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseReader implements MouseListener{
	
	private ClientModel model;
	
	public MouseReader(ClientModel model) {
		this.model = model;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		model.mouseClicked(e.getX(), e.getY());
	}
	
	//Below methods are not needed
	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

}
