package ferus.tigris.buzzle;

import android.util.Log;

public class SafeGameManager extends Thread {

	private boolean running = false;
	private GameView view;

	public SafeGameManager(GameView gameView) {
		super();
		view = gameView;
	}

	public GameView view() {
		return view;
	}

	public void setRunning(boolean run) {
		running  = run;
	}

	public void run() {
		Log.d(getClass().toString(), "start loop");

		while (running) {
			try {
				update();
			} catch (InterruptedException e) {
				Log.e(getClass().toString(), "sleep error");
			} catch(java.lang.IndexOutOfBoundsException e) {
				Log.e(getClass().toString(), "IndexOutOfBoundsException error");
			}
		}
	}

	protected void update() throws InterruptedException {
		throw new InterruptedException();
	}

	protected void onTouchMove(float x, float y, Direction directionMove) {}

	protected void onTouchEnd(float x, float y, Direction directionMove) {}

}