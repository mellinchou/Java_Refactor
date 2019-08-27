
import java.io.*;
import java.net.*;
import java.util.Scanner;

import javax.swing.JOptionPane;

//Server's side main
public class ServerMain {
	enum Status{CONTINUE, BLACK_WIN,WHITE_WIN,DRAW};
	enum Turn{BLACK,WHITE};
	static ServerSocket serverSock;
	static Socket connection1;
	static Socket connection2;
	static DataInputStream clientInput1;
	static DataOutputStream clientOutput1;
	static DataInputStream clientInput2;
	static DataOutputStream clientOutput2;
	static ComputerPlay computer;
	
	public static void main(String[] args) {
		
		int game_mode=-1;
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			JOptionPane.showMessageDialog(null, "Server IP: "+inetAddress.getHostAddress(), "Server IP", JOptionPane.INFORMATION_MESSAGE);
			
			serverSock=new ServerSocket(8888);
			connection1=serverSock.accept();
			clientInput1=new DataInputStream(connection1.getInputStream());
			clientOutput1=new DataOutputStream(connection1.getOutputStream());
			 
			game_mode=clientInput1.readInt();
			System.out.println("Game Mode Selected: "+game_mode);
			
			clientOutput1.writeInt(1);//assigning black to the player 1
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Play p1=new Play();
		Status status=Status.CONTINUE;
		Turn turn=Turn.BLACK;//black starts the game
		Scanner in =new Scanner(System.in);
		int x=-1,y=-1,x1=-1,y1=-1;
		
		if (game_mode==0) {//play with another online player
			try {
				connection2=serverSock.accept();
				clientInput2=new DataInputStream(connection2.getInputStream());
				clientOutput2=new DataOutputStream(connection2.getOutputStream());
				clientOutput2.writeInt(-1);//assigning white to the second player to connect
				clientInput2.readInt();//player 2's game mode
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			while(status==Status.CONTINUE) {
				
				try {
					for (int i=0;i<19;i++) { //send the current game board to the players
						for (int j=0;j<19;j++) {
							clientOutput1.writeInt(p1.keys[i][j]);
							clientOutput2.writeInt(p1.keys[i][j]);
						}
					}
					
					int win = p1.checkWin(p1.keys,5);
					if (win==1) {
						status=Status.BLACK_WIN;
						clientOutput1.writeInt(2);
						clientOutput2.writeInt(2);
					}else if (win==2) {
						status=Status.WHITE_WIN;
						clientOutput1.writeInt(-2);
						clientOutput2.writeInt(-2);
					}else if (win==8) {
						status=Status.DRAW;
						clientOutput1.writeInt(0);
						clientOutput2.writeInt(0);
					}
					
					
					if (turn==Turn.BLACK) {	
						x=-1;
						y=-1;
						clientOutput1.writeInt(1);//1 for black
						clientOutput2.writeInt(1);
						x = clientInput1.readInt();//read the coordinates from the players
						y = clientInput1.readInt();
						
						p1.keys[x][y]=1;
						System.out.println("After Black's play");
						//p1.print();
						System.out.println("From Client Black: "+x+y);
						turn=Turn.WHITE;
					}else {
						System.out.println("wtf");
						x1=-1;
						y1=-1;
						clientOutput1.writeInt(-1);
						clientOutput2.writeInt(-1);//2 for white
						x1 = clientInput2.readInt();//read the coordinates from the players
						y1 = clientInput2.readInt();
						
						p1.keys[x1][y1]=2;
						System.out.println("After White's play");
						//p1.print();
						System.out.println("From Client White: "+x1+""+y1);
						turn=Turn.BLACK;
					}
					
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
				
			}
			
			try {
				clientOutput1.close();
				clientInput1.close();
				clientOutput2.close();
				clientInput2.close();
				connection1.close();
				connection2.close();
				serverSock.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
		}else if (game_mode==1) {//play with computer
			while(status==Status.CONTINUE) {
				
				try {
					for (int i=0;i<19;i++) { //send the current game board to the player
						for (int j=0;j<19;j++) {
							clientOutput1.writeInt(p1.keys[i][j]);
						}
					}
					
					int win=p1.checkWin(p1.keys,5);
					if (win==1) {
						status=Status.BLACK_WIN;
						clientOutput1.writeInt(2);
					}else if (win==2) {
						status=Status.WHITE_WIN;
						clientOutput1.writeInt(-2);
					}else if (win==8) {
						status=Status.DRAW;
						clientOutput1.writeInt(0);
					}
					
					if (turn==Turn.BLACK) {				
						clientOutput1.writeInt(1);//1 for black
						x = clientInput1.readInt();//read the coordinates from the player
						y = clientInput1.readInt();
						
						p1.keys[x][y]=1;
						System.out.println("After Black's play");
						p1.print();
					
						turn=Turn.WHITE;
					}else {
						clientOutput1.writeInt(-1);
						
						int[] pc_decision=computer.computerPlay(win);
						p1.keys[pc_decision[0]][pc_decision[1]]=2;
						System.out.println("After Black's play");
						p1.print();
						
						turn=Turn.BLACK;
					}
			
				}catch (IOException e) {
					e.printStackTrace();
				}
				//System.out.println("From Client Black: "+x+y);
			}
			
			try {
				clientOutput1.close();
				clientInput1.close();
				clientOutput2.close();
				clientInput2.close();
				connection1.close();
				connection2.close();
				serverSock.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

	


