package tannt275.babyfood.common;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import tannt275.babyfood.R;

/**
 * Created by Administrator on 08/09/2015.
 */
public class AppUtils {

    public static String TAG = AppUtils.class.getSimpleName();
    public static String TAG_8MONTHS = "TAG_8MONTHS";
    public static String TAG_9MONTHS = "TAG_9MONTHS";
    public static String TAG_15MONTHS = "TAG_15MONTHS";
    public static String TAG_FORGET = "TAG_FORGET";
    public static String TAG_REMEMBER = "TAG_REMEMBER";
    public static String TAG_FAVORITE = "TAG_FAVORITE";

    public static String IS_FIRST_USE = "IS_FIRST_USE";

    public static String CURRENT_POSITION = "CURRENT_POSITION";

    public static int ADD_FAVORITE = 1;
    public static int REMOVE_FAVORITE = 2;

    /*for edit type in EditActivity*/
    public static int EDIT_TYPE_ADVICES = 1;
    public static int EDIT_TYPE_FOOD = 2;
    public static String EDIT_TYPE = "EDIT_TYPE";
    public static String ID_OBJECT_EDIT = "ID_OBJECT_EDIT";

    /*for save image*/
    public static String IMAGE_DIRECTORY_NAME = "BabyFood";
    public static String IMAGE_NAME_PREFIX = "bf_";

    public static String DATA_NUTRITION_TOWER_ITEM = "DATA_NUTRITION_TOWER_ITEM";
    public static String DATA_FOOD_IN_WEEK_ITEM = "DATA_FOOD_IN_WEEK_ITEM";

    public static DisplayImageOptions OPTION_IMAGE = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.ic_launcher)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

    public static DisplayImageOptions OPTION_IMAGE_LOCAL = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.ic_launcher)
            .showImageForEmptyUri(R.mipmap.ic_launcher)
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .showImageOnFail(R.mipmap.ic_launcher)
            .cacheInMemory()
            .cacheOnDisc()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .build();


    public static String getKeyHashFacebook(Context context){
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures){
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String keyhash = new String(Base64.encode(md.digest(),0));
                Log.e(TAG, "keyhash FB: " +  keyhash);
                return keyhash;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }

    public static void hideSoftKeyboard(Activity activity){
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View forcus = activity.getCurrentFocus();
        if (forcus != null){
            inputMethodManager.hideSoftInputFromWindow(forcus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
