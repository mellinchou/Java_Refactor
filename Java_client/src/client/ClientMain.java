package client;

//Client Side Main
public class ClientMain {
	
	public static GameExecuter gameExecuter=new GameExecuter();
	public static MusicPlayer musicPlayer=new MusicPlayer();

	public static void main(String[] args) {
		MusicPlayer.playMusic();
		gameExecuter.execute();
	}

}
