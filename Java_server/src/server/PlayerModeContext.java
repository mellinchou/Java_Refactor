package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import server.ServerMain.Status;
import server.ServerMain.Turn;


public class PlayerModeContext {
	private PlayerMode playerMode;
	
	public static ServerSocket serverSock;
	public static Socket connection1;
	public static DataInputStream clientInput1;
	public static DataOutputStream clientOutput1;
	public static ChessBoard chessBoard = new ChessBoard();
	public static Turn turn = Turn.BLACK;// black starts the game
	public static int x_black = -1, y_black = -1, x_white = -1, y_white = -1;//coordinates for playing the chess game
	public static Status status = Status.CONTINUE;//initialize the status as continue
	
	public PlayerModeContext() {
		try {
			serverSock = new ServerSocket(8888);
			connection1 = serverSock.accept();
			clientInput1 = new DataInputStream(connection1.getInputStream());
			clientOutput1 = new DataOutputStream(connection1.getOutputStream());
			
			//dynamically initializing the game with the selected game mode
			int game_mode_temp = clientInput1.readInt();
			if (game_mode_temp == 0) {
				this.setPlayerMode(new TwoPlayerMode());
			}
			else if (game_mode_temp == 1) {
				this.setPlayerMode(new SinglePlayerMode());
			}
			clientOutput1.writeInt(1);// assigning black to the player 1
			this.executeGame();//begin the game

		} catch (IOException e) {
			System.out.println("exception 1 from PlayerModeContext");
			e.printStackTrace();
			System.exit(0);
		}
	}
	public void setPlayerMode(PlayerMode mode) {
		this.playerMode=mode;
	}
	
	public void executeGame() {
		playerMode.execute();
	}
}
