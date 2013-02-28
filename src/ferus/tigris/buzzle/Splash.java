package ferus.tigris.buzzle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
	}

	protected void onResume() {
		super.onResume();
		new Handler().postDelayed(new Runnable()
		{
			public void run()
			{
				//Finish the splash activity so it can't be returned to.
				Splash.this.finish();
				// Create an Intent that will start the main activity.
				Intent mainIntent = new Intent(Splash.this, GameFieldActivity.class);
				Splash.this.startActivity(mainIntent);
			}
		}, 400);
	}

}
