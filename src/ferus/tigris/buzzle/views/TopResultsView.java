package ferus.tigris.buzzle.views;

import java.util.ArrayList;
import java.util.Collections;

import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.Builders.ImagesPool;
import ferus.tigris.buzzle.data.DB;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class TopResultsView implements AbstractView {
	private Bitmap background_image;
	private boolean show = false;
	private GameView view;
	private int x, y;
	private ArrayList<NumberView> resultsViews = new ArrayList<NumberView>();

	public TopResultsView(GameView view) {
		this.view = view;
		background_image = ImagesPool.instance(view).getBackgroundOfResultsForm();
	}

	public void draw(Canvas canvas) {
		if(show) {
			canvas.drawBitmap(background_image, x, y, null);
			int offset = 0;
			for(NumberView value: resultsViews) {
				offset += value.height();
				value.setPosition(new Point(x + view.getWidth()/2 - value.width()/2, y + offset));
				value.draw( canvas);
			}
		}
	}

	public void setPosition(Point position) {}

	public void setRealSpriteWidth(int elWidth) {}
	
	public void show(boolean visible) {
		view.loadResults();
		x = view.getWidth()/2 - background_image.getWidth()/2;
		y = view.getHeight()/2 - background_image.getHeight()/2;
		show = visible;
		if(!show) {
			resultsViews.clear();
		}
	}

	public void load(DB db) {
		synchronized (view.getHolder()) {
			onSaveLoad(db);
		}
	}

	protected void onSaveLoad(DB db) {
		resultsViews.clear();
		ArrayList<Integer> results = new ArrayList<Integer>();
		results = db.loadTopResults();
		results.add(view.scope());
		Collections.sort(results);
		Collections.reverse(results);
		for(int i = 0, offset = 0; true; i++) {
			Integer value = 0;
			if(results.size() > i) {
				value = results.get(i);
			}
			Log.d(getClass().toString(), "add scope=" + value);
			NumberView el = new StaticNumberView(view, value);
			offset += el.height();
			resultsViews.add(el);
			if(offset + 2*el.height() >= background_image.getHeight())break;
		}
	}

	public boolean isVisible() {
		return show;
	}

	public boolean isCollition(int x, int y) {
		Rect r = new Rect(x, y, background_image.getWidth(), background_image.getHeight());
		return r.contains(x, y)&&show;
	}
}