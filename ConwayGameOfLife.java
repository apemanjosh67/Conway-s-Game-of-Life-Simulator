/**
 * 
 * Josh Muszka
 * 
 * Conway's Game of Life Simulator
 * (from Wikipedia:) The Game of Life, also known simply as Life, is a cellular 
 * automaton devised by the British mathematician John Horton Conway 
 * in 1970. It is a zero-player game, meaning that its evolution is 
 * determined by its initial state, requiring no further input. One 
 * interacts with the Game of Life by creating an initial configuration
 * and observing how it evolves
 *  
 * Version 1.0
 * December 26, 2021
 * 
 */

package gameoflife;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameOfLife2 {



	

	final static int WINSIZE = 800;
	final static int GRID = 60;
	int gridSize = WINSIZE / 40;
	boolean isRunning = false;


	int board[][] = new int[GRID][GRID];
	int generation = 0;
	
	DrawingPanel panel;
	JPanel btnPanel;
	JButton startBtn;
	JButton resetBtn;
	JLabel genLbl;
	
	public static void main (String[] args) {
		new GameOfLife2();		
	}

	GameOfLife2() {

		JFrame window = new JFrame("Conway's Game of Life");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new DrawingPanel();
		btnPanel = new JPanel();


		btnPanel.setPreferredSize(new Dimension(WINSIZE, 40));
		btnPanel.setBackground(Color.decode("#1c1c1c"));
		startBtn = new JButton("Start");
		startBtn.addActionListener(new StartButtonAL());
		resetBtn = new JButton("Reset");
		resetBtn.addActionListener(new ResetButtonAL());

		genLbl = new JLabel (" GEN " + generation);
		genLbl.setFont(new Font("Arial", Font.BOLD, 24));
		genLbl.setForeground(Color.white);

		btnPanel.add(startBtn);
		btnPanel.add(resetBtn);
		btnPanel.add(genLbl);
		window.add(panel, BorderLayout.NORTH);
		window.add(btnPanel, BorderLayout.SOUTH);


		runLife();

		window.pack();
		window.setVisible(true);



	}


	public void runLife() {

		Thread gfxThread = new Thread() {

			public void run() {
				while(true) {
					
					panel.repaint();
					
					try {
						Thread.sleep(8);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		};


		Thread lifeThread = new Thread() {

			public void run() {

				while(true) {

					try {
						Thread.sleep(600);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					if (isRunning) {
						
						birth();
						death();

						updateBoard();
						generation++;

						genLbl.setText(" GEN " + generation);

					}
				}

			}

		};



		gfxThread.start();
		lifeThread.start();

	}


	
	
	
	//0 = dead
	//1 = alive
	//2 = alive, but will die next generation
	//3 = dead, but will be born next generation

	public void death() {

		for (int i = 0; i < GRID; i++) {

			for (int j = 0; j < GRID; j++) {

				int neighbours = 0;

				//check live cells
				//counts the number of living cells adjacent to the current cell
				
				if (board[i][j] == 1) {
					if (i > 0) {

						if (board[i-1][j] == 1 || board[i-1][j] == 2) {
							neighbours++;
						}

					}
					if (i < GRID -1) {

						if (board[i+1][j] == 1 || board[i+1][j] == 2) {
							neighbours++;
						}

					}
					if (j > 0) {

						if (board[i][j-1] == 1 || board[i][j-1] == 2) {							
							neighbours++;
						}

					}
					if (j < GRID - 1) {

						if (board[i][j+1] == 1 || board[i][j+1] == 2) {
							neighbours++;
						}

					}
					if (i > 0 && j > 0) {

						if(board[i-1][j-1] == 1 || board[i-1][j-1] == 2) {
							neighbours++;
						}

					}
					if (i < GRID -1 && j > 0) {

						if (board[i+1][j-1] == 1 || board[i+1][j-1] == 2) {
							neighbours++;
						}

					}
					if (i > 0 && j < GRID -1) {

						if (board[i-1][j+1] == 1 || board[i-1][j+1] == 2) {
							neighbours++;
						}

					}
					if (i < GRID-1 && j < GRID - 1) {

						if (board[i+1][j+1]==1 || board[i+1][j+1] == 2) {
							neighbours++;
						}

					}


					//Any live cell with fewer than two live neighbours will die, as if by underpopulation.
					if (neighbours < 2) {
						board[i][j] = 2;
					}

					//Any live cell with more than three live neighbours will die, as if by overpopulation.
					if (neighbours > 3) {
						board[i][j] = 2;
					}

				}
			}

		}

	}



	public void birth() {

		for (int i = 0; i < GRID; i++) {

			for (int j = 0; j < GRID; j++) {

				int neighbours = 0;

				//check empty cells
				//counts the number of living cells adjacent to the current cell
				
				if (board[i][j] == 0 ) {
					if (i > 0) {

						if (board[i-1][j] == 1 || board[i-1][j] == 2) {
							neighbours++;
						}

					}
					if (i < GRID -1) {

						if (board[i+1][j] == 1 || board[i+1][j] == 2) {
							neighbours++;
						}

					}
					if (j > 0) {

						if (board[i][j-1] == 1 || board[i][j-1] == 2) {							
							neighbours++;
						}

					}
					if (j < GRID - 1) {

						if (board[i][j+1] == 1 || board[i][j+1] == 2) {
							neighbours++;
						}

					}
					if (i > 0 && j > 0) {

						if(board[i-1][j-1] == 1 || board[i-1][j-1] == 2) {
							neighbours++;
						}

					}
					if (i < GRID -1 && j > 0) {

						if (board[i+1][j-1] == 1 || board[i+1][j-1] == 2) {
							neighbours++;
						}

					}
					if (i > 0 && j < GRID -1) {

						if (board[i-1][j+1] == 1 || board[i-1][j+1] == 2) {
							neighbours++;
						}

					}
					if (i < GRID-1 && j < GRID - 1) {

						if (board[i+1][j+1]==1 || board[i+1][j+1]==2) {
							neighbours++;
						}

					}

					//Any dead cell with exactly three live neighbours will become a live cell, as if by reproduction.
					if (neighbours == 3) {
						board[i][j] = 3;
					}

				}
			}

		}

	}




	public void updateBoard() {

		/*	
		 * After births and deaths have been determined, update the board by changing 2s (will die) and 3s 
		 * (will be born) to 0s (dead) and 1s (alive)
		 * 
		 * The placeholder values of 2 and 3 exist so that the birth and death calculations do not interfere with
		 * each other
		*/
		
		for (int i = 0; i < GRID; i++) {

			for (int j = 0; j < GRID; j++) {

				if (board[i][j] == 2) {

					board[i][j] = 0;

				}

				if (board[i][j] == 3) {

					board[i][j] = 1;

				}

			}

		}

	}



	@SuppressWarnings("serial")
	class DrawingPanel extends JPanel {

		DrawingPanel() {

			this.setBackground(Color.black);
			this.setPreferredSize(new Dimension(WINSIZE,WINSIZE-200));
			this.addMouseListener(new ClickAL());

		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);

			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON); //antialiasing


			//draw grid lines
			g2.setColor(Color.gray);
			for (int i = 0; i < GRID; i++) {
				g2.drawLine(gridSize*i, 0, gridSize*i, WINSIZE-200);
				g2.drawLine(0, gridSize*i, WINSIZE, gridSize*i);
			}

			//draw live cells
			g2.setColor(Color.white);
			for (int i = 0; i < GRID; i++) {

				for (int j = 0; j < GRID; j++) {

					//cells that are currently alive
					if (board[i][j] == 1 || board[i][j] == 2) {

						g2.fillOval(i*gridSize+1, j*gridSize+1, gridSize-1, gridSize-1);

					}

				}

			}




		}

	}

	class StartButtonAL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			//if game is running when button is clicked
			if (isRunning) {
				isRunning = false; //pause game
				startBtn.setText("Resume");
			}
			
			//if game is paused when button is clicked
			else {
				isRunning = true; //resume game
				startBtn.setText("Pause");
			}

		}

	}

	class ResetButtonAL implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			//reset the board and set generation back to zero
			
			isRunning = false; //pause game

			for (int i = 0; i < GRID; i++) {

				for (int j = 0; j < GRID; j++) {

					board[i][j] = 0;

				}

			}

			generation = 0;

			startBtn.setText("Start");
			genLbl.setText(" GEN " + generation);

		}


	}


	class ClickAL implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			//calculate the array position of the click
			int x = e.getX() / gridSize;
			int y = e.getY() / gridSize;

			//only let user click cells if the game is paused
			if (!isRunning) {
				//if a cell is empty, fill it with a live cell
				if (board[x][y] == 0) {		
					board[x][y] = 1;	
				}
				//if a cell is filled, kill that cell
				else {	
					board[x][y] = 0;	
				}
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}
		@Override
		public void mouseReleased(MouseEvent e) {
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}
		@Override
		public void mouseExited(MouseEvent e) {
		}

	}
	

}
