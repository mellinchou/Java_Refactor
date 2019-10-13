package client;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JLabel;

public abstract class UIFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	public ChessButton[][] buttons = new ChessButton[19][19];
	public final JLabel lab_turn=new JLabel("Waiting for the Other Player...");
	
	public UIFrame() {
		setTitle("Online Gomoku Game");
		setSize(1000, 800);
		setBackground(new Color(106, 197, 254));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
}
