package ferus.tigris.buzzle.personages;

import android.graphics.Point;
import ferus.tigris.buzzle.Matrix;

public class KillerWithEffectBehavior extends KillerBehavior {

	private Matrix matrix = new Matrix();
	private Point pos = new Point();
	private boolean block = false;

	public KillerWithEffectBehavior(BehaviorDelegate b) {
		super(b);
	}
	
	public void onKill() {
		if(block )return;
		block = true;
		BehaviorDelegate selfkiller = new SelfKillerBehavior(rootParent());
		matrix.insertMark(pos.x, pos.y, selfkiller);
	}

	public void onSavedAt(Matrix m, Point pos) {
		matrix = m;
		this.pos  = pos;
	}


}
