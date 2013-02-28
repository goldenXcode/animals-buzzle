package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameView;
import android.graphics.Canvas;

public class StaticNumberView extends NumberView {


	private int number;
	public StaticNumberView(GameView view, int number) {
		super(view);
		this.number = number;
	}
	
	public void draw(Canvas canvas) {
		draw(number, canvas);
	}
	
	public int width() {
		int count = 0, d = number;
		while(d > 0) {
			d = d / 10;
			count++;
		}
		return super.width()*count;
	}

}
