package server;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

//Server's side main
public class ServerMain {
	enum Status {
		CONTINUE, BLACK_WIN, WHITE_WIN, DRAW
	};

	enum Turn {
		BLACK, WHITE
	};

	enum GameMode {
		ONLINE, COMPUTER
	};

	public static PlayerModeContext playerModeContext;

	public static void main(String[] args) {

		try {
			//show the IP address of the current server in a message box
			InetAddress inetAddress = InetAddress.getLocalHost();
			JOptionPane.showMessageDialog(null, "Server IP: " + inetAddress.getHostAddress(), "Server IP",JOptionPane.INFORMATION_MESSAGE);
			
			//initialize the game with the gameModeContext class
			playerModeContext=new PlayerModeContext();

		} catch (IOException e) {

			System.out.println("exception 1 in ServerMain");
			e.printStackTrace();
			System.exit(0);
		}

		
//		
//		PlayerModeContext playerModeContext=new PlayerModeContext();
//		playerModeContext.setPlayerMode(new SinglePlayerMode());
//		playerModeContext.executeGame();
		
		
		
//		Status status = Status.CONTINUE;
//		Turn turn = Turn.BLACK;// black starts the game
//		int x_black = -1, y_black = -1, x_white = -1, y_white = -1;

//		if (game_mode == GameMode.ONLINE) {// play with another online player
//			try {
//				connection2 = serverSock.accept();
//				clientInput2 = new DataInputStream(connection2.getInputStream());
//				clientOutput2 = new DataOutputStream(connection2.getOutputStream());
//				clientOutput2.writeInt(-1);// assigning white to the second player to connect
//				clientInput2.readInt();// player 2's game mode
//			} catch (IOException e) {
//				System.out.println("exception 5");
//				e.printStackTrace();
//				System.exit(0);
//			}
//
//			while (status == Status.CONTINUE) {
//				turn=round_online(status,turn,x_black,y_black,x_white,y_white);
//			}
//
//
//		} else if (game_mode == GameMode.COMPUTER) {// play with computer
//			while (status == Status.CONTINUE) {
//				turn=round_computer(status, turn,x_black,y_black);
//			}
//		}
//		close();
	}
	
	
	

	
	
//	public static void close() {
//		try {
//			clientOutput1.close();
//			clientInput1.close();
//			clientOutput2.close();
//			clientInput2.close();
//			connection1.close();
//			connection2.close();
//			serverSock.close();
//		} catch (IOException e) {
//			System.out.println("exception 9");
//			e.printStackTrace();
//			System.exit(0);
//		}
//	}
}
