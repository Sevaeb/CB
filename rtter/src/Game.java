import java.awt.Color;
import java.awt.Dimension; 
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.geom.Rectangle2D;

/**
 * Called when a game is created
 * @author ochiers soulierc 
 * 
 * */
public class Game extends JPanel implements MouseMotionListener
{
	
	/**
     * Ratio Width size of the container
     */
    private static final float RATIO_WIDTH_OF_GAME_PANEL = 0.8F;

    /**
     * Ratio Height size of the container
     */
    private static final float RATIO_HEIGHT_OF_GAME_PANEL = 0.75F;
	
	 /**
     * Width size of the container
     */
    private static int WIDTH_OF_GAME_PANEL = (int) (BrickBreaker.getBrickBreakerWidthSize() * RATIO_WIDTH_OF_GAME_PANEL);

    /**
     * Height size of the container
     */
    private static int HEIGHT_OF_GAME_PANEL = (int) (BrickBreaker.getBrickBreakerHeightSize() * RATIO_HEIGHT_OF_GAME_PANEL);

    // TODO (fixed) comment is confusing
    // default number of bricks?
    /**
     * This number is the number of bricks in the level, But it's a temp
     * constant because this number can change depending on the level
     */
    public static final int DEFAULT_NUMBER_OF_BRICKS = 5;

    /**
     * Maximal number of player's lives
     */
    private static final int MAXIMAL_LIVES = 3;

    /**
     * Paddle initial position
     */
    private static final float PADDLE_INITIAL_POSITION = 250.0F;

    /**
     * Number that specify that there is no collision between ball and brick
     */
    private static final int NO_SIDE_COLLISION = 0;
    
    /**
     * Number that specify a collision between the ball and the left or the right side of a brick
     */
    private static final int COLLISION_LEFT_RIGHT_SIDE = 1;
    
    /**
     * Number that specify a collision between the ball and the top or the bottom side of a brick
     */
    private static final int COLLISION_TOP_BOTTOM_SIDE = 2;
    /**
     * Number that specify a collision between the ball and a corner of a brick
     */
    private static final int COLLISION_CORNER = 3;
     /**
     * Number of lives
     */
    private int currentNumberOfBalls;

    /**
     * The ball that shoots the bricks
     */
    private Ball theBall;

    /**
     * The paddle where the ball bounces
     */
    private paddle thePaddle;

    /**
     * This tab contains all bricks of the game That save their position, their
     * state
     * 
     */
    private Brick[] bricks;

    /**
     * This variable is a random number to give another direction to the ball
     */
    private Random rand;

    /**
     * Number of brick currently not in state destroyed
     */
    private int currentNumberOfBricks;

    /**
     * Used to stop the game
     */
    private boolean stopGame;
    
    /**
     * This is the procedure where the game find its start It's the
     * initialization of the game : a new paddle, a new ball and a new tab of
     * bricks is created, the number of lifes is 3
     * 
     */
    public Game()
    {
        super();
   	 	addMouseMotionListener(this);
        int yPositionBricks = 44;
        this.stopGame = false;
        this.currentNumberOfBalls = Game.MAXIMAL_LIVES;

        this.theBall = new Ball(Game.PADDLE_INITIAL_POSITION , Game.PADDLE_INITIAL_POSITION);

        this.thePaddle = new paddle(Game.PADDLE_INITIAL_POSITION, paddle.DEFAULT_PADDLE_SIZE);

        this.bricks = new Brick[Game.DEFAULT_NUMBER_OF_BRICKS];
        for (int i = 0; i < Game.DEFAULT_NUMBER_OF_BRICKS; i++)
        {
            this.bricks[i] = new Brick(i * Brick.DEFAULT_WIDTH, yPositionBricks);
        }
        
        this.bricks[0] = new Brick((int)Game.PADDLE_INITIAL_POSITION + 2*Ball.BALL_SIZE , (int)Game.PADDLE_INITIAL_POSITION + 2*Ball.BALL_SIZE);
        
        this.rand = new Random();
        currentNumberOfBricks = DEFAULT_NUMBER_OF_BRICKS;

    }

    // TODO (fixed) simplify this method by moving some inner code in outside
    // private methods
    /**
     * method who move the ball and take care of collisions
     */
    public void go()
    {
        boolean thereWasAcollision = false;
        int collisionSide = 0;

        while(!stopGame)
        {
        	
            if (!manageCollisionWithGamePanelSides())
            {
                manageCollisionWithPaddle();
            }

            this.theBall.updatePositions(this.theBall.getTopLeftCornerPosition().translate(this.theBall.getTrajectory().getCoefB(),
            this.theBall.getTrajectory().getCoefA()));

            if (this.currentNumberOfBalls != 0 && this.currentNumberOfBricks == 0)
            {
               onVictory();
            }
            
            if(!this.stopGame)
            {
                int j;
                thereWasAcollision = false;
                for (j = 0; j < DEFAULT_NUMBER_OF_BRICKS; j++)
                {
                    if (this.bricks[j].getState() != Brick.DESTROYED_STATE)
                    {
                    	collisionSide = isBallInCollisionWithBrick(this.bricks[j]);
                        onCollisionWithBrick(thereWasAcollision,j,collisionSide);
                        
                        if(collisionSide != NO_SIDE_COLLISION)
                        {
                        	thereWasAcollision = true;
                        }
                    }
                }                
            }
            this.repaint();
            try {
            	Thread.sleep(6);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        
    }

    /**
     * Called when the ball collide a brick
     * @param thereWasAcollision true if the ball collide at least two bricks in the same time
     * @param indexOfBrick index of the brick who is collided
     * @param collisionSide side collided
     */
    private void onCollisionWithBrick(boolean thereWasAcollision, int indexOfBrick, int collisionSide)
    {
        if (collisionSide != NO_SIDE_COLLISION)
        {
            this.bricks[indexOfBrick].setState(Brick.DESTROYED_STATE);
            this.currentNumberOfBricks--;
            System.out.println(this.currentNumberOfBricks);
            if (!thereWasAcollision)
            {
                switch (collisionSide)
                {
                case COLLISION_LEFT_RIGHT_SIDE:
                    this.theBall.getTrajectory().reverseCoefB();
                    break;
                case COLLISION_TOP_BOTTOM_SIDE:
                    this.theBall.getTrajectory().reverseCoefA();
                    break;
                case COLLISION_CORNER:
                    this.theBall.getTrajectory().reverseCoefA();
                    this.theBall.getTrajectory().reverseCoefB();
                    break;
                }
            }
            
        }
    }
    
    /**
     * Called when the player win the game
     */
    private void onVictory()
    {
        System.out.println("GAGNE !");
        this.stopGame = true;
    }
    
    /**
     * Called when the player lose the game
     */
    private void onLose()
    {
    	if(this.currentNumberOfBalls == 0)
    	{
	        System.out.println("perdu");
	        this.stopGame = true;
    	}
        else
        {
        	this.currentNumberOfBalls--;
        	this.theBall = new Ball(Game.PADDLE_INITIAL_POSITION , Game.PADDLE_INITIAL_POSITION);
        }
    }
    
    // TODO (fixed) fix comment, return type is not a boolean
    // TODO (fixed) declare constants for returned values
    /**
     * Function who say if the ball is in collision with the brick, in the tab
     * of bricks to the index i
     * 
     * @param i
     *            Index of the brick to compare in the game tab brick
     * @return 
     *            0 if no collision, 1 in case of collision with left or right sides, 2 in case of collision with top or bottom sides
     */
    private int isBallInCollisionWithBrick(Brick b)
    {
        int res = NO_SIDE_COLLISION;
        Rectangle2D.Float dest = new Rectangle2D.Float();
        dest = b.getBrickBox().getRectangleFromIntersectionWithOtherCollisionBox(this.theBall.getBallBox());
        if (dest.getWidth() < 0 && dest.getHeight() < 0)
        {
            return NO_SIDE_COLLISION;
        }
        if (dest.getWidth() == 1 && dest.getHeight() == 1)
            {
        	res = COLLISION_CORNER;
            }
        else if (dest.getWidth() == 1 && dest.getHeight() > 1)
            {
        	res = COLLISION_LEFT_RIGHT_SIDE;
            }
        else if (dest.getWidth() > 1 && dest.getHeight() == 1)
            {
        	res = COLLISION_TOP_BOTTOM_SIDE;
            }
        
        
        return res;
    }
    
    /**
     * Method who manages collisions between ball and paddle, if the ball don't hit the paddle the player loses
     */
    private void manageCollisionWithPaddle()
    {
    	if(this.theBall.getBottomLeftCornerPosition().getPosY() <= thePaddle.getPosition().getPosY())
    	{
	    	Rectangle2D.Float dest = this.theBall.getBallBox().getRectangleFromIntersectionWithOtherCollisionBox(this.thePaddle.getPaddleBox());
	    	if(dest.getWidth() >= 0 && dest.getHeight() >= 0)
	    	{
	    		this.theBall.setTrajectory(new Trajectory(-1 * this.rand.nextFloat(),this.theBall.getTrajectory().getCoefB()));
	    	}
		}
	    else
		{
			onLose();
		}
    }
    
    /**
     * Method who manage collsion between the ball and the sides of the game panel, change the ball's direction when collided and return true
     * @return true if the ball hit a side
     */
    private boolean manageCollisionWithGamePanelSides()
    {
        boolean thereIsCollision = false;
        if (!isFloatBetween(this.theBall.getTopLeftCornerPosition().getPosX(), 0, Game.WIDTH_OF_GAME_PANEL)
                || !isFloatBetween(this.theBall.getTopLeftCornerPosition().getPosX() + Ball.BALL_SIZE, 0,
                        Game.WIDTH_OF_GAME_PANEL))
        {
            this.theBall.getTrajectory().reverseCoefB();
            thereIsCollision = true;
        }
        else if (this.theBall.getTopLeftCornerPosition().getPosY() <= 0)
        {
            this.theBall.getTrajectory().reverseCoefA();
            thereIsCollision = true;
        }    
        return thereIsCollision;
    }

    /**
     * Function who say if the number toCompare is between the number a and the
     * number b
     * 
     * @param toCompare
     *            Number to compare
     * @param a
     *            Bound of comparison
     * @param b
     *            Bound of comparison
     * @return true if the number is in, false otherwise
     */
    // TODO (fixed) rename this method (i see no int)
    private boolean isFloatBetween(float toCompare, float a, float b)
    {
        return (toCompare >= a && toCompare <= b) || (toCompare >= b && toCompare <= a);
    }


    public void paintComponent(Graphics g)
    {
    	g.setColor(Color.white);
    	g.fillRect(0, 0, this.getWidth(), this.getHeight()); //Clear screen
    	
    	g.setColor(new Color(0.2F, 0.3F, 0.1F, 0.2F));
    	g.fillRect(9, 395, Game.WIDTH_OF_GAME_PANEL, 100);
    	for(int i=0; i <DEFAULT_NUMBER_OF_BRICKS;i++)
    	{
    		if(bricks[i].getState() != Brick.DESTROYED_STATE)
    		{
    			this.bricks[i].renderBrick(g);
    		}
    		else
    		{
    			this.bricks[i].renderBrickDestroyed(g);
    		}
    	}

    	theBall.renderBall(g);
    	thePaddle.renderPaddle(g);
    }

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		if(arg0.getX() + paddle.DEFAULT_PADDLE_SIZE < Game.WIDTH_OF_GAME_PANEL)
		{
		this.thePaddle.setPosition(new Position(arg0.getX(), this.thePaddle.getPosition().getPosY()));
		}
	}
}
