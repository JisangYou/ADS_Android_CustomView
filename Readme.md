# ADS04 Android

## 수업 내용

- CustomWidget 학습
- CustomView(그림 그릴 수 있는 뷰) 학습

## Code Review

1. MainActivity

```Java
public class MainActivity extends AppCompatActivity  {

   ConstraintLayout stage;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stage = (ConstraintLayout) findViewById(R.id.stage);

        CustomView cv= new CustomView(this); // 커스텀뷰 객체 생성
        cv.setX(300); //생성한 곳의 위치값을 지정
        cv.setY(300); // 위치값 지정
        stage.addView(cv); // 추가z


        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "하이", Toast.LENGTH_SHORT).show();
            }
        }); // 커스텀 위젯 호출시

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(intent);
            }
        }); //커스텀 드로우 뷰 호출시

    }

}
```
- 메인에 커스텀 뷰 생성
--------------------------------------------------------------------------------------------------------------
- 메인 xml

```Java
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/stage"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="org.androidtown.customview.MainActivity">


    <org.androidtown.customview.CustomView
        android:layout_width="242dp"
        android:layout_height="150dp"
        android:layout_marginBottom="8dp"
        android:layout_marginLeft="8dp"
        android:layout_marginRight="8dp"
        android:layout_marginTop="8dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.052" />

    <org.androidtown.customview.AniButton

        xmlns:custom="http://schemas.android.com/apk/res-auto"
        android:id="@+id/button1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello CustomWidget"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        custom:animation="true" /><!--xmlns:custom을 해줘야 custom이 가능함. -->

    <org.androidtown.customview.AniButton

        xmlns:custom="http://schemas.android.com/apk/res-auto"

        android:id="@+id/button2"

        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Go to draw"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        custom:animation="true"
        custom:layout_constraintHorizontal_bias="0.501"
        custom:layout_constraintVertical_bias="0.744" />

</android.support.constraint.ConstraintLayout>
```

2. CustomView 

```Java
public class CustomView extends View {

    //내가 소스코드에서 생성할때
    public CustomView(Context context) {
        super(context);
    }

    // xml에서 태그로 사용할 때 시스템에서 호출해주는 생성자
    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //사각형 그리기

        Paint paint = new Paint();//물감 - 겉모양을 결정하는 도구
        paint.setColor(Color.MAGENTA);

        // 화면에 사각형 그리기
        //drawRect는 붓, canvas는 그려지는 곳
        canvas.drawRect(100, 100, 200, 200, paint); // 각 꼭지점 (x,y,z,k,paint)

    }
}
```

- 뷰를 상속받아 해당 뷰를 커스터마이징
- 처음 생성시 뷰의 context를 받아 사용함.
- 이 코드에 의해 메인에 세팅됨.
```JAVA
   stage = (ConstraintLayout) findViewById(R.id.stage);

        CustomView cv= new CustomView(this); // 커스텀뷰 객체 생성
        cv.setX(300); //생성한 곳의 위치값을 지정
        cv.setY(300); // 위치값 지정
        stage.addView(cv); // 추가z
```

3. AniButton

```Java
public class AniButton extends AppCompatButton implements View.OnTouchListener {// 리소스에서 만든 Button을 상속받음.
                                                                                //AniButton을 타고 들어가보면, mainActivity xml에 정의 되어 있음.

    boolean animation = true;

    public AniButton(Context context, AttributeSet attrs) { // 버튼객체
        super(context, attrs);


        // 1. attrs.xml에 정의된 속성을 가져온다.
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.AniButton); //리소스에 정의해논 속성값을 가지고 오는것.
        // 2. 해당 이름으로 정의된 속성의 개수를 가져온다.
        int size = typed.getIndexCount(); // 정의된 속성의 갯수를 size에..
        Log.d("AniButton", "size=" + size);
        // 3. 반복문을 돌면서 해당 속성에 대한 처리를 해준다.
        for (int i = 0; i < size; i++) { // 속성의 갯수를 for문을 돌려 꺼낸다.
            //3.1 현재 배열에 있는 속성 아이디 가져오기
            int current_attr = typed.getIndex(i); // 리소스에 정의된 속성값에 대한 주소값을 가지고 옴.
            switch (current_attr) {
                case AniButton_animation:
                   String animation = typed.getString(current_attr);

                    if ("true".equals(animation)) { // true를 먼저 써주면, nullexception을 발생시키지 않는 팁
                        String currentText = getText().toString();
                        setText("[animation]\n" + currentText);
                        this.animation = true;


//                        setOnClickListener(new OnClickListener() { // 애니버튼은 하나의 객체이기 때문에,
//                            @Override
//                            public void onClick(View v) {
//                                scale();
//                            }
//                        });
                    }
                    break;
                case R.styleable.AniButton_delimeter:
                    break;
            }
        }


        setOnTouchListener(this);

    }



    public void scale() {
        if (animation) {
            ObjectAnimator aniX = ObjectAnimator.ofFloat(
                    this, //가 움직일 대상
                    "scaleX", //나. 애니메이션 속성(움직임)
                    2
            );
            ObjectAnimator aniY = ObjectAnimator.ofFloat(
                    this, //가 움직일 대상
                    "scaleY", //나. 애니메이션 속성(움직임)
                    2
            );

            AnimatorSet aniSet = new AnimatorSet();
            aniSet.playTogether(aniY, aniX);
            aniSet.setDuration(3000);
            aniSet.setInterpolator(new LinearInterpolator());


            aniSet.start();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                scale();
                break;
        }

        return false;
    }
}
```

4. drawActivity

```Java
public class DrawActivity extends AppCompatActivity {

    FrameLayout stage;
    RadioGroup radioColor;
    DrawView draw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        //라디오 버튼이 선택되면 draw의 paint 색상을 바꿔준다.
        radioColor = (RadioGroup) findViewById(R.id.radioColor);

        stage = (FrameLayout) findViewById(R.id.stage);
        draw = new DrawView(this);
        stage.addView(draw);


        radioColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) { // 그룹안에 있는 아이디를 찾아오게 하는 파라미터
                switch (checkedId) {// 인자값으로 넘어온 값을 통해 구분 가능
                    case R.id.radioButton_black:

                        draw.setColor(Color.BLACK);
                        break;
                    case R.id.radioButton_cyan:
                        draw.setColor(Color.CYAN);
                        break;
                    case R.id.radioButton_magenta:
                        draw.setColor(Color.MAGENTA);
                        break;
                    case R.id.radioButton_yellow:
                        draw.setColor(Color.YELLOW);
                        break;
                }
            }
        });
    }


}
```

5. drawView,PathTool

```Java
public class DrawView extends View {
    Paint paint;
    Path currentPath;
    PathTool tool;
    List<PathTool> paths = new ArrayList<>();

    // 그림이 그려지는 좌표

//    // 원의 크기
//    float r = 10;
//
//    // 좌표 값을 저장하는 저장소
//    ArrayList<Float> xs = new ArrayList<>();
//    ArrayList<Float> ys = new ArrayList<>();

    // 소스코드에서 사용하기 때문에 생성자 파라미터는 context만 필요
    public DrawView(Context context) {
        super(context);
        paint = new Paint();
        init();


    }

    private void init() {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5F);
        setColor(Color.BLACK);
    }

    public void setColor(int color) {

        tool = new PathTool();
        tool.setColor(color);
        currentPath = tool;
        paths.add(tool);
    }


    //화면을 그려주는 onDraw 오버라이드

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        // 1. 화면에 터치가 되면.
//        // 2.연속해서 그림을 그려준다.
//        //2.1. 터치된 좌표에 작은 동그라미를 그려준다.
//        if (xs.size()>0 ) {
//
//            for (int i = 0; i < xs.size(); i++) {
//                canvas.drawCircle(xs.get(i), ys.get(i), r, paint);
//            }
//        }
        for (PathTool tool : paths) {
            paint.setColor(tool.getColor());
            canvas.drawPath(tool, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 이전점과 현재점 사이를 그리지 않는다.
                currentPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                //이전점과 현재점 사이를 그린다.
                currentPath.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                //nothing to do
                break;
        }

        //터치가 일어나면 좌표값을 세팅해준다.

        //터치가 일어나면 패스를 해당 좌표로 이동한다.
        currentPath.lineTo(event.getX(), event.getY());

        // onDraw를 호출하는 메서드를 호출
        invalidate(); // <- 다른 언어에서는 대부분 그림을 그려주는 함수를 호출하는 메서드는
        // 기존 그림을 유지하는데, 안드로이드는 지운다.

        // 리턴이 false일 경우는 touch 이벤트를 연속해서 발생시키지 않는다.
        // 즉, 드래그를 하면, ontouchEvent가 재 호출되지 않는다.
        return true;
    }


}

class PathTool extends Path {
    private int color;



    public void setColor(int color) {
        this.color = color;

    }

    public int getColor() {
        return this.color;
    }
}
```
- 선이 끊기지 않게 터치이벤트를 하는 것 고려
- 색깔의 자연스러운 변경을 위해 ArrayList를 쓰는 것 고려
- 안드로이드 액티비티에는 좌표값이 있는 것을 고려


※ resource/value 아래 attrs.xml

```Java
<resources>
<declare-styleable name="AniButton"> <!-- 재정의할 객체이름-->
    <attr name="animation" format="string"/>
    <attr name="delimeter" format="string"/>
</declare-styleable>
</resources>
```




## 보충설명

### 커스텀 뷰 만드는 순서

- 커스텀 뷰의 레이아웃을 결정
- 레이아웃 xml로 설정할 수 있는 항목을 attrs.xml에 작성
- 커스텀 뷰의 클래스를 작성
- 메인 앱의 레이아웃에 삽입
- 메인 액티비티에서 커스텀 뷰를 컨트롤한다.


### Canvas, Paint, Path 사용

```Java
public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        MyView m = new MyView(MainActivity.this);
        setContentView(m);
    } // end of onCreate
} // end of class
 
class MyView extends View {
    public MyView(Context context) {
        super(context); // 부모의 인자값이 있는 생성자를 호출한다
    }
 
    @Override
    protected void onDraw(Canvas canvas) { // 화면을 그려주는 작업
        Paint paint = new Paint(); // 화면에 그려줄 도구를 셋팅하는 객체
        paint.setColor(Color.RED); // 색상을 지정
 
        setBackgroundColor(Color.GREEN); // 배경색을 지정
        canvas.drawRect(100,100,200,200,paint); // 사각형의 좌상,우하 좌표
        canvas.drawCircle(300, 300, 40, paint); // 원의중심 x,y, 반지름,paint
 
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10f);    // 선의 굵기
        canvas.drawLine(400, 100, 500, 150, paint); // 직선
 
        // path 자취 만들기
        Path path = new Path();
        path.moveTo(20, 100); // 자취 이동
        path.lineTo(100, 200); // 자취 직선
        path.cubicTo(150, 30, 200, 300, 300, 200); // 자취 베이지곡선
 
        paint.setColor(Color.MAGENTA);
 
        canvas.drawPath(path, paint);
    }
}

```
- paint : 물감, 겉모양을 결정하는 도구의 역할
- canvas : 그려지는 곳
- drawrect : 붓과 같은 역할?
- setColor : 색깔 지정
- invalidate() : onDraw를 불러오는 함수
- Path : 그림이 그려지는 좌표


### 참고(추후 체크 필요)
```Java
안드로이드 뷰는 화면에 그려지기 전에 아래 그림과 같은 몇 단계의 과정을 거친다. 커스텀뷰를 만들기 위해서는 뷰의 드로잉 과정을 이해해야 한다.

위 단계를 3개의 과정으로 나눌 수 있다. 하나의 과정이 실행되면 항상 Draws 단계에서 마무리 된다.

Animate 과정
Layout 과정
Draw 과정
각 과정의 시작점은 아래와 같다.

Animate 과정은 View의 animate() 메서드에 의해서 시작된다.
Layout 과정은 requestLayout() 메서드에 의해서 시작된다.
Draw 과정은 invalidate() 메서드에 의해서 시작된다.
안드로이드에서 커스텀뷰는 두 종류로 나뉜다.

자식뷰가 있는 뷰. 즉 뷰그룹
자식뷰가 없는 뷰.
안드로이드에서 뷰를 화면에 배치시키는 책임은 뷰를 담고 있는 뷰그룹에게 있다. 따라서 자식이 없는 뷰일 경우 Layout 과정에서 Measure 단계만 구현하는 경우가 많다. 커스텀뷰를 만들기 위해서는 Measure 단계를 정확하게 이해하는 것이 가장 중요하다. 각 뷰의 정확한 크기를 알아야  뷰를 위치 시키고 그릴 수 있기 때문이다.
```


#### 출처: http://blog.burt.pe.kr/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-%EC%BB%A4%EC%8A%A4%ED%85%80%EB%B7%B0-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0/
#### 출처: http://bitsoul.tistory.com/59?category=623707 [Happy Programmer~]

## TODO

- CustomView 복습 및 테스트
- 기능 추가하기(글씨 굵게하기)

## Retrospect

- 커스텀이라는 것이 들어가다 보니 고려할게 많아서 헷갈림.

## Output

- 예정 중

