package ferus.tigris.buzzle.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class SimpleLinearSprite implements AbstractView{
	private int width;
	private int height;
	private Point position = new Point(0, 0);

	private Bitmap sprite;

	private int spriteColumnCount = 4;

	private int currentFrame = 0;
	
	private long lastTimeOfChangeFrame = 0;
	Paint paint = new Paint();
	
	private long delayBetweenFrames = 100;

	public SimpleLinearSprite(Bitmap bmp, int columnCount, long delayBetweenFrames) {
		init(bmp, delayBetweenFrames, 1, columnCount);
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		
	}

	protected void init(Bitmap bmp, long delayBetweenFrames, int rowCount, int columnCount) {
		sprite = bmp;
		spriteColumnCount = columnCount;
		this.delayBetweenFrames = delayBetweenFrames;
		
		width = sprite.getWidth() / spriteColumnCount;
		height = sprite.getHeight();
	}

	protected int selectAnimationFrame(int lastFrame) {
		int currentFrame = lastFrame;
		long time = System.currentTimeMillis();
		if(time - lastTimeOfChangeFrame > delayBetweenFrames) {

			currentFrame = ++currentFrame % spriteColumnCount;
			lastTimeOfChangeFrame = time;
		}
		return currentFrame;
	}

	private Rect spriteRect() {
		currentFrame = selectAnimationFrame(currentFrame);
		int srcX = currentFrame * width;
		return new Rect(srcX, 0, srcX + width, height);
	}

	private Rect animationRect() {
		int x = position.x - width/2;
		int y = position.y - height/2;
		return new Rect(x, y, x + width, y + height);
	}

	public void draw(Canvas canvas) {
		Rect src = spriteRect();
		Rect dst = animationRect();
		canvas.drawBitmap(sprite, src, dst, paint);
	}

	public void setPosition(Point position) {
		this.position.set(position.x, position.y);
	}

	public void setRealSpriteWidth(int px) {}
}
