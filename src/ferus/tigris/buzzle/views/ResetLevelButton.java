package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameManager;
import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.R;
import android.graphics.BitmapFactory;

public class ResetLevelButton extends ButtonView{
	GameView view;

	public ResetLevelButton(GameView view) {
		super(BitmapFactory.decodeResource(view.getResources(), R.drawable.restart));
		this.view = view;
	}

	public void press(GameManager gameManager) {
		gameManager.restartLevel();
		if(gameManager.isLovesFinish()) {
			view.setResetButton(new StopGameButton(view));
		}
	}

}
