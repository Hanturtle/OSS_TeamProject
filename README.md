# SQLite를 활용한 안드로이드 메모장 앱 
조선대 20년 겨울학기 오픈소스프로젝트 팀프로젝트 4조<br>
<br>

## 목적
### 개발 주제
2학년때 배운 **자바** 와 3학년 2학기에 배운 **DB** 를 활용할 수 있는 프로젝트를 찾다가 **'SQLite를 사용한 안드로이드 메모장 어플'** 을 만들게되었다.
<br>

### Fork한 프로젝트
>[Hooooong/DAY14_SQLiteMemo](https://github.com/Hooooong/DAY14_SQLiteMemo)

<br>

### 구현 환경<br>

>안드로이드스튜디오 4.0.1 <br> Runtime version : 1.8.0 <br> VM : OpenJDK 64-Bit Server VM by JetBrains s.r.o

<br>

### 주요 일정
단계 | 일정 | 산출물
|:-----: | :-----: | :-----: |
요구사항 분석 및 프로젝트 계획 | 2021-01-17 | 계획서(주제 선정, 수정할 레파지토리)
설계 | 2021-01-17 ~ 2021-01-18 | 기존 코드 분석 자료 및 수정 내역 계획서
구현 | 2021-01-19 ~ 2021-01-20 | 소스 코드
테스팅 | 2021-01-20 ~ 2021-01-21 | 시연 영상 및 발표 자료
유지보수 | 2021-01-22 ~ |
<br>

### 👩🏻‍💻 팀원 구성 👩🏻‍💻

[![](https://images.velog.io/images/hanturtle/post/48ec6b69-5993-4748-9c9a-d6a8716a00d7/image.png)](https://github.com/Hanturtle)  | [![](https://images.velog.io/images/hanturtle/post/9cac4b4e-c9ea-49bf-b7a0-af67f78ad4e9/image.png)](https://github.com/shk2120)  | [![](https://images.velog.io/images/hanturtle/post/188e050e-f78e-4e6b-971b-d8bcb1e07717/image.png)](https://github.com/inhaaa)  | [![](https://images.velog.io/images/hanturtle/post/bd573aac-4902-4188-977a-ad5d339b10a3/image.png)](https://github.com/Jeongyeon999) 
--------- | --------- | --------- | ---------
한지원 (팀장)| 김수현 | 박인하 | 유정연
Fork 후, 전체 버전 수정<br>액티비티 분할<br>전체 DB 관리| DB 삭제 기능 구현<br>앱 디자인 수정 |DB 등록 기능 구현<br>앱 디자인 수정 | DB 수정 기능 구현<br>앱 아이콘 등록

<br><br><br>

## 설계 및 구현
### 기존 코드 분석
> [SQLite를 사용한 메모장 만들기](https://github.com/Hooooong/DAY14_SQLiteMemo/blob/master/README.md)

<br>

### 수정 내역
> xml은 간단하게 구성만 보여주고 소스파일에 자세한 소스 주석으로 설명하였음.


1. 액티비티 분할하여 제목으로 메모 리스트 만든 후 페이지 구현 
+ activity_main.xml
	- 리스트뷰를 사용하여 작성된 전체 메모의 제목만 불러온다.
``` xml
<ListView
        android:id="@+id/memo_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"/>
```
<br>

+ activity_action_memo.xml
	- 간단한 TextView, EditText 등을 사용하여 메모 작성 및 읽기 화면을 구성하였다.
```
<LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:padding="16dp">
        
        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content">

            <LinearLayout
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:gravity="center_horizontal"
                android:orientation="horizontal">

                <TextView
                    android:id="@+id/tvTitleAM" />

            </LinearLayout>
        </FrameLayout>

        <TextView
            android:id="@+id/info_text" />

        <EditText
            android:id="@+id/title_edit" />

        <EditText
            android:id="@+id/content_edit"
            android:background="@drawable/border_text"/>

        <Button
            android:id="@+id/btnSaveAM" />


    </LinearLayout>
```
<br><br>

2. 플로팅 작업 버튼 사용
+ activity_main.xml
	- [플로팅 작업 버튼](https://developer.android.com/guide/topics/ui/floating-action-button?hl=ko) 개발자 문서를 참고하여 구현하였다.
``` xml
<com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/ic_add_white_24dp"
        app:fabSize="auto"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.9"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.9" />
```
<br>

+ MainActivity.java
	- 앱 UI의 기본 작업을 트리거하는 원형 버튼인 플로팅 버튼을 사용함<br>이 버튼을 클릭하였을때 MemoActivity로 액티비티를 넘겨줌
``` java
        FloatingActionButton floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, MemoActivity.class), REQUEST_CODE_INSERT);
            }
        });
```
<br><br>

3. 버튼 클릭 이벤트가 아닌 아이템을 꾹 눌렀을 때 삭제될 수 있도록 변경
+ MainActivity.java
	- 버튼을 사용하지 않고 아이템을 길게 클릭하였을때 삭제창을 띄우기
```java
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
```
<br>

4. 기능별로 잘 작동하였는지 Toast 메시지 띄우기
**DB관련 코드 수정** 
> [SQLite](https://developer.android.com/training/data-storage/sqlite?hl=ko#java) 개발자용 문서를 참고하였음.
+ 추가
    - MemoActivity.java
	``` java
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

		```
<br>
	- MemoDbHelper.java
<br>

	``` java
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
	```
<br>

+ 삭제
    - MemoDbHelper.java
<br>
        
	``` java
	//테이블 삭제
	    private static final String SQL_DELETE_ENTRIES =
		    "DROP TABLE IF EXISTS " + MemoContract.MemoEntry.TABLE_NAME;
	```
<br>

+ MemoActivity.java
	- Toast 메시지 띄우기
	``` java
	//커서는 -1 위치에서 시작 함
	if(mMemoId == -1) {
	    long newRowId = db.insert(MemoContract.MemoEntry.TABLE_NAME, null, contentValues);

	    //저장관련 소스 성공 및 실패 toast 띄워주기
	    if (newRowId == -1) {
		Toast.makeText(this, "저장에 문제가 발생하였습니다", Toast.LENGTH_LONG).show();
		;
	    } else {
		Toast.makeText(this, "메모가 저장되었습니다", Toast.LENGTH_LONG).show();
		setResult(RESULT_OK);
	    }
	}

	//수정 관련 소스 성공 및 실패 toast 띄워주기
	else{
	    int count = db.update(MemoContract.MemoEntry.TABLE_NAME, contentValues,
		    MemoContract.MemoEntry._ID + "=" + mMemoId, null);

	    if(count == 0){
		Toast.makeText(this, "수정에 문제가 발생하였습니다", Toast.LENGTH_LONG).show();
	    } else {
		Toast.makeText(this, "메모가 수정되었습니다", Toast.LENGTH_LONG).show();
		setResult(RESULT_OK);
	    }
	}
	```
<br>

5. 앱 아이콘 변경 <br>
![](https://images.velog.io/images/hanturtle/post/f09fc218-7f5b-41ed-822f-f1075204f3ef/image.png) <br>
6. 전체적인 디자인 통일
<br>![](https://images.velog.io/images/hanturtle/post/b2e1362c-39b8-4eb7-8744-0d5e61812a82/image.png)<br>
**리스트 액티비티, 메모 작성 액티비티, 메모 보기 액티비티** <br>
![](https://images.velog.io/images/hanturtle/post/c28687e4-cce5-491b-93ea-31a667d6bb0f/image.png) <br>
**Toast 메시지, 아이템 길게 클릭하였을때 메모 삭제** <br><br>

## 결과물
### 시연 영상
#### 테스트용 폰 시연 영상 <br>
[![테스트용 폰 시연영상](https://img.youtube.com/vi/LQX-537yh3E/0.jpg)](https://www.youtube.com/watch?v=LQX-537yh3E&feature=youtu.be)
<br>

#### 에뮬레이터 시연 영상 <br>
[![에뮬레이터 시연영상](https://img.youtube.com/vi/zHqHyl9sqew/0.jpg)](https://www.youtube.com/watch?v=zHqHyl9sqew&feature=youtu.be)

<br>

### 앱 설치 링크
>[APK 다운로드](https://blog.naver.com/tjfdnjs0829/222214839904)

<br>
다운로드 페이지 댓글로 피드백이나 질문주시면 감사하겠습니다 😊
<br><br>
