package ferus.tigris.buzzle;

import android.graphics.Point;

public class TouchesManager extends SafeGameManager {
	protected Point touchFirstPosition;
	protected Direction directionMove = null;

	public TouchesManager(GameView gameView) {
		super(gameView);
	}

	public void onTouchBegin(float x, float y) {
		touchFirstPosition = new Point((int)x, (int)y);
	}

	public void onTouchMove(float x, float y) {
		if(directionMove == null) {
			if(Math.max(Math.abs(x - touchFirstPosition.x), Math.abs(y - touchFirstPosition.y)) < 10) {
				return;
			}
			if(Math.abs(x - touchFirstPosition.x) < Math.abs(y - touchFirstPosition.y)) {
				directionMove = new VerticalDirection(touchFirstPosition);
			} else {
				directionMove = new HorizontalDirection(touchFirstPosition);
			}
		}
		onTouchMove(x, y, directionMove);
	}

	public void onTouchEnd(float x, float y) {
		if(directionMove == null) {
			return;
		}

		onTouchEnd(x, y, directionMove);

		directionMove = null;
	}

}