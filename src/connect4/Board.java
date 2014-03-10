package connect4;

/**
 * This class represents a board for Connect 4. Please leave method headers as
 * is.
 * 
 * @author Glen Keane.
 * 
 */
public class Board {

	private LocationState board[][];
	private int noCols, noRows;

	/**
	 * 
	 * This constructor creates and initialises the board.
	 * 
	 * @param col
	 *            the number of columns in the board
	 * @param row
	 *            the number of rows in the board
	 * @see LocationState
	 */
	public Board(int col, int row) {

		board = new LocationState[col][row];
		noCols = col;
		noRows = row;
		clear();

	}

	/**
	 * This method clears the board by setting each element to
	 * LocationState.EMPTY
	 * 
	 * @return Nothing
	 */
	public void clear() {

		for (int i = 0; i < board.length; i++)
			for (int j = 0; j < board[0].length; j++)
				board[i][j] = LocationState.EMPTY;

	}

	/**
	 * This method gets the location state (i.e. colour) at a particular
	 * location
	 * 
	 * @param location
	 * @return Location state as LocationState
	 * @see Location
	 * @see LocationState
	 */
	public LocationState getLocationState(Location location) {
		try {
			return board[location.getX()][location.getY()];
		} catch (Exception e) {
			return LocationState.EMPTY;
		}
	}

	/**
	 * This method sets the location state (i.e. colour) at a particular
	 * location
	 * 
	 * @param location
	 * @return Nothing
	 * @see Location
	 * @see LocationState
	 */
	public boolean setLocationState(Location location, LocationState state) {
		if (location.getX() < getNoCols() && location.getY() < getNoRows()) {
			if (location.getX() >= 0 && location.getY() >= 0) {
				board[location.getX()][location.getY()] = state;
				return true;
			}
		}
		return false;
	}

	public String toString() {
		String s = "\t  [0]\t  [1]\t  [2]\t  [3]\t  [4]\t  [5]\n";
		for (int i = noRows - 1; i >= 0; i--) {
			s += ("[" + i + "]\t");
			for (int j = 0; j < noCols; j++)
				s += (board[j][i] + "\t");
			s += "\n";
		}

		return s;
	}

	// recursive public method to get a vertical value for a piece on the board.
	public int getVerticalValue(Location location) {
		if (location.getY() >= 0) {
			if (board[location.getX()][location.getY()] != LocationState.EMPTY) {
				if (getLocationState(new Location(location.getX(),
						location.getY() - 1)) == board[location.getX()][location
						.getY()]) {
					return getVerticalValue(new Location(location.getX(),
							location.getY() - 1)) + 1;
				}
			}
			return 1;
		}
		return 0;
	}

	// public method to get a horizontal value for a piece on the board.
	public int getHorizontalValue(Location location) {
		try {
			int value = 0;
			if (board[location.getX()][location.getY()] != LocationState.EMPTY) {
				value++;
				if (location.getX() > 0) {
					if (getLocationState(new Location(location.getX() - 1,
							location.getY())) == board[location.getX()][location
							.getY()]) {
						value += getLeftValue(new Location(location.getX() - 1,
								location.getY())); // call private recursive
													// method
													// to
													// get value.
					}
				}
				if (location.getX() < noCols - 1) {
					if (getLocationState(new Location(location.getX() + 1,
							location.getY())) == board[location.getX()][location
							.getY()]) {
						value += getRightValue(new Location(
								location.getX() + 1, location.getY())); // call
																		// private
																		// recursive
																		// method
																		// to
																		// get
																		// value.
					}
				}
			}
			return value;
		} catch (Exception e) {
			return 0;
		}
	}

	// private method to get the value of a piece to the left of a piece.
	private int getLeftValue(Location location) {
		if (location.getX() > 0) {
			if (getLocationState(new Location(location.getX() - 1,
					location.getY())) == board[location.getX()][location.getY()]) {
				return getLeftValue(new Location(location.getX() - 1,
						location.getY())) + 1;
			}
		}
		return 1;
	}

	// private method to get the value of a piece to the right of a piece.
	private int getRightValue(Location location) {
		if (location.getX() < noCols - 1) {
			if (getLocationState(new Location(location.getX() + 1,
					location.getY())) == board[location.getX()][location.getY()]) {
				return getRightValue(new Location(location.getX() + 1,
						location.getY())) + 1;
			}
		}
		return 1;
	}

	// public method to get the value of a piece in a positive diagonal line.
	// leaning like a forward slash => /
	public int getDiagonalPosValue(Location location) {
		try {
			int value = 0;
			if (board[location.getX()][location.getY()] != LocationState.EMPTY) {
				value++;
				if (location.getX() > 0) {
					if (location.getY() > 0) {
						if (getLocationState(new Location(location.getX() - 1,
								location.getY() - 1)) == board[location.getX()][location
								.getY()]) {
							value += GetDiaPosBelow(new Location(
									location.getX() - 1, location.getY() - 1));
						}
					}
				}
				if (location.getX() < noCols - 1) {
					if (location.getY() < noRows - 1) {
						if (getLocationState(new Location(location.getX() + 1,
								location.getY() + 1)) == board[location.getX()][location
								.getY()]) {
							value += GetDiaPosAbove(new Location(
									location.getX() + 1, location.getY() + 1));
						}
					}
				}
			}
			return value;
		} catch (Exception e) {
			return 0;
		}
	}

	// private recursive method for checking the value of
	// the piece in (x-1, y-1)
	private int GetDiaPosBelow(Location location) {
		if (location.getX() > 0) {
			if (location.getY() > 0) {
				if (getLocationState(new Location(location.getX() - 1,
						location.getY() - 1)) == board[location.getX()][location
						.getY()]) {
					return 1 + GetDiaPosBelow(new Location(location.getX() - 1,
							location.getY() - 1));
				}
			}
		}
		return 1;
	}

	// private recursive method for checking the value of
	// the piece in (x+1, y+1)
	private int GetDiaPosAbove(Location location) {
		if (location.getX() < noCols - 1) {
			if (location.getY() < noRows - 1) {
				if (getLocationState(new Location(location.getX() + 1,
						location.getY() + 1)) == board[location.getX()][location
						.getY()]) {
					return 1 + GetDiaPosAbove(new Location(location.getX() + 1,
							location.getY() + 1));
				}
			}
		}
		return 1;
	}

	// public method to get the value of a piece in a negative diagonal line.
	// leaning like a back slash => \
	public int getDiagonalNegValue(Location location) {
		try {
			int value = 0;
			if (board[location.getX()][location.getY()] != LocationState.EMPTY) {
				value++;
				if (location.getX() > 0) {
					if (location.getY() < noRows - 1) {
						if (getLocationState(new Location(location.getX() - 1,
								location.getY() + 1)) == board[location.getX()][location
								.getY()]) {
							value += GetDiaNegBelow(new Location(
									location.getX() - 1, location.getY() + 1));
						}
					}
				}
				if (location.getX() < noCols - 1) {
					if (location.getY() > 0) {
						if (getLocationState(new Location(location.getX() + 1,
								location.getY() - 1)) == board[location.getX()][location
								.getY()]) {
							value += GetDiaNegAbove(new Location(
									location.getX() + 1, location.getY() - 1));
						}
					}
				}
			}
			return value;
		} catch (Exception e) {
			return 0;
		}
	}

	private int GetDiaNegBelow(Location location) {
		if (location.getX() > 0) {
			if (location.getY() < noRows - 1) {
				if (getLocationState(new Location(location.getX() - 1,
						location.getY() + 1)) == board[location.getX()][location
						.getY()]) {
					return 1 + GetDiaNegBelow(new Location(location.getX() - 1,
							location.getY() + 1));
				}
			}
		}
		return 1;
	}

	private int GetDiaNegAbove(Location location) {
		if (location.getX() < noCols - 1) {
			if (location.getY() > 0) {
				if (getLocationState(new Location(location.getX() + 1,
						location.getY() - 1)) == board[location.getX()][location
						.getY()]) {
					return 1 + GetDiaNegAbove(new Location(location.getX() + 1,
							location.getY() - 1));
				}
			}
		}
		return 1;
	}

	/**
	 * Gets the number of columns on the board.
	 * 
	 * @return number of columns on board as an integer
	 */
	public int getNoCols() {
		return noCols;
	}

	/**
	 * Gets the number of rows on the board.
	 * 
	 * @return number of rows on board as an integer
	 */
	public int getNoRows() {
		return noRows;
	}

}