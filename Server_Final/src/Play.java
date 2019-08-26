
import java.util.Random;

public class Play {
	private static final int ROW = 19;
	private static final int COL = 19;

	public int keys[][] = new int[ROW][COL];// 0 is empty, 1 is black, 2 is white

	public Play() {
		for (int i = 0; i < ROW; i++) {
			for (int j = 0; j < COL; j++) {
				keys[i][j] = 0;//initialize the game board with all empty keys
			}
		}
		print();
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

	public int[] computerPlay() {//suppose pc is always white
		int[] decision= {-1,-1};
		int[][] imaginary_keys=this.keys;
		
		
		for (int imaginary_win_condition=5;imaginary_win_condition>1;imaginary_win_condition--) {
			//check if white is n steps from winning
			for (int i=0;i<ROW;i++) {
				for (int j=0;j<COL;j++) {
					if (imaginary_keys[i][j]==0) {
						imaginary_keys[i][j]=2;
						if (checkWin(imaginary_keys,imaginary_win_condition)==2) {
							decision[0]=i;
							decision[1]=j;
							return decision;
						}else imaginary_keys[i][j]=0;
					}
				}
			}
			//check if black is n steps from winning
			for (int i=0;i<ROW;i++) {
				for (int j=0;j<COL;j++) {
					if (imaginary_keys[i][j]==0) {
						imaginary_keys[i][j]=1;
						if (checkWin(imaginary_keys,imaginary_win_condition)==1) {
							decision[0]=i;
							decision[1]=j;
							return decision;
						}else imaginary_keys[i][j]=0;
					}
				}
			}
			
		}
		
		if (this.keys[7][7]!=0) {
			decision[0]=7;
			decision[1]=7;
			return decision;
		}else {
			Random rand=new Random();
			decision[0]=rand.nextInt(19);
			decision[1]=rand.nextInt(19);
		}
		return decision;
	}
}

