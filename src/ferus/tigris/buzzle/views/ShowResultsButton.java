package ferus.tigris.buzzle.views;

import ferus.tigris.buzzle.GameManager;
import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.R;
import android.graphics.BitmapFactory;

public class ShowResultsButton extends ButtonView{
	GameView view;

	public ShowResultsButton(GameView view) {
		super(BitmapFactory.decodeResource(view.getResources(), R.drawable.top));
		this.view = view;
	}

	public void press(GameManager gameManager) {
		gameManager.view().showTopTable();
	}

}
