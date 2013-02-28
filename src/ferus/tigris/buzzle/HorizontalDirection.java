package ferus.tigris.buzzle;

import ferus.tigris.buzzle.personages.AbstractBehavior;
import android.graphics.Point;

public class HorizontalDirection implements Direction {
	private int x = 0; 
	private int y = 0; 
	Point max = new Point(10000, 10000);

	public HorizontalDirection(Point touchFirstPosition) {
		x = touchFirstPosition.x;
		y = touchFirstPosition.y;
	} 

	public boolean isOutOf(int x1, int x2) {
		return ((y <= x1) || (y >= x2));
	}

	public void touchTo(AbstractBehavior b, Point pos) {
		Point p = createOffsetByTouchPoint(pos);
		b.move(p, this);
	}

	protected Point createOffsetByTouchPoint(Point pos) {
		Point p = new Point(pos.x - x, pos.y - y);
		if(p.x > max.x) {
			p.set(max.x, max.y);
		}
		else if(p.x < -max.x) {
			p.set(-max.x, max.y);
		}
		return p;
	}

	public void onMoveFinish(AbstractBehavior b, Point point) {
		Point p = createOffsetByTouchPoint(point);
		b.moveFinish(p, this);
	}

	public HorizontalDirection copyWithOffset(Point offset) {
		return new HorizontalDirection(new Point(x + offset.x, y + offset.y));
	}

	public void stopOnMax(Point max) {
		this.max.set(max.x, max.y);
	}
}
