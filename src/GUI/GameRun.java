package GUI;

/**
 * GUI wrote by Glen Keane for connect 4.
 * 
 * Not fully commented. A brief description of how it works follows:
 * There is 1 JFrame which contains all the JPanels used throughout.
 * There is panels for player selection(Computer or Human), the board(Click the column
 * You would like to go into) and then a window that says who won/if its a draw.
 * you can click replay to play again.
 * There is an internal class for a human player that uses a GUI.
 * 
 * @author Glen Keane 20057974.
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;

import connect4.Board;
import connect4.ComputerPlayer20057974;
import connect4.Connect4;
import connect4.IPlayer;
import connect4.Location;
import connect4.LocationState;

public class GameRun extends JFrame implements MouseListener {

	private static final long serialVersionUID = -4886604822753896156L;
	private Connect4 game;
	private IPlayer p1, p2;
	private Board currentBoard;
	private JFrame currentFrame;
	private JPanel currentPanel;
	private JFrame tempF;
	private JPanel tempP;

	public GameRun() {
		currentFrame = new JFrame();
		currentPanel = new JPanel();
		initUI1();
	}

	private void initUI1() {
		currentBoard = new Board(6, 7);
		currentPanel.setVisible(false);
		currentFrame.remove(currentPanel);
		currentFrame.setBounds(0, 0, 350, 150);
		currentFrame.setLocationRelativeTo(null);
		currentFrame.setTitle("Connect4 - 20057974");
		currentFrame.setSize(350, 150);
		currentFrame.setLocationRelativeTo(null);
		currentFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		currentPanel = new JPanel();
		currentPanel.setLayout(null);

		JLabel pSel = new JLabel("Select player 1's type:");
		pSel.setBounds(10, 10, 150, 30);
		JButton humButton = new JButton("Human Player");
		humButton.setBounds(20, 50, 140, 30);
		humButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				p1 = new HumanPlayerGUI(LocationState.RED);
				currentPanel.setVisible(false);
				currentFrame.remove(currentPanel);
				UI2();
			}
		});
		JButton comButton = new JButton("Michals Player");
		comButton.setBounds(160, 50, 140, 30);
		comButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				p1 = new ComputerPlayer20057974(LocationState.RED);
				currentPanel.setVisible(false);
				currentFrame.remove(currentPanel);
				UI2();
			}
		});
		currentPanel.add(comButton);
		currentPanel.add(humButton);
		currentPanel.add(pSel);
		currentPanel.setBounds(0, 0, 350, 150);
		currentPanel.setVisible(true);

		currentFrame.setLayout(null);
		currentFrame.add(currentPanel);
		currentFrame.setVisible(true);
	}

	private void UI2() {
		currentPanel = new JPanel();
		currentPanel.setLayout(null);

		JLabel pSel = new JLabel("Select player 2's type:");
		pSel.setBounds(10, 10, 150, 30);
		JButton humButton = new JButton("Human Player");
		humButton.setBounds(20, 50, 140, 30);
		humButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				p2 = new HumanPlayerGUI(LocationState.YELLOW);
				game = new Connect4(p1, p2, currentBoard);
				currentPanel.setVisible(false);
				currentFrame.remove(currentPanel);
				gameUI(6, 7);
			}
		});
		JButton comButton = new JButton("Computer Player");
		comButton.setBounds(160, 50, 140, 30);
		comButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				p2 = new ComputerPlayer20057974(LocationState.YELLOW);
				game = new Connect4(p1, p2, currentBoard);
				currentFrame.setVisible(false);
				currentFrame.remove(currentPanel);
				gameUI(6, 7);
			}
		});
		currentPanel.add(comButton);
		currentPanel.add(humButton);
		currentPanel.add(pSel);
		currentPanel.setBounds(0, 0, 350, 150);
		currentPanel.setVisible(true);

		currentFrame.add(currentPanel);
		currentFrame.setVisible(true);
	}

	private void gameUI(int x, int y) {
		currentFrame.setLayout(new BorderLayout());
		currentFrame.setSize(y * 70, x * 70);
		printBoard();
		playGame();
	}

	private void printBoard() {
		currentPanel.setVisible(false);
		currentFrame.remove(currentPanel);
		currentPanel = new JPanel();
		currentPanel.setLayout(new GridLayout(currentBoard.getNoRows(),
				currentBoard.getNoCols()));
		currentPanel.setOpaque(true);
		for (int i = currentBoard.getNoRows() - 1; i >= 0; i--) {
			for (int j = 0; j < currentBoard.getNoCols(); j++) {
				Panel panel = new Panel(j, i);
				if (currentBoard.getLocationState(new Location(j, i)) == LocationState.RED) {
					panel.setColour(Color.red);
					panel.setBackground(Color.red);
					panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
							Color.black));
				} else if (currentBoard.getLocationState(new Location(j, i)) == LocationState.YELLOW) {
					panel.setColour(Color.yellow);
					panel.setBackground(Color.yellow);
					panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
							Color.black));
				} else {
					panel.setColour(Color.white);
					panel.setBackground(Color.white);
					panel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
							Color.black));
				}
				currentPanel.add(panel);
			}
		}
		currentPanel.setVisible(true);
		currentFrame.add(currentPanel);
		currentFrame.setVisible(true);
	}

	private void playGame() {
		boolean turn, win = false;
		int col;
		if (game.getPlayer() instanceof ComputerPlayer20057974) {
			printBoard();
			col = game.getPlayer().getMove(currentBoard);
			turn = game.takeTurnGUI(col);
			currentBoard = game.getBoard();
			if (turn) {
				win = game.isWin(currentBoard);
				if (game.isDraw()) {
					draw();
					return;
				}
				if (win) {
					win();
					return;
				} else {
					game.nextPlayer();
				}
			}
			playGame();
		} else if (game.getPlayer() instanceof HumanPlayerGUI) {
			printBoard();
			col = game.getPlayer().getMove(currentBoard);
			turn = game.takeTurnGUI(col);
			currentBoard = game.getBoard();
			if (turn) {
				win = game.isWin(currentBoard);
				if (game.isDraw()) {
					draw();
					return;
				}
				if (win) {
					win();
					return;
				} else {
					game.nextPlayer();
				}
			}
			playGame();
		} else {
			currentPanel.setVisible(false);
			currentFrame.remove(currentPanel);
			currentPanel = new JPanel();
			currentPanel.setLayout(new GridLayout(currentBoard.getNoRows(),
					currentBoard.getNoCols()));
			currentPanel.setOpaque(true);
			for (int i = currentBoard.getNoRows() - 1; i >= 0; i--) {
				for (int j = 0; j < currentBoard.getNoCols(); j++) {
					Panel panel = new Panel(j, i);
					panel.addMouseListener(this);
					if (currentBoard.getLocationState(new Location(j, i)) == LocationState.RED) {
						panel.setColour(Color.red);
						panel.setBackground(Color.red);
						panel.setBorder(BorderFactory.createMatteBorder(1, 1,
								1, 1, Color.black));
					} else if (currentBoard
							.getLocationState(new Location(j, i)) == LocationState.YELLOW) {
						panel.setColour(Color.yellow);
						panel.setBackground(Color.yellow);
						panel.setBorder(BorderFactory.createMatteBorder(1, 1,
								1, 1, Color.black));
					} else {
						panel.setColour(Color.white);
						panel.setBackground(Color.white);
						panel.setBorder(BorderFactory.createMatteBorder(1, 1,
								1, 1, Color.black));
					}
					currentPanel.add(panel);
				}
			}
			currentPanel.setVisible(true);
			currentFrame.add(currentPanel);
			currentFrame.setVisible(true);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Panel p = (Panel) e.getSource();
		boolean turn, win = false;
		turn = game.takeTurnGUI(p.getxCoord());
		currentBoard = game.getBoard();
		if (turn) {
			win = game.isWin(currentBoard);
			if (game.isDraw()) {
				draw();
				return;
			}
			if (win) {
				win();
				return;
			} else {
				game.nextPlayer();
			}
		}
		playGame();
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	private void win() {
		printBoard();
		tempF = new JFrame();
		tempP = new JPanel();

		tempF.setBounds(0, 0, 350, 150);
		tempF.setLocationRelativeTo(null);
		tempF.setTitle("Connect4 - 20057974");
		tempF.setSize(350, 150);
		tempF.setLocationRelativeTo(null);
		tempF.setDefaultCloseOperation(EXIT_ON_CLOSE);

		tempP = new JPanel();
		tempP.setLayout(null);

		String str;
		if (game.getPlayer().getPlayerState() == LocationState.RED) {
			str = "Player 1 won. Would you like a new game?";
		} else {
			str = "Player 2 won. Would you like a new game?";
		}
		JLabel pSel = new JLabel(str);

		pSel.setBounds(10, 10, 300, 30);
		JButton replayButton = new JButton("Replay");
		replayButton.setBounds(20, 50, 140, 30);
		replayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				tempF.remove(tempP);
				tempF.setVisible(false);
				initUI1();
			}
		});
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(160, 50, 140, 30);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		tempP.add(replayButton);
		tempP.add(exitButton);
		tempP.add(pSel);
		tempP.setBounds(0, 0, 350, 150);
		tempP.setVisible(true);

		tempF.setLayout(null);
		tempF.add(tempP);
		tempF.setVisible(true);
	}

	private void draw() {
		printBoard();
		tempF = new JFrame();
		tempP = new JPanel();

		tempF.setBounds(0, 0, 350, 150);
		tempF.setLocationRelativeTo(null);
		tempF.setTitle("Connect4 - 20057974");
		tempF.setSize(350, 150);
		tempF.setLocationRelativeTo(null);
		tempF.setDefaultCloseOperation(EXIT_ON_CLOSE);

		tempP = new JPanel();
		tempP.setLayout(null);

		String str = "Draw! Would you like to play again?";
		JLabel pSel = new JLabel(str);

		pSel.setBounds(10, 10, 300, 30);
		JButton replayButton = new JButton("Replay");
		replayButton.setBounds(20, 50, 140, 30);
		replayButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				tempF.remove(tempP);
				tempF.setVisible(false);
				initUI1();
			}
		});
		JButton exitButton = new JButton("Exit");
		exitButton.setBounds(160, 50, 140, 30);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		tempP.add(replayButton);
		tempP.add(exitButton);
		tempP.add(pSel);
		tempP.setBounds(0, 0, 350, 150);
		tempP.setVisible(true);

		tempF.setLayout(null);
		tempF.add(tempP);
		tempF.setVisible(true);
	}

	public class HumanPlayerGUI extends IPlayer {
		int choice = 0;

		public HumanPlayerGUI(LocationState playerState) {
			super(playerState);
		}

		@Override
		public int getMove(Board board) {
			return choice;
		}

	}

	public class Panel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2784364280557054746L;
		private Color colour;
		private int xCoord;
		private int yCoord;

		public Panel(int xCoord, int yCoord) {
			this.colour = Color.white;
			this.xCoord = xCoord;
			this.yCoord = yCoord;
			this.setOpaque(true);
			this.setBorder(new MatteBorder(1, 1, 1, 1, Color.black));
		}

		public Color getColour() {
			return colour;
		}

		public void setColour(Color colour) {
			this.colour = colour;
		}

		public int getxCoord() {
			return xCoord;
		}

		public void setxCoord(int xCoord) {
			this.xCoord = xCoord;
		}

		public int getyCoord() {
			return yCoord;
		}

		public void setyCoord(int yCoord) {
			this.yCoord = yCoord;
		}

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				GameRun ex = new GameRun();
				ex.setVisible(false);
			}
		});
	}

}
