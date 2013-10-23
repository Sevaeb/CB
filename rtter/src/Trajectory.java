

/**
 * Define a trajectory of a moving object;
 * @author soulierc
 *
 */
public class Trajectory
{
    /**
     * Coeficient on vertical axis
     */
    private float coefA;
    
    /**
     * Coeficient on horizontal axis
     */
    private float coefB;
    
    /**
     * 
     * @param a
     * @param b
     */
    Trajectory(float a, float b)
    {
        this.coefA = a;
        this.coefB = b;
    }
    
    public void reverseCoefA()
    {
        this.coefA = -this.coefA;
    }
    public void reverseCoefB()
    {
        this.coefB = -this.coefB;
    }
        
    
    /**
     * Return the A coeficient
     * @return a coeficicent
     */
    public float getCoefA()
    {
        return coefA;
    }

    /**
     * Return the B coeficient
     * @return b coeficient
     */
    public float getCoefB()
    {
        return coefB;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString()
    {
        return "Coef A : "+this.coefA+", Coef B: "+this.coefB;
    
    }
}
