package ferus.tigris.buzzle.levels;

import java.util.Random;

import ferus.tigris.buzzle.GameManager;
import ferus.tigris.buzzle.Matrix;
import ferus.tigris.buzzle.Builders.ImagesPool;
import ferus.tigris.buzzle.personages.BehaviorDelegate;
import ferus.tigris.buzzle.personages.BlockBehavior;
import ferus.tigris.buzzle.personages.SlowViewBehavior;
import ferus.tigris.buzzle.personages.ViewsManager;
import ferus.tigris.buzzle.views.Sprite;

public class LevelWithBlocks extends StaticLevel {
	private GameManager gameManager;
	private LevelManager levelManager;
	private int maxScope = 100;
	private long startLevelTime = System.currentTimeMillis();
	private long timeoutForNullCreate = 10*1000;
	private int timeScale = 2;

	public LevelWithBlocks(LevelManager levelManager, GameManager gameManager, int maxScope) {
		super(levelManager, gameManager, maxScope);
		timeScale  = maxScope / 50;
		timeoutForNullCreate /= timeScale;
		this.gameManager = gameManager;
		this.levelManager = levelManager;
	}

	public AbstractLevel nextLevel() {
		return new LevelWithBlocksAndNull(levelManager, gameManager, maxScope);
	}

	public AbstractLevel clone() {
		return new LevelWithBlocks(levelManager, gameManager, maxScope);
	}

	public void update() {
		super.update();
		long t = System.currentTimeMillis();
		if((t - startLevelTime > timeoutForNullCreate )) {
			createBlockMark();
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

	protected void onDead() {
		if(timeoutForNullCreate < 5*1000) {
			startLevelTime = System.currentTimeMillis();
		}
	}

}
