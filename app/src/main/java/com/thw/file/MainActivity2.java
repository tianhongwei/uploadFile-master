package com.thw.file;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bingoogolapple.photopicker.activity.BGAPhotoPickerActivity;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 多文件上传
 * tianhongwei
 */
public class MainActivity2 extends Activity implements EasyPermissions.PermissionCallbacks {
    private Activity mContext;

    private static final int REQUEST_CODE_PERMISSION_PHOTO_PICKER = 1;//图片权限code
    private static final int REQUEST_CODE_CHOOSE_PHOTO = 1;//选择图片code
    private static final int MAX_COUNT_IMAGES = 9;

    ImageView image;
    TextView text;

    ArrayList<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        x.Ext.init(getApplication());
        setContentView(R.layout.activity_main);

        image = (ImageView) findViewById(R.id.image);
        text = (TextView) findViewById(R.id.text);

        Button uploadBtn = (Button) findViewById(R.id.uploadBtn);
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_onClick();
            }
        });
    }

    public void btn_onClick() {
        Toast.makeText(mContext, "上传按钮被点击了", Toast.LENGTH_SHORT).show();

        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(mContext, perms)) {
            // 如果不传递该参数的话就没有拍照功能
            File takePhotoDir = new File(AppConfig.APP_SOURCE_DIR);

            startActivityForResult(BGAPhotoPickerActivity.newIntent(mContext,
                    takePhotoDir, MAX_COUNT_IMAGES, null, false), REQUEST_CODE_CHOOSE_PHOTO);
        } else {
            Toast.makeText(mContext, "需要开启图片相关权限", Toast.LENGTH_SHORT).show();
            EasyPermissions.requestPermissions(mContext,
                    "图片选择需要以下权限:\n1.访问设备上的照片\n2.拍照",
                    REQUEST_CODE_PERMISSION_PHOTO_PICKER, perms);
        }
    }

    /**
     * --- 选择图片相关代码 start ---
     */
    //成功
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
    }

    //失败
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        if (requestCode == REQUEST_CODE_PERMISSION_PHOTO_PICKER) {
            Toast.makeText(mContext, "您拒绝了「图片选择」所需要的相关权限!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case REQUEST_CODE_CHOOSE_PHOTO:
                //选择图片code
                imageList = BGAPhotoPickerActivity.getSelectedImages(data);
                image.setImageBitmap(BitmapFactory.decodeFile(imageList.get(0)));
//                upLoadFile("http://192.168.1.226:8080/mServer20180129/localFile/uploadMulFile");
                upLoadFile("http://192.168.1.91:8080/mServer20180129/localFile/uploadMulFile");
                break;
        }
    }

    /**
     * --- 选择图片相关代码 end ---
     */

    private void upLoadFile(String url) {
        final ProgressDialog dia = new ProgressDialog(mContext);
        dia.setMessage("上传中....");
        dia.show();

        Map<String, Object> map = new HashMap<>();
        map.put("fileName", "tianhongwei.png");
        map.put("fileDescribe", "此文件是我从Android客户端上传来的，用于测试接口所用。");
        map.put("userid", 52);
        UpLoadMoreFile(url, map, imageList, new MyCallBack<String>() {
            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
                Toast.makeText(mContext, "上传失败" + ex.toString(), Toast.LENGTH_SHORT).show();
                dia.dismiss();
            }

            @Override
            public void onSuccess(String result) {
                super.onSuccess(result);
                Toast.makeText(mContext, "上传成功!", Toast.LENGTH_SHORT).show();
                text.append("\nresult:" + result);
                dia.dismiss();
            }
        });
    }

    /**
     * 上传文件
     *
     * @param <T>
     */
    public static <T> Callback.Cancelable UpLoadMoreFile(String url, Map<String, Object> map, ArrayList<String> imagelist, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (null != map) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params.addParameter(entry.getKey(), entry.getValue());
            }
        }
        for (int i = 0; i < imagelist.size(); i++) {
            params.addBodyParameter("moreFile", new File(imagelist.get(i)));
        }

        params.setMultipart(true);
        Callback.Cancelable cancelable = x.http().post(params, callback);
        return cancelable;
    }
}
