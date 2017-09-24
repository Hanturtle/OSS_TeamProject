# SQLite 를 사용한 메모장 만들기

### 설명
____________________________________________________

![MemoSQL](https://github.com/Hooooong/DAY14_SQLiteMemo/blob/master/image/SQLMemo.gif)

- SQLite 를 통해 메모 작성
- 간단한 데이터 읽기, 삽입, 수정, 삭제(수정과 삭제는 마지막 데이터를 이용하였다.)

### KeyPoint
____________________________________________________

- SQLite

  - 참조 : [SQLite](https://github.com/Hooooong/DAY13_Activity_etc)

- DAO 를 통한 데이터 접근

  - DAO : __D__ ata __A__ ccess __O__ bject 의 약자로 이름과 같이 Data Access 에 관련된 Objet이다.

  - 단일 Data 에 관련된 접근 및 갱신을 주로 담당하는 Object 로  `Memo` 데이터에 관련된 접근과 갱신에 대한 로직 처리가 담겨있다.

  - DB에 대한 생성 및 접근은 `DBHelper`에 대한 Class 를 통해 이루어지기 때문에 DAO 생성자를 통해 연결한다.

  - 데이터 `C(Create)`, `R(Read)`, `U(Update)`, `D(Delete)` 에 관련된 메소드를 작성한다.

  ```java
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
      // 삽입 로직 작성
  }

  /**
   * R : 읽기에 관련된 함수
   *
   * @return
   */
  public ArrayList<Memo> read(String columns[], String where) {
      // 읽기 로직 작성
  }

  /**
   * U : 수정에 관련된 함수
   *
   * @param memo
   */
  public void update(Memo memo) {
      // 수정 로직 작성
  }

  /**
   * D: 삭제에 관련된 함수
   */
  public void delete() {
      // 삭제 로직 작성
  }
  ```

### Code Review
____________________________________________________

- DBHelper.java

  - DB 생성 및 연결에 관련된 class

  - 생성자를 통해 DB의 유무와 Version Check 를 통해 DB 갱신을 한다.

  ```java
  public class DBHelper extends SQLiteOpenHelper {

      // DB name
      private static final String DB_NAME = "sqlite.db";
      // DB version
      private static final int DB_VERSION = 1;

      public DBHelper(Context context) {
          // super 에서 넘겨받은 데이터베이스가 생성되어 있는지 확인한 후
          // 1. 없으면 onCreate 를 호출
          // 2. 있으면 version 을 체크해서 생성되어 있는 DB 보다 version 이 높으면 onUpgrade 를 호출한다.
          super(context, DB_NAME, null, DB_VERSION);
      }

      // DB를 새로 생성할 때 호출되는 함수
      @Override
      public void onCreate(SQLiteDatabase db) {
          // 최초 생성할 테이블 상의
          String createDB = "CREATE TABLE `memo`                                \n" +
                            "( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, \n" +
                            "  `title` TEXT, \n" +
                            "  `content` TEXT, \n" +
                            "  `nDate` TEXT \n" +
                            ")";
          // DB Query 실행
          db.execSQL(createDB);
      }

      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
          // version check 를 통해 version 별로 업데이트 되는 내역 또한 반영이 되어야 한다.
          if( oldVersion < newVersion ) {
              // 쿼리
          }
      }
  }
  ```

- Memo.java

  - 개념 모델에 해당하는 Class 이다.

  ```java
  public class Memo {

      private int id;
      private String title;
      private String content;
      private String nDate;

      /**
       * 생성자 Overloading 을 한 경우
       * 기본 생성자도 작성을 꼭!!!해줘야 한다.
       */
      public Memo(){

      }

      /**
       * 생성자 Overloading
       *
       * @param title
       * @param content
       */
      public Memo(String title, String content){
          this.title = title;
          this.content = content;
      }

      public int getId() {
          return id;
      }

      public void setId(int id) {
          this.id = id;
      }

      public String getTitle() {
          return title;
      }

      public void setTitle(String title) {
          this.title = title;
      }

      public String getContent() {
          return content;
      }

      public void setContent(String content) {
          this.content = content;
      }

      public String getnDate() {
          return nDate;
      }

      public void setnDate(String nDate) {
          this.nDate = nDate;
      }
  }
  ```

- MemoDAO.java

  - `Memo` 에 관련된 데이터만 접근하는 Class 이다.

  - 주로 `CRUD` 에 대한 메소드들로 이루어져 있다.

  ```java
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
  ```

- MainActivity.java

  - 실질적인 데이터 활용 Class 이다.

  - 데이터들을 보여주고, 입력하는 Class 로 DB에서 가져온 Data 를 보여주거나, 입력한 Data 를 DB로 보내는 View 이다.

  ```java
  public class MainActivity extends AppCompatActivity implements View.OnClickListener {

      MemoDAO memoDAO;

      EditText editTitle, editContent;
      Button btnCreate, btnRead, btnUpdate, btnDelete;
      TextView textResult;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          initView();
          initListener();
          init();
      }

      /**
       * memoDAO 를 닫아준다.
       */
      @Override
      protected void onDestroy() {
          super.onDestroy();
          if (memoDAO != null) {
              memoDAO.close();
          }
      }

      /**
       * onClick 정의
       * @param v
       */
      @Override
      public void onClick(View v) {
          switch (v.getId()) {
              case R.id.btnCreate:
                  createAfterRead();
                  break;
              case R.id.btnRead:
                  read();
                  break;
              case R.id.btnUpdate:
                  updateAfterRead();
                  break;
              case R.id.btnDelete:
                  deleteAfterRead();
                  break;
          }
      }

      /**
       * 화면에 작성한 값들을 Memo 객체로 생성
       *
       * @return
       */
      private Memo getMemoFormScreen() {
          // 1. 화면에 입력된 값을 가져온다.
          String title = editTitle.getText().toString();
          String content = editContent.getText().toString();
          // 2. Memo 객체를 하나 생성하여 값을 담는다.
          Memo memo = new Memo(title, content);
          return memo;
      }

      /**
       * EditText 초기화
       */
      private void resetScreen() {
          editTitle.setText("");
          editContent.setText("");
          // 초기화 한 후 focus 준다.
          editTitle.requestFocus();
      }

      /**
       * Toast 출력
       *
       * @param message
       */
      private void showInfo(String message) {
          // Toast Message 출력
          Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
      }

      /**
       * CREATE 실행
       */
      public void create(Memo memo) {
          // DB 실행
          memoDAO.create(memo);
      }

      /**
       * READ 실행
       */
      public void read() {
          // 0. 쿼리 생성
          // 1. DB 실행한 후 결과값을 받아온다.
          ArrayList<Memo> memoList = memoDAO.read(new String[]{"id", "title", "content", "nDate"}, null);
          // 2. 결과값 출력
          textResult.setText("");
          if (memoList.size() != 0) {
              for (Memo memo : memoList) {
                  textResult.append(memo.toString()+"\n");
              }
          }

      }

      /**
       * UPDATE 실행
       */
      public void update(Memo memo) {
          // DB 실행
          memoDAO.update(memo);
      }

      /**
       * DELETE 실행
       */
      public void delete() {
          // DB 실행
          memoDAO.delete();
      }

      /**
       * CREATE 후 처리
       */
      private void createAfterRead() {
          Memo memo = getMemoFormScreen();
          // 1. 생성
          create(memo);
          // 2. 결과 완료
          showInfo("등록 완료!");
          // 3. 목록 갱신
          resetScreen();
          read();
      }

      /**
       * UPDATE 후 처리
       */
      private void updateAfterRead() {
          Memo memo = getMemoFormScreen();
          // 1. 생성
          update(memo);
          // 2. 결과 완료
          showInfo("수정 완료!");
          // 3. 목록 갱신
          resetScreen();
          read();
      }

      /**
       * DELETE 후 처리
       */
      private void deleteAfterRead() {
          ArrayList<Memo> memoList = memoDAO.read(new String[]{"id", "title", "content", "nDate"}, null);

          if (memoList.size() != 0) {
              delete();
              // 2. 결과 완료
              showInfo("삭제 완료!");
              // 3. 목록 갱신
              resetScreen();
              read();
          } else {
              showInfo("데이터가 없음!");
          }
      }

      /**
       * View 초기화
       */
      private void initView() {
          editTitle = (EditText) findViewById(R.id.editTitle);
          editContent = (EditText) findViewById(R.id.editContent);

          btnCreate = (Button) findViewById(R.id.btnCreate);
          btnRead = (Button) findViewById(R.id.btnRead);
          btnUpdate = (Button) findViewById(R.id.btnUpdate);
          btnDelete = (Button) findViewById(R.id.btnDelete);

          textResult = (TextView) findViewById(R.id.textResult);
      }

      /**
       * Listener 초기화
       */
      private void initListener() {
          btnCreate.setOnClickListener(this);
          btnRead.setOnClickListener(this);
          btnUpdate.setOnClickListener(this);
          btnDelete.setOnClickListener(this);
      }

      /**
       * memoDAO 초기화;
       */
      private void init() {
          memoDAO = new MemoDAO(getBaseContext());
          read();
      }

  }
  ```
