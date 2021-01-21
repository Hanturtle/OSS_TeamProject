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

>안드로이드스튜디오 4.0.1
Runtime version : 1.8.0
VM : OpenJDK 64-Bit Server VM by JetBrains s.r.o

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

<br><br>

## 설계 및 구현
### 기존 코드 분석
> [SQLite를 사용한 메모장 만들기](https://github.com/Hooooong/DAY14_SQLiteMemo/blob/master/README.md)

<br>

### 수정 내역
1. 액티비티 분할하여 제목으로 메모 리스트 만든 후 페이지 구현 
2. 플로팅 작업 버튼 사용
3. 버튼 클릭 이벤트가 아닌 아이템을 꾹 눌렀을 때 삭제될 수 있도록 변경
4. 기능별로 잘 작동하였는지 Toast 메시지 띄우기
5. 전체적인 디자인 통일
6. 앱 아이콘 변경

<br>

![](https://images.velog.io/images/hanturtle/post/f09fc218-7f5b-41ed-822f-f1075204f3ef/image.png)<br>![](https://images.velog.io/images/hanturtle/post/b2e1362c-39b8-4eb7-8744-0d5e61812a82/image.png)<br>![](https://images.velog.io/images/hanturtle/post/c28687e4-cce5-491b-93ea-31a667d6bb0f/image.png)

<br><br>

## 결과물
### 시연 영상

[![테스트용 폰 시연영상](https://img.youtube.com/vi/LQX-537yh3E/0.jpg)](https://www.youtube.com/watch?v=LQX-537yh3E&feature=youtu.be)
<br>

[![에뮬레이터 시연영상](https://img.youtube.com/vi/zHqHyl9sqew/0.jpg)](https://www.youtube.com/watch?v=zHqHyl9sqew&feature=youtu.be)

<br>

### 앱 설치 링크
>[APK 다운로드](https://blog.naver.com/tjfdnjs0829/222214839904)

다운로드 페이지 댓글로 피드백이나 질문주시면 감사하겠습니다 😊
<br><br>
