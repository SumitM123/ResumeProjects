
public class BallWorld extends World {
	Score score;
	
	public BallWorld() {
		score = new Score();
		score.setX(0);
		score.setY(20);
		
		getChildren().add(score);
	}
	public BallWorld(boolean noShow) {
		score = new Score();
		score.setX(0);
		score.setY(0);
		getChildren().add(score);
	}
	@Override
	public void act(long now) {
		
		
	}
	
	public Score getScore() {
		return score;
	}

}

