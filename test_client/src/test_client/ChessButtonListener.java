package test_client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ChessButtonListener implements ActionListener {
	public String coordinates= "";
	@Override
	public void actionPerformed(ActionEvent e) {
		((Component) e.getSource()).setEnabled(false);
		
		coordinates=((Component) e.getSource()).getName();//get the coordinates of the clicked button
		String[] cor = coordinates.split(" ");//split the string into separate numbers
		
		try {
			Main.send(Integer.parseInt(cor[0]), Integer.parseInt(cor[1]));//send the coordinates back to the server
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
	}

}
