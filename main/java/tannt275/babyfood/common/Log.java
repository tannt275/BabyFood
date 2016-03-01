package tannt275.babyfood.common;

/**
 * Created by tannt on 3/1/2016.
 */
public class Log {

    public static void e(String tag, String msg) {
        if (AppUtils.IS_DEV_MODE) {
            android.util.Log.e(tag, msg + "");
        }
    }

    public static void d(String tag, String msg) {
        if (AppUtils.IS_DEV_MODE) {
            android.util.Log.d(tag, msg + "");
        }
    }
}
