package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

//this is the window to select the game mode
public class GameModeFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JButton btn_online = new JButton("Online Player");
	private final JButton btn_pc = new JButton("  Computer  ");
	private final JButton btn_back = new JButton("    Back    ");

	Border paddingbtw = BorderFactory.createEmptyBorder(20, 40, 20, 40);

	private final JPanel container = new JPanel();

	public int height = this.getHeight();
	public int width = this.getWidth();
	Border padding = BorderFactory.createEmptyBorder(70, 70, 50, 50);

	public GameModeFrame() {
		setTitle("Online Gomoku Game");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(106, 197, 254));

		JLabel background = new JLabel(new ImageIcon(".\\gamemodepage2.jpg"));

		JPanel titleBox = new JPanel();
		titleBox.setBackground(new Color(106, 197, 254));

		btn_online.setFont(new Font("Monospaced", Font.BOLD, 20));
		btn_online.setBackground(Color.white);
		btn_online.setBorder(paddingbtw);
		btn_pc.setFont(new Font("Monospaced", Font.BOLD, 20));
		btn_pc.setBackground(Color.white);
		btn_pc.setBorder(paddingbtw);
		btn_back.setFont(new Font("Monospaced", Font.BOLD, 20));
		btn_back.setBackground(Color.white);
		btn_back.setBorder(paddingbtw);

		btn_online.addActionListener(this);
		btn_pc.addActionListener(this);
		btn_back.addActionListener(this);

		titleBox.add(btn_online, BorderLayout.CENTER);
		titleBox.add(btn_pc, BorderLayout.CENTER);
		titleBox.add(btn_back, BorderLayout.CENTER);

		container.setBackground(new Color(106, 197, 254));
		container.setLayout(new FlowLayout());

		container.add(background);
		container.add(titleBox, BorderLayout.SOUTH);
		this.add(container);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btn_online) {
			try {
				
				ClientMain.send(0);// send the decision of the game mode to the server
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.setVisible(false);// close the current frame and show the next frame
			ClientMain.game_frame.setVisible(true);
		} else if (e.getSource() == btn_pc) {
			try {
				System.out.println("Client sending game mode");
				ClientMain.send(1);// send the decision of the game mode to the server
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			this.setVisible(false);// close the current frame and show the next frame
			ClientMain.game_frame.setVisible(true);
		} else if (e.getSource() == btn_back) {
			this.setVisible(false);// close the current frame and show the next frame
			ClientMain.ip_frame.setVisible(true);
		}
	}

}
