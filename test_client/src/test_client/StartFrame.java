package test_client;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;

//this is the first window to show up
public class StartFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private final JButton btn_instructions=new JButton("Instructions");
	private final JButton btn_start=new JButton("    Play    ");
	private final JButton btn_exit=new JButton("    Exit    ");
	private final JPanel container=new JPanel();
	
	Border padding = BorderFactory.createEmptyBorder(70, 70, 50, 50);
	Border paddingbtw = BorderFactory.createEmptyBorder(20, 40, 20, 40);
	
	public StartFrame() {
		setTitle("Online Gomoku Game");
		setSize(800, 600);
		setBackground(new Color(106,197,254));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		JLabel background=new JLabel(new ImageIcon(".\\startpage2.jpg"));
		JPanel titleBox=new JPanel();
		titleBox.setBackground(new Color(106,197,254));
		
		btn_instructions.setFont(new Font("Monospaced", Font.BOLD, 20));
		btn_instructions.setBackground(Color.white);
		btn_instructions.setBorder(paddingbtw);
		btn_start.setFont(new Font("Monospaced", Font.BOLD, 20));
		btn_start.setBorder(paddingbtw);
		btn_start.setBackground(Color.white);
		btn_exit.setFont(new Font("Monospaced", Font.BOLD, 20));
		btn_exit.setBackground(Color.white);
		btn_exit.setBorder(paddingbtw);
		
		btn_instructions.addActionListener(this);
		btn_start.addActionListener(this);
		btn_exit.addActionListener(this);
		
		titleBox.add(btn_instructions, BorderLayout.CENTER);
		titleBox.add(btn_start, BorderLayout.CENTER);
		titleBox.add(btn_exit, BorderLayout.CENTER);
		
		container.setBackground(new Color(106,197 ,254));
		container.setLayout(new FlowLayout()); 
		
		container.add(background);
		container.add(titleBox,BorderLayout.SOUTH);
		this.add(container);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btn_instructions)JOptionPane.showMessageDialog(null, "Gomoku, also called Five in a Row, is an abstract strategy board game.\nGomoku is a simple game. Players taking turn to place black and white stones on the board, \nand whoever has 5 stones in a row first (horizontally, vertically, or diagonally) wins the game.\nYou can choose to play against computer or play online with another player.", "Instruction", JOptionPane.INFORMATION_MESSAGE);
		else if (e.getSource()==btn_start) {
			this.setVisible(false);
			Main.ip_frame.setVisible(true);
		}
		else if (e.getSource()==btn_exit)System.exit(0);
	}
	
	
}
