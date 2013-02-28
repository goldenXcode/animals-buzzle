package ferus.tigris.buzzle.Builders;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.personages.AbstractBehavior;
import ferus.tigris.buzzle.personages.AdditionViewBehavior;
import ferus.tigris.buzzle.personages.Cloud;
import ferus.tigris.buzzle.personages.KillerBehavior;
import ferus.tigris.buzzle.personages.KillerWithEffectBehavior;
import ferus.tigris.buzzle.personages.Mark;
import ferus.tigris.buzzle.personages.SlowViewBehavior;
import ferus.tigris.buzzle.personages.ViewsManager;
import ferus.tigris.buzzle.Builders.ImagesPool;
import ferus.tigris.buzzle.views.ComposeSprite;
import ferus.tigris.buzzle.views.LinearSprite;

public class EmptyMarkBuilder extends AbstractBehaviorBuilder {

	public AbstractBehavior create(GameView view) {
		Mark behavior = createPersonage(view);
		ViewsManager viewManager = new SlowViewBehavior(behavior, createSprite(view), view.gameField()); 
		KillerBehavior killer = new KillerWithEffectBehavior(viewManager); 

		AdditionViewBehavior additionView = new AdditionViewBehavior(killer, createSprite(view), view.gameField());
		return additionView;
	}

	protected Mark createPersonage(GameView view) {
		return new Cloud(null, view);
	}

	protected ComposeSprite createSprite(GameView view) {
		List<LinearSprite>sprites = new ArrayList<LinearSprite>();
		
		Bitmap img = ImagesPool.instance(view).getEmpty();
		sprites.add(new LinearSprite(img, 1, 1130, 0));
		
		ComposeSprite sprite = new ComposeSprite(sprites);
		return sprite;
	}

	
	protected boolean checkType(String type) {
		return type.contains("Cloud");
	}
}
