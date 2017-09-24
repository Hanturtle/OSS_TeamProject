package com.hooooong.androidmemosqlite.domain;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hooooong.androidmemosqlite.DBHelper;

import java.util.ArrayList;

/**
 * DAO : Data Access Object
 * Data 조작을 담당
 * <p>
 * 사용 예)
 * <p>
 * MemoDAO dao = new MemoDAO();                 1. DAO 객체 생성
 * String insertQuery = "insert into ~~"        2. Query 생성
 * dao.create(query);                           3. Query 실행
 */
public class MemoDAO {

    DBHelper dbHelper;

    public MemoDAO(Context context) {
        // 1. 데이터베이스에 연결
        this.dbHelper = new DBHelper(context);
    }

    /**
     * C: 삽입에 관련된 함수
     *
     * @param memo
     */
    public void create(Memo memo) {
        SQLiteDatabase con = dbHelper.getWritableDatabase();

        String createQuery = " INSERT INTO memo(title, content, nDate) ";
        createQuery += "VALUES('"+ memo.getTitle()+"', '"+ memo.getContent() +"', datetime('now', 'localtime') )";

        con.execSQL(createQuery);
        //con.insert(테이블명, );
        con.close();
    }

    /**
     * R : 읽기에 관련된 함수
     *
     * @return
     */
    public ArrayList<Memo> read(String columns[], String where) {

        String query_prefix = "SELECT ";
        String query_midifix = "";
        for (int i = 0 ; i<columns.length; i++) {
            query_midifix += " " + columns[i] + ((i != columns.length-1)? " ," : " ");
        }
        String query_suffix = "FROM memo";
        if (where != null) {
            query_suffix += " " + where;
        }

        String query = query_prefix + query_midifix + query_suffix;

        // 1. 반환할 결과 타입을 정의
        ArrayList<Memo> memoList = new ArrayList<>();

        SQLiteDatabase con = dbHelper.getReadableDatabase();

        // 2. 조작
        Cursor cursor = con.rawQuery(query, null);
        //con.query(테이블명, columns[], selection 인자(where 절), selectionArgs 인자, groupBy 인자, having 인자, orderBy 인자);
        while (cursor.moveToNext()) {
            Memo memo = new Memo();
            for (String col : columns) {
                int index = cursor.getColumnIndex(col);
                switch (col) {
                    case "id":
                        memo.setId(cursor.getInt(index));
                        break;
                    case "title":
                        memo.setTitle(cursor.getString(index));
                        break;
                    case "content":
                        memo.setContent(cursor.getString(index));
                        break;
                    case "nDate":
                        memo.setnDate(cursor.getString(index));
                        break;
                }
            }
            memoList.add(memo);
        }

        // 3. 연결 해제
        con.close();

        // 데이터를 리턴
        return memoList;
    }

    /**
     * U : 수정에 관련된 함수
     *
     * @param memo
     */
    public void update(Memo memo) {
        SQLiteDatabase con = dbHelper.getWritableDatabase();

        String updateQuery = " UPDATE memo " +
                " SET title = '"+ memo.getTitle()+"', " +
                " content = '"+ memo.getContent() +"', " +
                " nDate = datetime('now', 'localtime')  " +
                " WHERE id = (SELECT max(id) from Memo) ";

        con.execSQL(updateQuery);
        con.close();
    }

    /**
     * D: 삭제에 관련된 함수
     */
    public void delete() {
        SQLiteDatabase con = dbHelper.getWritableDatabase();

        String deleteQuery = " DELETE FROM memo " +
                " WHERE id = (SELECT max(id) from Memo)";
        con.execSQL(deleteQuery);
        con.close();
    }

    /**
     * DB Helper 를 닫는 메소드
     */
    public void close() {
        dbHelper.close();
    }
}
