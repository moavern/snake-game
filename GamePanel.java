import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;



public class GamePanel extends JPanel implements ActionListener{

	
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/(UNIT_SIZE*UNIT_SIZE);
	static final int DELAY = 75; //higher the number, the slower it is
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int [GAME_UNITS];
	int bodyParts = 6;
	int boobsEaten;
	int appleX;
	int appleY;
	char direction = 'R'; //begin with the snake going right
	boolean running = false;
	Timer timer;
	Random random;
	
	GamePanel(){ //has all the sizes and background b
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		 
	}
	
	public void startGame() {//starts the game
		newApples();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
		
			
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	
	public void draw(Graphics g) {
		
		if(running) {
			/*for(int i=0;i<SCREEN_HEIGHT/UNIT_SIZE;i++) {

				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
				g.setColor(Color.white);
			}
			*/
			
			g.setColor(Color.pink);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
		
			for(int i=0; i<bodyParts; i++) {
				if(i==0) {
					g.setColor(Color.yellow); //setting the color for Mr.Snake
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45,180,0)); //numbers represent RGB values
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);	
				
				}
			}
			g.setColor(Color.red);
			g.setFont( new Font("Times New Roman", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: " + boobsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score" + boobsEaten))/2, g.getFont().getSize()); //WILL PUT THE GAME OVER IN THE CENTER OF THE SCREEN
		
		}
		else {
			gameOver(g);
			
				
			}
		}
	
	public void newApples() {
		
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

	}
	
	public void move() {
		
		for(int i=bodyParts; i>0; i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
			
		}
		switch(direction) {
		case 'U':
			y[0]= y[0]- UNIT_SIZE;
			break;
		case 'D':
			y[0]= y[0]+ UNIT_SIZE;
			break;
		case 'R':
			x[0]=x[0]+ UNIT_SIZE;
			break;
		case 'L':
			x[0]= x[0]- UNIT_SIZE;
			break;
		
		}
		
	}
	
	public void checkApple() {  //MAKES THE SNAKE EAT THE APPLE
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++; //increases body part by 1
			boobsEaten++;
			newApples();
			
		}
		
		
		
	}
	
	public void checkCollisions() {
		//this checks if head collides with body
		for(int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i]) && (y[0] == y[i])) {
				running = false; //this will trigger a game over via collision
				
			}
			
		}
		//check if head touches left border
		if(x[0] < 0) {
			running = false;
			
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
			
		}
		
			//check if head touches top border
		if(y[0] < 0) {
			running = false;	
		}
		
		//CHECK IF HEAD TOUCHES BOTTOM BORDER
		
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		
		
		if (!running) {
			timer.stop();
		}
		
		
		
	}
		
	public void gameOver(Graphics g) { 
		//gameover text
		g.setColor(Color.red);
		g.setFont( new Font("Times New Roman", Font.BOLD, 75));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics1.stringWidth("Game Over"))/2, SCREEN_HEIGHT / 2); //WILL PUT THE GAME OVER IN THE CENTER OF THE SCREEN
		
		//shows score at gameover screen
		g.setColor(Color.red);
		g.setFont( new Font("Times New Roman", Font.BOLD, 40));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Score: " + boobsEaten, (SCREEN_WIDTH - metrics.stringWidth("Score" + boobsEaten))/2, g.getFont().getSize()); //WILL PUT THE GAME OVER IN THE CENTER OF THE SCREEN
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollisions();
			
		}
		repaint();
	 
	}

	public class MyKeyAdapter extends KeyAdapter{ //this is an inner class
		@Override
		public void keyPressed(KeyEvent e) {
			
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:    //SETS IT TO UP KEY
				if(direction != 'R') {    //MAKES IT SO THAT SNAKE CAN'T RUN INTO ITSELF
					direction = 'L';
				}
					break;
					
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
					break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
					break;
					
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
					break;
					
				
			}
			
		}
	}
} 




/* need to add a play again function
it needs to be after the game over screen comes up and needs to prompt the user for user input
so i'm thinking we need to add two more keys (yes and no) and then have the user pick an option. 
Picking yes, will restart the game
Now either I add this to another method (public void playAgain perhaps?) or add it into the gameOver Method and have it loop the gameFrame over again

*/