package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.Builders.ImagesPool;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;

public class BackgroundView implements AbstractView {
	Bitmap image = null;
	Rect srcRect;
	Rect dstRect;
	private GameView gameView;

	public BackgroundView(GameView gameView) {
		this.gameView = gameView;
		image = ImagesPool.instance(gameView).getBackground();
		srcRect = new Rect(0, 0, image.getWidth(), image.getHeight());
	}

	public void draw(Canvas canvas) {
		dstRect = new Rect(0, 0, gameView.getWidth(), gameView.getHeight());
		canvas.drawBitmap(image, srcRect, dstRect, null);
	}

	public void setPosition(Point position) {
	}

	public void setRealSpriteWidth(int px) {
	}
}