package com.hooooong.androidmemosqlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_INSERT = 1000;
    private MemoAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //앱 UI의 기본 작업을 트리거하는 원형 버튼인 플로팅 버튼을 사용함
        //이 버튼을 클릭하였을때 MemoActivity로 액티비티를 넘겨줌
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, MemoActivity.class), REQUEST_CODE_INSERT);
            }
        });


        ListView listView = findViewById(R.id.memo_list);

        //리싸이클러뷰로 보여지지않고 리스트로 보여지므로 커서를 사용하여 선택한 id에 해당하는 데이터들의 작업이 이루어지게함
        Cursor cursor = getMemoCursor();

        mAdapter = new MemoAdapter(this, cursor);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MemoActivity.class);

                Cursor cursor = (Cursor) adapterView.getItemAtPosition(i);
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE));
                String contents = cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COMLUMN_NAME_CONTENTS));

                intent.putExtra("id", l);
                intent.putExtra("title", title);
                intent.putExtra("contents", contents);

                startActivityForResult(intent, MainActivity.REQUEST_CODE_INSERT);
            }
        });


        //버튼을 사용하지 않고 아이템을 길게 클릭하였을때 삭제창을 띄우기
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final long deleteId = l;

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("메모 삭제")
                        .setMessage("메모를 삭제하시겠습니까?")
                        .setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SQLiteDatabase database = MemoDbHelper.getInstance(MainActivity.this).getWritableDatabase();
                                //테이블이름이 Memo.db인 테이블에서 선택한 id에 해당하는 내용을 지운다.
                                int deleteCount = database.delete(MemoContract.MemoEntry.TABLE_NAME,
                                        MemoContract.MemoEntry._ID + " = " + deleteId, null);

                                //삭제가 성공적으로 이루어졌는지를 판별하기위해 toast를 띄운다.
                                if(deleteCount == 0){
                                    Toast.makeText(MainActivity.this, "삭제에 문제가 발생하였습니다", Toast.LENGTH_LONG).show();
                                } else{
                                    Toast.makeText(MainActivity.this, "삭제되었습니다", Toast.LENGTH_LONG).show();
                                    mAdapter.swapCursor(getMemoCursor());
                                }
                            }
                        })
                        .setNegativeButton("취소", null)
                        .show();

                return true;
            }
        });
    }

    private Cursor getMemoCursor() {
        MemoDbHelper helper = MemoDbHelper.getInstance(this);
        return helper.getReadableDatabase()
                .query(MemoContract.MemoEntry.TABLE_NAME
                        ,null, null, null, null, null, MemoContract.MemoEntry._ID + " DESC");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MainActivity.REQUEST_CODE_INSERT && resultCode == RESULT_OK){
            mAdapter.swapCursor(getMemoCursor());
        }
    }

    private class MemoAdapter extends CursorAdapter {

        public MemoAdapter(Context context, Cursor c) {
            super(context, c, false);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE)));
        }
    }
}
