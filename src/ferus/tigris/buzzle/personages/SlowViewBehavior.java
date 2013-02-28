package ferus.tigris.buzzle.personages;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import ferus.tigris.buzzle.Direction;
import ferus.tigris.buzzle.HorizontalDirection;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.views.AbstractView;
import ferus.tigris.buzzle.VerticalDirection;

public class SlowViewBehavior extends ViewsManager {

	private Point dstPos = new Point(0, 0);
	private Point pos = new Point(0, 0);
	private Point position = new Point(0, 0);
	private Rect screen = new Rect();
	private int speed = 8;
	private int width = 0;
	private boolean moved = false;
	private int sign = 1;
	private boolean firstTime = true;
	Matrix m = new Matrix(0, 0);

	private DirectionB direction = new VerticalDirectionB();
	
	class DirectionB {
		public void update() {}

		public void setSign(Point p) {};
	}

	class VerticalDirectionB extends DirectionB {
		public void update() {
			onUpdate(this);
		}

		public void setSign(Point p) {
			if(p.y > 0) {
				sign = -1;
			} else {
				sign = 1;
			}
		};
	}

	class HorizontalDirectionB extends DirectionB {
		public void update() {
			onUpdate(this);
		}

		public void setSign(Point p) {
			if(p.x > 0) {
				sign = -1;
			} else {
				sign = 1;
			}
		};
	}

	public SlowViewBehavior(BehaviorDelegate delegate, AbstractView sprite, Rect screen) {
		super(delegate, sprite);
		this.screen = screen;
	}

	protected void onUpdate() {
		if(!pos.equals(dstPos)) {
			direction.update();
		} else {
			sign = 1;
		}
	}

	protected void onUpdate(HorizontalDirectionB direction) {
		pos.y = dstPos.y;

		if(Math.abs(dstPos.x - pos.x) <= speed) {
			pos = dstPos;
			sign = 1;
		}
		if(pos.x != dstPos.x) {
			pos.x += sign*speed;
		}
		if((sign > 0)&&(pos.x > screen.right)) {
			pos.x = screen.left;
		}
		if((sign < 0)&&(pos.x < screen.left)) {
			pos.x = screen.right;
		}
		super.onSetViewCenter(pos.x, pos.y);
	}

	protected void onUpdate(VerticalDirectionB direction) {
		pos.x = dstPos.x;

		if(Math.abs(dstPos.y - pos.y) <= speed) {
			pos = dstPos;
			sign = 1;
		}
		if(pos.y != dstPos.y) {
			pos.y += sign*speed;
		}
		if((sign > 0)&&(pos.y > screen.bottom)) {
			pos.y = screen.top;
		}
		if((sign < 0)&&(pos.y < screen.top)) {
			pos.y = screen.bottom;
		}
		super.onSetViewCenter(pos.x, pos.y);
	}

	public void onSavedAt(Matrix m, Point pos) {
		this.m = m;
		position.set(pos.x,  pos.y);
		int x = pos.x*width;
		int y = pos.y*width;
		doSetViewCenter(x, y);
	}

	protected void doSetViewCenter(int x, int y) {
		Log.d(getClass().toString(), "doSetViewCenter, y=" + y);

		x += screen.left + width/2;
		y += screen.top + width/2;

		dstPos = new Point(x, y);
		if(firstTime) {
			firstTime = false;
			pos = new Point(x, y);
			super.onSetViewCenter(x, y);
		}
	}


	public void onSetViewCenter(int x, int y) {
		if(moved) {
			Log.d(getClass().toString(), "onSetViewCenter, moved");
			super.onSetViewCenter(x, y);
		}
	}

	public void onSetViewSize(int elWidth) {
		speed = elWidth/5;
		super.onSetViewSize(elWidth);
		this.width  = elWidth;
	}

	public void onMove(Point pos, VerticalDirection direction) {
		if(isNotMyEvent(direction, position.x*width + screen.left))
			return;
		
		moved = true;
	}

	public void onMove(Point pos, HorizontalDirection direction) {
		if(isNotMyEvent(direction, position.y*width + screen.top))
			return;

		moved = true;
	}

	public void onMoveFinish(Point p, VerticalDirection direction) {
		Log.d(getClass().toString(), "onMoveFinish vertical, p.x=" + p.x + ", p.y=" + p.y);

		if(isNotMyEvent(direction, position.x*width + screen.left))
			return;

		this.direction = new VerticalDirectionB();
		this.direction.setSign(p);
		moved = false;
		pos = dstPos;

		Log.d(getClass().toString(), "sign=" + sign);
	}

	protected boolean isNotMyEvent(Direction direction, int pos) {
		return direction.isOutOf(pos, pos + width);
	}

	public void onMoveFinish(Point p, HorizontalDirection direction) {
		Log.d(getClass().toString(), "onMoveFinish horizontal, p.x=" + p.x + ", p.y=" + p.y);

		if(isNotMyEvent(direction, position.y*width + screen.top))
			return;

		this.direction = new HorizontalDirectionB();
		this.direction.setSign(p);
		moved = false;
		pos = dstPos;

		Log.d(getClass().toString(), "sign=" + sign);
	}
	
	protected boolean available() {
		return pos.equals(dstPos);
	}

}
