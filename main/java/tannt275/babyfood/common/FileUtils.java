package tannt275.babyfood.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import tannt275.babyfood.R;

/**
 * Created by Administrator on 09/09/2015.
 */
public class FileUtils {

    public static String TAG = FileUtils.class.getSimpleName();
    public static String CACHE_DIRECTORY = "app_data";
    public static String DATA_NAME = "mydata";

    public static String getDatabaseFile(Context context) {
        return context.getExternalFilesDir(CACHE_DIRECTORY).getAbsolutePath() + "/" + DATA_NAME;
    }

    public static boolean saveFileDataBase(Context context, OnSaveDataSuccess saveDataSuccess) {

        Log.d(TAG, "onsave database");

        InputStream is = context.getResources().openRawResource(R.raw.food_baby);

        File dir = new File(String.valueOf(context.getExternalFilesDir(CACHE_DIRECTORY)));

        if (!dir.mkdir()) Log.d(TAG, "directory exists...");

        try {
            File file = new File(dir, DATA_NAME);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int read = 0;
            while ((read = is.read(buff)) > 0) {
                fos.write(buff, 0, read);
            }
            is.close();
            fos.close();
            MySharePref.putBoolean(AppUtils.IS_FIRST_USE, false);
            saveDataSuccess.saveSuceess();
            Log.d(TAG, "save success...............");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Incase IOException");
            return false;
        }
    }

    public interface OnSaveDataSuccess {
        public void saveSuceess();
    }

    public static String getKeyHashFacebook(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(),
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyHashFB = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", keyHashFB);
                return keyHashFB;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return "";
    }
}
