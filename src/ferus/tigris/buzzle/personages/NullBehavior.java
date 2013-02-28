package ferus.tigris.buzzle.personages;

import android.graphics.Canvas;
import android.graphics.Point;
import ferus.tigris.buzzle.HorizontalDirection;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.VerticalDirection;

public class NullBehavior implements AbstractBehavior {

	public void update() {}

	public void draw(Canvas canvas) {}

	public String getType() {
		return this.getClass().toString();
	}

	public boolean isDied() {
		return false;
	}

	public void kill() {}

	public void saveAt(Matrix m, Point pos) {}

	public void move(Point pos, VerticalDirection direction) {}

	public void move(Point pos, HorizontalDirection direction) {}

	public void moveFinish(Point displacement, VerticalDirection direction) {}

	public void moveFinish(Point displacement, HorizontalDirection direction) {}

	public boolean isAvailable() {
		return true;
	}

}
