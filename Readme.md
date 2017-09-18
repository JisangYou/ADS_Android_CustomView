# 그릴 수 있는 메모(?)장
## 필요한 툴
- paint : 물감, 겉모양을 결정하는 도구의 역할
- canvas : 그려지는 곳
- drawrect : 붓
- setColor : 색깔 지정
- invalidate() : onDraw를 불러오는 함수
- Path : 그림이 그려지는 좌표

```Java
      Paint paint = new Paint();
      paint.setColor(Color.MAGENTA);
      canvas.drawRect(100, 100, 200, 200, paint);
      // 각 꼭지점 (x,y,z,k,paint)
```

## 예제

```Java
public class DrawView extends View {
    Paint paint;
    Path currentPath; // 그림이 그려지는 좌표!

    List<PathTool> paths = new ArrayList<>(); //좌표값을 리스트에 저장

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


    public void setColor(int color) { // pathTOOL이라는 클래스를 만들고 객체 생성! 해당 클래스에 함수에서 색을 받아와 어레이 리스트에 담는다(?)

        PathTool tool = new PathTool();
        tool.setColor(color);
        currentPath = tool;
        paths.add(tool);
    }


    //화면을 그려주는 onDraw 오버라이드

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        for (PathTool tool : paths) {//반복문을 돌면서, 점이 선이 되도록 한다.
            paint.setColor(tool.getColor()); //tool에서 받아온 색깔을 물감 역할을 하는 paint에 적용
            canvas.drawPath(tool, paint);// 현재의 그려지는 곳에 해당 색깔을 대입해서 그린다.
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
```
- 선이 끊기지 않게 터치이벤트를 하는 것 고려
- 색깔의 자연스러운 변경을 위해 ArrayList를 쓰는 것 고려
- 안드로이드 액티비티에는 좌표값이 있는 것을 고려
