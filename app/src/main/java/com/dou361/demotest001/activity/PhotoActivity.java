package com.dou361.demotest001.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dou361.demotest001.R;
import com.dou361.ui.SelectPicPopupWindow;
import com.dou361.utils.PhotoUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private LinearLayout ll_main;
    private TextView tv_path;
    private ImageView iv_path;
    private Button btn_select;
    private SelectPicPopupWindow menuWindow;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        mContext = this;
        activity = this;
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        tv_path = (TextView) findViewById(R.id.tv_path);
        btn_select = (Button) findViewById(R.id.btn_select);
        iv_path = (ImageView) findViewById(R.id.iv_path);
        btn_select.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_select:
                showPicDialog();
                break;
        }
    }

    /**
     * 头像选择
     */
    private void showPicDialog() {
        menuWindow = new SelectPicPopupWindow(activity, itemsOnClick, true);
        /** 设置layout在PopupWindow中显示的位置 */
        menuWindow.showAtLocation(ll_main, Gravity.BOTTOM
                | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    private String strIconPath = "/mnt/sdcard/eluxue_live/";
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
                    tv_path.setText(file.getAbsolutePath());
                    iv_path.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
//                    PhotoUtils.cutImage(Uri.fromFile(file), 200, 200,
//                            PhotoUtils.CUTPHOTO, true, activity);
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
                    tv_path.setText(PhotoUtils.getImagePathFromUri(uri,mContext));
                    Bitmap bm = null;
                    try {
                        bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    iv_path.setImageBitmap(bm);
//                    PhotoUtils.cutImage(uri, 200, 200, PhotoUtils.CUTPHOTO, true,
//                            activity);
                } else {
//                    UIUtils.showToastShort("照片获取失败");
                }
                break;
            /** 裁剪图片 */
            case PhotoUtils.CUTPHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Log.e("", "-----------------进来了吗----------------");
                    if (data == null) {
                        /** 取消选择 */
                        return;
                    } else {
                        strCutPath = PhotoUtils.saveImage(data, strIconPath, isFromCamera,
                                degree);
                        tv_path.setText(strCutPath);
                        iv_path.setImageBitmap(BitmapFactory.decodeFile(strCutPath));
                        Log.e("", "-----------------进来了吗----------------" + strCutPath);
                    }
                    /** 初始化文件路径 */
                    strFilePath = "";
                }
                break;
        }
    }


}
