package connect4;

import java.util.Random;

/**
 * 
 * Example Computer Player. CREATE YOUR OWN VERSION OF THIS, REPLACING THE
 * NUMBER IN THE CLASS NAME WITH YOUR STUDENT NUMBER.
 * 
 * @author Glen Keane.
 * 
 */
public class ComputerPlayer20057974 extends IPlayer {

	private Random rnd;
	private int block2, block3, block4, make1, make2, make3, make4;

	public ComputerPlayer20057974(LocationState playerState) {
		super(playerState);
		rnd = new Random();
		make1 = 1;
		block2 = 100;
		block3 = 250;
		block4 = 5000;
		make2 = 150;
		make3 = 200;
		make4 = 100000;
	}

	@Override
	public int getMove(Board board) {
		int weighting[] = new int[board.getNoCols()];
		for (int i = 0; i < board.getNoCols(); i++) {
			for (int j = 0; j < board.getNoRows(); j++) {
				if (board.getLocationState(new Location(i, j)) == LocationState.EMPTY) {
					weighting[i] += offensiveValues(board, i, j);
					weighting[i] += defensiveValues(board, i, j);
					j = board.getNoRows();
				}else if(board.getLocationState(new Location(i, j)) != LocationState.EMPTY && j == board.getNoRows()-1){
					weighting[i] = 0;
				}
			}
		}
		int highest = 0, value = 0;
		for (int i = 0; i < board.getNoCols(); i++) {
			for (int j = 0; j < board.getNoRows(); j++) {
				if (board.getLocationState(new Location(i, j)) == LocationState.EMPTY) {
					value += offensiveValues(board, i, j);
					value += defensiveValues(board, i, j);
					if (weighting[highest] < value) {
						highest = i;
					} else if (weighting[highest] == value) {
						if (flip()) {
							highest = i;
						}
					}
					value = 0;
					j = board.getNoRows();
				}
			}
		}
		return highest;
	}

	private boolean flip() {
		return rnd.nextBoolean();
	}

	private int defensiveValues(Board board, int i, int j) {
		int value = 0;
		if (board.getLocationState(new Location(i - 1, j)) != getPlayerState() &&
				board.getLocationState(new Location(i - 1, j)) != LocationState.EMPTY) {
			switch (board.getHorizontalValue(new Location(i - 1, j))) {
			case 1:
				value += block2;
				break;
			case 2:
				value += block3;
				break;
			case 3:
				value += block4;
				break;
			case 0:
				value += make1;
				break;
			}
		}
		if (board.getLocationState(new Location(i + 1, j)) != getPlayerState() &&
				board.getLocationState(new Location(i + 1, j)) != LocationState.EMPTY) {
			switch (board.getHorizontalValue(new Location(i + 1, j))) {
			case 1:
				value += block2;
				break;
			case 2:
				value += block3;
				break;
			case 3:
				value += block4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i, j - 1)) != getPlayerState() &&
				board.getLocationState(new Location(i, j - 1)) != LocationState.EMPTY) {
			switch (board.getVerticalValue(new Location(i, j - 1))) {
			case 1:
				value += block2;
				break;
			case 2:
				value += block3;
				break;
			case 3:
				value += block4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i - 1, j - 1)) != getPlayerState() &&
				board.getLocationState(new Location(i - 1, j - 1)) != LocationState.EMPTY) {
			switch (board.getDiagonalPosValue(new Location(i - 1, j - 1))) {
			case 1:
				value += block2;
				break;
			case 2:
				value += block3;
				break;
			case 3:
				value += block4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i + 1, j + 1)) != getPlayerState() &&
				board.getLocationState(new Location(i + 1, j + 1)) != LocationState.EMPTY) {
			switch (board.getDiagonalPosValue(new Location(i + 1, j + 1))) {
			case 1:
				value += block2;
				break;
			case 2:
				value += block3;
				break;
			case 3:
				value += block4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i + 1, j - 1)) != getPlayerState() &&
				board.getLocationState(new Location(i + 1, j - 1)) != LocationState.EMPTY) {
			switch (board.getDiagonalNegValue(new Location(i + 1, j - 1))) {
			case 1:
				value += block2;
				break;
			case 2:
				value += block3;
				break;
			case 3:
				value += block4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i - 1, j + 1)) != getPlayerState() &&
				board.getLocationState(new Location(i - 1, j + 1)) != LocationState.EMPTY) {
			switch (board.getDiagonalNegValue(new Location(i - 1, j + 1))) {
			case 1:
				value += block2;
				break;
			case 2:
				value += block3;
				break;
			case 3:
				value += block4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		return value;
	}

	private int offensiveValues(Board board, int i, int j) {
		int value = 0;
		if (board.getLocationState(new Location(i - 1, j)) == getPlayerState()) {
			switch (board.getHorizontalValue(new Location(i - 1, j))) {
			case 1:
				value += make2;
				break;
			case 2:
				value += make3;
				break;
			case 3:
				value += make4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i + 1, j)) == getPlayerState()) {
			switch (board.getHorizontalValue(new Location(i + 1, j))) {
			case 1:
				value += make2;
				break;
			case 2:
				value += make3;
				break;
			case 3:
				value += make4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i, j - 1)) == getPlayerState()) {
			switch (board.getVerticalValue(new Location(i, j - 1))) {
			case 1:
				value += make2;
				break;
			case 2:
				value += make3;
				break;
			case 3:
				value += make4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i - 1, j - 1)) == getPlayerState()) {
			switch (board.getDiagonalPosValue(new Location(i - 1, j - 1))) {
			case 1:
				value += make2;
				break;
			case 2:
				value += make3;
				break;
			case 3:
				value += make4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i + 1, j + 1)) == getPlayerState()) {
			switch (board.getDiagonalPosValue(new Location(i + 1, j + 1))) {
			case 1:
				value += make2;
				break;
			case 2:
				value += make3;
				break;
			case 3:
				value += make4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i + 1, j + 1)) == getPlayerState()) {
			switch (board.getDiagonalPosValue(new Location(i + 1, j + 1))) {
			case 1:
				value += make2;
				break;
			case 2:
				value += make3;
				break;
			case 3:
				value += make4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i + 1, j - 1)) == getPlayerState()) {
			switch (board.getDiagonalNegValue(new Location(i + 1, j - 1))) {
			case 1:
				value += make2;
				break;
			case 2:
				value += make3;
				break;
			case 3:
				value += make4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		if (board.getLocationState(new Location(i - 1, j + 1)) == getPlayerState()) {
			switch (board.getDiagonalNegValue(new Location(i - 1, j + 1))) {
			case 1:
				value += make2;
				break;
			case 2:
				value += make3;
				break;
			case 3:
				value += make4;
				break;
			case 0:
				value += make1;
				break;
			}
		} else {
			value += make1;
		}
		return value;
	}
}