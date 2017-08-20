package com.example.yangg.wifidemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.R.attr.radius;
import static android.R.attr.singleUser;

/**
 * Created by yangg on 2017/7/2.
 */

public class MyWifidemo extends View {

    private float baseLength;
    private Paint paint;

    public MyWifidemo(Context context) {
        this(context,null);
    }

    public MyWifidemo(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyWifidemo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private Handler mHandler = new Handler();
    //初始化 画笔画布等信息
    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);//设置抗拒出

//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                invalidate();
//                mHandler.postDelayed(this,1000);
//            }
//        },1000);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        baseLength = Math.min(w,h);
    }

    private float signSize  = 4f; //定义总共有四个线
    private float shouldExistSize = 4f;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        shouldExistSize++;
        if (shouldExistSize>4){
            shouldExistSize=1;
        }
        RectF rectF;
        canvas.save();
        canvas.translate(0,baseLength/signSize);
        paint.setStrokeWidth(10);

        //计算出基准园半径  为了不超出所画的区域
        float baseRadius = baseLength/2/signSize;//得到的是没一个信号格子之间的距离

        for (int i = 0; i <signSize; i++) {
            if (i>= signSize - shouldExistSize) {
                float radius = baseRadius * i;  //得到的是,第一个第二个信号的半径
                rectF = new RectF(radius, radius, baseLength - radius, baseLength - radius);
                if (i < signSize - 1) {
                    //相当与是中间矩形距离画布的距离,由大变小
                    //绘制信号  需要一个RectF
                    paint.setStyle(Paint.Style.STROKE);//设置喂空心的

                    canvas.drawArc(rectF, -135, 90, false, paint);
                } else {
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawArc(rectF, -135, 90, true, paint);
                }
            }
        }

        canvas.restore();
        invalidate();
        SystemClock.sleep(1000);
    }
}
