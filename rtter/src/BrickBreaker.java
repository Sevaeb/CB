import javax.swing.JFrame;

// TODO (fixed) write a "real" comment 
/**
 * Main class, the program start here and use the other classes 
 * @author ochiers
 * 
 */

public class BrickBreaker
{
	
	private static int brickBreakerWidthSize = 800;
	private static int brickBreakerHeightSize = 640;
	
    
	public static int getBrickBreakerWidthSize() 
	{
		return brickBreakerWidthSize;
	}

	public static void setBrickBreakerWidthSize(int brickBreakerWithSize)
	{
		BrickBreaker.brickBreakerWidthSize = brickBreakerWithSize;
	}

	public static int getBrickBreakerHeightSize() 
	{
		return brickBreakerHeightSize;
	}

	public static void setBrickBreakerHeightSize(int brickBreakerHeightSize) 
	{
		BrickBreaker.brickBreakerHeightSize = brickBreakerHeightSize;
	}

	/**
     * The program start here
     * @param args
     */
    // TODO (fixed) write a comment
    public static void main(String[]args)
    {
        // tests 
    	JFrame fen = new JFrame();
    	fen.setTitle("Ma première fenêtre Java");
        //Définit sa taille : 400 pixels de large et 100 pixels de haut
    	fen.setSize(brickBreakerWidthSize, brickBreakerHeightSize);
        //Nous demandons maintenant à notre objet de se positionner au centre
    	fen.setLocationRelativeTo(null);
        //Termine le processus lorsqu'on clique sur la croix rouge
    	fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
        Game newGame = new Game();
        fen.setContentPane(newGame);
        fen.setVisible(true);
        newGame.go();
    }
}
