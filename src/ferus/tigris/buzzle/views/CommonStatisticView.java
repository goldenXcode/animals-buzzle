package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameManager;
import ferus.tigris.buzzle.Builders.ImagesPool;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;

public class CommonStatisticView implements AbstractView {
	private GameManager gameManager;
	Bitmap progress;
	Bitmap progress_back;
	
	public CommonStatisticView(GameManager gameManager) {
		this.gameManager = gameManager;
		
		progress = ImagesPool.instance(gameManager.view()).getCloud();
		progress_back = ImagesPool.instance(gameManager.view()).getTransporentCloud();
	}
	
	public void draw(Canvas canvas) {
		int width = gameManager.view().getWidth();
		int x = 0;
		while(x + progress_back.getWidth() < width) {
			canvas.drawBitmap(progress_back, x, 0, null);
			x += progress_back.getWidth();
		}
		
		x = 0;
		while(x  < width*getScaleFactor()) {
			canvas.drawBitmap(progress, x, 0, null);
			x += progress.getWidth();
		}
		
		
	}

	protected double getScaleFactor() {
		return gameManager.levelScope();
	}

	public void setPosition(Point position) {}

	public void setRealSpriteWidth(int px) {}

}
