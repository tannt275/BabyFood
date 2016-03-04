package tannt275.babyfood.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

import tannt275.babyfood.R;
import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.FileUtils;
import tannt275.babyfood.model.AdvicesModel;
import tannt275.babyfood.model.FoodInWeekModel;
import tannt275.babyfood.model.FoodModel;
import tannt275.babyfood.model.NutritionTowerModel;

/**
 * Created by Administrator on 09/09/2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    public static String TAG = DatabaseHandler.class.getSimpleName();

    /*tables*/
    private static String TABLE_8_MONTHS = "below_8months";
    private static String TABLE_9_TO_11_MONTHS = "bf_9_10_11months";
    private static String TABLE_15_MONTHS = "below_15months";
    private static String TABLE_FORGET = "forget";
    private static String TABLE_REMEMBER = "remember";
    private static String TABLE_FOOD_IN_WEEK = "food_in_week";
    private static String TABLE_NUTRITION_TOWER = "nutrition_tower";

    /*common variables*/
    private static String FOOD_KEY_ID = "id";
    private static String FOOD_KEY_NAME = "name";
    private static String FOOD_KEY_MATERIAL = "materials";
    private static String FOOD_KEY_METHOD = "method";
    private static String FOOD_KEY_TIME = "times";
    private static String FOOD_KEY_ADMIN = "admins";
    private static String FOOD_KEY_FAVORITE = "favorites";

    /*common variables NOTE TABLE*/
    private static String NOTE_KEY_ID = "id";
    private static String NOTE_KEY_NAME = "name";
    private static String NOTE_KEY_CONTENT = "content";
    private static String NOTE_KEY_URL = "url";
    private static String NOTE_KEY_ADMIN = "admins";

    /*variables food in week*/
    private static String FOOD_WEEK_ID = "id";
    private static String FOOD_WEEK_DAY = "day";
    private static String FOOD_WEEK_CONTENT = "content";

    public static int DATABASE_VERSION = 1;
    public static String DATABASE_PATH = "";

    private Context context;

    public DatabaseHandler(Context context) {
        super(context, FileUtils.getDatabaseFile(context), null, DATABASE_VERSION);
        this.context = context;
        this.DATABASE_PATH = FileUtils.getDatabaseFile(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREAT_TABLE_8_MONTHS = "CREATE TABLE IF NOT EXISTS " + TABLE_8_MONTHS + "(" + FOOD_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + FOOD_KEY_NAME + " TEXT, " + FOOD_KEY_MATERIAL + " TEXT, " + FOOD_KEY_METHOD + " TEXT, " + FOOD_KEY_TIME + " TEXT, "
                + FOOD_KEY_ADMIN + " INTEGER, " + FOOD_KEY_FAVORITE + " INTEGER)";

        String CREAT_TABLE_9_TO_11_MONTHS = "CREATE TABLE IF NOT EXISTS " + TABLE_9_TO_11_MONTHS + "(" + FOOD_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + FOOD_KEY_NAME + " TEXT, " + FOOD_KEY_MATERIAL + " TEXT, " + FOOD_KEY_METHOD + " TEXT, " + FOOD_KEY_TIME + " TEXT, "
                + FOOD_KEY_ADMIN + " INTEGER, " + FOOD_KEY_FAVORITE + " TEXT)";

        String CREAT_TABLE_15_MONTHS = "CREATE TABLE IF NOT EXISTS " + TABLE_15_MONTHS + "(" + FOOD_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + FOOD_KEY_NAME + " TEXT, " + FOOD_KEY_MATERIAL + " TEXT, " + FOOD_KEY_METHOD + " TEXT, " + FOOD_KEY_TIME + " TEXT, "
                + FOOD_KEY_ADMIN + " INTEGER, " + FOOD_KEY_FAVORITE + " TEXT)";

        String CREAT_TABLE_FORGET = "CREATE TABLE IF NOT EXISTS " + TABLE_FORGET + "(" + NOTE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + NOTE_KEY_NAME + " TEXT, " + NOTE_KEY_CONTENT + " TEXT, " + NOTE_KEY_URL + " TEXT, " + NOTE_KEY_ADMIN + " INTEGER)";

        String CREAT_TABLE_REMEMBER = "CREATE TABLE IF NOT EXISTS " + TABLE_REMEMBER + "(" + NOTE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + NOTE_KEY_NAME + " TEXT, " + NOTE_KEY_CONTENT + " TEXT, " + NOTE_KEY_URL + " TEXT, " + NOTE_KEY_ADMIN + " INTEGER)";

        String CREAT_TABLE_FOOD_WEEK = "CREATE TABLE IF NOT EXISTS " + TABLE_FOOD_IN_WEEK + "(" + FOOD_WEEK_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + FOOD_WEEK_DAY + " TEXT, " + FOOD_WEEK_CONTENT + " TEXT)";

        String CREAT_TALE_NUTRITION_TOWER = "CREATE TABLE IF NOT EXISTS " + TABLE_NUTRITION_TOWER + "(" + NOTE_KEY_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, "
                + NOTE_KEY_NAME + " TEXT, " + NOTE_KEY_CONTENT + " TEXT, " + NOTE_KEY_URL + " TEXT)";
        ;

        sqLiteDatabase.execSQL(CREAT_TABLE_8_MONTHS);
        sqLiteDatabase.execSQL(CREAT_TABLE_9_TO_11_MONTHS);
        sqLiteDatabase.execSQL(CREAT_TABLE_15_MONTHS);
        sqLiteDatabase.execSQL(CREAT_TABLE_FOOD_WEEK);
        sqLiteDatabase.execSQL(CREAT_TABLE_FORGET);
        sqLiteDatabase.execSQL(CREAT_TABLE_REMEMBER);
        sqLiteDatabase.execSQL(CREAT_TALE_NUTRITION_TOWER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_8_MONTHS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_9_TO_11_MONTHS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_15_MONTHS);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_IN_WEEK);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REMEMBER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_FORGET);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NUTRITION_TOWER);
        onCreate(sqLiteDatabase);
    }

    /**
     * get food was add favorite
     *
     * @return
     */
    public ArrayList<FoodModel> getListFavorite() {
        ArrayList<FoodModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String queryInTable8Months = "SELECT * FROM " + TABLE_8_MONTHS;
        Cursor cursor8Months = database.rawQuery(queryInTable8Months, null);
        if (cursor8Months.moveToFirst()) {
            do {
                FoodModel foodModel = new FoodModel();
                int stateFavorite = cursor8Months.getInt(cursor8Months.getColumnIndexOrThrow(FOOD_KEY_FAVORITE));
                if (stateFavorite == 1) {
                    foodModel.set_id(cursor8Months.getInt(cursor8Months.getColumnIndexOrThrow(FOOD_KEY_ID)));
                    foodModel.set_admins(cursor8Months.getInt(cursor8Months.getColumnIndexOrThrow(FOOD_KEY_ADMIN)));
                    foodModel.set_favorite(cursor8Months.getInt(cursor8Months.getColumnIndexOrThrow(FOOD_KEY_FAVORITE)));
                    foodModel.set_nameFood(cursor8Months.getString(cursor8Months.getColumnIndexOrThrow(FOOD_KEY_NAME)));
                    foodModel.set_materialContent(cursor8Months.getString(cursor8Months.getColumnIndexOrThrow(FOOD_KEY_MATERIAL)));
                    foodModel.set_methodContent(cursor8Months.getString(cursor8Months.getColumnIndexOrThrow(FOOD_KEY_METHOD)));
                    foodModel.set_timesFood(cursor8Months.getString(cursor8Months.getColumnIndexOrThrow(FOOD_KEY_TIME)));
                    list.add(foodModel);
                }
            } while (cursor8Months.moveToNext());
        }
        cursor8Months.close();
        String query9Months = "SELECT * FROM " + TABLE_9_TO_11_MONTHS;
        Cursor cursor9Months = database.rawQuery(query9Months, null);
        if (cursor9Months.moveToFirst()) {
            do {
                FoodModel foodModel = new FoodModel();
                int stateFavorite = cursor9Months.getInt(cursor9Months.getColumnIndexOrThrow(FOOD_KEY_FAVORITE));
                if (stateFavorite == 1) {
                    foodModel.set_id(cursor9Months.getInt(cursor9Months.getColumnIndexOrThrow(FOOD_KEY_ID)));
                    foodModel.set_admins(cursor9Months.getInt(cursor9Months.getColumnIndexOrThrow(FOOD_KEY_ADMIN)));
                    foodModel.set_favorite(cursor9Months.getInt(cursor9Months.getColumnIndexOrThrow(FOOD_KEY_FAVORITE)));
                    foodModel.set_nameFood(cursor9Months.getString(cursor9Months.getColumnIndexOrThrow(FOOD_KEY_NAME)));
                    foodModel.set_materialContent(cursor9Months.getString(cursor9Months.getColumnIndexOrThrow(FOOD_KEY_MATERIAL)));
                    foodModel.set_methodContent(cursor9Months.getString(cursor9Months.getColumnIndexOrThrow(FOOD_KEY_METHOD)));
                    foodModel.set_timesFood(cursor9Months.getString(cursor9Months.getColumnIndexOrThrow(FOOD_KEY_TIME)));
                    list.add(foodModel);
                }
            } while (cursor9Months.moveToNext());
        }
        cursor9Months.close();
        String query15Months = "SELECT * FROM " + TABLE_15_MONTHS;
        Cursor cursor15Months = database.rawQuery(query15Months, null);
        if (cursor15Months.moveToFirst()) {
            do {
                FoodModel FoodModel = new FoodModel();
                int stateFavorite = cursor15Months.getInt(cursor15Months.getColumnIndexOrThrow(FOOD_KEY_FAVORITE));
                if (stateFavorite == 1) {
                    FoodModel.set_id(cursor15Months.getInt(cursor15Months.getColumnIndexOrThrow(FOOD_KEY_ID)));
                    FoodModel.set_admins(cursor15Months.getInt(cursor15Months.getColumnIndexOrThrow(FOOD_KEY_ADMIN)));
                    FoodModel.set_favorite(cursor15Months.getInt(cursor15Months.getColumnIndexOrThrow(FOOD_KEY_FAVORITE)));
                    FoodModel.set_nameFood(cursor15Months.getString(cursor15Months.getColumnIndexOrThrow(FOOD_KEY_NAME)));
                    FoodModel.set_materialContent(cursor15Months.getString(cursor15Months.getColumnIndexOrThrow(FOOD_KEY_MATERIAL)));
                    FoodModel.set_methodContent(cursor15Months.getString(cursor15Months.getColumnIndexOrThrow(FOOD_KEY_METHOD)));
                    FoodModel.set_timesFood(cursor15Months.getString(cursor15Months.getColumnIndexOrThrow(FOOD_KEY_TIME)));
                    list.add(FoodModel);
                }
            } while (cursor15Months.moveToNext());
        }
        cursor15Months.close();
        return list;
    }

    /**
     * get food by tag
     *
     * @param params
     * @return
     */
    public ArrayList<FoodModel> getFoods(String params) {

        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<FoodModel> list = new ArrayList<>();
        String query = "SELECT * FROM ";

        if (params.equals(AppUtils.TAG_8MONTHS)) {
            query += TABLE_8_MONTHS;
        } else if (params.equals(AppUtils.TAG_9MONTHS)) {
            query += TABLE_9_TO_11_MONTHS;
        } else if (params.equals(AppUtils.TAG_15MONTHS)) {
            query += TABLE_15_MONTHS;
        }
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                FoodModel FoodModel = new FoodModel();
                FoodModel.set_nameFood(cursor.getString(cursor.getColumnIndexOrThrow(FOOD_KEY_NAME)));
                FoodModel.set_materialContent(cursor.getString(cursor.getColumnIndexOrThrow(FOOD_KEY_MATERIAL)));
                FoodModel.set_methodContent(cursor.getString(cursor.getColumnIndexOrThrow(FOOD_KEY_METHOD)));
                FoodModel.set_timesFood(cursor.getString(cursor.getColumnIndexOrThrow(FOOD_KEY_TIME)));
                FoodModel.set_admins(cursor.getInt(cursor.getColumnIndexOrThrow(FOOD_KEY_ADMIN)));
                FoodModel.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(FOOD_KEY_ID)));
                FoodModel.set_favorite(cursor.getInt(cursor.getColumnIndexOrThrow(FOOD_KEY_FAVORITE)));
                list.add(FoodModel);
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return list;
    }

    /**
     * get count food for each table
     *
     * @param tableName
     * @return
     */
    public int getCountFoods(String tableName) {
        String querry = "SELECT * FROM ";
        if (tableName.equals(AppUtils.TAG_8MONTHS)) {
            querry += TABLE_8_MONTHS;
        } else if (tableName.equals(AppUtils.TAG_9MONTHS)) {
            querry += TABLE_9_TO_11_MONTHS;
        } else if (tableName.equals(AppUtils.TAG_15MONTHS)) {
            querry += TABLE_15_MONTHS;
        }
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(querry, null);
        int count = cursor.getCount();
        database.close();
        cursor.close();

        return count;
    }

    /**
     * add food to database
     *
     * @param food
     * @param tableName
     * @param callBack
     */
    public void putDataFoods(FoodModel food, String tableName, SaveDataBase callBack) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FOOD_KEY_NAME, food.get_nameFood());
        values.put(FOOD_KEY_MATERIAL, food.get_materialContent());
        values.put(FOOD_KEY_METHOD, food.get_methodContent());
        values.put(FOOD_KEY_TIME, food.get_timesFood());
        values.put(FOOD_KEY_ADMIN, 2);

        String selectTable = "";
        if (tableName.equals(AppUtils.TAG_8MONTHS)) {
            selectTable = TABLE_8_MONTHS;
        } else if (tableName.equals(AppUtils.TAG_9MONTHS)) {
            selectTable = TABLE_9_TO_11_MONTHS;
        } else if (tableName.equals(AppUtils.TAG_15MONTHS)) {
            selectTable = TABLE_15_MONTHS;
        }

        long stateInsert = database.insert(selectTable, null, values);

        if (stateInsert > 0) {

            callBack.saveSuccess();
        } else {

            callBack.saveFail();
        }
    }

    /**
     * update state favorite in table
     *
     * @param tableName
     * @param foodModel
     */
    public void updateFavorite(Context context, String tableName, FoodModel foodModel) {

        SQLiteDatabase database = this.getWritableDatabase();
        int _idFood = foodModel.get_id();

        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_KEY_FAVORITE, foodModel.get_favorite());

        long d = 0;
        if (tableName.equals(AppUtils.TAG_8MONTHS)) {

            d = database.update(TABLE_8_MONTHS, contentValues, FOOD_KEY_ID + "=?", new String[]{String.valueOf(_idFood)});

        } else if (tableName.equals(AppUtils.TAG_9MONTHS)) {

            d = database.update(TABLE_9_TO_11_MONTHS, contentValues, FOOD_KEY_ID + "=?", new String[]{String.valueOf(_idFood)});

        } else if (tableName.equals(AppUtils.TAG_15MONTHS)) {

            d = database.update(TABLE_15_MONTHS, contentValues, FOOD_KEY_ID + "=?", new String[]{String.valueOf(_idFood)});

        }
        if (d > 0) {
            Toast.makeText(context, context.getString(R.string.update_favorite_success), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getString(R.string.update_favorite_fail), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * get advices in database
     *
     * @param typeAdvices
     * @return
     */
    public ArrayList<AdvicesModel> getListAdvices(String typeAdvices) {

        ArrayList<AdvicesModel> list = new ArrayList<>();
        String queryCondition = "SELECT * FROM ";
        if (typeAdvices.equals(AppUtils.TAG_FORGET)) {
            queryCondition += TABLE_FORGET;
        } else if (typeAdvices.equals(AppUtils.TAG_REMEMBER)) {
            queryCondition += TABLE_REMEMBER;
        }

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(queryCondition, null);
        if (cursor.moveToFirst()) {
            do {
                AdvicesModel mAdvices = new AdvicesModel();
                mAdvices.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_KEY_ID)));
                mAdvices.set_name(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_NAME)));
                mAdvices.set_content(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_CONTENT)));
                mAdvices.set_url(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_URL)));
                mAdvices.set_admin(cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_KEY_ADMIN)));
                list.add(mAdvices);
            } while (cursor.moveToNext());
        }

        return list;
    }

    /**
     * get food by week
     *
     * @return
     */
    public ArrayList<FoodInWeekModel> getFoodInWeek() {

        ArrayList<FoodInWeekModel> listItem = new ArrayList<>();

        SQLiteDatabase database = this.getWritableDatabase();
        String queryCondition = "SELECT * FROM " + TABLE_FOOD_IN_WEEK;
        Cursor cursor = database.rawQuery(queryCondition, null);
        if (cursor.moveToFirst()) {
            do {
                FoodInWeekModel item = new FoodInWeekModel();
                item.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(FOOD_WEEK_ID)));
                item.set_content(cursor.getString(cursor.getColumnIndexOrThrow(FOOD_WEEK_CONTENT)));
                item.set_day(cursor.getString(cursor.getColumnIndexOrThrow(FOOD_WEEK_DAY)));
                listItem.add(item);
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return listItem;
    }

    /**
     * get all item of list tower nutrition
     *
     * @return
     */
    public ArrayList<AdvicesModel> getListTowerNutrition() {
        ArrayList<AdvicesModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String queryCondition = "SELECT * FROM " + TABLE_NUTRITION_TOWER;
        Cursor cursor = database.rawQuery(queryCondition, null);
        if (cursor.moveToFirst()) {
            do {
                AdvicesModel mAdvices = new AdvicesModel();
                mAdvices.set_id(cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_KEY_ID)));
                mAdvices.set_url(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_URL)));
                mAdvices.set_name(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_NAME)));
                mAdvices.set_content(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_CONTENT)));
                list.add(mAdvices);
            } while (cursor.moveToNext());
        }
        database.close();
        cursor.close();
        return list;
    }

    /**
     * insert Advices to database
     *
     * @param typeAdvices
     * @param advices
     * @param callBack
     */
    public void putAdvicesToDB(String typeAdvices, AdvicesModel advices, SaveDataBase callBack) {
        String selectTable = "";
        if (typeAdvices.equals(AppUtils.TAG_REMEMBER)) {
            selectTable = TABLE_REMEMBER;
        } else if (typeAdvices.equals(AppUtils.TAG_FORGET)) {
            selectTable = TABLE_FORGET;
        }

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTE_KEY_NAME, advices.get_name());
        contentValues.put(NOTE_KEY_CONTENT, advices.get_content());
        contentValues.put(NOTE_KEY_URL, advices.get_url());
        contentValues.put(NOTE_KEY_ADMIN, advices.get_admin());

        long insertTable = sqLiteDatabase.insert(selectTable, null, contentValues);

        if (insertTable > 0) {
            callBack.saveSuccess();
            sqLiteDatabase.close();
        } else {
            callBack.saveFail();
            sqLiteDatabase.close();
        }
    }

    /**
     * delete food or advice with admins = 2
     *
     * @param object
     * @param typeName
     * @param callBack
     */
    public void deleteObjectFromDataBase(Object object, String typeName, SaveDataBase callBack) {
        String table = "";
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        if (object instanceof AdvicesModel) {
            if (typeName.equals(AppUtils.TAG_FORGET)) {
                table = TABLE_FORGET;
            } else if (typeName.equals(AppUtils.TAG_REMEMBER)) {
                table = TABLE_REMEMBER;
            }
            long d = sqLiteDatabase.delete(table, NOTE_KEY_ID + "=?", new String[]{String.valueOf(((AdvicesModel) object).get_id())});
            if (d > 0) {
                callBack.saveSuccess();
            } else {
                callBack.saveFail();
            }
        } else if (object instanceof FoodModel) {
            //TODO delete food
            if (typeName.equals(AppUtils.TAG_8MONTHS)) {
                table = TABLE_8_MONTHS;
            } else if (typeName.equals(AppUtils.TAG_9MONTHS)) {
                table = TABLE_9_TO_11_MONTHS;
            } else if (typeName.equals(AppUtils.TAG_15MONTHS)) {
                table = TABLE_15_MONTHS;
            }
            long d = sqLiteDatabase.delete(table, FOOD_KEY_ID + "=?", new String[]{String.valueOf(((FoodModel) object).get_id())});
            if (d > 0)
                callBack.saveSuccess();
            else
                callBack.saveFail();
        }

    }

    /**
     * get Object MAddvice with id and name table
     *
     * @param _id
     * @param nameTable
     * @return
     */
    public AdvicesModel getAdviceWithID(int _id, String nameTable) {
        AdvicesModel mAdvices = new AdvicesModel();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        String queryCondition = "SELECT * FROM ";
        if (nameTable.equals(AppUtils.TAG_FORGET)) {
            queryCondition += TABLE_FORGET;
        } else if (nameTable.equals(AppUtils.TAG_REMEMBER)) {
            queryCondition += TABLE_REMEMBER;
        }
        Cursor cursor = sqLiteDatabase.rawQuery(queryCondition, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_KEY_ID)) == _id) {
                    mAdvices.set_id(_id);
                    mAdvices.set_admin(cursor.getInt(cursor.getColumnIndexOrThrow(NOTE_KEY_ADMIN)));
                    mAdvices.set_name(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_NAME)));
                    mAdvices.set_content(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_CONTENT)));
                    mAdvices.set_url(cursor.getString(cursor.getColumnIndexOrThrow(NOTE_KEY_URL)));
                    break;
                }
            } while (cursor.moveToNext());
        }
        return mAdvices;
    }

    /**
     * update Advice to table
     *
     * @param advices
     * @param nameTable
     * @param callBack
     */
    public void updateAdvice(AdvicesModel advices, String nameTable, SaveDataBase callBack) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String table = "";
        if (nameTable.equals(AppUtils.TAG_FORGET)) {
            table = TABLE_FORGET;
        } else if (nameTable.equals(AppUtils.TAG_REMEMBER)) {
            table = TABLE_REMEMBER;
        }
        contentValues.put(NOTE_KEY_ID, advices.get_id());
        contentValues.put(NOTE_KEY_ADMIN, advices.get_admin());
        contentValues.put(NOTE_KEY_NAME, advices.get_name());
        contentValues.put(NOTE_KEY_CONTENT, advices.get_content());
        contentValues.put(NOTE_KEY_URL, advices.get_url());
        long d = database.update(table, contentValues, NOTE_KEY_ID + "=?", new String[]{String.valueOf(advices.get_id())});

        if (d > 0) {
            callBack.saveSuccess();
        } else {
            callBack.saveFail();
        }
        database.close();
    }


    public interface SaveDataBase {
        public void saveSuccess();

        public void saveFail();
    }
}
