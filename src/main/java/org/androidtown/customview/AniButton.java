package org.androidtown.customview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static org.androidtown.customview.R.styleable.AniButton_animation;

/**
 * animation 속성이 true일 경우
 *
 * scale 애니메이션을 사용해서
 * 클릭시 살짝 커졌다 작아지는 버튼을 만들기.
 *
 *
 */


public class AniButton extends AppCompatButton implements View.OnTouchListener {// 리소스에서 만든 Button을 상속받음.

    boolean animation = true;

    public AniButton(Context context, AttributeSet attrs) { // 버튼객체
        super(context, attrs);


        // 1. attrs.xml에 정의된 속성을 가져온다.
        TypedArray typed = context.obtainStyledAttributes(attrs, R.styleable.AniButton); //리소스에 정의해논 속성값을 가지고 오는것.
        // 2. 해당 이름으로 정의된 속성의 개수를 가져온다.
        int size = typed.getIndexCount(); // 배열이나 리스트가 아니므로 그냥 getIndexCount로 한것인가?
        Log.d("AniButton", "size=" + size);
        // 3. 반복문을 돌면서 해당 속성에 대한 처리를 해준다.
        for (int i = 0; i < size; i++) {
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
