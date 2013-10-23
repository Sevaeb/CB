import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


// TODO (fix) write a "real" comment 
/**
 * 
 * Define what is a brick
 * That fix the position of a brick,
 * and this state
 * @author ochiers soulierc
 * 
 */
public class Brick extends JPanel
{
	/**
     * Width size of the brick (in pixels)
     */
    public final static int DEFAULT_WIDTH = 50;

    /**
     * Height size of the brick (in pixels)
     */
    public final static int DEFAULT_HEIGHT = 44;

    /**
     * That is the normal state of a brick
     */
    private static final int NORMAL_STATE = 3;

    /**
     * That is the second state of a brick That is when the brick was hit
     */
    public static final int TOUCHED_STATE = 2;

    /**
     * That is the third state of a brick That is when the brick was hit
     */
    public static final int DAMAGED_STATE = 1;

    /**
     * That is the laster state of a brick That is when the brick was hit for
     * the last time And then, it is delete of the screen
     */
    public static final int DESTROYED_STATE = 0;

    /**
     * Position of the top left corner (in pixels)
     */
    private Position topLeftCornerPosition;

    /**
     * State of the brick (normal, a bit broken, really broken, destroyed)
     */
    private int state;

    // TODO (fixed) finish writing comment
    /**
     * This is the initialization of a brick, a new position is created
     * according to posX and posY, the initial state is normal
     * 
     * @param posX
     * @param posY
     */
    // TODO (fix) replace x and y by a position
    public Brick(int posX, int posY)
    {
        super();
        this.topLeftCornerPosition = new Position(posX, posY);
        this.state = Brick.NORMAL_STATE;
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "{x : " + this.topLeftCornerPosition.getPosX() + ", y : " + this.topLeftCornerPosition.getPosY()
                + ", state :" + this.state + "}";
    }

    /**
     * Return the position of the brick
     * 
     * @return the position of the brick
     */
    public Position getBrickPosition()
    {
        return this.topLeftCornerPosition;
    }

    // TODO (fix) finish writing comment
    /**
     * Return the current state of the brick
     * 
     * @return Return the current state of the brick
     */
    public int getState()
    {
        return this.state;
    }

    // TODO (fix) finish writing comment
    /**
     * Set the current state of the brick
     * 
     * SET THE @param state
     */
    public void setState(int state)
    {
        this.state = state;
       
     }
    
    /**
     * Function who say if a position is in a rectangle who can be a brick It is
     * used to simplify the algorithm of collision
     * 
     * @param posBall
     *            Ball's position
     * @return true if the position is in, false otherwise
     */
    public boolean isPositionInRect(Position posBall)
    {
        return (isFloatBetween(posBall.getPosX(), this.topLeftCornerPosition.getPosX(), this.topLeftCornerPosition.getPosX() + this.DEFAULT_WIDTH) && isFloatBetween(
                posBall.getPosY(), this.topLeftCornerPosition.getPosY(), this.topLeftCornerPosition.getPosY() + this.DEFAULT_HEIGHT));
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
    private boolean isFloatBetween(float toCompare, float a, float b)
    {
        return (toCompare >= a && toCompare <= b) || (toCompare >= b && toCompare <= a);
    }
    
    public void renderBrick(Graphics g)
    {
    	g.setColor(Color.BLUE);
    	g.fillRect((int)this.topLeftCornerPosition.getPosX(), (int)this.topLeftCornerPosition.getPosY(), this.DEFAULT_WIDTH, this.DEFAULT_HEIGHT);
    
    }
    public void renderBrickDestroyed(Graphics g)
    {
    	g.setColor(Color.white);
    	g.fillRect((int)this.topLeftCornerPosition.getPosX(), (int)this.topLeftCornerPosition.getPosY(), this.DEFAULT_WIDTH, this.DEFAULT_HEIGHT);
    
    }
    

}
