package test_client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class GameFrame extends JFrame{
	private static final int RADIUS = 23;
	public ChessButton[][] buttons = new ChessButton[19][19];
	public final JLabel lab_turn=new JLabel("Waiting for the Other Player...");
	private final JPanel chess_container=new JPanel(new GridLayout(19,19)); 
	Border padding1 = BorderFactory.createEmptyBorder(15, 15, 40, 40);
	Border padding = BorderFactory.createEmptyBorder(30, 70, 50, 50);
	private final JPanel container=new JPanel();
	
	public GameFrame() {
		setTitle("The Online Gomoku Game");
		setSize(1000, 800);
		setBackground(new Color(106,197,254));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel background=new JLabel(new ImageIcon(".\\gamepage2.jpg"));
		JPanel box=new JPanel();
		
		lab_turn.setFont(new Font("Monospaced", Font.BOLD, 20));
		chess_container.setSize(300, 300);
		chess_container.setBorder(padding);
		chess_container.setBackground(new Color(106,197,254));
		for (int i = 0; i < 19; i++) { // create an array of buttons that represent the chess board
			for (int j = 0; j < 19; j++) {
				buttons[i][j] = new ChessButton(i,j);
				buttons[i][j].setBackground(new Color(250, 207, 141));
				chess_container.add(buttons[i][j]);
				buttons[i][j].setPreferredSize(new Dimension(25, 25));
			}
		}
		
		box.setBackground(new Color(106,197 ,254));
		box.add(lab_turn, BorderLayout.CENTER);
		box.add(chess_container);
		
		container.setBackground(new Color(106,197 ,254));
		container.setLayout(new FlowLayout()); 
		
		container.add(background);
		container.add(box,BorderLayout.SOUTH);
		this.add(container);
		
	}

	
}
