package com.dou361.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/3/15 23:31
 * <p>
 * 描 述：图片选择
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class PhotoUtils {

    /**
     * 拍照
     */
    public final static int TAKEPHOTO = 1;
    /**
     * 相册中选择
     */
    public final static int SELECTPHOTO = 2;
    /**
     * 裁剪
     */
    public final static int CUTPHOTO = 3;

    /**
     * 拍照
     */
    @SuppressLint("NewApi")
    public static int takePhoto(Activity activity, String strFilePath) {
        String dirPath = strFilePath.substring(0, strFilePath.lastIndexOf("/"));
        String substring = strFilePath
                .substring(strFilePath.lastIndexOf("/") + 1);
        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, substring);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri imageUri = Uri.fromFile(file);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        int numCameras = Camera.getNumberOfCameras();
        if (numCameras > 0) {
            if (activity != null) {
                activity.startActivityForResult(takePictureIntent,
                        TAKEPHOTO);
            }
        }
        return numCameras;
    }

    /**
     * 从相册中选择图片
     */
    public static void pickPhoto(Activity activity) {
        Intent innerIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        /** 查看类型 */
        innerIntent.setType("image/*");
        Intent wrapperIntent = Intent.createChooser(innerIntent, null);
        if (activity != null) {
            activity.startActivityForResult(wrapperIntent,
                    SELECTPHOTO);
        }
    }

    /**
     * @param uri         路径
     * @param outputX     输出大小
     * @param outputY     输出大小
     * @param requestCode 请求码
     * @param isCrop      true CROP false ACTION_GET_CONTENT
     * @param activity
     * @return void
     * @throws
     * @Title: startImageAction
     */
    public static void cutImage(Uri uri, int outputX, int outputY,
                                int requestCode, boolean isCrop, Activity activity) {
        Intent intent = null;
        /** 剪裁 */
        if (isCrop) {
            intent = new Intent("com.android.camera.action.CROP");
        } else {
            intent = new Intent(Intent.ACTION_GET_CONTENT, null);
        }
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        /** 设置宽高比例 */
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        /** 设置裁剪图片宽高 */
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        /** 图片格式 */
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        /** 取消人脸识别 */
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("scaleUpIfNeeded", true);
        if (activity != null) {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 保存裁剪的头像
     *
     * @param data         裁剪后图片的路径
     * @param isFromCamera 是否是拍照的，true拍照，false选择相册
     */
    public static String saveImage(Intent data, String strIconPath, boolean isFromCamera, int degree) {
        Log.e("","------saveImage-----strIconPath----------"+strIconPath);
        Bundle extras = data.getExtras();
        String strCutPath = "";
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");
            if (bitmap != null) {
                bitmap = PhotoUtils.toRoundCorner(bitmap, 10);
                if (isFromCamera && degree != 0) {
                    bitmap = PhotoUtils.rotaingImageView(degree, bitmap);
                }

                /** 保存图片 */
                String filename = new SimpleDateFormat("yyMMddHHmmss")
                        .format(new Date());
                strCutPath = strIconPath + File.separator + filename;
                PhotoUtils.saveBitmap(strIconPath, filename, bitmap,
                        true);
                /** 上传头像 */
                if (bitmap != null && bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            }
        }
        return strCutPath;
    }

    /**
     * 回收垃圾 recycle
     *
     * @throws
     */
    public static void recycle(Bitmap bitmap) {
        // 先判断是否已经回收
        if (bitmap != null && !bitmap.isRecycled()) {
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }

    /**
     * 获取指定路径下的图片的指定大小的缩略图 getImageThumbnail
     *
     * @return Bitmap
     * @throws
     */
    public static Bitmap getImageThumbnail(String imagePath, int width,
                                           int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * saveBitmap
     *
     * @param @param filename---完整的路径格式-包含目录以及文件名
     * @param @param bitmap
     * @param @param isDelete --是否只留一张
     * @return void
     * @throws
     */
    public static void saveBitmap(String dirpath, String filename,
                                  Bitmap bitmap, boolean isDelete) {
        File dir = new File(dirpath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dirpath, filename);
        // 若存在即删除-默认只保留一张
        if (isDelete) {
            if (file.exists()) {
                file.delete();
            }
        }

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File getFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return file;
    }

    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;

    }

    /**
     * 旋转图片一定角度 rotaingImageView
     *
     * @return Bitmap
     * @throws
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        // 旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 将图片变为圆角
     *
     * @param bitmap 原Bitmap图片
     * @param pixels 图片圆角的弧度(单位:像素(px))
     * @return 带有圆角的图片(Bitmap 类型)
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    /**
     * 将图片转化为圆形头像
     *
     * @throws
     * @Title: toRoundBitmap
     */
    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;

            left = 0;
            top = 0;
            right = width;
            bottom = width;

            height = width;

            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;

            float clip = (width - height) / 2;

            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;

            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);// 设置画笔无锯齿

        canvas.drawARGB(0, 0, 0, 0); // 填充整个Canvas

        // 以下有两种方法画圆,drawRounRect和drawCircle
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);// 画圆角矩形，第一个参数为图形显示区域，第二个参数和第三个参数分别是水平圆角半径和垂直圆角半径。
        // canvas.drawCircle(roundPx, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));// 设置两张图片相交时的模式,参考http://trylovecatch.iteye.com/blog/1189452
        canvas.drawBitmap(bitmap, src, dst, paint); // 以Mode.SRC_IN模式合并bitmap和已经draw了的Circle

        return output;
    }


    public static String getImagePathFromUri(Uri fileUrl, Context context)
    {
        String fileName = null;
        Uri filePathUri = fileUrl;
        if (fileUrl != null)
        {
            if (fileUrl.getScheme().toString().compareTo("content") == 0)
            {
                // content://开头的uri
                Cursor cursor = context.getContentResolver().query(fileUrl, null, null, null, null);
                if (cursor != null && cursor.moveToFirst())
                {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    fileName = cursor.getString(column_index); // 取出文件路径

                    // Android 4.1 更改了SD的目录，sdcard映射到/storage/sdcard0
                    if (!fileName.startsWith("/storage") && !fileName.startsWith("/mnt"))
                    {
                        // 检查是否有”/mnt“前缀
                        fileName = "/mnt" + fileName;
                    }
                    cursor.close();
                }
            }
            else if (fileUrl.getScheme().compareTo("file") == 0) // file:///开头的uri
            {
                fileName = filePathUri.toString();// 替换file://
                fileName = filePathUri.toString().replace("file://", "");
                int index = fileName.indexOf("/sdcard");
                fileName  = index == -1 ? fileName : fileName.substring(index);
            }
        }
        return fileName;
    }

}
