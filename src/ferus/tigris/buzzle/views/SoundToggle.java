package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class SoundToggle implements AbstractView{
	private GameView view;
	private Bitmap on;
	private Bitmap off;
	private int x;
	private int y;
	private int width;
	private int height;
	public SoundToggle(GameView gameView) {
		view = gameView;
		on = BitmapFactory.decodeResource(view.getResources(), R.drawable.soundon);
		off = BitmapFactory.decodeResource(view.getResources(), R.drawable.soundoff);

		width = on.getWidth();
		height = on.getHeight();
	}

	public void draw(Canvas canvas, boolean soundOn) {
		canvas.drawBitmap(soundOn ? on : off, x, y, null);
	}

	public boolean isCollition(float xTouch, float yTouch) {
		Rect dst = new Rect(x, y, x + width, y + height);
		return dst.contains((int)xTouch, (int)yTouch);
	}

	public void setPosition(Point position) {
		x = position.x;
		y = position.y;
	}

	public void draw(Canvas canvas) {
		draw(canvas, true);
	}

	public void setRealSpriteWidth(int px) {}

	public int width() {
		return width;
	}

	public int height() {
		return height;
	}

}
