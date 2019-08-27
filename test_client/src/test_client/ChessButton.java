package test_client;
import javax.swing.JButton;

//this is the individual buttons on the chess board
public class ChessButton extends JButton{
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;
	private String coordinates;
	
	public ChessButton(int x,int y) { //when creating the button object, use x and y to record it's coordinates
		this.addActionListener(new ChessButtonListener());
		this.x=x;
		this.y=y;
	}
	
	public String getCoordinates() {
		return coordinates;
	}
	
	public String getName() { //to return the coordinates recorded
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append(this.y);
		stringBuilder.append(" ");
		stringBuilder.append(this.x);
		String coordinates = stringBuilder.toString();
		return coordinates;
	}
}
