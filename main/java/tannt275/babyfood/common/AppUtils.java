package tannt275.babyfood.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;

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
    public static String TAG_FOOD_TABLE = "TAG_FOOD_TABLE";

    public static String IS_FIRST_USE = "IS_FIRST_USE";

    public static String CURRENT_POSITION = "CURRENT_POSITION";

    public static boolean IS_DEV_MODE = true;

    /*for save image*/
    public static String IMAGE_DIRECTORY_NAME = "BabyFood";
    public static String IMAGE_NAME_PREFIX = "bf_";

    public static String DATA_NUTRITION_TOWER_ITEM = "DATA_NUTRITION_TOWER_ITEM";
    public static String DATA_FOOD_IN_WEEK_ITEM = "DATA_FOOD_IN_WEEK_ITEM";
    public static String DATA_TYPE_ADVICES = "DATA_TYPE_ADVICES";

    public static String ADD_TYPE = "ADD_TYPE";
    public static String ADD_ADVICE = "ADD_ADVICE";
    public static String MODIFIED_ADVICE = "MODIFIED_ADVICE";
    public static String MODIFIED_ADVICE_DATA = "MODIFIED_ADVICE_DATA";

    public static String ADD_FOOD = "ADD_FOOD";
    public static String MODIFIED_FOOD = "EDIT_FOOD";
    public static String MODIFIED_FOOD_DATA = "MODIFIED_FOOD_DATA";

    public static int ADD_NEW_FOOD = 999;
    public static int ADD_OLD_FOOD = 998;

    public static DisplayImageOptions OPTION_IMAGE = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.app_splash_icon)
            .showImageForEmptyUri(R.mipmap.app_splash_icon)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

    public static DisplayImageOptions OPTION_IMAGE_LOCAL = new DisplayImageOptions.Builder()
            .showStubImage(R.mipmap.app_splash_icon)
            .showImageForEmptyUri(R.mipmap.app_splash_icon)
            .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .showImageOnFail(R.mipmap.app_splash_icon)
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


}
