package test_client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.net.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.io.*;

//Client Side Main
public class Main extends JFrame{
	
	public static int[][] keys = new int[19][19];//to store the state of each cell in the game board (coming from the server)
	
	private static DataInputStream serverInput;
	private static DataOutputStream serverOutput;
	
	public static GameFrame game_frame=new GameFrame();	
	public static StartFrame start_frame=new StartFrame();
	public static GameModeFrame gamemode_frame=new GameModeFrame();
	public static IPFrame ip_frame=new IPFrame();
	public static String ipAddress="";
	
	public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException  {
		start_frame.setVisible(true);
		int player = 1;//"player" stores the state of the game (1 = black's turn, -1 = white's turn, 2 = black win, -2 = white win, 0 = draw)
		int role = 0;//"role" stores the color of the player
		Scanner in = new Scanner(System.in);
		try {
			AudioInputStream musicFile = AudioSystem.getAudioInputStream(new File(".\\javamusic.wav"));
			Clip music = AudioSystem.getClip();
			music.open(musicFile);
			music.start();
			music.loop(Clip.LOOP_CONTINUOUSLY);
			
			
			while(getAddress()=="") {
				//stay here dont move until ip address is entered
			}
			Socket cSock= new Socket(ipAddress, 8888);
			serverInput = new DataInputStream(cSock.getInputStream());
			serverOutput = new DataOutputStream(cSock.getOutputStream());

			//System.out.println("Online or play with computer? Input 0 for online, 1 for computer:");
			//serverOutput.writeInt(in.nextInt());

			role = serverInput.readInt();//the first integer server sends is the color of the player

			while (true) { 
				//read the game board from the server
				for (int i = 0; i < 19; i++) {
					for (int j = 0; j < 19; j++) {
						int temp = serverInput.readInt();
						System.out.print(temp + "  ");
						keys[i][j] = temp;
						game_frame.buttons[i][j].setEnabled(false);
						if (keys[i][j]==1) {//if the cell is black, set the corresponding button to black
							game_frame.buttons[i][j].setBackground(Color.black);
						}else if(keys[i][j]==2) {//if the cell is white, set the corresponding button to white
							game_frame.buttons[i][j].setBackground(Color.white);
						}
					}
					System.out.println("");
				}
				player = serverInput.readInt();
				if (player == 1) {
					System.out.println("Black's turn");
					game_frame.lab_turn.setText("Black's Turn");
				}
				if (player == -1) {
					System.out.println("White's turn");
					game_frame.lab_turn.setText("White's Turn");
				}

				if (player == role) { //if the current turn is the player's color
					for (int i=0;i<19;i++) {
						for (int j=0;j<19;j++) {
							if (keys[i][j]==0) {//enable the cells that are empty
								game_frame.buttons[i][j].setEnabled(true);
							}
							
						}
					}
				} else if (player == 2) {
					System.out.println("Black wins");
					JOptionPane.showMessageDialog(null, "Black Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
					break;
				} else if (player == -2) {
					System.out.println("White wins");
					JOptionPane.showMessageDialog(null, "White Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
					break;
				} else if (player == 0) {
					System.out.println("Draw");
					JOptionPane.showMessageDialog(null, "It's a Draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			}
			game_frame.setVisible(false);
			start_frame.setVisible(true);

			serverOutput.close();
			serverInput.close();
			cSock.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
System.out.println("hello!");

		in.close();
	}
	
	
	public static void send(int x, int y) throws IOException {//send the coordinates to the server
		serverOutput.writeInt(y);
		serverOutput.writeInt(x);
	}
	public static void send(int x)throws IOException{//send one int to server (for button decisions)
		serverOutput.writeInt(x);
	}
	public static String getAddress() {
		System.out.println("Get address function's ip: "+ipAddress);
		return ipAddress;
	}
	public static void setAddress(String ads) {
		System.out.println("Address updated");
		ipAddress=ads;
	}
	
}