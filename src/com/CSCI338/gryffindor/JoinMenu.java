package com.CSCI338.gryffindor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class JoinMenu extends JPanel{
	
	private static final long serialVersionUID = 960655254661077021L;
	
	private static final Color[] COLORS = {Color.BLACK, Color.BLUE,
			Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.GREEN, 
			Color.LIGHT_GRAY, Color.MAGENTA, Color.ORANGE, Color.PINK, 
			Color.RED, Color.WHITE, Color.YELLOW};
	
	private static final String[] COLORNAMES = {"Black", "Blue", "Cyan",
			"Dark Gray", "Gray", "Green", "Light Gray", "Magenta",
			"Orange", "Pink", "Red", "White", "Yellow"};
	
	private boolean currentView = true;
	private JLabel title;
	private JLabel ipFieldLabel;
	private JTextField ipField;
	private JList<String> colorList;
	private JButton joinButton;
	
	public JoinMenu() {
		setPreferredSize(View.DIMENSIONS);
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		title = new JLabel("Gryffindor MVC Project");
		title.setFont(new Font("Arial", Font.PLAIN, 50));
		add(title);
		
		ipFieldLabel = new JLabel("Host IP Address:");
		add(ipFieldLabel);
		
		ipField = new JTextField(15);
		ipField.setMaximumSize(new Dimension(this.getMaximumSize().width, 30));
		add(ipField);
		
		colorList = new JList<>(COLORNAMES);
		colorList.setVisibleRowCount(5);
		colorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		add(colorList);
		
		joinButton = new JButton("Join!");
		add(joinButton);
	}
	
	public String getIPAddress() {
		return ipField.getText();
	}
	
	public Color getPlayerColor() {
		return COLORS[colorList.getSelectedIndex()];
	}
	
	public void registerListener(Controller controller) {
		// TODO Plug controller into appropriate GUI components. Just the button needs a listener?
		controller.getJoinButtonListener().setJoinButton(joinButton, this);
		
	}
	
}
