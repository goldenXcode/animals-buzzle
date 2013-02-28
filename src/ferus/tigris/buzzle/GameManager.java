package ferus.tigris.buzzle;

import android.graphics.Point;
import android.util.Log;
import ferus.tigris.buzzle.data.DB;
import ferus.tigris.buzzle.levels.LevelManager;
import ferus.tigris.buzzle.personages.AbstractBehavior;


public class GameManager extends TouchesManager {
	private static final int LOVES_COUNT = 3;
	
	private Matrix marks = new Matrix();
	LevelManager levels = new LevelManager(this);
	private int scope = 0;
	private int loves = LOVES_COUNT;
	private boolean loaded = false;
	
	public GameManager(GameView gameView) {
		super(gameView);
	}
	
	public void setPersonages(Matrix marks) {
		Log.d(getClass().toString(), "set personages");
		this.marks = marks;
		view().setSprites(marks);
	}

	public void update() throws InterruptedException {
		long ticksPS = 20;
		long startTime = System.currentTimeMillis();

		if(loaded) {
			synchronized (view().getHolder()) {
				for(AbstractBehavior m: marks.getMarks()) {
					m.update();
				}
				removeDiedPersonages();	
				levels.update();
			}
		}
		long sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
		sleep(sleepTime < 10 ? 10 : sleepTime);
	}

	private void removeDiedPersonages() {
		boolean check = false;
		for(AbstractBehavior mark: marks.getMarks()) {
			if(mark.isDied()) {
				levels.died(mark);
				check = true;
			}
		}
		if(check) {
			levels.check();
		}
	}

	public int scope() {
		return scope ;
	}

	protected void onTouchMove(float x, float y, Direction directionMove) {
		if(!isAvailable())return;

		synchronized (view().getHolder()) {
			for(AbstractBehavior b: marks.getMarks()) {
				directionMove.touchTo(b, new Point((int)x, (int)y));
			}
		}
	}

	protected boolean isAvailable() {
		for(AbstractBehavior mark: marks.getMarks()) {
			if(!mark.isAvailable()) {
				return false;
			}
		}
		return true;
	}

	protected void onTouchEnd(float x, float y, Direction directionMove) {
		if(!isAvailable())return;
		Matrix prevState = marks.copyMatrix();

		synchronized (view().getHolder()) {
			for(AbstractBehavior b: marks.getMarks()) {
				directionMove.onMoveFinish(b, new Point((int)x, (int)y));
			}
			if(!levels.check()) {
				marks.restoreFrom(prevState);
			}
		}
	}

	public double levelScope() {
		return levels.scopeScale();
	}

	protected void load(DB db) {
		synchronized (view().getHolder()) {
			onLoad(db);
		}
	}

	protected void onLoad(DB db) {
		if(db.isSave()) {
			long level_id = db.loadLastLevelId();
			levels.load(db.loadLevel(level_id), db.loadLevelScope(level_id), db.loadLevelComplexity(level_id));
			levels.fill(db.loadPersonages(level_id));
			scope = db.loadScope(level_id);
			loves = db.loadLoves(level_id);
		} else {
			levels.start();
		}
		loaded  = true;
	}

	protected void save(DB db) {
		synchronized (view().getHolder()) {
			onSave(db);
		}
	}

	protected void onSave(DB db) {
		long level_id = db.saveLevel(levels.levelType(), levels.scope(), levels.complexity());
		db.savePersonages(level_id, levels.personages());
		db.saveScope(level_id, scope);
		db.saveLoves(level_id, loves);
	}

	public void playLevelCompliteSound() {
		view().sounds().playLevelComplite();
	}

	public void playLevelFailSound() {
		view().sounds().playLevelFail();
	}

	public void playOnAddEmptyMark() {
		view().sounds().playSneezing();
	}

	public void playOnAddBlock() {
		view().sounds().playBlock();
	}

	public void playOnKillSound() {
		view().sounds().playTouch();
	}

	public void restartGame() {
		view().save();
		synchronized (view().getHolder()) {
			onRestartGame();
		}
		playLevelFailSound();
	}

	private void onRestartGame() {
		levels = new LevelManager(this);
		levels.start();
		scope = 0;
		loves = LOVES_COUNT;
	}

	public boolean isLovesFinish() {
		return loves <= 0;
	}

	public void restartLevel() {
		synchronized (view().getHolder()) {
			onRestartLevel();
		}
		playOnAddBlock();
	}

	private void onRestartLevel() {
		levels.start();
		loves--;
	}

	public void addPoint(int i) {
		scope += i;
	}

}
