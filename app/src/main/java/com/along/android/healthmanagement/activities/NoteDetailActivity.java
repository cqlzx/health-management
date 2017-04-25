package com.along.android.healthmanagement.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.Toast;


import com.along.android.healthmanagement.R;
import com.along.android.healthmanagement.entities.Note;
import com.along.android.healthmanagement.entities.User;
import com.along.android.healthmanagement.helpers.EntityManager;

import java.util.Calendar;

public class NoteDetailActivity extends AppCompatActivity {
    int PICK_PIC = 101;
    float mInsertedImgWidth;
    EditText detail_title,detail_content;
    Note note;
    Long id;
    Long date;
    String title;
    String content;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.note_detail_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_add:
                //note = EntityManager.findById(Note.class, id);
                SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
                long uid = sp.getLong("uid", 0);
                User user = EntityManager.findById(User.class, uid);

                note = new Note();
                note.setUid(user.getId());
                detail_title = (EditText)findViewById(R.id.detail_title);
                detail_content = (EditText)findViewById(R.id.detail_content);
                note.setTitle(detail_title.getText().toString());
                note.setContent(detail_content.getText().toString());
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                calendar.clear();
                calendar.set(year, month, day);
                note.setDate(calendar.getTimeInMillis());

                note.save();
                finish();
                break;
            case R.id.action_pic:
                /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image*//*");
                startActivityForResult(intent, 0);*/
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_PIC);

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_PIC) {
                if (data == null) {
                    Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
                } else {
                    Uri uri = data.getData();
                    Bitmap bitmap = getOriginalBitmap(uri);
                    SpannableString ss = getBitmapMime(bitmap, uri);
                    insertIntoEditText(ss);
                }
            }
        }
    }

    private SpannableString getBitmapMime(Bitmap pic, Uri uri) {
        int imgWidth = pic.getWidth();
        int imgHeight = pic.getHeight();

        detail_content = (EditText)findViewById(R.id.detail_content);


        ViewTreeObserver vto = detail_content.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                detail_content.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                float mImgViewWidth = detail_content.getWidth();
                mInsertedImgWidth = mImgViewWidth * 0.8f;
            }
        });

        if (imgWidth >= mInsertedImgWidth) {
            float scale = (float) mInsertedImgWidth / imgWidth;
            Matrix mx = new Matrix();
            mx.setScale(scale, scale);
            pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true);
        }
        String smile = uri.getPath();
        SpannableString ss = new SpannableString(smile);
        ImageSpan span = new ImageSpan(this, pic);
        ss.setSpan(span, 0, smile.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
    private void insertIntoEditText(SpannableString ss) {

        detail_content = (EditText)findViewById(R.id.detail_content);

        Editable et = detail_content.getText();
        int start = detail_content.getSelectionStart();
        et.insert(start, ss);
        detail_content.setText(et);
        detail_content.setSelection(start + ss.length());
    }
    private Bitmap getOriginalBitmap(Uri photoUri) {
        if (photoUri == null) {
            return null;
        }
        Bitmap bitmap = null;
        try {
            ContentResolver conReslv = getContentResolver();
            bitmap = MediaStore.Images.Media.getBitmap(conReslv, photoUri);
        } catch (Exception e) {
            Log.e("Exception", "Media.getBitmap failed", e);
        }
        return bitmap;
    }


}
