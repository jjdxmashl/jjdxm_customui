package com.dou361.demotest001;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dou361.ui.CustomKeyBoard;
import com.dou361.ui.SelectPicPopupWindow;
import com.dou361.utils.PhotoUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    private EditText et_keyboard;
    private CustomKeyBoard customKeyBoard;
    private Button btn_photo;
    private SelectPicPopupWindow menuWindow;
    private Activity activity;
    private LinearLayout main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        main = (LinearLayout) findViewById(R.id.main);
        et_keyboard = (EditText) findViewById(R.id.et_keyboard);
        btn_photo = (Button) findViewById(R.id.btn_photo);
        customKeyBoard = new CustomKeyBoard(activity);
        et_keyboard.setFocusable(false);
        et_keyboard.setOnTouchListener(this);
        btn_photo.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final View v0 = getWindow().peekDecorView();
        if (v0 != null && v0.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v0.getWindowToken(), 0);
        }
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (event.getAction() == MotionEvent.ACTION_UP) {

            v.setFocusableInTouchMode(true);
            v.setFocusable(true);
            v.requestFocus();

            if (customKeyBoard.popup == null
                    || customKeyBoard.popup.isShowing() == false
                    || customKeyBoard.isShowing == false) {

                clearViews();

                customKeyBoard.isCapital = false;
                customKeyBoard.isShowing = true;

                int inputback = ((EditText) v).getInputType();
                ((EditText) v).setInputType(InputType.TYPE_NULL);
                customKeyBoard.showKeyboard(et_keyboard);

                ((EditText) v).onTouchEvent(event);
                ((EditText) v).setInputType(inputback);
                String str = et_keyboard.getText().toString();
                et_keyboard.setSelection(str.length());

                return true;
            } else {
                return true;
            }
        }
        return false;
    }

    private void clearViews() {
        if (customKeyBoard.popup != null && customKeyBoard.popup.isShowing())
            customKeyBoard.popup.dismiss();
        customKeyBoard.popup = null;
        System.gc();
    }


    /**
     * 显示popuwindow
     */
    private void showPicDialog() {
        menuWindow = new SelectPicPopupWindow(activity, itemsOnClick,  true);
        /** 设置layout在PopupWindow中显示的位置 */
        menuWindow.showAtLocation(main, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private String strIconPath;
    /**
     * 为弹出窗口实现监听类
     */
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        @SuppressLint("NewApi")
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                /** 拍照 */
                case R.id.tv_take_photo:
                    strFilePath = strIconPath
                            + File.separator
                            + new SimpleDateFormat("yyMMddHHmmss")
                            .format(new Date());
                    int numCameras = PhotoUtils.takePhoto(activity, strFilePath);
                    if (numCameras <= 0) {
//                        UIUtils.showToastShort("打开摄像头失败!");
                    }
                    break;
                /** 从相册中选择 */
                case R.id.tv_pick_photo:
                    PhotoUtils.pickPhoto(activity);
                    break;
            }

        }

    };

    /**
     * 旋转的角度
     */
    private int degree;
    /**
     * 是否是拍照
     */
    private boolean isFromCamera;
    /**
     * 裁剪后的保存的路径
     */
    private String strCutPath;
    /**
     * 拍照保存的路径
     */
    private String strFilePath;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            /** 拍照 */
            case PhotoUtils.TAKEPHOTO:
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
//                        UIUtils.showToastShort("SD不可用");
                        return;
                    }
                    isFromCamera = true;
                    File file = new File(strFilePath);
                    degree = PhotoUtils.readPictureDegree(file.getAbsolutePath());
                    PhotoUtils.cutImage(Uri.fromFile(file), 200, 200,
                            PhotoUtils.CUTPHOTO, true, activity);
                }
                break;
            /** 选择系统图片 */
            case PhotoUtils.SELECTPHOTO:
                Uri uri = null;
                if (data == null) {
                    return;
                }
                if (resultCode == RESULT_OK) {
                    if (!Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
//                        UIUtils.showToastShort("SD不可用");
                        return;
                    }
                    isFromCamera = false;
                    uri = data.getData();
                    PhotoUtils.cutImage(uri, 200, 200, PhotoUtils.CUTPHOTO, true,
                            activity);
                } else {
//                    UIUtils.showToastShort("照片获取失败");
                }
                break;
            /** 裁剪图片 */
            case PhotoUtils.CUTPHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    if (data == null) {
                        /** 取消选择 */
                        return;
                    } else {
                        String urlPath = PhotoUtils.saveImage(data, strIconPath, isFromCamera,
                                degree);
//                        iv_person.setImageBitmap(BitmapFactory.decodeFile(urlPath));
                    }
                    /** 初始化文件路径 */
                    strFilePath = "";
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        showPicDialog();
    }
}
