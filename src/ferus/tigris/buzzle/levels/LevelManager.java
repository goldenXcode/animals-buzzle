package ferus.tigris.buzzle.levels;

import java.util.List;

import android.util.Log;

import ferus.tigris.buzzle.GameManager;
import ferus.tigris.buzzle.data.PersonageInfo;
import ferus.tigris.buzzle.personages.AbstractBehavior;
import ferus.tigris.buzzle.levels.AbstractLevel;
import ferus.tigris.buzzle.levels.StaticLevel;

public class LevelManager {
	private AbstractLevel level;
	GameManager gameManager;

	public LevelManager(GameManager gameManager) {
		level = new StaticLevel(this, gameManager, 100);
		this.gameManager = gameManager;
	}

	public void start() {
		level.start();
	}

	public void setLevel(AbstractLevel newLevel) {
		Log.d(getClass().toString(), "setLevel " + newLevel.getClass().toString());
		
		level = newLevel;
	}

	public void complite() {
		gameManager.playLevelCompliteSound();
		setLevel(level.nextLevel());
		level.start();
	}

	public void fail() {
		gameManager.playLevelFailSound();
		setLevel(level.clone());
		level.start();
	}

	public boolean check() {
		Log.d(getClass().toString(), "check");
		
		return level.check();
	}
	
	public void died(AbstractBehavior personage) {
		level.died(personage);
	}


	public double scopeScale() {
		return level.scopeScale();
	}

	public void load(String loadLevel, int scope, int complexity) {
		if(loadLevel.contains("StaticLevel")) {
			setLevel(new StaticLevel(this, gameManager, complexity));
		} else if(loadLevel.contains("LevelWithNullInit")) {
			setLevel(new LevelWithNullInit(this, gameManager, complexity));
		} else if(loadLevel.contains("LevelWithBlocksAndNull")) {
			setLevel(new LevelWithBlocksAndNull(this, gameManager, complexity));
		} else if(loadLevel.contains("LevelWithBlocks")) {
			setLevel(new LevelWithBlocks(this, gameManager, complexity));
		} else {
			setLevel(new StaticLevel(this, gameManager, complexity));
		}
		level.setScope(scope);
	}

	public String levelType() {
		if(level != null)return level.getClass().toString();
		return "";
	}

	public void fill(List<PersonageInfo> personages) {
		level.fill(personages);
		level.onLoad();
	}

	public List<PersonageInfo> personages() {
		return level.personages();
	}

	public int scope() {
		return level.scope();
	}

	public int complexity() {
		return level.complexity();
	}

	public void update() {
		level.update();
	}

}
