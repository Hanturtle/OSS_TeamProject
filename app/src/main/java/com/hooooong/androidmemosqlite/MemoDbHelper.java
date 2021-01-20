package com.hooooong.androidmemosqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MemoDbHelper extends SQLiteOpenHelper {
    private static MemoDbHelper sInstance;

    //Memo이름을 가진 DB 생성,title, contents 필드 포함
    //ID 값은 정수형으로 자동으로 생성하여 primary key로 설정함
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "Memo.db";
    private static final String SQL_CREATE_ENTRIES =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                    MemoContract.MemoEntry.TABLE_NAME,
                    MemoContract.MemoEntry._ID,
                    MemoContract.MemoEntry.COLUMN_NAME_TITLE,
                    MemoContract.MemoEntry.COMLUMN_NAME_CONTENTS);

<<<<<<< HEAD
=======
    //테이블 삭제
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MemoContract.MemoEntry.TABLE_NAME;
>>>>>>> a185f19376ade7b6281fdf9e33c23394f2a633d7

    //객체를 가져와 MemoDbHelper을 수행함
    public static MemoDbHelper getInstance(Context context) {
        if(sInstance == null){
            sInstance = new MemoDbHelper(context);
        }
        return sInstance;
    }

    //가져온 context를 해당 값들에 넘겨줌
    private MemoDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    //자동으로 생성된 번호에 해당하는 데이터들을 삭제/수정하는 메소드
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
<<<<<<< HEAD
=======
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
>>>>>>> a185f19376ade7b6281fdf9e33c23394f2a633d7
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }
}
