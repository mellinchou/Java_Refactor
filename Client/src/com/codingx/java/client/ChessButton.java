package com.codingx.java.client;

import javax.swing.JButton;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

//this is the individual buttons on the chess board
public class ChessButton extends JButton implements ActionListener {
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private String coordinates="";

	public ChessButton(int x, int y) { // when creating the button object, use x and y to record it's coordinates
		this.addActionListener(this);
		this.x = x;
		this.y = y;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public String getName() { // to return the coordinates recorded
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(this.y);
		stringBuilder.append(" ");
		stringBuilder.append(this.x);
		String coordinates = stringBuilder.toString();
		return coordinates;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		((Component) e.getSource()).setEnabled(false);

		coordinates = ((Component) e.getSource()).getName();// get the coordinates of the clicked button
		String[] cor = coordinates.split(" ");// split the string into separate numbers

		try {
			Main.send(Integer.parseInt(cor[0]), Integer.parseInt(cor[1]));// send the coordinates back to the server
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

	}
}
