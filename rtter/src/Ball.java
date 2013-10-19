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
    // TODO (FIXED) move this couple of int in a class named Position

    /**
     * It is the future position of the ball
     */
    private Position positionLeftTopCorner;
    
    private Position positionLeftBottomCorner;
    
    private Position positionRightTopCorner;
    
    private Position positionRightBottomCorner;
    
    
    /**
     * a is the director coefficient of the ball's trajectory (y=ax+b)
     */
    private float a;
    
    /**
     * b from (y=ax+b)
     */
    private float b;
    
    /**
     * Size of the container where we play
     */
    public final static int BALL_SIZE = 10;

   // private  E llipse2D.Double circle;
    
    /**
     * It create a ball at position (x,y)
     */
   
   
    public Ball(float x, float y)
    {
        super();
        this.positionLeftTopCorner = new Position(x,y);
        this.positionLeftBottomCorner = new Position(x,y+Ball.BALL_SIZE);
        this.positionRightTopCorner = new Position(x+Ball.BALL_SIZE,y);
        this.positionRightBottomCorner = new Position(x+Ball.BALL_SIZE,y+Ball.BALL_SIZE);
        this.a=1;
        this.b=1;
        //circle = new Ellipse2D.Float(x, y, 10, 10);
    }
    
    
    public Position getPositionLeftTopCorner()
    {
        return this.positionLeftTopCorner; 
    }
    public Position getPositionLeftBottomCorner() {
		return positionLeftBottomCorner;
	}

	public Position getPositionRightTopCorner() {
		return positionRightTopCorner;
	}

	public Position getPositionRightBottomCorner() {
		return positionRightBottomCorner;
	}

	public void setPositionBall(float x, float d)
    {
        
        this.positionLeftTopCorner = new Position(x,d);
        this.positionLeftBottomCorner = new Position(x,d+Ball.BALL_SIZE);
        this.positionRightTopCorner = new Position(x+Ball.BALL_SIZE,d);
        this.positionRightBottomCorner = new Position(x+Ball.BALL_SIZE,d+Ball.BALL_SIZE);
        
    }
    
    public float getA()
    {
        return a;
    }

    public void setA(float valeur)
    {
        this.a = valeur;
    }

    public float getB()
    {
        return b;
    }

    public void setB(float b)
    {
        this.b = b;
    }
    
public void renderBall(Graphics g){
        //Vous verrez cette phrase chaque fois que la méthode sera invoquée
    	g.setColor(Color.white);
        //On le dessine de sorte qu'il occupe toute la surface
        //g.fillRect( 0, 0,500, 440);
        //On redéfinit une couleur pour le rond
        g.setColor(Color.red);
        //On le dessine aux coordonnées souhaitées
        //circle = new Ellipse2D.Double(this.positionBall.getPosX(), this.positionBall.getPosY(), 10, 10);
        
        Graphics2D g2d = (Graphics2D)g; //le cast
        AffineTransform xform = new AffineTransform();
        xform.setToTranslation(this.positionLeftTopCorner.getPosX(), this.positionLeftTopCorner.getPosY());
		Image img = null;
			try {
				img = ImageIO.read(new File("balle.jpg"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		g2d.drawImage(img, xform, null);
    }   
    
}
