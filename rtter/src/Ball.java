import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;
import java.awt.geom.AffineTransform;
import java.io.IOException;
import javax.imageio.ImageIO;



import java.io.File;
/**
 * This class defines the ball used to destroy bricks
 * 
 * @author ochiers soulierc
 **/
public class Ball extends JPanel
{

	/**
     * Ratio of the size of a ball
     */
    public final static float RATIO_BALL_SIZE =0.0125F;
    
    /**
     * Size of a ball
     */
    public static int BALL_SIZE = (int)(BrickBreaker.getBrickBreakerWidthSize() * RATIO_BALL_SIZE);

    // TODO (think about it) consider gathering the 4 positions in a single
    // object called BoundingBox
    /**
     * It is the top left corner position of the ball
     */
    private Position topLeftCornerPosition;
    /**
     * It is the Bottom left corner position of the ball
     */
    private Position bottomLeftCornerPosition;
    /**
     * It is the top right corner position of the ball
     */
    private Position topRightCornerPosition;
    /**
     * It is the bottom right corner position of the ball
     */
    private Position bottomRightCornerPosition;

    // TODO (think about it) consider gathering a and b in a single object
    /**
     * The ball's trajectory
     */
    private Trajectory trajectory;

    private CollisionBox ballBox;
    
    /**
     * Creates a new ball at new position (x,y) and set fields a and b to 1
     */
    public Ball(float x, float y)
    {
        super();
        this.topLeftCornerPosition = new Position(x, y);
        this.bottomLeftCornerPosition = new Position(x, y + Ball.BALL_SIZE);
        this.topRightCornerPosition = new Position(x + Ball.BALL_SIZE, y);
        this.bottomRightCornerPosition = new Position(x + Ball.BALL_SIZE, y + Ball.BALL_SIZE);
        this.trajectory = new Trajectory(1,1);
        this.ballBox = new CollisionBox(this.topLeftCornerPosition, BALL_SIZE, BALL_SIZE);
    }

    public CollisionBox getBallBox()
    {
        return ballBox;
    }
    /**
     * Return the ball's top left corner position
     * 
     * @return position
     */
    public Position getTopLeftCornerPosition()
    {
        return this.topLeftCornerPosition;
    }

    /**
     * Return the ball's bottom left corner position
     * 
     * @return position
     */
    public Position getBottomLeftCornerPosition()
    {
        return this.bottomLeftCornerPosition;
    }

    /**
     * Return the ball's top right corner position
     * 
     * @return position
     */
    public Position getTopRightCornerPosition()
    {
        return this.topRightCornerPosition;
    }

    /**
     * Return the ball's bottom right corner position
     * 
     * @return position
     */
    public Position getBottomRightCornerPosition()
    {
        return this.bottomRightCornerPosition;
    }

    /**
     * Set the four corner's position according to x,d and the bal size
     * 
     * @param x
     * @param y
     */
    // TODO (fixed) consider having a single position as parameter
    public void updatePositions(Position pos)
    {
        this.topLeftCornerPosition  = pos;
        this.bottomLeftCornerPosition = pos.translate(0, Ball.BALL_SIZE);
        this.topRightCornerPosition = pos.translate(Ball.BALL_SIZE,0);
        this.bottomRightCornerPosition = pos.translate(Ball.BALL_SIZE, Ball.BALL_SIZE);
        this.ballBox = new CollisionBox(this.topLeftCornerPosition,Ball.BALL_SIZE,Ball.BALL_SIZE);
    }

    /**
     * Return a
     * 
     * @return a
     */
    public Trajectory getTrajectory()
    {
        return this.trajectory;
    }

    /**
     * Set a field
     * 
     * @param a
     */
    public void setTrajectory(Trajectory a)
    {
        this.trajectory = a;
    }


    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "{" + this.topLeftCornerPosition.toString() + ", " + this.trajectory.toString() + "}";
    }
    
public void renderBall(Graphics g){

	
        g.setColor(Color.red);
        
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform xform = new AffineTransform();
        xform.setToTranslation(this.topLeftCornerPosition.getPosX(), this.topLeftCornerPosition.getPosY());
		Image img = null;
			try {
				img = ImageIO.read(new File("balle.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			img = img.getScaledInstance(BALL_SIZE, BALL_SIZE, (int) RATIO_BALL_SIZE);
		g2d.drawImage(img, xform, null);
    }   
    
}
