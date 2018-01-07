package org.androidtown.customview;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

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
