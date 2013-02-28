package ferus.tigris.buzzle.personages;

import ferus.tigris.buzzle.Direction;
import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.HorizontalDirection;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.VerticalDirection;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Mark extends BehaviorDelegate {

	private Point position = new Point(0, 0);
	private int elWidth = 0;
	private Matrix matrix = new Matrix();
	private boolean moved = false;
	private Rect screen = new Rect();

	public Mark(BehaviorDelegate delegate, GameView gameView, Rect screen) {
		super(delegate);
		this.screen = screen;
	}

	public void onUpdate() {
		if(rootParent().isDied())return;
		if(!rootParent().isAvailable()) {
			Log.d(getClass().toString(), "I am not avaible! x=" + position.x + ", y=" + position.y);
			return;
		}
		Point checkPos = new Point(position.x, position.y + 1);
		if(checkPos.y < matrix.rows()) {
			if(matrix.getMark(checkPos).isDied()) {
				matrix.getMark(position).moveFinish(new Point(0, 0), new VerticalDirection(new Point(0, 10)));
				matrix.swap(position, checkPos);
			}
		}
	}

	public void onSavedAt(Matrix m, Point pos) {
		this.matrix = m;
		elWidth = screen.width() / matrix.columns();

		doSetViewSize();

		position.set(pos.x, pos.y);
		int x = pos.x*width();
		int y = pos.y*width();
		doSetViewCenterWithScreenOffset(x, y);
	}

	protected void doSetViewSize() {
		rootParent().setViewSize(width());
	}

	public void onMove(Point pos, VerticalDirection direction) {
		if(isNotMyEvent(direction, position.x*width() + screen.left))
			return;
		if(pos.y / width() >= matrix.rows())
			return;

		moved = true;

		int x = position.x*width();
		int y = (pos.y + position.y*width()) % (width() * matrix.rows());
		if(y < 0)y += matrix.rows()*width();

		doSetViewCenterWithScreenOffset(x, y);
	}

	protected void doSetViewCenterWithScreenOffset(int x, int y) {
		x += screen.left;
		y += screen.top;
		doSetViewCenter(x, y);
	}

	protected void doSetViewCenter(int x, int y) {
		rootParent().setViewCenter(x + width()/2, y + width()/2);
	}

	public void onMove(Point pos, HorizontalDirection direction) {
		if(isNotMyEvent(direction, position.y*width() + screen.top))
			return;
		if(pos.x >= matrix.columns()*width())
			return;

		moved = true;

		int x = (pos.x + position.x*width()) % (width() * matrix.rows());
		if(x < 0)x += matrix.columns()*width();
		int y = position.y*width();

		doSetViewCenterWithScreenOffset(x, y);
	}

	protected boolean isNotMyEvent(Direction direction, int pos) {
		return direction.isOutOf(pos, pos + width());
	}

	public void onMoveFinish(Point p, VerticalDirection direction) {
		if(!moved)
			return;
		moved = false;
		if(isNotMyEvent(direction, position.x*width() + screen.left))
			return;

		int x = position.x;
		int y = ((p.y + (int)Math.signum(p.y)*width()/2)/width() + position.y) % matrix.rows();
		if(y < 0)y += matrix.rows();

		save(matrix, x, y);
	}

	protected void save(Matrix m, int x, int y) {
		m.insertMark(x, y, rootParent());
	}

	public void onMoveFinish(Point p, HorizontalDirection direction) {
		if(!moved)
			return;
		moved = false;
		if(isNotMyEvent(direction, position.y*width() + screen.top))
			return;

		int x = ((p.x + (int)Math.signum(p.x)*width()/2)/width() + position.x) % matrix.columns();
		if(x < 0)x += matrix.columns();
		int y = position.y;

		save(matrix, x, y);
	}

	public int width() {
		return elWidth;
	}
}