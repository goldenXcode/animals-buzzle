package ferus.tigris.buzzle.personages;

import android.graphics.Point;
import android.graphics.Rect;
import ferus.tigris.buzzle.Direction;
import ferus.tigris.buzzle.HorizontalDirection;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.VerticalDirection;

public class BlockBehavior extends BehaviorDelegate {

	private Point position;
	private int elWidth;
	private Matrix matrix;
	private Rect screen;
	private int width = 0;

	public BlockBehavior(BehaviorDelegate b, Rect screen) {
		super(b);
		this.screen = screen;
	}

	public void onMove(Point pos, HorizontalDirection direction) {
		if((pos.y == 0)&&(pos.x == 0)) return;
		if(isNotMyEvent(direction, position.y*elWidth + screen.top))
			return;
		if(Math.abs(pos.x) >= width/3)
			direction.stopOnMax(new Point(width/3, 0));
	}

	public void onSetViewSize(int elWidth) {
		width = elWidth;
	}
	
	private boolean isNotMyEvent(Direction direction, int pos) {
		return direction.isOutOf(pos, pos + elWidth);
	}

	public void onMove(Point pos, VerticalDirection direction) {
		if((pos.y == 0)&&(pos.x == 0)) return;
		if(isNotMyEvent(direction, position.x*elWidth + screen.left))
			return;
		if(Math.abs(pos.y) >= width/3)
			direction.stopOnMax(new Point(0, width/3));
	}

	public void onSavedAt(Matrix m, Point pos) {
		matrix = m;
		elWidth = screen.width() / matrix.columns();
		position = new Point(pos.x, pos.y);
	}

}
