import java.awt.Color;
import java.awt.Dimension; 
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.JComponent;


/**
 * Called when a game is created
 * @author ochiers soulierc 
 * 
 * */
public class Game extends JPanel implements MouseMotionListener
{
	 /**
     * Width size of the container
     */
    private static final int WIDTH_OF_GAME_PANEL = 500;

    /**
     * Height size of the container
     */
    private static final int HEIGHT_OF_GAME_PANEL = 440;

    // TODO (fixed) comment is confusing
    // default number of bricks?
    /**
     * This number is the number of bricks in the level, But it's a temp
     * constant because this number can change depending on the level
     */
    public static final int DEFAULT_NUMBER_OF_BRICKS = 10;

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
    private static final int NO_SIDE_COLLISION = 1;
    
    /**
     * Number that specify a collision between the ball and the left or the right side of a brick
     */
    private static final int COLLISION_LEFT_RIGHT_SIDE = 1;
    
    /**
     * Number that specify a collision between the ball and the top or the bottom side of a brick
     */
    private static final int COLLISION_TOP_BOTTOM_SIDE = 2;
        
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
        int yPositionBricks = 44;
        this.stopGame = false;
        this.currentNumberOfBalls = Game.MAXIMAL_LIVES;

        this.theBall = new Ball(Game.PADDLE_INITIAL_POSITION , Game.PADDLE_INITIAL_POSITION);

        this.thePaddle = new paddle(Game.PADDLE_INITIAL_POSITION, paddle.PADDLE_SIZE);

        this.bricks = new Brick[Game.DEFAULT_NUMBER_OF_BRICKS];
        for (int i = 0; i < Game.DEFAULT_NUMBER_OF_BRICKS; i++)
        {
            this.bricks[i] = new Brick(i * Brick.DEFAULT_WIDTH, yPositionBricks);
        }
        this.rand = new Random();
        currentNumberOfBricks = 10;

    }

    // TODO (fixed) simplify this method by moving some inner code in outside
    // private methods
    /**
     * method who move the ball and take care of collisions
     */
    public void go()
    {
        int nbMaxBallMoves = 1000;
        boolean stop = false;
        boolean thereWasAcollision = false;
        int collisionSide = 0;

        while(!stopGame)
        {
            if (manageCollisionWithGamePanelSides())
            {
                //this.theBall.setB(-1 * this.theBall.getTrajectory().getCoefB());
            }
            else
            {
                manageCollisionWithPaddle();
            }

            this.theBall.setPositionsFromTopLeftCorner(this.theBall.getTopLeftCornerPosition().getPosX() + this.theBall.getTrajectory().getCoefB(),
            this.theBall.getTopLeftCornerPosition().getPosY() + this.theBall.getTrajectory().getCoefA());

            if (this.currentNumberOfBalls != 0 && this.currentNumberOfBricks == 0)
            {
               onVictory();
            }
            
            if(!this.stopGame)
            {
                int j;
                thereWasAcollision = false;
                for (j = 0; j < 10; j++)
                {
                    if (this.bricks[j].getState() != Brick.DESTROYED_STATE)
                    {
                        collisionSide = isBallInCollisionWithBrick(j);
                        onCollisionWithBrick(thereWasAcollision,j,collisionSide);
                        thereWasAcollision = true;
                    }
                }                
                System.out.println(this.theBall.toString());
            }
            else
            {
                
            }
            
            try {
				Thread.currentThread().sleep(10);
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
        if (collisionSide != 0)
        {
        	//System.out.println(this.bricks[indexOfBrick].toString());
            this.bricks[indexOfBrick].setState(Brick.DESTROYED_STATE);
            this.currentNumberOfBricks--;
            if (!thereWasAcollision)
            {
                switch (collisionSide)
                {
                case 1:
                    this.theBall.getTrajectory().reverseCoefB();
                    break;
                case 2:
                    this.theBall.getTrajectory().reverseCoefA();
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
        System.out.println("perdu");
        this.stopGame = true;
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
    private int isBallInCollisionWithBrick(int i)
    {
        int res;
        Position posBaLT = this.theBall.getTopLeftCornerPosition();
        Position posBaLB = this.theBall.getBottomLeftCornerPosition();
        Position posBaRT = this.theBall.getTopRightCornerPosition();
        Position posBaRB = this.theBall.getBottomRightCornerPosition();

        if       (!this.bricks[i].isPositionInRect(posBaLT) && !this.bricks[i].isPositionInRect(posBaLB)
                && (this.bricks[i].isPositionInRect(posBaRT) || this.bricks[i].isPositionInRect(posBaRB)))
        {
            res = Game.COLLISION_LEFT_RIGHT_SIDE;
        }
        else if ((!this.bricks[i].isPositionInRect(posBaRT) && !this.bricks[i].isPositionInRect(posBaRB))
                && (this.bricks[i].isPositionInRect(posBaLT) || this.bricks[i].isPositionInRect(posBaLB)))
        {
            res = Game.COLLISION_LEFT_RIGHT_SIDE;
        }
        else if ((!this.bricks[i].isPositionInRect(posBaLT) && !this.bricks[i].isPositionInRect(posBaRT))
                && (this.bricks[i].isPositionInRect(posBaLB) || this.bricks[i].isPositionInRect(posBaRB)))
        {
            res = Game.COLLISION_TOP_BOTTOM_SIDE;
        }
        else if ((!this.bricks[i].isPositionInRect(posBaLB) && !this.bricks[i].isPositionInRect(posBaRB))
                && (this.bricks[i].isPositionInRect(posBaLT) || this.bricks[i].isPositionInRect(posBaRT)))
        {
            res = Game.COLLISION_TOP_BOTTOM_SIDE;
        }
        else
        {
            res = Game.NO_SIDE_COLLISION;
        }
        
        return res;
    }
    
    /**
     * Method who manages collisions between ball and paddle, if the ball don't hit the paddle the player loses
     */
    private void manageCollisionWithPaddle()
    {
        if (this.theBall.getTopLeftCornerPosition().getPosY() + Ball.BALL_SIZE >= paddle.INITIAL_Y_POSITION)
        {
            if (isFloatBetween(this.theBall.getTopLeftCornerPosition().getPosX(), this.thePaddle.getPosition()
                    .getPosX(), this.thePaddle.getPosition().getPosX() + this.thePaddle.getSize1())
                    || isFloatBetween(this.theBall.getTopLeftCornerPosition().getPosX() + Ball.BALL_SIZE,
                            this.thePaddle.getPosition().getPosX(), this.thePaddle.getPosition().getPosX()
                                    + this.thePaddle.getSize1()))
            {
                this.theBall.setTrajectory(new Trajectory(-1 * this.rand.nextFloat(),this.theBall.getTrajectory().getCoefB()));
            }
            else
            {
                onLose();
            }
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
    	g.fillRect(9, 395, this.WIDTH_OF_GAME_PANEL, 100);
    	for(int i=0; i <10;i++)
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
		if(arg0.getX()+paddle.PADDLE_SIZE < this.WIDTH_OF_GAME_PANEL)
		this.thePaddle.setPosition(new Position(arg0.getX(), this.thePaddle.getPosition().getPosY()));
		
	}
}
