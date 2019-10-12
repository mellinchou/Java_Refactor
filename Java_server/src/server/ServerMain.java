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
