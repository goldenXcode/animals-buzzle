package ferus.tigris.buzzle.personages;

import android.graphics.Point;
import android.util.Log;
import ferus.tigris.buzzle.Matrix;

public class SelfKillerBehavior extends BehaviorDelegate {

	private int elWidth = 0;
	private int speed = 10;
	private Matrix matrix;
	private Point pos;
	private boolean firstTime = true;

	public SelfKillerBehavior(BehaviorDelegate b) {
		super(b);
	}
	
	public void onUpdate() {
		elWidth -= speed ;
		if(elWidth <= 0) {
			matrix.insertMark(pos.x, pos.y, new DeadBehavior());
		}
		rootParent().setViewSize(elWidth);
	}
	public void onSavedAt(Matrix m, Point pos) {
		this.matrix = m;
		this.pos = pos;
	}
	
	public boolean dead() {
		return false;
	}

	public void onSetViewSize(int elWidth) {
		if(firstTime) {
			this.elWidth = elWidth; 
			speed = elWidth/5;
			firstTime = false;
		}
	}
	
	protected boolean available() {
		Log.d(getClass().toString(), "no avaiable");
		return false;
	}

}
