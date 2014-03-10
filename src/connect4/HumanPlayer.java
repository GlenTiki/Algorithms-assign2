package connect4;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class HumanPlayer extends IPlayer {

	public HumanPlayer(LocationState playerState) {
		super(playerState);
	}

	@Override
	public int getMove(Board board) {
		int move = -1;
			StdOut.println("Which column would you like to place a piece in?");
			try {
				move = StdIn.readInt();
				if(move < 0 || move >= board.getNoCols()){
					move = -1;
					throw new Exception();
				}
			} catch (Exception e) {
				StdOut.println("That is not a valid move. Try again.");
			}
		return move;
	}

}
