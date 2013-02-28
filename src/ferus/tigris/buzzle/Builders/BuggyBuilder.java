package ferus.tigris.buzzle.Builders;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.personages.Buggy;
import ferus.tigris.buzzle.personages.Mark;
import ferus.tigris.buzzle.Builders.ImagesPool;
import ferus.tigris.buzzle.views.ComposeSprite;
import ferus.tigris.buzzle.views.LinearSprite;

public class BuggyBuilder extends EmptyMarkBuilder {

	protected Mark createPersonage(GameView view) {
		return new Buggy(null, view);
	}

	protected ComposeSprite createSprite(GameView view) {
		List<LinearSprite>sprites = new ArrayList<LinearSprite>();
		
		Bitmap img = ImagesPool.instance(view).getBug1();
		sprites.add(new LinearSprite(img, 4, 30, 1000));
		img = ImagesPool.instance(view).getBug2();
		sprites.add(new LinearSprite(img, 4, 30, 1000));
		
		ComposeSprite sprite = new ComposeSprite(sprites);
		return sprite;
	}
	
	protected boolean checkType(String type) {
		return type.contains("Bug");
	}

}
