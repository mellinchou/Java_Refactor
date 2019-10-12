package server;

import java.io.IOException;

import server.ServerMain.Status;
import server.ServerMain.Turn;

public class SinglePlayerMode extends PlayerMode{

	public SinglePlayerMode() {
		super();
	}
	
	Status status = Status.CONTINUE;
	Turn turn = Turn.BLACK;// black starts the game
	int x_black = -1, y_black = -1, x_white = -1, y_white = -1;

	

	public static ComputerPlay computer=new ComputerPlay();
	
	@Override
	public void execute() {
		while (status == Status.CONTINUE) {
			turn=round_computer(status, turn,x_black,y_black);
		}
	}
	public static Turn round_computer(Status status,Turn turn, int x_black,int y_black) {
		try {
			for (int i = 0; i < 19; i++) { // send the current game board to the player
				for (int j = 0; j < 19; j++) {
					PlayerModeContext.clientOutput1.writeInt(PlayerModeContext.chessBoard.keys[i][j]);
				}
			}

			int gameState = PlayerModeContext.chessBoard.checkWin(PlayerModeContext.chessBoard.keys, 5);
			if (gameState == 1) {
				status = Status.BLACK_WIN;
				PlayerModeContext.clientOutput1.writeInt(2);
			} else if (gameState == 2) {
				status = Status.WHITE_WIN;
				PlayerModeContext.clientOutput1.writeInt(-2);
			} else if (gameState == 8) {
				status = Status.DRAW;
				PlayerModeContext.clientOutput1.writeInt(0);
			}

			if (turn == Turn.BLACK) {
				PlayerModeContext.clientOutput1.writeInt(1);// 1 for black
				x_black = PlayerModeContext.clientInput1.readInt();// read the coordinates from the player
				y_black = PlayerModeContext.clientInput1.readInt();

				PlayerModeContext.chessBoard.keys[x_black][y_black] = 1;
				System.out.println("After Black's play");
				PlayerModeContext.chessBoard.print();

				turn = Turn.WHITE;
			} else {

				System.out.println("white turn if else");
				PlayerModeContext.clientOutput1.writeInt(-1);
				computer.setKeys(PlayerModeContext.chessBoard.keys);
				int[] pc_decision = computer.computerPlay();
				PlayerModeContext.chessBoard.keys[pc_decision[0]][pc_decision[1]] = 2;
				System.out.println("After White's play");
				PlayerModeContext.chessBoard.print();

				turn = Turn.BLACK;
			}

		} catch (IOException e) {
			System.out.println("exception 8");
			e.printStackTrace();
			System.exit(0);
		}
		return turn;
	}

}
