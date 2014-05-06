package org.escoladeltreball.shooter2d;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preferences extends Activity {

	public String readPreferences(String key){
		SharedPreferences prefs = this.getSharedPreferences(key, MODE_PRIVATE);
		return prefs.getString(key, null);
	}

	public boolean savePreferences(String key, String text){
		try {
			SharedPreferences prefs = this.getSharedPreferences(key, MODE_PRIVATE);
			Editor editor = prefs.edit();
			editor.putString(key, text);
			editor.commit();
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean clearPreferences(String key){
		try {
			SharedPreferences prefs = this.getSharedPreferences(key, MODE_PRIVATE);
			Editor editor = prefs.edit();
			editor.remove(key);
			editor.commit();
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	public boolean clearPreferences(){
		try {
			SharedPreferences prefs = this.getSharedPreferences(null, MODE_PRIVATE);
			Editor editor = prefs.edit();
			editor.clear();
			editor.commit();
			return true;
		}
		catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

}
