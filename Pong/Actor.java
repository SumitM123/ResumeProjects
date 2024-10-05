import java.util.ArrayList;

import javafx.geometry.Bounds;

public abstract class Actor extends javafx.scene.image.ImageView {
	public Actor() {
		
	}
	
	
	public void move(double dx, double dy) {
		this.setX(this.getX() + dx);
		this.setY(this.getY() + dy);
		
	//	System.out.printf("(%f, %f) -> (%f, %f)\n",
	//			this.getX() - dx, this.getX() - dy,
	//			this.getX(), this.getY());
	}
	
	public World getWorld() {
		return ((World)this.getParent());
	}
	
	public double getWidth() {
		Bounds imageBounds = this.getBoundsInParent(); 
		return imageBounds.getWidth();
	}
	
	public double getHeight() {
		Bounds imageBounds = this.getBoundsInParent();
		return imageBounds.getHeight();
	}
	
	public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls) {
		ArrayList<A> typeAObjects = new ArrayList<A>();
		
		for (int i = 0; i < this.getParent().getChildrenUnmodifiable().size(); i++) {
			if (cls.isInstance(this.getParent().getChildrenUnmodifiable().get(i))) {
				Actor currentActor = ((Actor) this.getParent().getChildrenUnmodifiable().get(i));
				if (currentActor != this) {
					if (this.intersects(currentActor)) {
						typeAObjects.add((A) currentActor);
					}
				}
			}
		}
		
		return typeAObjects;
	}
	
	private boolean intersects(Actor other) {
		if (this.getX() == other.getX() && this.getY() == other.getY()) {
			return true;
		}
		
		return false;
	}
	
	public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls) {
		
		for (int i = 0; i < this.getParent().getChildrenUnmodifiable().size(); i++) {
			if (cls.isInstance(this.getParent().getChildrenUnmodifiable().get(i))) {
				Actor currentActor = ((Actor) this.getParent().getChildrenUnmodifiable().get(i));
				if (currentActor != this) {
					return (A) currentActor;
				}
			}
		}
		
		return null;
	}
	
	public abstract void act(long now);
	
}