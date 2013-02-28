package ferus.tigris.buzzle;

import ferus.tigris.buzzle.personages.AbstractBehavior;

import ferus.tigris.buzzle.data.Config;
import ferus.tigris.buzzle.data.DB;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import ferus.tigris.buzzle.views.ButtonView;
import ferus.tigris.buzzle.views.ResetLevelButton;
import ferus.tigris.buzzle.views.BackgroundView;
import ferus.tigris.buzzle.views.StopGameButton;

@SuppressLint("ShowToast")
public class GameView extends SurfaceView implements OnTouchListener {
	final private GameDrawManager gameDrawManager = new GameDrawManager(this);
	final private GameManager gameManager = new GameManager(this);
	
	private Matrix sprites = new Matrix(0, 0);
	private BackgroundView background = new BackgroundView(this);
	private ControlsViews controls;
	private SoundManager sounds;
	private GameFieldActivity gameFieldActivity;
	
	public GameView(Context context) {
		super(context);
		gameFieldActivity = (GameFieldActivity)context;
		setOnTouchListener(this);
		getHolder().addCallback(new SurfaceHolder.Callback() {
			public void surfaceDestroyed(SurfaceHolder holder) {}
			public void surfaceCreated(SurfaceHolder holder) {onSufaceCreate();}
			public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
		});
		sounds = new SoundManager(context);
		controls = new ControlsViews(gameManager, sounds);
	}

	private void onSufaceCreate() {
		
		if (gameDrawManager.getState() == Thread.State.NEW) {
			gameDrawManager.setRunning(true);
			gameDrawManager.start();
		}
		if (gameManager.getState() == Thread.State.NEW) {
			gameManager.setRunning(true);
			gameManager.start();
		}
		gameFieldActivity.loadFromDB();
		controls.resetButton = gameManager.isLovesFinish() ? new StopGameButton(this) : new ResetLevelButton(this);
	}
	
	private void waitThread(Thread th) {
		boolean retry = true;
		while (retry) {
			try {
				th.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}
	
	protected void onDraw(Canvas canvas) {
		controls.layout(getWidth(), getHeight());
		background.draw(canvas);
		for(AbstractBehavior sprite: sprites.getMarks()) {
			sprite.draw(canvas);
		}
		controls.draw(canvas);
	}

	public boolean onTouch(View v, MotionEvent event) {
		if (!controls.onTouch(v, event)) return false;
		int act = event.getAction();
		if (act == MotionEvent.ACTION_DOWN) {
			gameManager.onTouchBegin(event.getX(), event.getY());
		} else
		if (act == MotionEvent.ACTION_MOVE) {
				gameManager.onTouchMove(event.getX(), event.getY());
		} else
		if (act == MotionEvent.ACTION_UP) {
			gameManager.onTouchEnd(event.getX(), event.getY());
		}
		return true;
	}

	public void setSprites(Matrix marks) {
		this.sprites = marks;
	}

	public Rect gameField() {
		int y = 2*getWidth()/10;
		Rect screen = new Rect(0, y, getWidth(), y + getWidth());
		return screen;
	}

	public SoundManager sounds() {
		return sounds;
	}

	public void load(DB db, Config conf) {
		gameManager.load(db);
		sounds.setEnabled(conf.isSoundEnable());
	}

	public void save(DB db, Config conf) {
		conf.setSoundEnabled(sounds.enable());
		gameManager.save(db);
		loadResults();
	}

	public void stop() {
		gameDrawManager.setRunning(false);
		gameManager.setRunning(false);
		waitThread(gameDrawManager);
		waitThread(gameManager);
	}

	public void setResetButton(ButtonView b) {
		controls.resetButton = b;
	}

	public void loadResults() {
		gameFieldActivity.loadTopResults();
	}

	public void save() {
		gameFieldActivity.saveToDB();
	}

	public void showTopTable() {
		controls.topResults.show(!controls.topResults.isVisible());
	}

	public Integer scope() {
		Log.d(getClass().toString(), "scope=" + gameManager.scope());
		return gameManager.scope();
	}

	public void loadResults(DB db) {
		controls.topResults.load(db);
	}
}