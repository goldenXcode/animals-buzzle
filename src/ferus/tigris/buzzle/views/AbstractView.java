package ferus.tigris.buzzle.views;

import android.graphics.Canvas;
import android.graphics.Point;

public interface AbstractView {
	abstract public void draw(Canvas canvas);
	abstract public void setPosition(Point position);
	public abstract void setRealSpriteWidth(int elWidth);
}
