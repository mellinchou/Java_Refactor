package com.codingx.java.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IPFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private final TextField ip_tf =new TextField(50);
	private final JButton btn_connect=new JButton("Connect");
	private final JButton btn_back=new JButton("  Back  ");
	private final JPanel container=new JPanel();
	
	public IPFrame() {
		setTitle("Server IP");
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(new Color(106,197,254));
		
		JLabel background=new JLabel(new ImageIcon(".\\ippage2.jpg"));
		
		JPanel titleBox=new JPanel();
		JPanel btnBox=new JPanel();
		btnBox.setLayout(new BoxLayout(btnBox,BoxLayout.Y_AXIS));
		btnBox.setBackground(new Color(106,197,254));
		
		btn_connect.setFont(new Font("Monospaced", Font.BOLD, 20));
		btn_connect.setBackground(Color.white);
		btn_back.setFont(new Font("Monospaced", Font.BOLD, 20));
		btn_back.setBackground(Color.white);
		
		btn_connect.addActionListener(this);
		btn_back.addActionListener(this);
		
		titleBox.add(ip_tf, BorderLayout.CENTER);
		titleBox.setBackground(new Color(106,197 ,254));
		Box box=Box.createHorizontalBox();
		box.add(btn_connect);
		box.add(btn_back);
		btnBox.add(box);
		
		container.setBackground(new Color(106,197 ,254));
		container.setLayout(new FlowLayout()); 
		
		container.add(background);
		container.add(titleBox,BorderLayout.SOUTH);
		container.add(btnBox,BorderLayout.SOUTH);
		this.add(container);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==btn_back) {
			this.setVisible(false);// close the current frame and show the next frame
			Main.start_frame.setVisible(true);
		}else if (e.getSource()==btn_connect) {
			Main.setAddress(ip_tf.getText());//set the ip entered to the ip used by main to create the socket
			this.setVisible(false);// close the current frame and show the next frame
			Main.gamemode_frame.setVisible(true);
		}
		

	}

}
