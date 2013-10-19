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
    // TODO (FIXED) declare field visibility
    /**
     * Number of life
     */
    public int nbBalls; 

    // TODO (fixed) rename field 
   /**
    * The ball that shoots the bricks
    */
    private Ball theBall;
    
    // TODO (fixed) declare field visibility
    // TODO (FIXED) write a comment that really describe what this field is
    /**
     * This tab contains all bricks of the game
     * That save their position, their state
     *
     */
    //TODO next time : create Get and Set methods
    public Brick tabBrick[];
    
    // TODO (FIXED) rename field (game has no width)
    /**
     * Width size of the container where we play
     */
    private final int WIDTH_OF_GAME_PANEL = 500;

    // TODO (FIXED) rename field (game has no height)
    /**
     * Height size of the container where we play
     */
    private final int HEIGHT_OF_GAME_PANEL = 440;
    
    /**
     * This number is the maximal number of bricks in the level,
     * But it's a temp constant because this number can change depending 
     * on the level
     */
    public final int MAX_NUMBER_OF_BRICKS = 10;
    
    /**
     * this is a test to create a line of bricks with y position who equals to 44
     * 
     */
    private final int Y_POSITION_BRICKS = 44;
    /**
     * This is the procedure where the game find his start
     * It's the initialization of the game
     */
    // TODO (FIXED) write a comment
    
    private Random r;

	private paddle thePaddle;
	
	private int currentNbBricks;
    
    public Game()
    {
        super();
       
        this.addMouseMotionListener(this);
        r = new Random();
        this.currentNbBricks = this.MAX_NUMBER_OF_BRICKS;
        this.nbBalls = 3;
        this.thePaddle = new paddle(190F,paddle.PADDLE_SIZE);
        this.theBall = new Ball(this.thePaddle.getpaddlePosition().getPosX(),this.thePaddle.getpaddlePosition().getPosY()-100);
        this.tabBrick = new Brick[this.MAX_NUMBER_OF_BRICKS];
        int count =0;
        int espace=0; //en pixel
        for(int i = 0; i<2; i++)
        {
        	for(int j = 0; j<this.MAX_NUMBER_OF_BRICKS/2; j++)
            {
            this.tabBrick[count] = new Brick(((j+1)*Brick.WIDTH_BRICK)+espace,(i+1)*this.Y_POSITION_BRICKS);
            espace+=5;
            count++;
            }
        }
        
        this.tabBrick[0] =new Brick(50,50);
        this.tabBrick[1] =new Brick(71,50);
        this.tabBrick[2] =new Brick(371,50);
        this.theBall.setPositionBall(67, 15);
        this.theBall.setA(1);this.theBall.setB(0);
        }
    
    private float choseRandomFloatBetween(float min, float max)
    {
    	float valeur = 0;
    	while (valeur <= min ||  valeur >= max)
        {
        	valeur = r.nextFloat();
        }
    	
    	return valeur;
    }
    
    /**
     * method who move the ball and take care of the game's outlines
     */
    public void go()
    {
    	float valeur = r.nextFloat();
    	boolean stop = false;
    	boolean thereWasAcollision = false;
    	int collisionSide = 0;
        while(!stop){
        	/*
             * It is the test to know if the ball arrive at the outline 
             */
        	if(!isIntBetween(this.theBall.getPositionLeftTopCorner().getPosX(),10,this.WIDTH_OF_GAME_PANEL) || !isIntBetween(this.theBall.getPositionLeftTopCorner().getPosX() + Ball.BALL_SIZE,0,this.WIDTH_OF_GAME_PANEL))
            {
                this.theBall.setB(-1*this.theBall.getB());
            }
            /*
             * Si l'ordonnée de la balle est supérieur au bord inférieur du jeu ou si  l'ordonnée de la balle est inférieur ou égale au bord supérieur du jeu
             * alors on change l'angle de la trajectoire
             */
            else if (this.theBall.getPositionLeftTopCorner().getPosY() <= 10 )
            {
                this.theBall.setA(-1*this.theBall.getA());
            }
            else if(this.theBall.getPositionLeftTopCorner().getPosY() + Ball.BALL_SIZE >= paddle.initialYPosition)
            {
                if (isIntBetween(this.theBall.getPositionLeftTopCorner().getPosX(),thePaddle.getpaddlePosition().getPosX(),thePaddle.getpaddlePosition().getPosX()+thePaddle.getSizePaddle())||isIntBetween(this.theBall.getPositionLeftTopCorner().getPosX()+theBall.BALL_SIZE,thePaddle.getpaddlePosition().getPosX(),thePaddle.getpaddlePosition().getPosX()+thePaddle.getSizePaddle()))
                {
                        this.theBall.setA(-1*choseRandomFloatBetween((float) 0.3,1F));
                }
                else
                {
                	if(this.nbBalls ==0)
                	{
	                    stop = true;
	            		System.out.println("PERDU !");
                	}
                	else
                	{
                		this.nbBalls--;
                		this.theBall = new Ball(this.thePaddle.getpaddlePosition().getPosX()+theBall.BALL_SIZE,this.thePaddle.getpaddlePosition().getPosY()-100);
                		this.theBall.setA(-1);
                		this.theBall.setB(1);
                		System.out.println("Il vous reste " + this.nbBalls + " vies.");
                	}}}
        	
            this.theBall.setPositionBall(this.theBall.getPositionLeftTopCorner().getPosX() + this.theBall.getB(),this.theBall.getPositionLeftTopCorner().getPosY() + this.theBall.getA() );
            if(this.nbBalls !=0 && this.currentNbBricks ==0)
        	{
                stop = true;
        		System.out.println("GAGNE !");
        	}
            int i;
            thereWasAcollision = false;
            for(i=0; i <10;i++)
        	{
            	if(this.tabBrick[i].getState() != Brick.STATE_DESTROYED)
            	{
	            	collisionSide = isBallInColisionToBrick(i);
	            	if(collisionSide != 0)
	        		{
		        		this.tabBrick[i].setState(Brick.STATE_DESTROYED);
		        		this.currentNbBricks--;
		        		if(!thereWasAcollision)
		        		{
		        		switch (collisionSide)
		            		{
		            		case 1: this.theBall.setB(-1*this.theBall.getB()); break;
		            		case 2: this.theBall.setA(-1*this.theBall.getA()); break;
		            		}
		        		}
		        		thereWasAcollision = true;
	        		}}}
            try {
                Thread.sleep(10);
              	} catch (InterruptedException e) {
                e.printStackTrace();
              	}
        this.repaint(); // on réactualise l'écran
        }
    }
    private int isBallInColisionToBrick(int i)
    {
    	int res;
    	Position posBaLT = this.theBall.getPositionLeftTopCorner();
    	Position posBaLB = this.theBall.getPositionLeftBottomCorner();
    	Position posBaRT = this.theBall.getPositionRightTopCorner();
    	Position posBaRB = this.theBall.getPositionRightBottomCorner();
    	
    	Position posBr = this.tabBrick[i].getBrickPosition();
    	
    	if((!isPositionInRect(posBaLT,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK) && !isPositionInRect(posBaLB,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK)) && (isPositionInRect(posBaRT,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK) || isPositionInRect(posBaRB,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK)) )
    	{
    		res = 1;
    	}
    	else if((!isPositionInRect(posBaRT,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK) && !isPositionInRect(posBaRB,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK)) && (isPositionInRect(posBaLT,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK) || isPositionInRect(posBaLB,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK)) )
    	{
    		res = 1;
    	}
    	else if((!isPositionInRect(posBaLT,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK) && !isPositionInRect(posBaRT,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK)) && (isPositionInRect(posBaLB,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK) || isPositionInRect(posBaRB,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK)) )
    	{
    		res = 2;
    	}
    	else if((!isPositionInRect(posBaLB,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK) && !isPositionInRect(posBaRB,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK)) && (isPositionInRect(posBaLT,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK) || isPositionInRect(posBaRT,posBr,Brick.WIDTH_BRICK,Brick.HEIGHT_BRICK)) )
    	{
    		res = 2;
    	}
    	else
    	{
    		res = 0;
    	}
    	
    	return res;
    }
    private boolean isIntBetween(float toCompare, float a, float b)
    {
        return (toCompare >= a && toCompare <= b)||(toCompare >= b && toCompare <= a);
    }
    
    private boolean isPositionInRect(Position posBall, Position posRect, int widthRect, int heightRect)
    {
    	boolean res = (isIntBetween(posBall.getPosX(),posRect.getPosX(),posRect.getPosX()+widthRect) && isIntBetween(posBall.getPosY(), posRect.getPosY(),posRect.getPosY()+heightRect));
    	//if(res)System.out.println(res);
    	return res;
    }
    public void paintComponent(Graphics g)
    {
    	g.setColor(Color.white);
    	g.fillRect(0, 0, this.getWidth(), this.getHeight()); //Clear screen
    	
    	g.setColor(new Color(0.2F, 0.3F, 0.1F, 0.2F));
    	g.fillRect(9, 395, this.WIDTH_OF_GAME_PANEL, 100);
    	for(int i=0; i <10;i++)
    	{
    		if(tabBrick[i].getState() != Brick.STATE_DESTROYED)
    		{
    			this.tabBrick[i].renderBrick(g);
    		}
    		else
    		{
    			this.tabBrick[i].renderBrickDestroyed(g);
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
		this.thePaddle.setPosition(arg0.getX());
		
	}
}
