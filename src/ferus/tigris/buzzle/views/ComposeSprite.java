package ferus.tigris.buzzle.views;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Point;

public class ComposeSprite implements AbstractView {
	private static final int MIN_DELAY_BETWEEN_ANIMATIONS = 4000;
	private List<LinearSprite> views = new ArrayList<LinearSprite>();
	private int currentRow = 0;
	private long lastTimeOfChangeFrame = 0;
	private long delayBetweenAnimations = MIN_DELAY_BETWEEN_ANIMATIONS;

	public ComposeSprite(List<LinearSprite> views) {
		this.views = views;
	}

	private boolean selectAnimation() {
		long time = System.currentTimeMillis();
		if(time - lastTimeOfChangeFrame  > delayBetweenAnimations ) {
			lastTimeOfChangeFrame = time;
			Random rnd = new Random();
			currentRow  = rnd.nextInt(views.size());
			delayBetweenAnimations = MIN_DELAY_BETWEEN_ANIMATIONS + rnd.nextInt(MIN_DELAY_BETWEEN_ANIMATIONS*4);
			return true;
		}
		return false;
	}

	public void draw(Canvas canvas) {
		boolean animate = selectAnimation();
		LinearSprite view = views.get(currentRow);
		
		if(animate)view.resetAnimation();
		view.draw(canvas); 
	}

	public void setPosition(Point position) {
		for(LinearSprite view: views) {
			view.setPosition(position);
		}
	}

	public void setRealSpriteWidth(int px) {
		for(LinearSprite view: views) {
			view.setRealSpriteWidth(px);
		}
	}

}
