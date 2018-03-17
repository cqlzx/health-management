package com.along.android.healthmanagement.activities;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.adapters.SelectGridViewAdapter;

/**
 * Created by wilber on 4/26/17.
 */

public class GetPictureActivity extends AppCompatActivity{
    private GridView gridview;
    private SelectGridViewAdapter adapter;

    private Context context;

    private List<String> srcList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_picture);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    // Explain to the user why we need to read the contacts
                }

                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                // MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE is an
                // app-defined int constant that should be quite unique
            }
        }

        gridview = (GridView) findViewById(R.id.grid);

        gridview.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent();
                        //把返回数据存入Intent
                        intent.putExtra("result", srcList.get(position));
                        //设置返回数据
                        GetPictureActivity.this.setResult(RESULT_OK, intent);
                        //关闭Activity
                        GetPictureActivity.this.finish();
                    }
                }
        );

        getImages();
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    adapter = new SelectGridViewAdapter(GetPictureActivity.this,srcList);
                    gridview.setAdapter(adapter);
                    break;
            }
        }
    };

    public void getImages() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                    ContentResolver mContentResolver = GetPictureActivity.this
                            .getContentResolver();

                    // 只查询jpeg和png的图片
                    Cursor mCursor = mContentResolver.query(mImageUri, null,
                            MediaStore.Images.Media.MIME_TYPE + "=? or "
                                    + MediaStore.Images.Media.MIME_TYPE + "=?",
                            new String[]{"image/jpeg", "image/png"},
                            MediaStore.Images.Media.DATE_MODIFIED);

                    while (mCursor.moveToNext()) {
                        String path = mCursor.getString(mCursor
                                .getColumnIndex(MediaStore.Images.Media.DATA));

                        srcList.add(path);
                    }

                    mCursor.close();
                    handler.sendEmptyMessage(0);
                }
            }).start();
        }
    }
}
