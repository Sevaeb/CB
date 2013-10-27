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
    /**
     * Ratio Width size of the paddle
     */
    private static final float RATIO_PADDLE_SIZE = 0.2F;
    
    /**
     * Ratio of the vertical position of paddle 
     */
    private static final float RATIO_VERTICAL_POSITION = 0.85F;
    
    /**
     * Width size of the paddle (in pixels)
     */
    public static int DEFAULT_PADDLE_SIZE = (int)(BrickBreaker.getBrickBreakerWidthSize() * RATIO_PADDLE_SIZE);
    
    /**
     * Height size of the paddle (in pixels)
     */
    public final static int DEFAULT_PADDLE_HEIGHT = 10;
    
    /**
     * The paddle is always at the same height
     */
    public static final int INITIAL_Y_POSITION = (int)(BrickBreaker.getBrickBreakerHeightSize() * RATIO_VERTICAL_POSITION);

    /**
     * Width size of the paddle (in pixels)
     */
    private int sizePaddle;

    private CollisionBox paddleBox;
    
    /**
     * Position of top left corner of the paddle, in fact only the x will change with the mouse's position
     */
    // TODO (explain) what is precisely this position?
    private Position position;

    /**
     * That create a paddle with that we can play, X position can be specified
     * 
     * @param posX
     *            horizontal position of the paddle, vertical is constant
     * @param size
     *            width of the paddle
     */
    public paddle(float posX, int size)
    {
        super();
        this.position = new Position(posX, (float) paddle.INITIAL_Y_POSITION);
        this.sizePaddle = size;
        this.paddleBox = new CollisionBox(this.position, paddle.DEFAULT_PADDLE_SIZE, paddle.DEFAULT_PADDLE_SIZE);
    }

    /**
     * That create a paddle with that we can play
     */
    public paddle()
    {
        super();
        this.position = new Position(0, paddle.INITIAL_Y_POSITION);
        this.sizePaddle = paddle.DEFAULT_PADDLE_SIZE;
        this.paddleBox = new CollisionBox(this.position, paddle.DEFAULT_PADDLE_SIZE, paddle.DEFAULT_PADDLE_SIZE);
    }

    
    
    /**
     * Return the paddle's position
     * 
     * @return position
     */
    public Position getPosition()
    {
        return this.position;
    }
    public void setPosition(Position pos)
    {
        this.position = pos;
        this.paddleBox = new CollisionBox(this.position, paddle.DEFAULT_PADDLE_SIZE, paddle.DEFAULT_PADDLE_SIZE);
    }
    /**
     * Return the paddle's size
     * 
     * @return give the size of paddle
     */
    public int getSizePaddle()
    {
        return this.sizePaddle;
    }

    /**
     * Set the paddle's size
     * 
     * @param size
     */
    public void setSizePaddle(int size)
    {
        this.sizePaddle = size;
    }
    
    public CollisionBox getPaddleBox() {
		return paddleBox;
	}

	/*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "{" + this.position.toString() + ", size : " + this.sizePaddle + "}";
    }
    
    public void renderPaddle(Graphics g)
    {
    	g.setColor(Color.BLUE);
    	g.fillRect((int)this.position.getPosX(), (int)this.position.getPosY(), this.sizePaddle, this.sizePaddle/5);
    
    }
}
