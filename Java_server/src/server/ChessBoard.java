package server;

public class ChessBoard {
	private static final int ROW = 19;
	private static final int COL = 19;

	public int keys[][] = new int[ROW][COL];// 0 is empty, 1 is black, 2 is white

	public ChessBoard() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				keys[i][j] = 0;// initialize the game board with all empty keys
			}
		}
		//print();
	}

	public void print() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				System.out.print(keys[i][j] + "  ");
			}
			System.out.println("");
		}
	}

	public int checkWin(int keys[][], int win_condition) {

		// check lines horizontally
		for (int i = 0; i < ROW; i++) {
			int count = 1;
			int role = 0;// 0 is empty, 1 is black, 2 is white
			for (int j = 0; j < COL - 1; j++) {
				if (keys[i][j] == keys[i][j + 1] && keys[i][j] != 0) {
					count++;
					role = keys[i][j];
					if (count == win_condition) {
						return role;
					}
				} else
					count = 1;
			}

		}
		// check lines vertically
		for (int j = 0; j < COL - 1; j++) {
			int count = 1;
			int role = 0;
			for (int i = 0; i < ROW - 1; i++) {
				if (keys[i][j] == keys[i + 1][j] && keys[i][j] != 0) {
					count++;
					role = keys[i][j];
					if (count == win_condition) {
						return role;
					}
				} else
					count = 1;
			}

		}
		// check lines diagonally to right (upper triangle)
		for (int start = 0; start < ROW - 1; start++) {
			int count = 1;
			int role = 0;
			for (int i = 0, j = start; i < ROW - 1 && j < COL - 1; i++, j++) {
				if (keys[i][j] != 0 && keys[i][j] == keys[i + 1][j + 1]) {
					count++;
					role = keys[i][j];
					if (count == win_condition) {
						return role;
					}
				} else
					count = 1;
			}
		}
		// check lines diagonally to right (lower triangle)
		for (int start = 1; start < COL - 1; start++) {
			int count = 1;
			int role = 0;
			for (int i = start, j = 0; i < ROW - 1 && j < COL - 1; i++, j++) {
				if (keys[i][j] != 0 && keys[i][j] == keys[i + 1][j + 1]) {
					count++;
					role = keys[i][j];
					if (count == win_condition) {
						return role;
					}
				} else
					count = 1;
			}
		}
		// check lines diagonally to left (upper triangle)
		for (int start = 4; start < ROW - 1; start++) {
			int count = 1;
			int role = 0;
			for (int i = start, j = 0; i > 0 && j < COL - 1; i--, j++) {
				if (keys[i][j] != 0 && keys[i][j] == keys[i - 1][j + 1]) {
					count++;
					role = keys[i][j];
					if (count == win_condition) {
						return role;
					}
				} else
					count = 1;
			}
		}
		// check lines diagonally to left (lower triangle)
		for (int start = 1; start <= 6; start++) {
			int count = 1;
			int role = 0;
			for (int i = ROW - 1, j = start; i > 0 && j < COL - 1; i--, j++) {
				if (keys[i][j] != 0 && keys[i][j] == keys[i - 1][j + 1]) {
					count++;
					role = keys[i][j];
					if (count == win_condition) {
						return role;
					}
				} else
					count = 1;
			}
		}
		// check for draw
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				if (keys[i][j] == 0)
					return 0;// return "continue"
			}
		}
		return 8;// return "draw"
	}

}
