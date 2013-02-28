package ferus.tigris.buzzle.personages;

import ferus.tigris.buzzle.GameView;

public class Mouse extends Mark {

	public Mouse(BehaviorDelegate delegate, GameView gameView) {
		super(delegate, gameView, gameView.gameField());
	}

}
