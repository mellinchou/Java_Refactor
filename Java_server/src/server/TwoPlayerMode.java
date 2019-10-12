package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import server.ServerMain.Status;
import server.ServerMain.Turn;

public class TwoPlayerMode extends PlayerMode{
	public static DataInputStream clientInput2;
	public static DataOutputStream clientOutput2;
	public static Socket connection2;
	public static Status status = Status.CONTINUE;//initialize the status as continue
	public static Turn turn = Turn.BLACK;// black starts the game
	public static int x_black = -1, y_black = -1, x_white = -1, y_white = -1;//coordinates for playing the chess game

	public TwoPlayerMode() {
		super();
		
		//initialize the sockets and data streams for the second player
		try {
			connection2 = PlayerModeContext.serverSock.accept();
			clientInput2 = new DataInputStream(connection2.getInputStream());
			clientOutput2 = new DataOutputStream(connection2.getOutputStream());
			clientOutput2.writeInt(-1);// assigning white to the second player to connect
			clientInput2.readInt();// player 2's game mode
		} catch (IOException e) {
			System.out.println("exception 1 from TwoPlayerMode");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
	@Override
	public void execute() {
		while (status == Status.CONTINUE) {
			turn=round_online(status,turn,x_black,y_black,x_white,y_white);
		}
	} 
	public static Turn round_online(Status status,Turn turn, int x_black,int y_black,int x_white, int y_white) {

		try {
			for (int i = 0; i < 19; i++) { // send the current game board to the players
				for (int j = 0; j < 19; j++) {
					PlayerModeContext.clientOutput1.writeInt(PlayerModeContext.chessBoard.keys[i][j]);
					clientOutput2.writeInt(PlayerModeContext.chessBoard.keys[i][j]);
				}
			}

			int win = PlayerModeContext.chessBoard.checkWin(PlayerModeContext.chessBoard.keys, 5);
			if (win == 1) {
				status = Status.BLACK_WIN;
				PlayerModeContext.clientOutput1.writeInt(2);
				clientOutput2.writeInt(2);
			} else if (win == 2) {
				status = Status.WHITE_WIN;
				PlayerModeContext.clientOutput1.writeInt(-2);
				clientOutput2.writeInt(-2);
			} else if (win == 8) {
				status = Status.DRAW;
				PlayerModeContext.clientOutput1.writeInt(0);
				clientOutput2.writeInt(0);
			}

			if (turn == Turn.BLACK) {
				x_black = -1;
				y_black = -1;
				PlayerModeContext.clientOutput1.writeInt(1);// 1 for black
				clientOutput2.writeInt(1);
				x_black = PlayerModeContext.clientInput1.readInt();// read the coordinates from the players
				y_black = PlayerModeContext.clientInput1.readInt();

				PlayerModeContext.chessBoard.keys[x_black][y_black] = 1;
				System.out.println("After Black's play");
				System.out.println("From Client Black: " + x_black + y_black);
				turn = Turn.WHITE;
			} else {
				x_white = -1;
				y_white = -1;
				PlayerModeContext.clientOutput1.writeInt(-1);
				clientOutput2.writeInt(-1);// 2 for white
				x_white = clientInput2.readInt();// read the coordinates from the players
				y_white = clientInput2.readInt();

				PlayerModeContext.chessBoard.keys[x_white][y_white] = 2;
				System.out.println("After White's play");
				// p1.print();
				System.out.println("From Client White: " + x_white + "" + y_white);
				turn = Turn.BLACK;
			}

		} catch (IOException e) {

			System.out.println("exception 2 from TwoPlayerMode");
			e.printStackTrace();
			System.exit(0);
		}
		return turn;
	}
}
