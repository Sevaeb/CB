import javax.swing.JFrame;

// TODO (fixed) write a "real" comment 
/**
 * Main class, the program start here and use the other classes 
 * @author ochiers
 * 
 */

public class BrickBreaker
{
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
    	fen.setSize(600, 540);
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
