import javafx.scene.image.Image;
//testing
import javafx.scene.image.Image;
//testing fork
public class Ball extends Actor {
	public double dxSpeed;
	public double dySpeed; 
	public boolean flag; 
	
	public Ball() {
		dxSpeed = 3;
		dySpeed = 2;
		
		String path = getClass().getClassLoader().getResource("resources/ball.png").toString();
		Image img = new Image(path);
		this.setImage(img);
	}
	
	@Override
	public void act(long now) {
		if (flag) {
			flag = false;
			this.move(dxSpeed*10, dySpeed*10); // guarantees escape even if excessive
			return;
		}
		
		flag = false;
		
		if (onUpperLeftCorner()) {
			reverseDirection();
			
			double currX = this.getX(), currY = this.getY();
			if (currX + dxSpeed <= 0 || currY + dySpeed <= 0) {
				// some point close to upper left corner 
				
				this.setX(0.1 * getWorld().getWidth());
				this.setY(0.1 * getWorld().getHeight());
			}
		}
		
		if (onUpperRightCorner()) {
			reverseDirection();
			
			double currX = this.getX(), currY = this.getY();
			if (currX + dxSpeed >= getWorld().getWidth() || currY + dySpeed <= 0) {
				// some point close to upper right corner 
				
				this.setX(0.9 * getWorld().getWidth());
				this.setY(0.1 * getWorld().getHeight());
			}
		}
		
		if (onLowerLeftCorner()) {
			reverseDirection();
			// System.out.println("On lower left corner");
			
			double currX = this.getX(), currY = this.getY();
			if (currX + dxSpeed <= 0 || currY + dySpeed >= getWorld().getHeight()) {
				// System.out.println("Deep into lower left corner");
				// some point close to lower left corner 
				
				this.setX(0.1 * getWorld().getWidth());
				this.setY(0.9 * getWorld().getHeight());
			}
		}
		
		if (onLowerRightCorner()) {
			reverseDirection();
			
			double currX = this.getX(), currY = this.getY();
			if (currX + dxSpeed >= getWorld().getWidth() || currY + dySpeed >= getWorld().getHeight()) {
				// some point close to upper left corner 
				
				this.setX(0.9 * getWorld().getWidth());
				this.setY(0.9 * getWorld().getHeight());
			}
		}
		
		if (onTopEdge()) {
			dySpeed = -dySpeed; 
			
			double currY = this.getY();
			
			if (currY + dySpeed <= 0) {
				this.setY(0.1*getWorld().getHeight());
			}
			
		} else if (onBottomEdge()) {
			dySpeed = -dySpeed;
			Score tempScore = ((BallWorld) getWorld()).getScore();
			tempScore.setScore(tempScore.getScore() - 100000);
			
			
			 double currY = this.getY();
			  
			 if (currY + dySpeed >= getWorld().getHeight()) { 
				 this.setY(0.9 * getWorld().getHeight()); 
			 }
			 
			
		} else if (onLeftEdge()) {
			dxSpeed = -dxSpeed;
			// System.out.println("Here");
			
			double currX = this.getX();
			
			if (currX + dxSpeed <= 0) {
				this.setX(0.1 * getWorld().getWidth());
			}
			
		} else if (onRightEdge()) {
			dxSpeed = -dxSpeed; 
			
			double currX = this.getX();
			
			if (currX + dxSpeed >= getWorld().getWidth()) {
				this.setX(0.9 * getWorld().getWidth());
			}
		}
		
		boolean canEscapePaddle = false;

		for (int i = 0; i < getParent().getChildrenUnmodifiable().size(); i++) {
			if (getParent().getChildrenUnmodifiable().get(i) instanceof Paddle) {
				if (intersectsWithPaddle((Paddle)getParent().getChildrenUnmodifiable().get(i)))
				{
			//		System.out.println("The ball intersected the paddle");
			//		dySpeed = -dySpeed; 
					
			/*
			 * Ball class - Give the ball better Paddle bounce control as follows:
				
				If the Ball hits the Paddle within its left/right bounds AND the Paddle isn't moving, bounce normally (Fig 1)
				
				If the Ball hits the Paddle within the middle third region AND the Paddle is moving any direction, bounce normally (Fig 2)
				
				If the Ball hits the Paddle within the left third AND is Paddle is moving left then bounce LEFT no matter which direction
				the Ball came from (Fig 3)
				Same idea on the right third
				
				If the Ball hits the Paddle to the left of its boundary then bounce at a sharp angle no matter which direction the Paddle
				or Ball are moving (Fig 4)
				Same idea on the right boundary
				
				You'll need to use your creativity to determine whether the Paddle is moving left, right, or not at all

			 * 
			 * 
			 * */		
					canEscapePaddle = bounceOffPaddle((Paddle)getParent().getChildrenUnmodifiable().get(i)); 
					/*
					 * int[] x = {-100, 100, 1000, 1000, 1000, 100,-100, -100}; int[] y = {-100,
					 * -100, -100, 100, 1000, 1000, 1000, 100}; int ri = (int) (Math.random() * 8);
					 * this.setX(x[ri]); this.setY(y[ri]);
					 */
					
					if (!canEscapePaddle) {
						flag = true;
					}
				}
			}
		}
		
		for (int i = 0; i < getParent().getChildrenUnmodifiable().size(); i++) {
			if (getParent().getChildrenUnmodifiable().get(i) instanceof Brick) {
				if (intersectsWithBrick((Brick) getParent().getChildrenUnmodifiable().get(i)))
				{
				//	System.out.println("The ball does intersect with brick");
					bounceOffBrick((Brick) getParent().getChildrenUnmodifiable().get(i));
					this.getWorld().remove((Brick) getParent().getChildrenUnmodifiable().get(i));
					((BallWorld) getWorld()).getScore().setScore(((BallWorld) getWorld()).getScore().getScore() + 5);
				}
			}
		}
		
		
		
		this.move(dxSpeed, dySpeed);
	}
	
	private void bounceOffBrick(Brick other) {
		/*
		 * If (Ball is touching a Brick)
				If Ball x-coord is between the Brick's left and right edges
						reverse y-direction
				else if Ball y-coord is between the Brick's top and bottom edges
						reverse x-direction
				else
						reverse both x and y directions because we hit a corner

		 */
		
		
		double currX = this.getX();
		double currY = this.getY();
		
		if (currX > other.getX() && currX < other.getX() + other.getWidth()) {
			dySpeed = -dySpeed;
		} else if (currY > other.getY() && currY < other.getY() + other.getHeight()) {
			dxSpeed = -dxSpeed;
		} else {
			reverseDirection();
		}
	}
	private boolean intersectsWithBrick(Brick other) {
		// System.out.println("There is a brick in ball world");
		double currX = this.getX();
		double currY = this.getY();
		
	//	System.out.printf("(%f, %f) is the ball's pos and (%f, %f) is the brick's pos\n", currX, currY, other.getX(), other.getY());
	
	// 	System.out.println(other.getY() + " -> brick's y pos " +  other.getHeight() + " -> brick's height");
	// 	System.out.println(currY + " -> ball's y pos\n");
		
		if (currX >= other.getX() && currX <= other.getX() + other.getWidth()) { 
			// System.out.println("The y-coordinates of the brick and paddle do not match");
			if (currY >= other.getY() && currY <= other.getY() + other.getHeight())  {
			// 	System.out.println("Brick and ball intersected!");
				return true;
			}
			
		}
		
		return false;
	}
	
	// Returns whether or not the ball can be escaped on the next iteration
	private boolean bounceOffPaddle(Paddle paddle) {
		if (paddle.isBeingUsed()) {
			
			if (paddleWithinMiddleThird(paddle)) {
				// System.out.println("Figure 2");
				dySpeed = -dySpeed; 
			}
			
			if (paddleWithinLeftThird(paddle)) {
				// System.out.println("Figure 3 -> Left"); 
				if (paddle.isMovingLeft()) {
					if (dxSpeed > 0) {
						reverseDirection();
					} else {
						dySpeed = -dySpeed; 
					}
				} else {
					dySpeed = -dySpeed; 
				}
			}
			
			if (paddleWithinRightThird(paddle)) {
				// System.out.println("Figure 3 -> Right");
				if (paddle.isMovingRight()) {
					if (dxSpeed > 0) {
						dySpeed = -dySpeed; 
					} else {
						reverseDirection();
					}
				} else {
					dySpeed = -dySpeed; 
				}
			} 
			
			if (paddleOnRightBoundary(paddle)) {
				// System.out.println("Figure 4 -> Right");
				
				if (paddle.isMovingRight()) {
					if (dxSpeed > 0) {
						dySpeed = -dySpeed/15; 
					} else {
						reverseDirection();
						dySpeed /= 15;
					}
				}
				
			}
			
			if (paddleOnLeftBoundary(paddle)) {
				// System.out.println("Figure 4 -> Left");
				if (paddle.isMovingLeft()) {
					if (dxSpeed > 0) {
						reverseDirection();
						dySpeed /= 15;
					} else {
						dySpeed = -dySpeed/15; 
					}
				}
			}
			
			
			
		} else {
			if (paddleWithinBounds(paddle)) {
				dySpeed = -dySpeed; 
			}
			
		}
		
		double hypotheticalXPos = this.getX() + dxSpeed;
		double hypotheticalYPos = this.getY() + dySpeed; 
		
		if (hypotheticalXPos >= paddle.getX() && hypotheticalXPos <= paddle.getX() + paddle.getWidth()) {
			if (hypotheticalYPos >= paddle.getY() && hypotheticalYPos <= paddle.getY() + paddle.getHeight()) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
		
		
		/*
		 * // System.out.println("Bounced off paddle on: "); //
		 * System.out.println("                Third: " +
		 * (paddleWithinMiddleThird(paddle) ? "middle" : //
		 * (paddleWithinLeftThird(paddle) ? "left" : // (paddleWithinRightThird(paddle)
		 * ? "right" : (paddleOnLeftBoundary(paddle) ? "left boundary" :
		 * "right boundary") ) )));
		 * 
		 * System.out.println("Moving paddle: " + (paddle.isMovingLeft() ||
		 * paddle.isMovingRight() ? "YES" : "NO"));
		 * System.out.printf("dx = %f, dy = %f\n\n", dxSpeed, dySpeed);
		 */
		
		
	}
	
	private boolean paddleWithinBounds(Paddle paddle) {
		double currX = this.getX();
		double currY = this.getY();
		
		if (currX > paddle.getX() && currX < paddle.getX() + paddle.getWidth()) {
			if (currY > paddle.getY() && currY < paddle.getY() + paddle.getHeight()) {
				return true; 
			}
		}
		
		
		return false;
	}
	
	private boolean paddleWithinMiddleThird(Paddle paddle) {
		double middleStartX = paddle.getX() + paddle.getWidth()/3;
		double middleEndX = paddle.getX() + (2*paddle.getWidth())/3;
		
		double middleStartY = paddle.getY();
		double middleEndY = paddle.getY() + paddle.getHeight();
		
		double currX = this.getX();
		double currY = this.getY();
		
		if (currX >= middleStartX && currX <= middleEndX) {
			if (currY >= middleStartY && currY <= middleEndY) {
				return true; 
			}
		}
		
		return false; 
	}
	
	private boolean paddleWithinLeftThird(Paddle paddle) {
		double leftStartX = paddle.getX();
		double leftEndX = paddle.getX() + paddle.getWidth()/3; 
		
		double leftStartY = paddle.getY();
		double leftEndY = paddle.getY() + paddle.getHeight();
		
		double currX = this.getX();
		double currY = this.getY();
		
		if (currX >= leftStartX && currX < leftEndX) {
			if (currY >= leftStartY && currY <= leftEndY) {
				return true;
			}
		}
		
		return false; 
	}
	
	private boolean paddleOnLeftBoundary(Paddle paddle) {
		double currX = this.getX();
		double currY = this.getY();
		
		if (currX < paddle.getX() && currX + this.getWidth() >= paddle.getX()) {
			if (currY >= paddle.getY() && currY <= paddle.getY() + paddle.getHeight()) {
				return true;
			}
		}
		
		
		return false;
	}
	
	private boolean paddleOnRightBoundary(Paddle paddle) {
		double currX = this.getX();
		double currY = this.getY();
		
		if (currX > paddle.getX() + paddle.getWidth() && currX - paddle.getWidth() <= paddle.getX() + paddle.getWidth()) {
			if (currY >= paddle.getY() && currY <= paddle.getY() + paddle.getHeight()) {
				return true;
			}
		}
		
		
		return false;
	}
	
	private boolean paddleWithinRightThird(Paddle paddle) {
		double rightStartX = paddle.getX() + paddle.getWidth() * (2.0/3);
		double rightEndX = paddle.getX() + paddle.getWidth();
		
		double rightStartY = paddle.getY();
		double rightEndY = paddle.getY() + paddle.getHeight(); 
		
		double currX = this.getX();
		double currY = this.getY();
		
		if (currX > rightStartX && currX <= rightEndX) {
			if (currY > rightStartY && currY <= rightEndY) {
				return true;
			}
		}
		
		
		return false;
	}
	
	private boolean onTopEdge() {
		if (this.getY() <= 0) return true;
		
		return false;
	}
	
	private boolean onBottomEdge() {
		if (this.getY() + this.getHeight() >= this.getWorld().getHeight())
			return true;
		
		return false;
	}
	
	private boolean onLeftEdge() {
		if (this.getX() <= 0) {
			return true;
		}
		
		return false;
	}
	
	private boolean onRightEdge() {
		if (this.getX() + this.getWidth() >= this.getWorld().getWidth()) {
			return true;
		}
		
		return false;
	}
	
	private boolean onUpperLeftCorner() {
		return onTopEdge() && onLeftEdge();
	}
	
	private boolean onUpperRightCorner() {
		return onTopEdge() && onRightEdge();
	}
	
	private boolean onLowerLeftCorner() {
		return onBottomEdge() && onLeftEdge();
	}
	
	private boolean onLowerRightCorner() {
		return onBottomEdge() && onRightEdge();
	}
	
	private void reverseDirection() {
		dxSpeed = -dxSpeed;
		dySpeed = -dySpeed; 
	}
	
	private boolean intersectsWithPaddle(Paddle other) {
	//	System.out.println("There is a paddle in the world");
		double currentX = this.getX();
		double currentY = this.getY();
		
		if (currentX >= other.getX() && currentX <= other.getX() + other.getWidth()) {
			if (currentY >= other.getY() && currentY <= other.getY() + other.getHeight()) {
				return true;
			}
		}
		
		
		return false;
	}
	public void setDxSpeed(int speed) {
		dxSpeed = speed;
	}
	public void setDySpeed(int speed) {
		dySpeed = speed;
	}
	
}

