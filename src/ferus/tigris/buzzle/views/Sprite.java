package ferus.tigris.buzzle.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import ferus.tigris.buzzle.views.AbstractView;

public class Sprite implements AbstractView{
	private int width;
	private int height;
	private Point position = new Point(0, 0);

	private Bitmap sprite;

	private int currentFrame = 0;
	private int spriteRowCount = 4;
	private int spriteColumnCount = 3;
	
	private long delay = 100;
	private int realWidth;
	private Paint paint = new Paint();

	public Sprite(Bitmap bmp, long delay, int rowCount, int columnCount) {
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);

		sprite = bmp;
		spriteRowCount = rowCount;
		spriteColumnCount = columnCount;
		this.delay = delay;
		
		width = sprite.getWidth() / spriteColumnCount;
		height = sprite.getHeight() / spriteRowCount;
	}

	private long lastTimeOfHitBall = 0;
	private void update() {
		long time = System.currentTimeMillis();
		if(time - lastTimeOfHitBall > delay) {
			currentFrame  = ++currentFrame % spriteColumnCount;
			lastTimeOfHitBall = time;
		}
	}

	private int getAnimationRow() {
		return 0;
	}

	private Rect spriteRect() {
		int srcX = currentFrame * width;
		int srcY = getAnimationRow() * height;
		return new Rect(srcX, srcY, srcX + width, srcY + height);
	}

	private Rect animationRect() {
		int x = position.x - realWidth/2;
		int y = position.y - realWidth/2;
		return new Rect(x, y, x + realWidth, y + realWidth);
	}

	public void draw(Canvas canvas) {
		update();
		Rect src = spriteRect();
		Rect dst = animationRect();
		canvas.drawBitmap(sprite, src, dst, paint);
	}

	public void setPosition(Point position) {
		this.position.set(position.x, position.y);
	}

	public void setRealSpriteWidth(int px) {
		Log.d(getClass().toString(), "set real width=" + px);
		realWidth = px;
	}
}
