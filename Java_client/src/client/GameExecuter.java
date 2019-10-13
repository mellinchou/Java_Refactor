package client;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;


public class GameExecuter {
	public static int[][] keys = new int[19][19];// to store the state of each cell in the game board (coming from the server)
	public static DataInputStream serverInput;
	public static DataOutputStream serverOutput;
	public static UIFrame[] ui_frames= {new StartFrame(),new IPFrame(),new GameModeFrame(),new GameFrame()};
	public static String ipAddress = "";
	public static enum GAME_STATE {
		BLACK, WHITE, BLACK_WIN, WHITE_WIN, DRAW
	};
	public static GAME_STATE gameState = GAME_STATE.BLACK;// "player" stores the state of the game (1 = black's turn, -1 = white's turn, 2 = black win, -2 = white win, 0 = draw)

	public void execute() {
		ui_frames[0].setVisible(true);//open the start frame
		int role = 0;// "role" stores the color of the player from serverInput
		GAME_STATE gameRole = GAME_STATE.BLACK;// initialize
		Socket cSock;
		try {
			cSock = new Socket(ipAddress, 8888);
			serverInput = new DataInputStream(cSock.getInputStream());
			serverOutput = new DataOutputStream(cSock.getOutputStream());
			while (getAddress() == "") {
			// stay here dont move until ip address is entered
			}
			role = serverInput.readInt();// the first integer server sends is the color of the player
			System.out.println("Client got role " + role);
			gameRole = serverFeedBackToGameState(role);// convert the role into BLACK or WHITE
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int roundCount = 1;
		while (true) {
			// read the game board from the server
			System.out.println("Round " + roundCount);
			setUI();
			roundCount++;
			updateGame(gameRole);
			if (gameState == GAME_STATE.BLACK_WIN || gameState == GAME_STATE.WHITE_WIN|| gameState == GAME_STATE.DRAW) {
				break;
			}
		}
		// game ends, reset for playing again
		ui_frames[3].setVisible(false);
		ui_frames[0].setVisible(true);
		ipAddress = "";

//		serverOutput.close();
//		serverInput.close();
//		cSock.close();
	}
	
	public static void setUI() {
		try {
			// read the game board from the server
			for (int i = 0; i < 19; i++) {
				for (int j = 0; j < 19; j++) {
					int temp = serverInput.readInt();
					System.out.print(temp + "  ");
					keys[i][j] = temp;
					ui_frames[3].buttons[i][j].setEnabled(false);
					if (keys[i][j] == 1) {// if the cell is black, set the corresponding button to black
						ui_frames[3].buttons[i][j].setBackground(Color.black);
					} else if (keys[i][j] == 2) {// if the cell is white, set the corresponding button to white
						ui_frames[3].buttons[i][j].setBackground(Color.white);
					}
				}
				System.out.println("");
			}
			// read in the game state from server and output "black turn" or "white turn"
			int serverFeedBack = serverInput.readInt();
			gameState = serverFeedBackToGameState(serverFeedBack);
			if (gameState == GAME_STATE.BLACK) {
				System.out.println("Black's turn");
				ui_frames[3].lab_turn.setText("Black's Turn");
			} else if (gameState == GAME_STATE.WHITE) {
				System.out.println("White's turn");
				ui_frames[3].lab_turn.setText("White's Turn");
			}
		} catch (Exception e) {
			System.out.println("exception 3 from ClientMain " + e);
			System.exit(0);
		}
	}

	public static GAME_STATE serverFeedBackToGameState(int serverFeedBack) { // transfer serverInput to current game's
																				// state
		if (serverFeedBack == 1) {
			return GAME_STATE.BLACK;
		} else if (serverFeedBack == -1) {
			return GAME_STATE.WHITE;
		} else if (serverFeedBack == 2) {
			return GAME_STATE.BLACK_WIN;
		} else if (serverFeedBack == -2) {
			return GAME_STATE.WHITE_WIN;
		} else { // serverFeedBack==0
			return GAME_STATE.DRAW;
		}
	}

	public static void updateGame(GAME_STATE gameRole) {
		if (gameState == gameRole) { // if the current turn is the player's color
			for (int i = 0; i < 19; i++) {
				for (int j = 0; j < 19; j++) {
					if (keys[i][j] == 0) {// enable the cells that are empty
						ui_frames[3].buttons[i][j].setEnabled(true);
					}

				}
			}
		} else if (gameState == GAME_STATE.BLACK_WIN) {
			System.out.println("Black wins");
			JOptionPane.showMessageDialog(null, "Black Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		} else if (gameState == GAME_STATE.WHITE_WIN) {
			System.out.println("White wins");
			JOptionPane.showMessageDialog(null, "White Wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		} else if (gameState == GAME_STATE.DRAW) {
			System.out.println("Draw");
			JOptionPane.showMessageDialog(null, "It's a Draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void send(int x, int y) throws IOException {// send the coordinates to the server
		serverOutput.writeInt(y);
		serverOutput.writeInt(x);
	}

	public static void send(int x) throws IOException {// send one int to server (for button decisions)
		serverOutput.writeInt(x);
	}

	public static String getAddress() {
		System.out.print("");
		return ipAddress;
	}

	public static void setAddress(String ads) {
		System.out.println("Address updated");
		ipAddress = ads;
	}


}
