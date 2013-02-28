package ferus.tigris.buzzle.personages;

import ferus.tigris.buzzle.GameView;

public class Buggy extends Mark {

	public Buggy(BehaviorDelegate delegate, GameView gameView) {
		super(delegate, gameView, gameView.gameField());
	}

}
