package ferus.tigris.buzzle.personages;

import android.graphics.Canvas;
import android.graphics.Point;
import ferus.tigris.buzzle.views.AbstractView;

public class ViewsManager extends BehaviorDelegate {
	private AbstractView view;

	public ViewsManager(BehaviorDelegate delegate, AbstractView sprite) {
		super(delegate);
		this.view = sprite;
	}

	public void onSetViewSize(int elWidth) {
		view.setRealSpriteWidth(elWidth);
	}

	public void onSetViewCenter(int x, int y) {
		view.setPosition(new Point(x, y));
	}

	public void onDraw(Canvas canvas) {
		view.draw(canvas);
	}
}