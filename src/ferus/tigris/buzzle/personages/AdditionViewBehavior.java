package ferus.tigris.buzzle.personages;

import ferus.tigris.buzzle.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import ferus.tigris.buzzle.views.AbstractView;

public class AdditionViewBehavior extends ViewsManager {
	private Matrix matrix = new Matrix();
	private int elWidth;
	private Rect screen = new Rect(0,0,0,0);
	
	public AdditionViewBehavior(BehaviorDelegate b, AbstractView additionView, Rect screen) {
		super(b, additionView);
		this.screen = screen;
	}

	public void onSetViewSize(int elWidth) {
		this.elWidth = elWidth;
		super.onSetViewSize(elWidth);
	}

	public void onSetViewCenter(int x, int y) {
		if (y + elWidth/2 < screen.top) {
			super.onSetViewCenter(-elWidth, -elWidth);
			return;
		}
		if (x + elWidth/2 > screen.right) {
			super.onSetViewCenter(x - matrix.columns()*elWidth, y);
		} else
		if (x - elWidth/2 < screen.left) {
			super.onSetViewCenter(x + matrix.columns()*elWidth, y);
		} else
		if (y + elWidth/2 > screen.bottom) {
			super.onSetViewCenter(x, y - matrix.rows()*elWidth);
		} else
		if (y - elWidth/2 < screen.top) {
			super.onSetViewCenter(x, y + matrix.rows()*elWidth);
		} else {
			super.onSetViewCenter(-elWidth, -elWidth);
		}
	}

	public void onSavedAt(Matrix m, Point pos) {
		matrix = m;
	}
}
