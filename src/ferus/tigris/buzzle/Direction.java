package ferus.tigris.buzzle;

import ferus.tigris.buzzle.personages.AbstractBehavior;
import android.graphics.Point;

public interface Direction {
	abstract public void touchTo(AbstractBehavior b, Point pos);
	public boolean isOutOf(int x1, int x2);
	public abstract void onMoveFinish(AbstractBehavior b, Point point);
	void stopOnMax(Point max);
	Direction copyWithOffset(Point offset);
}
