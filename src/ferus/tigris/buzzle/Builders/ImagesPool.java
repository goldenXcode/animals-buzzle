package ferus.tigris.buzzle.Builders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import ferus.tigris.buzzle.GameView;
import ferus.tigris.buzzle.R;

public class ImagesPool {
	public Bitmap bug1, bug2;
	public Bitmap cat1, cat2, cat3;
	public Bitmap mouse1;
	public Bitmap bird1;
	private Bitmap frog1, frog2;
	private Bitmap rabbit1, rabbit2, rabbit3;
	private Bitmap pig1, pig2;
	private Bitmap sky;
	private Bitmap grass;
	private Bitmap sun;
	private Bitmap cage;
	private Bitmap cloud, transporentCloud;
	private Bitmap bear;
	private Bitmap progressBarBackground, progressBar, digits;
	private Bitmap backgroundOfResultsForm;
	
	private static ImagesPool pool = null;

	protected ImagesPool(GameView view) {
		bug1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bug1);
		bug2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bug2);
		cat1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.cat1);
		cat2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.cat2);
		cat3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.cat3);
		mouse1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.mouse1);
		bird1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.bird1);
		frog1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.frog1);
		frog2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.frog2);
		rabbit1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.rabbit1);
		rabbit2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.rabbit2);
		rabbit3 = BitmapFactory.decodeResource(view.getResources(), R.drawable.rabbit3);
		sky = BitmapFactory.decodeResource(view.getResources(), R.drawable.sky);
		grass = BitmapFactory.decodeResource(view.getResources(), R.drawable.grass);
		sun = BitmapFactory.decodeResource(view.getResources(), R.drawable.sun);
		cage = BitmapFactory.decodeResource(view.getResources(), R.drawable.cage);
		cloud = BitmapFactory.decodeResource(view.getResources(), R.drawable.cloud);
		transporentCloud = BitmapFactory.decodeResource(view.getResources(), R.drawable.transparent_cloud);
		pig1 = BitmapFactory.decodeResource(view.getResources(), R.drawable.pig1);
		pig2 = BitmapFactory.decodeResource(view.getResources(), R.drawable.pig2);
		bear = BitmapFactory.decodeResource(view.getResources(), R.drawable.bear);
		backgroundOfResultsForm = BitmapFactory.decodeResource(view.getResources(), R.drawable.results_background);
		
		progressBarBackground = BitmapFactory.decodeResource(view.getResources(), R.drawable.progressbar_background);
		progressBar = BitmapFactory.decodeResource(view.getResources(), R.drawable.progressbar);
		digits = BitmapFactory.decodeResource(view.getResources(), R.drawable.digits);
	}
	
	static public ImagesPool instance(GameView view) {
		if(pool == null) {
			pool = new ImagesPool(view);
		}
		return pool;
	}

	public Bitmap getBird1() {
		return bird1;
	}

	public Bitmap getCat1() {
		return cat1;
	}

	public Bitmap getCat2() {
		return cat2;
	}

	public Bitmap getCat3() {
		return cat3;
	}

	public Bitmap getMouse1() {
		return mouse1;
	}

	public Bitmap getFrog1() {
		return frog1;
	}

	public Bitmap getFrog2() {
		return frog2;
	}

	public Bitmap getRabbit1() {
		return rabbit1;
	}

	public Bitmap getRabbit2() {
		return rabbit2;
	}

	public Bitmap getRabbit3() {
		return rabbit3;
	}

	public Bitmap getBackground() {
		return sky;
	}

	public Bitmap getForeground() {
		return grass;
	}

	public Bitmap getSun() {
		return sun;
	}

	public Bitmap getBug1() {
		return bug1;
	}

	public Bitmap getBug2() {
		return bug2;
	}

	public Bitmap getCage() {
		return cage;
	}

	public Bitmap getEmpty() {
		return cloud;
	}

	public Bitmap getProgressBarBackground() {
		return progressBarBackground;
	}

	public Bitmap getProgressBarForeground() {
		return progressBar;
	}

	public Bitmap getDigits() {
		return digits;
	}

	public Bitmap getBear() {
		return bear;
	}

	public Bitmap getBackgroundOfResultsForm() {
		return backgroundOfResultsForm;
	}

	public Bitmap getPig1() {
		return pig1;
	}

	public Bitmap getPig2() {
		return pig2;
	}

	public Bitmap getCloud() {
		return cloud;
	}

	public Bitmap getTransporentCloud() {
		return transporentCloud;
	}
}
