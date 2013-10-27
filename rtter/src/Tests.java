import java.awt.geom.Rectangle2D;
public class Tests {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Rectangle2D.Float dest = new Rectangle2D.Float();
		Ball ball = new Ball(Brick.DEFAULT_WIDTH,Brick.DEFAULT_HEIGHT);
		Brick brick = new Brick(1,1);
		dest = brick.getBrickBox().getRectangleFromIntersectionWithOtherCollisionBox(ball.getBallBox());
		System.out.println( dest);
		
		
	}

}
