package ferus.tigris.buzzle.personages;

import ferus.tigris.buzzle.GameView;

public class Pig extends Mark {

	public Pig(BehaviorDelegate delegate, GameView gameView) {
		super(delegate, gameView, gameView.gameField());
	}

}
