package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.Builders.ImagesPool;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class NumberView implements AbstractView {

	private Bitmap digitSprite;
	private float digitWidth = 0;
	int x = 0;
	int y = 0;

	public NumberView(GameView view) {
		digitSprite = ImagesPool.instance(view).getDigits();
		digitWidth  = digitSprite.getWidth()/10;
	}
	public void draw(Canvas canvas) {}

	public void setPosition(Point position) {
		x = position.x;
		y = position.y;
	}

	public void setRealSpriteWidth(int elWidth) {}

	public void draw(int i, Canvas canvas) {
		
		int d = i, count = 0;
		while(d > 0) {
			d /= 10;
			count++;
		}
		
		if(i == 0)count++;
		
		d = i;
		while(count-- > 0) {
			int digit = d % 10;
			d /= 10;

			Rect srcRect = new Rect((int) (digit*digitWidth), 0, (int) (digit*digitWidth + digitWidth), digitSprite.getHeight());
			Rect dstRect = new Rect((int) (count * digitWidth) + x, y, (int) (count * digitWidth + digitWidth) + x, digitSprite.getHeight() + y);

			canvas.drawBitmap(digitSprite, srcRect, dstRect, null);
			if(d == 0)break;
		}
	}
	public int height() {
		return digitSprite.getHeight();
	}
	public int width() {
		return (int)digitWidth;
	}

}
