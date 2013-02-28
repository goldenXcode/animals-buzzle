package ferus.tigris.buzzle.personages;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import ferus.tigris.buzzle.Direction;
import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.HorizontalDirection;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.VerticalDirection;

public class LargePersonage extends Mark {
	int x, y;
	private Rect screen;
	private Matrix m = new Matrix();

	public LargePersonage(BehaviorDelegate delegate, GameView gameView, Rect screen) {
		super(delegate, gameView, screen);
		//parent = delegate;
		this.screen = screen;
	}

	public void onUpdate() {
		
	}
	
	public void save(Matrix m, int x, int y) {
		Log.d(getClass().toString(), "save at x=" + x + ", y=" + y);
		
		this.m = m;
		this.x = x;
		this.y = y;
		
		m.insertMark(x + 1, y + 1, rootParent());
		m.insertMark(x + 1, y, rootParent());
		m.insertMark(x, y + 1, rootParent());
		m.insertMark(x, y, rootParent());
	}

	protected void doSetViewSize() {
		rootParent().setViewSize(2*width());
	}
	
	protected void doSetViewCenter(int x, int y) {
		rootParent().setViewCenter(x + width(), y + width());
	}

	public void onMove(Point pos, HorizontalDirection direction) {
		if(isMyEvent(direction, y*width() + screen.top)) {
			super.onMove(pos, direction);
			
			Point offset = new Point(0, width());
			HorizontalDirection d = direction.copyWithOffset(offset);
			for(AbstractBehavior mark: m.getMarks()) {
				if(mark != rootParent()) {
					mark.move(createOffset(pos, offset), d);
				}
			}
		} else
		if(isMyEvent(direction, (1 + y)*width() + screen.top)) {
			Point offset = new Point(0, -width());
			HorizontalDirection d = direction.copyWithOffset(offset);
			for(AbstractBehavior mark: m.getMarks()) {
				mark.move(createOffset(pos, offset), d);
			}

		}
	}
	public void onMove(Point pos, VerticalDirection direction) {
		if(isMyEvent(direction, x*width() + screen.left)) {
			super.onMove(pos, direction);
			
			Point offset = new Point(width(), 0);
			VerticalDirection d = direction.copyWithOffset(offset);
			for(AbstractBehavior mark: m.getMarks()) {
				if(mark != rootParent()) {
					mark.move(createOffset(pos, offset), d);
				}
			}
		} else
		if(isMyEvent(direction, (1 + x)*width() + screen.left)) {
			Point offset = new Point(-width(), 0);
			VerticalDirection d = direction.copyWithOffset(offset);
			for(AbstractBehavior mark: m.getMarks()) {
				mark.move(createOffset(pos, offset), d);
			}

		}
	}

	public void onMoveFinish(Point pos, VerticalDirection direction) {
		if(isMyEvent(direction, x*width() + screen.left)) {
			super.onMoveFinish(pos, direction);
			
			Point offset = new Point(width(), 0);
			VerticalDirection d = direction.copyWithOffset(offset);
			for(AbstractBehavior mark: m.getMarks()) {
				if(mark != rootParent()) {
					mark.moveFinish(createOffset(pos, offset), d);
				}
			}
		} else
		if(isMyEvent(direction, (1 + x)*width() + screen.left)) {
			Point offset = new Point(-width(), 0);
			VerticalDirection d = direction.copyWithOffset(offset);
			for(AbstractBehavior mark: m.getMarks()) {
				mark.moveFinish(createOffset(pos, offset), d);
			}
		}
	}
	
	public void onMoveFinish(Point pos, HorizontalDirection direction) {
		if(isMyEvent(direction, y*width() + screen.top)) {
			super.onMoveFinish(pos, direction);
			
			Point offset = new Point(0, width());
			HorizontalDirection d = direction.copyWithOffset(offset);
			for(AbstractBehavior mark: m.getMarks()) {
				if(mark != rootParent()) {
					mark.moveFinish(createOffset(pos, offset), d);
				}
			}
		} else
		if(isMyEvent(direction, (1 + y)*width() + screen.top)) {
			Point offset = new Point(0, -width());
			HorizontalDirection d = direction.copyWithOffset(offset);
			for(AbstractBehavior mark: m.getMarks()) {
				mark.moveFinish(createOffset(pos, offset), d);
			}
		}
	}
	
	protected Point createOffset(Point pos, Point offset) {
		return new Point(pos.x + offset.x, pos.y + offset.y);
	}

	protected boolean isMyEvent(Direction direction, int pos) {
		return !direction.isOutOf(pos, pos + width());
	}

/*	
	public int width() {
		return 2*super.width();
	}
	protected void doSetViewSize() {
		rootParent().setViewSize(2*width());
	}
	
	protected void doSetViewCenter(int x, int y) {
		rootParent().setViewCenter(x + width(), y + width());
	}

	public void onMove(Point pos, VerticalDirection direction) {
		super.onMove(pos, direction);
		if(isNotMyEvent(direction, pos.x)) return;
		Log.d(getClass().toString(), "vertical move element by " + getClass().toString());

		moveParent(parent, pos, direction);
	}

	public void onMove(Point pos, HorizontalDirection direction) {
		super.onMove(pos, direction);
		if(isNotMyEvent(direction, pos.y)) return;
		Log.d(getClass().toString(), "horizontal move element by " + getClass().toString());

		moveParent(parent, pos, direction);
	}

	protected void moveParent(LargePersonage parent2, Point pos, VerticalDirection direction) {}

	protected void moveParent(LargePersonage parent2, Point pos, HorizontalDirection direction) {}

	public void onMoveFinish(Point p, VerticalDirection direction) {
		super.onMoveFinish(p, direction);
		moveFinishParent(p, direction);
	}

	protected void moveFinishParent(Point p, VerticalDirection direction) {}

	public void onMoveFinish(Point p, HorizontalDirection direction) {
		super.onMoveFinish(p, direction);
		moveFinishParent(p, direction);
	}

	private void moveFinishParent(Point p, HorizontalDirection direction) {}
*/
	public Matrix marks() {
		return null;
	}


}
