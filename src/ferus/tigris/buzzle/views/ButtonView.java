package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class ButtonView implements AbstractView {
	private Bitmap icon;
	private int x;
	private int y;
	private Rect src;
	int width, height;

	public ButtonView(Bitmap b) {
		super();
		icon = b;

		width = icon.getWidth();
		height = icon.getHeight();
		
		src = new Rect(0, 0, width, height);
	}

	public boolean isCollition(float xTouch, float yTouch) {
		return rect().contains((int)xTouch, (int)yTouch);
	}

	public void setPosition(Point position) {
		x = position.x;
		y = position.y;
	}

	public Rect rect() {
		return new Rect(x, y, x + width, y + height);
	}

	public void draw(Canvas canvas) {
		Rect dst = rect();
		canvas.drawBitmap(icon, src, dst, null);
	}

	public void setRealSpriteWidth(int elWidth) {}

	public void press(GameManager gameManager) {}

	public int width() {
		return icon.getWidth();
	}

	public int height() {
		return icon.getHeight();
	}
}