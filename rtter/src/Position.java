

public class Position 
{
 
    /**
     * Position on horizontal axis
     */
    private float posX;
    
    /**
     * Position on vertical axis
     */
    private float posY;

    
    Position(float x, float d)
    {
        this.posX = x;
        this.posY = d;
    }

    
    /**
     * @return x position
     */
    public float getPosX()
    {
        return posX;
    }

    /**
     * @return y position
     */
    public float getPosY()
    {
        return posY;
    }
    
    
    
    
}
