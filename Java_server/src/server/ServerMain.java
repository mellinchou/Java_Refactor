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

	public static ServerSocket serverSock;
	public static Socket connection1;
	public static Socket connection2;
	public static DataInputStream clientInput1;
	public static DataOutputStream clientOutput1;
	public static DataInputStream clientInput2;
	public static DataOutputStream clientOutput2;
	public static ComputerPlay computer=new ComputerPlay();
	public static ChessBoard chessBoard = new ChessBoard();

	public static void main(String[] args) {

		GameMode game_mode = GameMode.COMPUTER;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			JOptionPane.showMessageDialog(null, "Server IP: " + inetAddress.getHostAddress(), "Server IP",
					JOptionPane.INFORMATION_MESSAGE);

			serverSock = new ServerSocket(8888);
			connection1 = serverSock.accept();
			clientInput1 = new DataInputStream(connection1.getInputStream());
			clientOutput1 = new DataOutputStream(connection1.getOutputStream());
			int game_mode_temp = clientInput1.readInt();
			if (game_mode_temp == 0)
				game_mode = GameMode.ONLINE;
			else if (game_mode_temp == 1)
				game_mode = GameMode.COMPUTER;
			System.out.println("Game Mode Selected: " + game_mode);
			clientOutput1.writeInt(1);// assigning black to the player 1

		} catch (IOException e) {

			System.out.println("exception 4");
			e.printStackTrace();
			System.exit(0);
		}

		
		Status status = Status.CONTINUE;
		Turn turn = Turn.BLACK;// black starts the game
		int x_black = -1, y_black = -1, x_white = -1, y_white = -1;

		if (game_mode == GameMode.ONLINE) {// play with another online player
			try {
				connection2 = serverSock.accept();
				clientInput2 = new DataInputStream(connection2.getInputStream());
				clientOutput2 = new DataOutputStream(connection2.getOutputStream());
				clientOutput2.writeInt(-1);// assigning white to the second player to connect
				clientInput2.readInt();// player 2's game mode
			} catch (IOException e) {
				System.out.println("exception 5");
				e.printStackTrace();
				System.exit(0);
			}

			while (status == Status.CONTINUE) {
				turn=round_online(status,turn,x_black,y_black,x_white,y_white);
			}


		} else if (game_mode == GameMode.COMPUTER) {// play with computer
			while (status == Status.CONTINUE) {
				turn=round_computer(status, turn,x_black,y_black);
			}
		}
		close();
	}
	
	
	public static Turn round_online(Status status,Turn turn, int x_black,int y_black,int x_white, int y_white) {

		try {
			for (int i = 0; i < 19; i++) { // send the current game board to the players
				for (int j = 0; j < 19; j++) {
					clientOutput1.writeInt(chessBoard.keys[i][j]);
					clientOutput2.writeInt(chessBoard.keys[i][j]);
				}
			}

			int win = chessBoard.checkWin(chessBoard.keys, 5);
			if (win == 1) {
				status = Status.BLACK_WIN;
				clientOutput1.writeInt(2);
				clientOutput2.writeInt(2);
			} else if (win == 2) {
				status = Status.WHITE_WIN;
				clientOutput1.writeInt(-2);
				clientOutput2.writeInt(-2);
			} else if (win == 8) {
				status = Status.DRAW;
				clientOutput1.writeInt(0);
				clientOutput2.writeInt(0);
			}

			if (turn == Turn.BLACK) {
				x_black = -1;
				y_black = -1;
				clientOutput1.writeInt(1);// 1 for black
				clientOutput2.writeInt(1);
				x_black = clientInput1.readInt();// read the coordinates from the players
				y_black = clientInput1.readInt();

				chessBoard.keys[x_black][y_black] = 1;
				System.out.println("After Black's play");
				System.out.println("From Client Black: " + x_black + y_black);
				turn = Turn.WHITE;
			} else {
				x_white = -1;
				y_white = -1;
				clientOutput1.writeInt(-1);
				clientOutput2.writeInt(-1);// 2 for white
				x_white = clientInput2.readInt();// read the coordinates from the players
				y_white = clientInput2.readInt();

				chessBoard.keys[x_white][y_white] = 2;
				System.out.println("After White's play");
				// p1.print();
				System.out.println("From Client White: " + x_white + "" + y_white);
				turn = Turn.BLACK;
			}

		} catch (IOException e) {

			System.out.println("exception 6");
			e.printStackTrace();
			System.exit(0);
		}
		return turn;
	}

	public static Turn round_computer(Status status,Turn turn, int x_black,int y_black) {
		try {
			for (int i = 0; i < 19; i++) { // send the current game board to the player
				for (int j = 0; j < 19; j++) {
					clientOutput1.writeInt(chessBoard.keys[i][j]);
				}
			}

			int gameState = chessBoard.checkWin(chessBoard.keys, 5);
			if (gameState == 1) {
				status = Status.BLACK_WIN;
				clientOutput1.writeInt(2);
			} else if (gameState == 2) {
				status = Status.WHITE_WIN;
				clientOutput1.writeInt(-2);
			} else if (gameState == 8) {
				status = Status.DRAW;
				clientOutput1.writeInt(0);
			}

			if (turn == Turn.BLACK) {
				clientOutput1.writeInt(1);// 1 for black
				x_black = clientInput1.readInt();// read the coordinates from the player
				y_black = clientInput1.readInt();

				chessBoard.keys[x_black][y_black] = 1;
				System.out.println("After Black's play");
				chessBoard.print();

				turn = Turn.WHITE;
			} else {

				System.out.println("white turn if else");
				clientOutput1.writeInt(-1);
				computer.setKeys(chessBoard.keys);
				int[] pc_decision = computer.computerPlay();
				chessBoard.keys[pc_decision[0]][pc_decision[1]] = 2;
				System.out.println("After White's play");
				chessBoard.print();

				turn = Turn.BLACK;
			}

		} catch (IOException e) {
			System.out.println("exception 8");
			e.printStackTrace();
			System.exit(0);
		}
		return turn;
	}
	
	public static void close() {
		try {
			clientOutput1.close();
			clientInput1.close();
			clientOutput2.close();
			clientInput2.close();
			connection1.close();
			connection2.close();
			serverSock.close();
		} catch (IOException e) {
			System.out.println("exception 9");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
