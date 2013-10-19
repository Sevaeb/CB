import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;



// TODO (fixed)(think about it) for plate, you mean "paddle" ?
/**
 * This class define what is a plate 
 * @author ochiers soulierc
 *
 */
public class paddle extends JPanel
{
 // TODO (fixed) move this couple of int in a class named Position
    
    /**
     * position on the paddle
     */
    private Position paddlePosition;

    public final static int initialYPosition = 396;
    
    /**
     *  Width size of the paddle (in pixels)
     */
    private int size;
    
    /**
     * Width size of the paddle (in pixels)
     */
    public final static int paddle_SIZE = 50;

    public final static int PADDLE_SIZE = 50;
    
    // TODO (fixed) write a comment
    /**
     * That create a paddle with that we can play, X position can be specified
     * @param f horizontal position of the paddle, vertical is constant
     * @param size  width of the paddle
     */
    public paddle(float f, int size)
    {
        super();
        this.paddlePosition = new Position(f,this.initialYPosition);
        this.size = size;
    }

    /**
     * That create a paddle with that we can play
     */
    // TODO (fixed) write a comment
    public paddle()
    {
        super();
        this.paddlePosition = new Position(0,this.initialYPosition);
        // TODO (fixed) declare hard-coded value as a constant
        this.size = this.paddle_SIZE; 
    }

    public Position getpaddlePosition()
    {
        return this.paddlePosition;
    }
    

    /**
     * @return give the size of paddle
     */
    // TODO (fixed) write a comment
    public int getSizePaddle()
    {
        return size;
    }
    
    /**
     * @return set the size of paddle
     */
    // TODO (fixed) write a comment
    public void setSize(int size)
    {
        this.size = size;
    }   
    
    public void setPosition(float a)
    {
    	this.paddlePosition = new Position(a, this.paddlePosition.getPosY());
    	
    }
    
    public void renderPaddle(Graphics g)
    {
    	g.setColor(new Color(0.2F, 0.0F, 0.9F, 0.8F));
    	g.fillRect((int)this.paddlePosition.getPosX(), (int)this.paddlePosition.getPosY(), PADDLE_SIZE, 10);
    
    }
    
}
