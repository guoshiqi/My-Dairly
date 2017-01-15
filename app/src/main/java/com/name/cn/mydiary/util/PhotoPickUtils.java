package com.name.cn.mydiary.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;

import com.name.cn.mydiary.framework.AppConstants;
import com.name.cn.mydiary.framework.DiaryApplication;

import java.io.File;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * take photo
 * Created by guoshiqi on 2017/1/6.
 */

public class PhotoPickUtils {
    private static final int ALBUM_OK = 1000;
    private static final int CUT_OK = 1001;
    private static final int CAMERA_OK = 1002;
    private NoPermissionDone listener;
    private Activity activity;
    private Uri uriTempFile;

    public PhotoPickUtils(Activity activity, String fileName, NoPermissionDone listener) {
        this.activity = activity;
        this.listener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File imagePath = new File(activity.getFilesDir(), "images");
            File newFile = new File(imagePath, fileName + ".jpg");
            uriTempFile = FileProvider.getUriForFile(activity,
                    "cn.suncloud.kopak.fileprovider", newFile);
        } else {
            uriTempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + fileName + ".jpg");
        }
    }

    public void showPhotoSelect() {
        showPhotoSelectDialog();
    }


    private void showPhotoSelectDialog() {
        if (activity != null) {
            String[] arr = {"拍照", "相册"};
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
            alertDialogBuilder
                    .setItems(arr, (dialogInterface, index) -> {
                        if (index == 0) {
                            useCamera();
                        } else if (index == 1) {
                            useAlbum();
                        }
                        dialogInterface.dismiss();
                    }).show();
        }
    }

    private void choicePicFromAlbum() {
        // 来自相册
        Intent albumIntent = new Intent(Intent.ACTION_PICK, null);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        activity.startActivityForResult(albumIntent, ALBUM_OK);
    }

    private void choicePicFromCamera() {
        // 来自相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径，这样通过这个uri就可以得到这个照片了
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriTempFile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivityForResult(cameraIntent, CAMERA_OK);// CAMERA_OK是用作判断返回结果的标识
    }

    private void useCamera() {
        PermissionUtils.requestPermissionsResult(activity,
                AppConstants.REQUEST_CODE_ASK_PERMISSIONS_CAMERA,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        choicePicFromCamera();
                    }

                    @Override
                    public void onPermissionDenied() {
                        PermissionUtils.showTipsDialog(activity, "未授权读取相册权限，无法使用相册");
                        if (listener != null) {
                            listener.needDone();
                        }
                    }
                });
    }

    private void useAlbum() {
        PermissionUtils.requestPermissionsResult(activity,
                AppConstants.REQUEST_CODE_ASK_PERMISSIONS_CAMERA,
                new String[]{Manifest.permission.CAMERA},
                new PermissionUtils.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        choicePicFromAlbum();
                    }

                    @Override
                    public void onPermissionDenied() {
                        PermissionUtils.showTipsDialog(activity, "未授权相机权限，无法使用拍照功能");
                        if (listener != null) {
                            listener.needDone();
                        }
                    }
                });
    }

    private void clipPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        //可以选择图片类型，如果是*表明所有类型的图片
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop = true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例，这里设置的是正方形（长宽比为1:1）
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 500);
        intent.putExtra("outputY", 500);
        //裁剪时是否保留图片的比例，这里的比例是1:1
        intent.putExtra("scale", true);
        //是否是圆形裁剪区域，设置了也不一定有效
        intent.putExtra("circleCrop", true);
        //设置输出的格式
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uriTempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        activity.startActivityForResult(intent, CUT_OK);
    }

    public void onActivityResultWithClip(int requestCode, int resultCode, Intent data, PhotoReturnUri uri) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ALBUM_OK:
                    if (data == null)
                        return;
                    Uri albumUri = data.getData();
                    if (albumUri != null) {
                        clipPhoto(albumUri);
                    }
                    break;
                case CAMERA_OK:
                    clipPhoto(uriTempFile);
                    break;
                case CUT_OK:
                    uri.getUri(uriTempFile);
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            DiaryApplication.getInstance().showToast("取消");
        } else {
            DiaryApplication.getInstance().showToast("失败");
        }
    }

    public void onActivityResultWithoutClip(int requestCode, int resultCode, Intent data, PhotoReturnUri uri) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ALBUM_OK:
                    if (data == null)
                        return;
                    Uri albumUri = data.getData();
                    if (albumUri != null) {
                        uri.getUri(albumUri);
                    }
                    break;
                case CAMERA_OK:
                    uri.getUri(uriTempFile);
                    break;
            }
        }
    }


    public interface PhotoReturnUri {
        void getUri(Uri uri);
    }

    public interface NoPermissionDone {
        void needDone();
    }

    public interface Dismiss {
        void dismiss();
    }

}
