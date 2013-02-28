package ferus.tigris.buzzle.personages;

import android.graphics.Canvas;
import android.graphics.Point;
import ferus.tigris.buzzle.HorizontalDirection;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.VerticalDirection;

public class BehaviorDelegate implements AbstractBehavior, AbstractViewManager {
	private BehaviorDelegate delegate = null;
	private BehaviorDelegate parent = null;
	
	public BehaviorDelegate(BehaviorDelegate b) {
		this.delegate = b;
		if(b != null)
			b.parent = this;
	}

	protected BehaviorDelegate rootParent() {
		BehaviorDelegate parent = this;
		while(parent.parent != null) {
			parent = parent.parent;
		}
		return parent;
	}

	protected BehaviorDelegate personage() {
		BehaviorDelegate personage = this;
		while (personage.delegate != null) {
			personage = parent.delegate;
		}
		return personage;
	}
	
	public void update() {
		if(delegate != null)
			delegate.update();
		onUpdate();
	}

	protected void onUpdate() {}

	public void draw(Canvas canvas) {
		if(delegate != null)
			delegate.draw(canvas);
		onDraw(canvas);
	}

	protected void onDraw(Canvas canvas) {}

	public boolean isDied() {
		boolean result = dead();
		if(delegate != null)
			result = result || delegate.isDied();
		return result;
	}

	protected boolean dead() {
		return false;
	}

	public void move(Point displacement, VerticalDirection direction) {
		if(delegate != null)
			delegate.move(displacement, direction);
		onMove(displacement, direction);
	}

	protected void onMove(Point displacement, VerticalDirection direction) {}

	public void move(Point displacement, HorizontalDirection direction) {
		if(delegate != null)
			delegate.move(displacement, direction);
		onMove(displacement, direction);
	}

	protected void onMove(Point pos, HorizontalDirection direction) {}

	public void moveFinish(Point displacement, VerticalDirection direction) {
		if(delegate != null)
			delegate.moveFinish(displacement, direction);
		onMoveFinish(displacement, direction);
	}

	protected void onMoveFinish(Point displacement, VerticalDirection direction) {}

	public void moveFinish(Point displacement, HorizontalDirection direction) {
		if(delegate != null)
			delegate.moveFinish(displacement, direction);
		onMoveFinish(displacement, direction);
	}

	protected void onMoveFinish(Point displacement, HorizontalDirection direction) {}

	public void saveAt(Matrix m, Point pos) {
		if(delegate != null)
			delegate.saveAt(m, pos);
		onSavedAt(m, pos);
	}

	protected void onSavedAt(Matrix m, Point pos) {}

	public void kill() {
		if(delegate != null)
			delegate.kill();
		onKill();
	}

	protected void onKill() {}

	public String getType() {
		if(delegate != null)
			return delegate.getType();
		return getClass().toString();
	}

	public void setViewSize(int elWidth) {
		if(delegate != null)
			delegate.setViewSize(elWidth);
		onSetViewSize(elWidth);
	}

	protected void onSetViewSize(int elWidth) {}

	public void setViewCenter(int x, int y) {
		if(delegate != null)
			delegate.setViewCenter(x, y);
		onSetViewCenter(x, y);
	}

	protected void onSetViewCenter(int x, int y) {}

	public boolean isAvailable() {
		if(delegate != null) 
			return available() && delegate.isAvailable();
		return available();
	}

	protected boolean available() {
		return true;
	}

}
