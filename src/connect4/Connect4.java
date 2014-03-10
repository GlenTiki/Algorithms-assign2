package connect4;

import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
/**
 * 
 * Class to manage the connect 4 game
 * 
 * @author Glen Keane 20057974
 * 
 */
public class Connect4 {

	private IPlayer p1, p2;
	private Board board;
	private IPlayer currentPlayer;
	private int numTurns = 0;
	private Location lastLocation;

	public Connect4(IPlayer p1, IPlayer p2, Board board) {
		super();
		this.p1 = p1;
		this.p2 = p2;
		this.board = board;
		this.currentPlayer = p1;
	}

	/**
	 * Toggles current player
	 */
	public void nextPlayer() {
		if (currentPlayer == p1) {
			currentPlayer = p2;
		} else {
			currentPlayer = p1;
		}
	}

	public IPlayer getPlayer(){
		return currentPlayer;
	}
	
	/**
	 * Checks if there's a winner
	 * 
	 * @param board
	 *            to evaluate for winner
	 * @return boolean to detect winner
	 */
	public boolean isWin(Board board) {
		if (numTurns >= 7) {
			if (board.getLocationState(lastLocation) != LocationState.EMPTY) {
				if (lastLocation.getY() >= 3) {// no need to call vertical check
												// as there isn't enough
					// pieces below this piece to have 4 in a row.
					if (board.getVerticalValue(lastLocation) >= 4) {
						return true;
					}
				}
				if (board.getHorizontalValue(lastLocation) >= 4) {
					return true;
				}
				if (board.getDiagonalPosValue(lastLocation) >= 4) {
					return true;
				}
				if (board.getDiagonalNegValue(lastLocation) >= 4) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks for a draw
	 * 
	 * @return
	 */
	public boolean isDraw() {
		return numTurns == board.getNoCols() * board.getNoRows();
	}

	/**
	 * Method called to get next move from player(For a human player in a GUI)
	 * 
	 * @return boolean indicating move take successfully
	 */
	public boolean takeTurnGUI(int col) {
		if (col >= 0) {
			for (int i = 0; i < board.getNoRows(); i++) {
				if (board.getLocationState(new Location(col, i)) == LocationState.EMPTY) {
					board.setLocationState(new Location(col, i), currentPlayer.getPlayerState());
					lastLocation = new Location(col, i);
					numTurns++;
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Method called to get next move from player
	 * 
	 * @return boolean indicating move take successfully
	 */
	public boolean takeTurn() {

		int col = currentPlayer.getMove(board);
		if (col >= 0) {
			for (int i = 0; i < board.getNoRows(); i++) {
				if (board.getLocationState(new Location(col, i)) == LocationState.EMPTY) {
					board.setLocationState(new Location(col, i),
							currentPlayer.getPlayerState());
					lastLocation = new Location(col, i);
					numTurns++;
					return true;
				}
			}
		}
		return false;
	}

	public Board getBoard() {
		return board;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args){
		int playerOne;
		int playerTwo;
		IPlayer p1 = null, p2 = null;
		boolean play = true;
		while (play) {
			// p1 initialisation
			StdOut.println("Player One: ");
			StdOut.println("1) Human Player");
			StdOut.println("2) Computer Player");
			try {
				playerOne = StdIn.readInt();
				if (playerOne == 1) {
					p1 = new HumanPlayer(LocationState.RED);
				} else if (playerOne == 2) {
					p1 = new ComputerPlayer20057974(LocationState.RED);
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				StdOut.println("An error occured, player 1 set to human.");
				p1 = new HumanPlayer(LocationState.RED);
			}
			// p2 initialisation
			StdOut.println("Player Two: ");
			StdOut.println("1) Human Player");
			StdOut.println("2) Computer Player");
			try {
				playerTwo = StdIn.readInt();
				if (playerTwo == 1) {
					p2 = new HumanPlayer(LocationState.YELLOW);
				} else if (playerTwo == 2) {
					p2 = new ComputerPlayer20057974(LocationState.YELLOW);
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				StdOut.println("An error occured, player 2 set to computer.");
				p2 = new ComputerPlayer20057974(LocationState.YELLOW);
			}

			Board board = new Board(6, 7);
			Connect4 game = new Connect4(p1, p2, board);
			boolean win = false;
			boolean draw = false;
			boolean turn = false;
			while (!win && !draw) {
				if (turn) {
					game.nextPlayer();
				}
				StdOut.println(board);
				if (game.currentPlayer == p1) {
					StdOut.println("Player 1's turn.");
				} else if (game.currentPlayer == p2) {
					StdOut.println("Player 2's turn.");
				}
				turn = game.takeTurn();
				if (turn) {
					win = game.isWin(board);
				}
				draw = game.isDraw();
			}
			StdOut.println(board.toString());
			if (win) {
				if (game.currentPlayer == p1) {
					StdOut.println("Player 1 won. Would you like to play another game?(1 for yes, 0 to exit.)");
				} else {
					StdOut.println("Player 2 won. Would you like to play another game?(1 for yes, 0 to exit.)");
				}
			}
			if (draw) {
				StdOut.println("Draw! Want to play another game?(1 for yes, 0 to exit.)");
			}
			int in = StdIn.readInt();
			while (in < 0 || in > 1) {
				StdOut.println("Invalid entry. try again. (1 for replay, 0 to exit.)");
				in = StdIn.readInt();
			}
			if (in == 1) {
				play = true;
			} else {
				play = false;
			}
		}
	}
}
