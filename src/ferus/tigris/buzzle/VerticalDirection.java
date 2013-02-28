package ferus.tigris.buzzle;

import ferus.tigris.buzzle.personages.AbstractBehavior;
import android.graphics.Point;

public class VerticalDirection implements Direction {
	private int x = 0; 
	private int y = 0; 
	Point max = new Point(10000, 10000);

	public VerticalDirection(Point touchFirstPosition) {
		x = touchFirstPosition.x;
		y = touchFirstPosition.y;
	} 

	public boolean isOutOf(int x1, int x2) {
		return ((x <= x1) || (x >= x2));
	}

	public void touchTo(AbstractBehavior b, Point pos) {
		Point p = createOffsetByTouchPoint(pos);
		b.move(p, this);
	}

	protected Point createOffsetByTouchPoint(Point pos) {
		Point p = new Point(pos.x - x, pos.y - y);
		if(p.y > max.y) {
			p.set(max.x, max.y);
		}
		else if(p.y < -max.y) {
			p.set(max.x, -max.y);
		}
		return p;
	}

	public void onMoveFinish(AbstractBehavior b, Point point) {
		Point p = createOffsetByTouchPoint(point);
		b.moveFinish(p, this);
	}

	public VerticalDirection copyWithOffset(Point offset) {
		return new VerticalDirection(new Point(x + offset.x, y + offset.y));
	}

	public void stopOnMax(Point max) {
		this.max.set(max.x, max.y);
	}

}
