package tannt275.babyfood.common;

import android.util.Log;

/**
 * Created by TanNT on 12/18/2015.
 */
public class CountTimes {
    public static String TAG = CountTimes.class.getSimpleName();
    private long startTime;
    private String message;

    public CountTimes(String message) {
        this.message = message;
        this.startTime = System.currentTimeMillis();
    }

    public void onStop(){
        Log.d(TAG, message + " : " + System.currentTimeMillis() + " .ms" );
    }
}
