package tannt275.babyfood.common;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Administrator on 10/09/2015.
 */
public class MySharePref {

    public static String TAG = MySharePref.class.getSimpleName();

    public static SharedPreferences pref;

    public static SharedPreferences getPref() {
        if (pref == null){
            pref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
        }
        return pref;
    }

    public void setPref(SharedPreferences pref) {
        this.pref = pref;
    }

    public static void putBoolean(String name, boolean value){
        SharedPreferences.Editor editor = getPref().edit();
        editor.putBoolean(name, value);
        editor.commit();
    }

    public static boolean get(String name, boolean defaultValue){
        return getPref().getBoolean(name, defaultValue);
    }
}
