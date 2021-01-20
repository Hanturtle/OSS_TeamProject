package com.hooooong.androidmemosqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MemoActivity extends AppCompatActivity {
    private EditText mTitleEditText;
    private EditText mContentsEditText;
    private TextView mInfoTextView;
    private long mMemoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //메모 입력하는 레이아웃 띄우고, 변수에 해당하는 id값 찾아오기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_memo);

        mTitleEditText = findViewById(R.id.title_edit);
        mContentsEditText = findViewById(R.id.content_edit);
        mInfoTextView = findViewById(R.id.info_text);


        //넘겨받은 intent로 부터 해당 데이터의 제목, 내용, 글번호를 불러온다.
        Intent intent = getIntent();
        if(intent != null){
            mMemoId = intent.getLongExtra("id", -1);
            mTitleEditText.setText(intent.getStringExtra("title"));
            mContentsEditText.setText(intent.getStringExtra("contents"));
            mInfoTextView.setText(String.valueOf(mMemoId)+"번째 글입니다.");
        }
    }




    //버튼 이벤트 -> 입력한 정보들 db에 저장하고 목록으로 넘어가기
    public void onClickMain(View view) {

        //입력받을것이 제목과 내용이므로 문자열 변수 선언해줌
        String title = mTitleEditText.getText().toString();
        String contents = mContentsEditText.getText().toString();

        //글의 번호는 자동으로 생성되므로 제목과 내용만 추가해준다.
        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(MemoContract.MemoEntry.COMLUMN_NAME_CONTENTS, contents);

        //db에 추가하기
        SQLiteDatabase db = MemoDbHelper.getInstance(this).getWritableDatabase();

        super.onBackPressed();
    }
}