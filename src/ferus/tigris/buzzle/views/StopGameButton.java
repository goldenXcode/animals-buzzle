package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameManager;
import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.R;
import android.graphics.BitmapFactory;

public class StopGameButton extends ButtonView{
	GameView view;

	public StopGameButton(GameView view) {
		super(BitmapFactory.decodeResource(view.getResources(), R.drawable.shutdown));
		this.view = view;
	}

	public void press(GameManager gameManager) {
		gameManager.restartGame();
		if(!gameManager.isLovesFinish()) {
			view.setResetButton(new ResetLevelButton(view));
		}
	}

}
