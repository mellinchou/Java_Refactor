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
		while (PlayerModeContext.status == Status.CONTINUE) {
			round_online();
		}
	} 
	public static void round_online() {
		try {
			for (int i = 0; i < 19; i++) { // send the current game board to the players
				for (int j = 0; j < 19; j++) {
					PlayerModeContext.clientOutput1.writeInt(PlayerModeContext.chessBoard.keys[i][j]);
					clientOutput2.writeInt(PlayerModeContext.chessBoard.keys[i][j]);
				}
			}

			int win = PlayerModeContext.chessBoard.checkWin(PlayerModeContext.chessBoard.keys, 5);
			if (win == 1) {
				PlayerModeContext.status = Status.BLACK_WIN;
				PlayerModeContext.clientOutput1.writeInt(2);
				clientOutput2.writeInt(2);
			} else if (win == 2) {
				PlayerModeContext.status = Status.WHITE_WIN;
				PlayerModeContext.clientOutput1.writeInt(-2);
				clientOutput2.writeInt(-2);
			} else if (win == 8) {
				PlayerModeContext.status = Status.DRAW;
				PlayerModeContext.clientOutput1.writeInt(0);
				clientOutput2.writeInt(0);
			}

			if (PlayerModeContext.turn == Turn.BLACK) {
				PlayerModeContext.clientOutput1.writeInt(1);// 1 for black
				clientOutput2.writeInt(1);
				PlayerModeContext.x_black = PlayerModeContext.clientInput1.readInt();// read the coordinates from the players
				PlayerModeContext.y_black = PlayerModeContext.clientInput1.readInt();

				PlayerModeContext.chessBoard.keys[PlayerModeContext.x_black][PlayerModeContext.y_black] = 1;
				System.out.println("After Black's play");
				System.out.println("From Client Black: " + PlayerModeContext.x_black + PlayerModeContext.y_black);
				PlayerModeContext.turn = Turn.WHITE;
			} else {
				PlayerModeContext.clientOutput1.writeInt(-1);
				clientOutput2.writeInt(-1);// 2 for white
				PlayerModeContext.x_white = clientInput2.readInt();// read the coordinates from the players
				PlayerModeContext.y_white = clientInput2.readInt();

				PlayerModeContext.chessBoard.keys[PlayerModeContext.x_white][PlayerModeContext.y_white] = 2;
				System.out.println("After White's play");
				System.out.println("From Client White: " + PlayerModeContext.x_white + "" + PlayerModeContext.y_white);
				PlayerModeContext.turn = Turn.BLACK;
			}

		} catch (IOException e) {

			System.out.println("exception 2 from TwoPlayerMode");
			e.printStackTrace();
			System.exit(0);
		}
	}
}
