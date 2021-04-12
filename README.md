# Speech Recognize Calendar (음성 인터페이스를 활용한 캘린더 일정 관리 앱)

**By <a href="http://github.com/jinhyung426/" target="_blank">Jinhyung Park</a>, Youngjin Moon, Sujin Lee, Jiwon Lee from 2020 NAVER CAMPUS HACKDAY**

<p align="center">
  <img width="1000" height="330" src="https://github.com/jinhyung426/SpeechRecognizeCalender/blob/feature/keystore_modify/app/utils/teaser1.png">
</p>

<p align="center">
  <img width="800" height="400" src="https://github.com/jinhyung426/SpeechRecognizeCalender/blob/feature/keystore_modify/app/utils/teaser2.png">
</p>

### 요약

**2020 NAVER CAMPUS HACKDAY (2020.05.05 - 2020.05.20)**

Google Calendar와 연동하여 사용자의 음성으로 일정을 생성, 변경, 삭제하는 어플을 MVVM Architecture을 이용하여 개발함.<br/>
Google REST API를 이용하여 Oauth를 통해 AccessToken을 받아오는 Google Login 기능을 구현했으며,<br/>
입력 받은 음성에 따라 일정을 생성할 수 있도록 LSTM Model을 설계하고 학습 DB를 구축하는 역할을 맡음.<br/>
안드로이드에서 지원하는 Tensorflow Lite 버전 관련 문제로 인해 머신러닝을 이용하여 음성을 인식하는 방법 대신, <br/>
Regex(정규식)을 이용하여 사용자로부터 입력받은 값을 처리하여 일정을 생성/변경/삭제 기능을 구현하는 것으로 구현함.
<br/>
<br/>
### Naming Convention

* Java 내의 변수, 함수명은 Camel Case를 사용한다    ex) getCalander()
* java Class 파일명은 Pascal Case를 사용한다     ex) MainActivity
* xml의 파일명, id는 Snake Case를 사용한다       ex)activity_main

* Component의 변수명은 (종류)+(의미/용도) ex) mEditTextPassword
* xml의 id는 (xml의 종류)+(액티비티/프래그먼트명)+(view종류)+(의미/용도) ex) activity_main_tv_password


### Packaging

* 패키징의 기준은 화면단위가 아닌 기능기준이다 ex) ui/event/EventDetailActivity, network/login/LoginService


### 기타 참고사항
* common 패키지는 앱 전반에 공통적으로 사용되는것들을 모아놓는다 (특히 common-util-AppConstants에는 상수값 저장)
* BaseActivity는 Activity가 상속받는 부모액티비티로, init 추상함수, 다이얼로그띄우기, 토스트띄우기, 로딩띄우기 등 여러 액티비티에서 공통으로 사용하는 작업들이 들어있다.
* 네트워크를 담당하는 Service 클래스와 ViewModel은 Callback매개변수를 통해서 데이터를 주고받는다

