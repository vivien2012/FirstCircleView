package com.vivien.firstcircleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by vivien on 16/11/21.
 */

public class FirstCircleView extends View {

    /**
     * 圆环背景色
     */
    private int circleBgColor;

    /**
     * 圆环前景色
     */
    private int circleFgColor;

    /**
     * 圈的宽度
     */
    private int circleWidth;

    /**
     * 速度
     */
    private int circleSpeed;

    /**
     * 画笔
     */
    private Paint mPaint;

    /**
     * 进度
     */
    private int mProgress = 90;

    private boolean isContinue = true;

    public FirstCircleView(Context context) {
        this(context, null);
    }

    public FirstCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 必要的初始化，获得一些自定义的值
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public FirstCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FirstCircleView, defStyleAttr, 0);
        circleBgColor = a.getColor(R.styleable.FirstCircleView_circleBgColor, Color.GRAY);
        circleFgColor = a.getColor(R.styleable.FirstCircleView_circleFgColor, Color.YELLOW);
        circleWidth = a.getDimensionPixelSize(R.styleable.FirstCircleView_circleWidth, 26);
        circleSpeed = a.getInt(R.styleable.FirstCircleView_circleSpeed, 20);
        a.recycle();

        mPaint = new Paint();
        //绘制线程
        new Thread() {
            @Override
            public void run() {
                while (isContinue) {
                    mProgress++;
                    if (mProgress == 360) {
                        mProgress = 0;
                        int tempColor = circleBgColor;
                        circleBgColor = circleFgColor;
                        circleFgColor = tempColor;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(circleSpeed);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int center = getWidth() / 2; //获取圆心的坐标
        int radius = center - circleWidth; //半径
        mPaint.setStrokeWidth(circleWidth); //设置圆环的宽度
        mPaint.setAntiAlias(true); //消除锯齿
        mPaint.setStyle(Paint.Style.STROKE); //设置空心
        RectF oval = new RectF(center - radius, center - radius, center + radius, center + radius); //用于定义圆弧的形状和大小的界限
        // 第一颜色的圈完整，第二颜色跑
        mPaint.setColor(circleBgColor); //设置圆环的颜色
        canvas.drawCircle(center, center, radius, mPaint); //画出圆环
        mPaint.setColor(circleFgColor); //设置圆环进度的颜色
        canvas.drawArc(oval, -90, mProgress, false, mPaint); // 根据进度画圆弧

    }

    public void destory() {
        isContinue = false;
    }
}
