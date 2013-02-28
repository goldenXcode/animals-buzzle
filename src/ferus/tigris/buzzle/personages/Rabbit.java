package ferus.tigris.buzzle.personages;

import ferus.tigris.buzzle.GameView;

public class Rabbit extends Mark {

	public Rabbit(BehaviorDelegate delegate, GameView gameView) {
		super(delegate, gameView, gameView.gameField());
	}

}
