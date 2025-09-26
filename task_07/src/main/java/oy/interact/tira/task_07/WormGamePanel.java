package oy.interact.tira.task_07;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JPanel;

import oy.interact.tira.factories.ListFactory;
import oy.interact.tira.util.LinkedListInterface;
import oy.interact.tira.util.NotYetImplementedException;

public class WormGamePanel extends JPanel implements KeyListener {

	public enum Direction {
		NORTH,
		EAST,
		SOUTH,
		WEST;

		public boolean isOpposite(final Direction to) {
			switch (this) {
				case NORTH:
					return to == SOUTH;
				case SOUTH:
					return to == NORTH;
				case EAST:
					return to == WEST;
				case WEST:
					return to == EAST;
			}
			return false;
		}
	}
	
	private static final Color SNAKE_COLOR = Color.GREEN;
	private static final Color FOOD_COLOR = Color.RED;	
	private static final int INITIAL_SPEED_EASY = 240;
	private int currentSpeed = INITIAL_SPEED_EASY;

	// The snake and food
	private LinkedListInterface<Point> snake;

	private Point food;
	private Direction currentDirection = Direction.NORTH;

	public WormGamePanel() {
		super();
		// STUDENT TODO: Go to initializeSnake and implement it following the instructions.
		snake = initializeSnake();
		placeFood();
		setFocusable(true);
		addKeyListener(this);
		setBackground(Color.BLACK);
		repaint();
	}

	// STUDENT TODO: initialize your linked list implementation with one Point
	// being the head of the snake, in the center of the game view.
	// Use GameViewConstants.GAME_WIDTH and GameViewConstants.GAME_HEIGHT to 
	// calculate the center coordinate.
	LinkedListInterface<Point> initializeSnake() {
		// 1. create the linkedlink implementation as the snake, using ListFactory.createPointLinkedList().
		// 2. add one Point to the linked list, point at the center of the game view
		//  - use constants of GameViewConstants to calculate the middle point in Points of the game view.
		// 3. add the point as the head of the snake
		// 4. return the snake
		return null;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Color oldColor = g.getColor();

		// Draw food		
		g.setColor(FOOD_COLOR);
		int xCoord = food.x * GameViewConstants.TILE_SIZE;
		int yCoord = food.y * GameViewConstants.TILE_SIZE;
		g.fillRect(xCoord, yCoord, GameViewConstants.TILE_SIZE, GameViewConstants.TILE_SIZE);

		// Draw Snake
		g.setColor(SNAKE_COLOR);

		/// STUDENT TODO: In the code below, get each snake segment (Point)
		/// from the linked list and draw the Point
		/// as a filled rectangle to the view.

		// STUDENT TODO: Get the size of the snake
		int snakeSize = 0;
		for (int pieceNumber = 0; pieceNumber < snakeSize; pieceNumber++) {
			// STUDENT TODO: Get the snake point using pieceNumber as index to linked list
			final Point snakePiece = new Point(5, 5); 
			// Drawing of a Point is ready below.
			xCoord = snakePiece.x * GameViewConstants.TILE_SIZE;
			yCoord = snakePiece.y * GameViewConstants.TILE_SIZE;
			g.fillRect(xCoord, yCoord, GameViewConstants.TILE_SIZE, GameViewConstants.TILE_SIZE);
		}		
		g.setColor(oldColor);
	}


	// STUDENT TODO: Finish this to make the game work.
	// Follow the comments in the code.
	// The method is called by the timer, periodically.
	private void tick() {
		// STUDENT TODO: Get the head of the snake (first element in linked list)
		final Point currentHead = null;
		if (currentHead == null) { return; }
		Point newHead = null;
		switch (currentDirection) {
			case NORTH:
				newHead = new Point(currentHead.x, currentHead.y - 1);
				break;
			case EAST:
				newHead = new Point(currentHead.x + 1, currentHead.y);
				break;
			case SOUTH:
				 newHead = new Point(currentHead.x, currentHead.y + 1);
				break;
			case WEST:
				newHead = new Point(currentHead.x - 1, currentHead.y);
				break;
			default:
				assert(newHead != null);
				break;
		}
		// TODO: Add the newHead as the new first element in the linked list
		// This moves the snake ahead in the game.
		

		// If head collides with food, snake eats it...
		if (currentHead.equals(food)) {
			// place a new food item on the game view
			placeFood();
		} else {
			// STUDENT TODO: snake didn't get anything to eat, so it does no
			// grow. Therefore, remove the tail (last element) so that the snake does
			// not grow.

		}
		repaint();	
		scheduleTimer();
	}


	// -----------------------------------------------
	// Code below does not need changes nor additions.
	// -----------------------------------------------

	void placeFood() {
		food = null;
		int foodX = ThreadLocalRandom.current().nextInt(GameViewConstants.GAME_WIDTH);
		int foodY = ThreadLocalRandom.current().nextInt(GameViewConstants.GAME_HEIGHT);
		food = new Point(foodX, foodY);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == KeyEvent.VK_SPACE) {
			if (timer == null) {
				System.out.println("Game started");
				scheduleTimer();
			} else {
				System.out.println("Game ended");
				stopTimer();
			}
			repaint();
		} else if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			currentDirection = Direction.NORTH;			
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S) {
			currentDirection = Direction.SOUTH;
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A) {
			currentDirection = Direction.WEST;
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D) {
			currentDirection = Direction.EAST;
		}
	}

	private Timer timer;

	private void scheduleTimer() {
		if (timer == null) {
			timer = new Timer();
		}
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				tick();
			}			
		}, currentSpeed);
	}

	private void stopTimer() {
		if (timer != null) {
			timer.cancel();
			timer = null;
		}
	}


	@Override
	public Dimension getMinimumSize() {
		return GameViewConstants.dimension;
	}

	@Override
	public Dimension getPreferredSize() {
		return GameViewConstants.dimension;
	}

	@Override
	public Dimension getMaximumSize() {
		return GameViewConstants.dimension;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Empty
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Empty
	}

}
