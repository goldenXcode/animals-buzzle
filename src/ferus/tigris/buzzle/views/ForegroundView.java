package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.Builders.ImagesPool;
import ferus.tigris.buzzle.views.SimpleLinearSprite;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class ForegroundView implements AbstractView {
	Bitmap image = null;
	Rect srcRect;
	Rect dstRect;

	private Bitmap backgroundImage;
	private Rect foregroundTopRect;
	private Rect foregroundBottomRect;
	private Rect foregroundTopSrcRect;
	private Rect foregroundBottomSrcRect;
	private SimpleLinearSprite sun;
	private GameView gameView;
	Paint paint = new Paint();

	public ForegroundView(GameView gameView) {
		this.gameView = gameView;
	}

	protected void init(GameView gameView) {
		image = ImagesPool.instance(gameView).getForeground();
		int w = gameView.getWidth() > image.getWidth() ? gameView.getWidth() : image.getWidth();
		
		srcRect = new Rect(0, 0, image.getWidth(), image.getHeight());
		dstRect = new Rect(gameView.getWidth() - w, gameView.getHeight() - image.getHeight(), gameView.getWidth(), gameView.getHeight());

		backgroundImage = ImagesPool.instance(gameView).getBackground();

		int gameWidth = gameView.getWidth();
		int gameHeight = gameView.getHeight();
		Rect field = gameView.gameField();
		float topPartScaleFactor = (float)field.top/gameHeight;
		float bottomPartScaleFactor = (float)(gameHeight - field.bottom)/gameHeight;
		
		foregroundTopRect = new Rect(0, 0, gameWidth, field.top);
		foregroundTopSrcRect = new Rect(0, 0, backgroundImage.getWidth(), (int)(backgroundImage.getHeight()*topPartScaleFactor));
		
		foregroundBottomRect = new Rect(0, field.bottom, gameWidth, gameHeight);
		foregroundBottomSrcRect = new Rect(0, (int)(backgroundImage.getHeight() - backgroundImage.getHeight()*bottomPartScaleFactor), backgroundImage.getWidth(), backgroundImage.getHeight());
	
		Bitmap sunImg = ImagesPool.instance(gameView).getSun();
		sun = new SimpleLinearSprite(sunImg, 13, 100);
		sun.setPosition(new Point(gameWidth, 0));
	}

	protected int foregroundImageHeight() {
		return image.getHeight()/8;
	}

	protected int gameFieldHeight(GameView gameView) {
		return gameView.getHeight()/2 + gameView.getWidth()*2/3;
	}

	public void draw(Canvas canvas) {
		if(image == null) {
			init(gameView);
			paint.setAntiAlias(true);
			paint.setFilterBitmap(true);
			paint.setDither(true);
		}
		canvas.drawBitmap(backgroundImage, foregroundTopSrcRect, foregroundTopRect, paint);
		canvas.drawBitmap(backgroundImage, foregroundBottomSrcRect, foregroundBottomRect, paint);
		
		canvas.drawBitmap(image, srcRect, dstRect, paint);
		sun.draw(canvas);
	}

	public void setPosition(Point position) {}

	public void setRealSpriteWidth(int px) {}
}