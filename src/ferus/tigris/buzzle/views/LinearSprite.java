package ferus.tigris.buzzle.views;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class LinearSprite implements AbstractView{
	private int width;
	private int height;
	private int realWidth;
	private Point position = new Point(0, 0);

	private Bitmap sprite;

	private int spriteColumnCount = 4;

	private int currentFrame = 0;
	
	private int animationDirection = 1;
	private long lastTimeOfChangeFrame = 0;
	
	private long delayBetweenFrames = 100;
	private long delayAtLastFrame = 400;

	public LinearSprite(Bitmap bmp, int columnCount, long delayBetweenFrames, int delayAtLastFrame) {
		init(bmp, delayBetweenFrames, 1, columnCount);
		this.delayAtLastFrame = delayAtLastFrame; 
		
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

			currentFrame += animationDirection;
			if(currentFrame >= spriteColumnCount) {
				animationDirection = -1;
				time += delayAtLastFrame;
			}

			lastTimeOfChangeFrame = time;
		}
		if(currentFrame >= spriteColumnCount)currentFrame = spriteColumnCount - 1;
		if(currentFrame < 0)currentFrame = 0;
		return currentFrame;
	}

	public void resetAnimation() {
		animationDirection = 1;
		currentFrame = 0;
	}

	private Rect spriteRect() {
		currentFrame = selectAnimationFrame(currentFrame);
		int srcX = currentFrame * width;
		return new Rect(srcX, 0, srcX + width, height);
	}

	private Rect animationRect() {
		int x = position.x - realWidth/2;
		int y = position.y - realWidth/2;
		return new Rect(x, y, x + realWidth, y + realWidth);
	}

	public void draw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		Rect src = spriteRect();
		Rect dst = animationRect();
		canvas.drawBitmap(sprite, src, dst, paint);
	}

	public void setPosition(Point position) {
		this.position.set(position.x, position.y);
	}

	public void setRealSpriteWidth(int px) {
		realWidth = px;
	}
}
