package com.example.yangg.wifidemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义wifi动画
 * Created by yangg on 2017/7/2.
 */

public class Wifi extends View {

    private Paint paint;
    private int baseLength;
    private Handler mHandler = new Handler();

    public Wifi(Context context) {
        this(context, null);
    }

    public Wifi(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Wifi(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);//没有锯齿
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //这个方法,就是触发 onDraw方法
                invalidate();
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
    }



    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        baseLength = Math.min(w, h);
    }

    private float signaSize = 4f;//四个信号

    private float shouldExisSize = 4f;

    /**
     * onDraw只执行一次
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        shouldExisSize++;
        if (shouldExisSize > 4) {
            shouldExisSize = 1;
        }
        canvas.save();
        canvas.translate(0, baseLength / signaSize);//移动到中间
        RectF rectF;//直接写死了，不好
        paint.setStrokeWidth(50);//粗细
        //计算出一个基准园半径:跟当前空间的宽度和高度之间的
        float baseRadius = baseLength / 2 / signaSize;//为了不超出去: 上下的距离
        for (int i = 0; i < signaSize; i++) {
            if (i >= signaSize - shouldExisSize) {

                float radius = baseRadius * i;
                //左边的坐标,右边的坐标
                rectF = new RectF(radius, radius, baseLength - radius, baseLength - radius);//左上又下
                if (i < signaSize - 1) {
                    //回执一个弧形
                    paint.setStyle(Paint.Style.STROKE);//绘制空心的
                    //0,0, ,
                    //可能是扇形，也一刻是弧形  起始角度,画90度
                    canvas.drawArc(rectF, -135, 90, false, paint);//从-135都顺时针走90度
                } else {
                    //回执一个扇形
                    //回执一个实心的图形
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawArc(rectF, -135, 90, true, paint);//从-135都顺时针走90度
                }
            }
        }
        canvas.restore();

    }
}
