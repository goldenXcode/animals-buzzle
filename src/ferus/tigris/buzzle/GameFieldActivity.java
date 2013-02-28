package ferus.tigris.buzzle;

import ferus.tigris.buzzle.data.Config;
import ferus.tigris.buzzle.data.DB;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class GameFieldActivity extends Activity {

	private Config conf;
	private DB db;
	private GameView view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(LayoutParams.FLAG_FULLSCREEN);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		conf = new Config(getPreferences(MODE_PRIVATE));
		db = new DB(this);
		
		view = new GameView(this);
		setContentView(view);
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		view.showTopTable();
		return false;
	}
	
	protected void onStart() {
		super.onStart();
	}
	
	protected void onResume() {
		super.onResume();
	}
	
	protected void onPause() {
		super.onPause();
		saveToDB();
	}
	
	protected void onDestroy() {
		super.onDestroy();
		view.stop();
	}

	public void loadFromDB() {
		view.load(db, conf);
	}

	public void loadTopResults() {
		view.loadResults(db);
	}

	public void saveToDB() {
		view.save(db, conf);
	}
}
