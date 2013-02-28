package ferus.tigris.buzzle.personages;

import ferus.tigris.buzzle.GameView;

public class Cloud extends Mark {

	static int counter = 0;
	public Cloud(BehaviorDelegate delegate, GameView gameView) {
		super(delegate, gameView, gameView.gameField());
	}

	public String getType() {
		return getClass().toString() + String.valueOf(counter++);
	}

}
