package ferus.tigris.buzzle.data;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Config {
	private SharedPreferences pref;
	public Config(SharedPreferences pref) {
		this.pref = pref;
		
		Editor ed = pref.edit();
		ed.putString("asd", "hello, world!");
		ed.commit();
	}
	public CharSequence getText() {
		String result = pref.getString("asd", "");
		return result;
	}
	public boolean isSoundEnable() {
		boolean result = pref.getBoolean("sound", true);
		return result;
	}
	public void setSoundEnabled(boolean enable) {
		Editor ed = pref.edit();
		ed.putBoolean("sound", enable);
		ed.commit();
	}
	
}
