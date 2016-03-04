package tannt275.babyfood;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import tannt275.babyfood.common.AppUtils;
import tannt275.babyfood.common.Log;
import tannt275.babyfood.database.DatabaseHandler;
import tannt275.babyfood.model.AdvicesModel;
import tannt275.babyfood.model.FoodModel;

public class AddingActivity extends AppCompatActivity {

    public static String TAG = AddingActivity.class.getSimpleName();
    private Toolbar toolbar;

    private Button saveBtn;
    private Button captureBtn;
    private ImageView imagePreView;
    private RelativeLayout imageLayout;

    private EditText nameItem;
    private EditText timeItem;

    private LinearLayout materialLayout;
    private EditText materialItem;

    private TextView methodPrefix;
    private EditText methodItem;

    private String nameTable;
    private String typeAdvices;
    private String typeAdd;

    private Dialog dialog;
    private int REQUEST_CAMERA = 999;
    private int REQUEST_PICK_IMAGE = 998;

    private String pathImage;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            typeAdd = bundle.getString(AppUtils.ADD_TYPE);
            Log.e(TAG, "type add: " + typeAdd);
            if (typeAdd.equals(AppUtils.ADD_ADVICE)) {
                typeAdvices = bundle.getString(AppUtils.DATA_TYPE_ADVICES);
                Log.e(TAG, "type addvices: " + typeAdvices);
            } else if (typeAdd.equals(AppUtils.ADD_FOOD)) {
                nameTable = bundle.getString(AppUtils.TAG_FOOD_TABLE);
                Log.e(TAG, "name table for add: " + nameTable);
            }
        }

        toolbar = (Toolbar) findViewById(R.id.addingToolBar);
        setSupportActionBar(toolbar);

        imageLayout = (RelativeLayout) findViewById(R.id.add_activity_addImageLayout);
        captureBtn = (Button) findViewById(R.id.add_activity_captureButton);
        saveBtn = (Button) findViewById(R.id.add_activity_saveBtn);

        imagePreView = (ImageView) findViewById(R.id.add_activity_ImagePreview);
        nameItem = (EditText) findViewById(R.id.add_activity_title);

        materialLayout = (LinearLayout) findViewById(R.id.add_activity_materialLayout);
        materialItem = (EditText) findViewById(R.id.add_activity_material);

        methodPrefix = (TextView) findViewById(R.id.add_activity_method_prefix);
        methodItem = (EditText) findViewById(R.id.add_activity_method);
        timeItem = (EditText) findViewById(R.id.add_activity_time);

        nameItem.setOnTouchListener(touchEdittextListener);
        timeItem.setOnTouchListener(touchEdittextListener);
        materialItem.setOnTouchListener(touchEdittextListener);
        methodItem.setOnTouchListener(touchEdittextListener);
        captureBtn.setOnClickListener(chooseAPhotoListener);
        saveBtn.setOnClickListener(saveDataListener);

        suitableLayoutAdd();

    }

    private void suitableLayoutAdd() {

        if (typeAdd.equals(AppUtils.ADD_ADVICE)) {
            imageLayout.setVisibility(View.VISIBLE);
            materialLayout.setVisibility(View.GONE);
            methodPrefix.setText(getString(R.string.add_activity_advices_content));
        } else {
            materialLayout.setVisibility(View.VISIBLE);
            methodPrefix.setText(getString(R.string.add_activity_foods_method));
            imageLayout.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener saveDataListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String name = nameItem.getText().toString();
            String time = timeItem.getText().toString();
            String method = methodItem.getText().toString();
            String material = materialItem.getText().toString();
            if (typeAdd.equals(AppUtils.ADD_ADVICE)) {
                if (pathImage == null || TextUtils.isEmpty(pathImage)) {
                    Toast.makeText(AddingActivity.this, "Chưa chọn ảnh cho nội dung muốn lưu", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(name)) {
                    showToastEmpty("Tên ");
                } else if (TextUtils.isEmpty(method)) {
                    showToastEmpty("Nội dung ");
                } else {
                    saveAddvice(name, method);
                }
            } else if (typeAdd.equals(AppUtils.ADD_FOOD)) {
                if (TextUtils.isEmpty(name)) {
                    showToastEmpty("Tên ");
                } else if (TextUtils.isEmpty(time)) {
                    showToastEmpty("Thời gian thực hiện ");
                } else if (TextUtils.isEmpty(material)) {
                    showToastEmpty("Nguyên liệu ");
                } else if (TextUtils.isEmpty(method)) {
                    showToastEmpty("Cách làm ");
                } else {
                    saveFood(name, time, material, method);
                }
            }
        }
    };

    private void saveFood(String name, String time, final String material, String method) {

        FoodModel foodModel = new FoodModel();
        foodModel.set_admins(2);
        foodModel.set_timesFood(time + " phút");
        foodModel.set_nameFood(name);
        foodModel.set_materialContent(material);
        foodModel.set_methodContent(method);
        foodModel.set_favorite(1);

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.putDataFoods(foodModel, nameTable, new DatabaseHandler.SaveDataBase() {
            @Override
            public void saveSuccess() {
                Toast.makeText(AddingActivity.this, "Lưu món ăn thành công", Toast.LENGTH_SHORT).show();
                timeItem.setText("");
                nameItem.setText("");
                materialItem.setText("");
                methodItem.setText("");
                databaseHandler.close();
            }

            @Override
            public void saveFail() {
                Toast.makeText(AddingActivity.this, "Lưu món ăn thất bại", Toast.LENGTH_SHORT).show();
                databaseHandler.close();
            }
        });

    }

    private void saveAddvice(String name, String method) {

        AdvicesModel advicesModel = new AdvicesModel();
        advicesModel.set_name(name);
        advicesModel.set_content(method);
        advicesModel.set_admin(2);
        advicesModel.set_url(pathImage);

        databaseHandler = new DatabaseHandler(this);
        databaseHandler.putAdvicesToDB(typeAdvices, advicesModel, new DatabaseHandler.SaveDataBase() {
            @Override
            public void saveSuccess() {
                Toast.makeText(AddingActivity.this, "Lưu thành công", Toast.LENGTH_LONG).show();
                timeItem.setText("");
                methodItem.setText("");
                databaseHandler.close();
            }

            @Override
            public void saveFail() {
                Toast.makeText(AddingActivity.this, "Lưu thất bại", Toast.LENGTH_LONG).show();
                databaseHandler.close();
            }
        });
    }

    private void showToastEmpty(String string) {
        Toast.makeText(this, string + " đang để trống", Toast.LENGTH_SHORT).show();
    }

    View.OnClickListener chooseAPhotoListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showDialogChooseImage();
        }
    };

    private void showDialogChooseImage() {

        dialog = new Dialog(this);
        dialog.setCancelable(false);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_choose_image);
        dialog.show();

        TextView capture = (TextView) dialog.findViewById(R.id.dialog_choose_image_capture);
        TextView gallery = (TextView) dialog.findViewById(R.id.dialog_choose_image_gallery);
        TextView cancel = (TextView) dialog.findViewById(R.id.dialog_choose_image_cancel);
        capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                takeAPhoto();
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                chooseAphotoFromGallery();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void chooseAphotoFromGallery() {

        Intent toPickImageFromGallery = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(toPickImageFromGallery, REQUEST_PICK_IMAGE);

    }

    private void takeAPhoto() {
        Intent toGetCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(toGetCamera, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

                String fileName = "BF_" + System.currentTimeMillis() + ".jpg";

                File direct = new File(Environment.getExternalStorageDirectory() + "/BabyFood");
                if (!direct.exists()) {
                    File wallpaperDirectory = new File("/sdcard/BabyFood/");
                    wallpaperDirectory.mkdirs();
                }

                File file = new File(new File("/sdcard/BabyFood/"), fileName);
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream fileOutputStream;
                try {
                    fileOutputStream = new FileOutputStream(file);
                    fileOutputStream.write(bytes.toByteArray());
                    fileOutputStream.close();
                    pathImage = file.getAbsolutePath();
                    Log.e(TAG, "path: " + pathImage);
                    showImagePreview(thumbnail);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (requestCode == REQUEST_PICK_IMAGE) {

                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                        selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                pathImage = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bitmap = BitmapFactory.decodeFile(pathImage);
                Log.e(TAG, "path: " + pathImage);
                showImagePreview(bitmap);
            }
        }
    }

    private void showImagePreview(Bitmap thumbnail) {
        imagePreView.setVisibility(View.VISIBLE);
        imagePreView.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, 150, 150, false));
    }

    View.OnTouchListener touchEdittextListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            v.setFocusable(true);
            v.setFocusableInTouchMode(true);
            return false;
        }
    };

}
