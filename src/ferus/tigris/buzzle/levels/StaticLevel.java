package ferus.tigris.buzzle.levels;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Point;
import android.util.Log;

import ferus.tigris.buzzle.Builders.AbstractBehaviorBuilder;
import ferus.tigris.buzzle.GameManager;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.data.PersonageInfo;
import ferus.tigris.buzzle.personages.AbstractBehavior;
import ferus.tigris.buzzle.personages.FriendsFinder;

public class StaticLevel implements AbstractLevel {
	protected static final int GROUP_COUNT = 3;
	private GameManager gameManager;
	private LevelManager levelManager;
	Matrix marks = new Matrix();
	private float scope = 0;
	private int complexity = 100;
	private long lastCheckTime = 0;

	public StaticLevel(LevelManager levelManager, GameManager gameManager, int complexity) {
		this.complexity = complexity;
		this.gameManager = gameManager;
		this.levelManager = levelManager;
	}

	public AbstractLevel nextLevel() {
		return new LevelWithNullInit(levelManager, gameManager, complexity);
	}

	public AbstractLevel clone() {
		return new StaticLevel(levelManager, gameManager, complexity);
	}

	public void start() {
		scope = 0;
		fillByRandom();
		checkForCollisions();
		gameManager.setPersonages(marks);
	}

	protected void fillByRandom() {
		for(int x = 0; x < marks.columns(); x++) {
			for(int y = 0; y < marks.rows(); y++) {
				insertRandomMark(x, y);
			}
		}
	}

	protected void insertRandomMark(int x, int y) {
		AbstractBehaviorBuilder builder = new AbstractBehaviorBuilder();
		AbstractBehavior mark = builder.createRandomMark(gameManager.view());
		marks.insertMark(x, y, mark);
	}

	protected void checkForCollisions() {
		boolean hasGroups;
		do {
			hasGroups = false;
			for(int x = 0; x < marks.columns(); x++) {
				for(int y = 0; y < marks.rows(); y++) {
					List<AbstractBehavior> friends = getFriends(x, y);
					if(isGroup(friends)) {
						insertRandomMark(x, y);
						hasGroups = true;
					}
				}
			}
		} while(hasGroups);
	}

	protected List<AbstractBehavior> getFriends(int x, int y) {
		FriendsFinder finder = new FriendsFinder();
		List<AbstractBehavior>friends = finder.getFriends(marks, new Point(x, y));
		return friends;
	}

	public boolean isGroup(List<AbstractBehavior>friends) {
		return friends.size() >= GROUP_COUNT;
	}

	public boolean check() {
		lastCheckTime = System.currentTimeMillis();
		
		if(isLevelComplited()) {
			levelManager.complite();
			return false;
		}
		return findGroups();
	}

	private boolean findGroups() {
		boolean result = false;
		for(int x = 0; x < marks.columns(); x++) {
			for(int y = 0; y < marks.rows(); y++) {
				if(marks.getMark(x, y).isDied())return false;
				if(!marks.getMark(x, y).isAvailable())return false;
			}
		}
		for(int x = 0; x < marks.columns(); x++) {
			for(int y = 0; y < marks.rows(); y++) {
				if(marks.getMark(x, y).isDied())continue;
				if(!marks.getMark(x, y).isAvailable())break;
				List<AbstractBehavior> friends = getFriends(x, y);
				if(isGroup(friends)) {
					result = true;
					for(AbstractBehavior b: friends) {
						if(b.isAvailable()) {
							b.kill();
						}
					}
					gameManager.playOnKillSound();
					gameManager.addPoint(friends.size() - GROUP_COUNT + 1);
				}
			}
		}
		return result;
	}

	private boolean isLevelComplited() {
		return scopeScale() >= 1;
	}

	public void died(AbstractBehavior personage) {
		for(int x = 0; x < marks.columns(); x++) {
			if(marks.getMark(x, 0).isDied()) {
				AbstractBehaviorBuilder builder = new AbstractBehaviorBuilder();
				AbstractBehavior mark = builder.createRandomMark(gameManager.view());
				mark.saveAt(marks, new Point(x, -1));
				marks.insertMark(x, 0, mark);
				scope++;
				onDead();
			}
		}
	}

	protected void onDead() {
	}

	public double scopeScale() {
		return scope/complexity ;
	}

	public void fill(List<PersonageInfo> personages) {
		AbstractBehaviorBuilder builder = new AbstractBehaviorBuilder();
		for(PersonageInfo info: personages) {
			AbstractBehavior mark = builder.createByName(gameManager.view(), info.name);
			Log.d(getClass().toString(), "created " + mark.getType());
			marks.insertMark(info.x, info.y, mark);
		}
		gameManager.setPersonages(marks);
		checkForCollisions();
	}

	public List<PersonageInfo> personages() {
		List<PersonageInfo> res = new ArrayList<PersonageInfo>();

		for(int x = 0; x < marks.columns(); x++) {
			for(int y = 0; y < marks.rows(); y++) {
				res.add(createPersonageInfo(x, y));
			}
		}

		return res;
	}

	protected PersonageInfo createPersonageInfo(int x, int y) {
		AbstractBehavior mark = marks.getMark(x, y);
		PersonageInfo info = new PersonageInfo();
		info.name = mark.getType();
		info.x = x;
		info.y = y;
		return info;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public int scope() {
		return (int)scope;
	}

	public int complexity() {
		return (int)complexity;
	}

	public void onLoad() {}

	protected Matrix marks() {
		return marks;
	}

	public void update() {
		long t = System.currentTimeMillis();
		if(t - lastCheckTime  > 300) {
			check();
		}
	}

}
