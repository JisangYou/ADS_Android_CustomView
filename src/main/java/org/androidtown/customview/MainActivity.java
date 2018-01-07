package org.androidtown.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

/**
 * 커스텀뷰 만들기
 * 1. 커스텀 속성을 atrs.xml 파일에 정의
 *
 * 2. 커스텀할 객체(위젯)을 상속받은 후 재정의
 *
 * 3. 커스텀한 위젯을 레이아웃.xml에서 태그로 사용
 *
 */



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

























