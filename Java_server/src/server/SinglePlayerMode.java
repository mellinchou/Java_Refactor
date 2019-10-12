package server;

import java.io.IOException;

import server.ServerMain.Status;
import server.ServerMain.Turn;

public class SinglePlayerMode extends PlayerMode{
	
	public static ComputerPlay computer=new ComputerPlay();
	
	public SinglePlayerMode() {
		super();
	}
	
	@Override
	public void execute() {
		while (PlayerModeContext.status == Status.CONTINUE) {
			round_computer();
		}
	}
	public static void round_computer() {
		try {
			for (int i = 0; i < 19; i++) { // send the current game board to the player
				for (int j = 0; j < 19; j++) {
					PlayerModeContext.clientOutput1.writeInt(PlayerModeContext.chessBoard.keys[i][j]);
				}
			}

			int gameState = PlayerModeContext.chessBoard.checkWin(PlayerModeContext.chessBoard.keys, 5);
			if (gameState == 1) {
				PlayerModeContext.status = Status.BLACK_WIN;
				PlayerModeContext.clientOutput1.writeInt(2);
			} else if (gameState == 2) {
				PlayerModeContext.status = Status.WHITE_WIN;
				PlayerModeContext.clientOutput1.writeInt(-2);
			} else if (gameState == 8) {
				PlayerModeContext.status = Status.DRAW;
				PlayerModeContext.clientOutput1.writeInt(0);
			}

			if (PlayerModeContext.turn == Turn.BLACK) {
				PlayerModeContext.clientOutput1.writeInt(1);// 1 for black
				PlayerModeContext.x_black = PlayerModeContext.clientInput1.readInt();// read the coordinates from the player
				PlayerModeContext.y_black = PlayerModeContext.clientInput1.readInt();

				PlayerModeContext.chessBoard.keys[PlayerModeContext.x_black][PlayerModeContext.y_black] = 1;
				System.out.println("After Black's play");
				PlayerModeContext.chessBoard.print();

				PlayerModeContext.turn = Turn.WHITE;
			} else {

				System.out.println("white turn if else");
				PlayerModeContext.clientOutput1.writeInt(-1);
				computer.setKeys(PlayerModeContext.chessBoard.keys);
				int[] pc_decision = computer.computerPlay();
				PlayerModeContext.chessBoard.keys[pc_decision[0]][pc_decision[1]] = 2;
				System.out.println("After White's play");
				PlayerModeContext.chessBoard.print();

				PlayerModeContext.turn = Turn.BLACK;
			}

		} catch (IOException e) {
			System.out.println("exception 1 in SinglePlayerMode");
			e.printStackTrace();
			System.exit(0);
		}
	}

}
