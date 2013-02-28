package ferus.tigris.buzzle.levels;

import java.util.Random;

import ferus.tigris.buzzle.GameManager;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.Builders.AbstractBehaviorBuilder;
import ferus.tigris.buzzle.Builders.EmptyMarkBuilder;
import ferus.tigris.buzzle.Builders.ImagesPool;
import ferus.tigris.buzzle.personages.AbstractBehavior;
import ferus.tigris.buzzle.personages.BehaviorDelegate;
import ferus.tigris.buzzle.personages.BlockBehavior;
import ferus.tigris.buzzle.personages.SlowViewBehavior;
import ferus.tigris.buzzle.personages.ViewsManager;
import ferus.tigris.buzzle.views.Sprite;

public class LevelWithBlocksAndNull extends StaticLevel {
	private GameManager gameManager;
	private LevelManager levelManager;
	private int maxScope = 100;
	private long startLevelTime = System.currentTimeMillis();
	private long timeoutForNullCreate = 10*1000;
	private int timeScale = 2;

	public LevelWithBlocksAndNull(LevelManager levelManager, GameManager gameManager, int maxScope) {
		super(levelManager, gameManager, maxScope);
		timeScale  = maxScope / 50;
		timeoutForNullCreate /= timeScale;
		this.gameManager = gameManager;
		this.levelManager = levelManager;
	}

	public AbstractLevel nextLevel() {
		return new LevelWithNullInit(levelManager, gameManager, maxScope + 50);
	}

	public AbstractLevel clone() {
		return new LevelWithBlocksAndNull(levelManager, gameManager, maxScope);
	}

	public void update() {
		super.update();
		long t = System.currentTimeMillis();
		if((t - startLevelTime > timeoutForNullCreate )) {
			Random rnd = new Random();
			if(rnd.nextInt(12) % 2 == 0) {
				createBlockMark();
			} else {
				createNullMark();
			}
			timeoutForNullCreate = 60*1000 / timeScale;
			if(timeoutForNullCreate < 2*1000) {
				timeoutForNullCreate = 2*1000;
			}
			startLevelTime = t;
		}
	}

	protected void createBlockMark() {
		Matrix m = marks();
		Random rnd = new Random();
		int x = rnd.nextInt(m.columns());
		int y = rnd.nextInt(m.rows());
		while(!blockMark(x, y));
	}

	protected boolean blockMark(int x, int y) {
		Matrix m = marks();
		BehaviorDelegate mark = (BehaviorDelegate)m.getMark(x, y);
		if(mark.isDied())return false;

		Sprite sprite = new Sprite(ImagesPool.instance(gameManager.view()).getCage(), 1000, 1, 1);
		ViewsManager viewManager = new SlowViewBehavior(mark, sprite, gameManager.view().gameField()); 
		BlockBehavior b = new BlockBehavior(viewManager, gameManager.view().gameField());
		
		m.insertMark(x, y, b);
		gameManager.playOnAddBlock();
		return true;
	}

	protected void createNullMark() {
		Matrix m = marks();
		Random rnd = new Random();
		int x = rnd.nextInt(m.columns());
		int y = rnd.nextInt(m.rows());
		AddNullMark(x, y);
	}

	protected void AddNullMark(int x, int y) {
		gameManager.playOnAddEmptyMark();
		AbstractBehaviorBuilder builder = new EmptyMarkBuilder();
		Matrix m = marks();
		AbstractBehavior mark = builder.create(gameManager.view());
		m.insertMark(x, y, mark);
	}

	protected void onDead() {
		if(timeoutForNullCreate < 5*1000) {
			startLevelTime = System.currentTimeMillis();
		}
	}

}
