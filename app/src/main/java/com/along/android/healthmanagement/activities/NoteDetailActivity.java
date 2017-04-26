package com.along.android.healthmanagement.activities;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

        try{
            String selectedNoteItemId = getIntent().getStringExtra("selectedNoteItemId");
            note = EntityManager.findById(Note.class,Long.parseLong(selectedNoteItemId));
            detail_title = (EditText)findViewById(R.id.detail_title);
            detail_content = (EditText)findViewById(R.id.detail_content);
            detail_title.setText(note.getTitle());
            detail_content.setText(note.getContent());
        }catch(Exception e)
        {

        }
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
                SharedPreferences sp = getSharedPreferences("Login", Context.MODE_PRIVATE);
                long uid = sp.getLong("uid", 0);
                User user = EntityManager.findById(User.class, uid);

                try{
                    String selectedNoteItemId = getIntent().getStringExtra("selectedNoteItemId");
                    note = EntityManager.findById(Note.class,Long.parseLong(selectedNoteItemId));

                }catch (Exception e){

                    note = new Note();
                }
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

                if(detail_title.getText().toString().equals("")||detail_content.getText().toString().equals("")){
                    Toast.makeText(NoteDetailActivity.this, "title and content cannot be empty", Toast.LENGTH_SHORT).show();
                }else {
                    note.save();
                    finish();
                }
                break;
            case R.id.action_pic:
                Intent intent = new Intent(NoteDetailActivity.this,GetPictureActivity.class);
                startActivityForResult(intent,1);

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            String path = data.getExtras().getString("result");//得到新Activity 关闭后返回的数据

            ContentResolver resolver = getContentResolver();
            // 获得图片的uri
            Uri originalUri = Uri.parse("file://" + path);

            Bitmap bitmap = null;

            try {
                bitmap = BitmapFactory.decodeStream(resolver.openInputStream(originalUri));
            } catch (Exception e) {
            }

            System.out.println(bitmap == null);

            ImageSpan imageSpan = new ImageSpan(NoteDetailActivity.this, bitmap);

            // 创建一个SpannableString对象，以便插入用ImageSpan对象封装的图像
            String tempUrl = "<img src=" + path + " />";
            SpannableString spannableString = new SpannableString(tempUrl);
            // 用ImageSpan对象替换你指定的字符串
            spannableString.setSpan(imageSpan, 0, tempUrl.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            // 将选择的图片追加到EditText中光标所在位置
            int index = detail_content.getSelectionStart(); // 获取光标所在位置
            Editable edit_text = detail_content.getEditableText();
            if (index < 0 || index >= edit_text.length()) {
                edit_text.append(spannableString);
            } else {
                edit_text.insert(index, spannableString);
            }
        }
    }


}
