음성 인터페이스를 활용한 캘린더 일정 관리 앱 개발
========
2020-NAVER-CAMPUS-HACKDAY


Naming Convention
-----------
* java내의 변수/함수명은 Camel Case를 사용한다 ex) getCalander()
* java Class 파일명은 Pascal Case를 사용한다 ex)MainActivity
* xml의 파일명, id는 Snake Case를 사용한다 ex)activity_main

* Component의 변수명은 (종류)+(의미/용도) ex) mEditTextPassword
* xml의 id는 (xml의 종류)+(액티비티/프래그먼트명)+(view종류)+(의미/용도) ex) activity_main_tv_password


Packaging
-----------
* 패키징의 기준은 화면단위가 아닌 기능기준이다 ex) ui/event/EventDetailActivity, network/login/LoginService


기타 참고사항
(권장사항은 아니며, 기본 프로젝트에 대한 설명)
-----------
* common 패키지는 앱 전반에 공통적으로 사용되는것들을 모아놓는다 (특히 common-util-AppConstants에는 상수값 저장)
* BaseActivity는 Activity가 상속받는 부모액티비티로, init 추상함수, 다이얼로그띄우기, 토스트띄우기, 로딩띄우기 등 여러 액티비티에서 공통으로 사용하는 작업들이 들어있다.
* 네트워크를 담당하는 Service 클래스와 ViewModel은 Callback매개변수를 통해서 데이터를 주고받는다

