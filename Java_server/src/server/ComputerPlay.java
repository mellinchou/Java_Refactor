package server;

import java.util.Random;

public class ComputerPlay {
	private static final int ROW = 19;
	private static final int COL = 19;
	private int[][] keys;
	
	public void setKeys(int[][]keys) {
		this.keys=keys;
	}

	public int[] computerPlay() {// suppose pc is always white
		int[] decision = { -1, -1 };
		int[][] imaginary_keys = this.keys;

		for (int imaginary_win_condition = 5; imaginary_win_condition > 1; imaginary_win_condition--) {
			// check if white is n steps from winning
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					if (imaginary_keys[i][j] == 0) {
						imaginary_keys[i][j] = 2;
						if (ServerMain.chessBoard.checkWin(keys, imaginary_win_condition) == 2) {
							decision[0] = i;
							decision[1] = j;
							return decision;
						} else
							imaginary_keys[i][j] = 0;
					}
				}
			}
			// check if black is n steps from winning
			for (int i = 0; i < ROW; i++) {
				for (int j = 0; j < COL; j++) {
					if (imaginary_keys[i][j] == 0) {
						imaginary_keys[i][j] = 1;
						if (ServerMain.chessBoard.checkWin(keys, imaginary_win_condition) == 1) {
							decision[0] = i;
							decision[1] = j;
							return decision;
						} else
							imaginary_keys[i][j] = 0;
					}
				}
			}

		}

		if (this.keys[7][7] != 0) {
			decision[0] = 7;
			decision[1] = 7;
			return decision;
		} else {
			Random rand = new Random();
			decision[0] = rand.nextInt(19);
			decision[1] = rand.nextInt(19);
		}
		return decision;
	}

}
