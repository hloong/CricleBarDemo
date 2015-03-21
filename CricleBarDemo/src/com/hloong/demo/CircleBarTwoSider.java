package com.hloong.demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class CircleBarTwoSider extends View {
    private RectF mColorWheelRectangle = new RectF();//圆圈的矩形范围
    private Paint mDefaultWheelPaint;  //绘制底部灰色圆圈的画笔
    private Paint mColorWheelPaintLeft; //绘制蓝色扇形的画笔 左边的
    private Paint mColorWheelPaintRight; //绘制蓝色扇形的画笔 右边的
    
    private Paint textPaint; //中间文字的画笔
    private float mColorWheelRadius; //圆圈普通状态下的半径
    private float circleStrokeWidth; //圆圈的线条粗细 

    private float pressExtraStrokeWidth;//按下状态下增加的圆圈线条增加的粗细   

    private String mText;//中间文字内容
    private int mCount; //为了达到数字增加效果而添加的变量，他和mText其实代表一个意思
    private float mSweepAnglePer;  //为了达到蓝色扇形增加效果而添加的变量，他和mSweepAngle其实代表一个意思   

    private float mSweepAngle; //扇形弧度
    private int mTextSize;//文字颜色
    BarAnimation anim;//动画类
    private int TIME = 300;
    
    public CircleBarTwoSider(Context context) {
        super(context);
        init(null, 0);
    }
    public CircleBarTwoSider(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    public CircleBarTwoSider(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    
    private void init(AttributeSet attrs, int defStyle) {
        circleStrokeWidth = MyUtils.dip2px(getContext(), 10);
        pressExtraStrokeWidth = MyUtils.dip2px(getContext(), 2);
        mTextSize = MyUtils.dip2px(getContext(), 20);
                                                                          
        mColorWheelPaintRight = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorWheelPaintRight.setColor(0xFF29a6f6);
        mColorWheelPaintRight.setStyle(Paint.Style.STROKE);
        mColorWheelPaintRight.setStrokeMiter(0);
//        mColorWheelPaintRight.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形
        mColorWheelPaintRight.setStrokeWidth(circleStrokeWidth);
        
        mColorWheelPaintLeft = new Paint(Paint.ANTI_ALIAS_FLAG);
        mColorWheelPaintLeft.setColor(0xFF29a6f6);
        mColorWheelPaintLeft.setStyle(Paint.Style.STROKE);
        mColorWheelPaintLeft.setStrokeMiter(0);
//        mColorWheelPaintLeft.setStrokeCap(Paint.Cap.ROUND);//开启显示边缘为圆形
        mColorWheelPaintLeft.setStrokeWidth(circleStrokeWidth);
                                                                          
        mDefaultWheelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDefaultWheelPaint.setColor(0xFFeeefef);
        mDefaultWheelPaint.setStyle(Paint.Style.STROKE);
        mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
        textPaint.setColor(0xFF333333);
        textPaint.setStyle(Style.FILL_AND_STROKE);
        textPaint.setTextAlign(Align.LEFT);
        textPaint.setTextSize(mTextSize);
                                                                          
        mText = "0";
        mSweepAngle = 1;
                                                                          
        anim = new BarAnimation();
        anim.setDuration(TIME);//设置动画时常
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        canvas.drawArc(mColorWheelRectangle, -90, 360, false, mDefaultWheelPaint);
        canvas.drawArc(mColorWheelRectangle, 90, mSweepAnglePer, false, mColorWheelPaintRight);
        canvas.drawArc(mColorWheelRectangle, 90, -mSweepAnglePer, false, mColorWheelPaintLeft);
        Rect bounds = new Rect();
        String textstr="可以买："+mCount+"";
        textPaint.getTextBounds(textstr, 0, textstr.length(), bounds);
        canvas.drawText(textstr+"", (mColorWheelRectangle.centerX()) - (textPaint.measureText(textstr) / 2),
                mColorWheelRectangle.centerY() + bounds.height() / 2,textPaint);
    }
    
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int min = Math.min(width, height);
        setMeasuredDimension(min, min);
        mColorWheelRadius = min - circleStrokeWidth -pressExtraStrokeWidth ;
        mColorWheelRectangle.set(circleStrokeWidth+pressExtraStrokeWidth, circleStrokeWidth+pressExtraStrokeWidth,
                mColorWheelRadius, mColorWheelRadius);
    }
    
    @Override
    public void setPressed(boolean pressed) {
        // TODO Auto-generated method stub
        Log.i("hloong","call setPressed ");
        if (pressed) {
            mColorWheelPaintLeft.setColor(0xFF165da6);
            mColorWheelPaintRight.setColor(0xFF165da6);
            textPaint.setColor(0xFF070707);
            mColorWheelPaintLeft.setStrokeWidth(circleStrokeWidth+pressExtraStrokeWidth);
            mColorWheelPaintRight.setStrokeWidth(circleStrokeWidth+pressExtraStrokeWidth);
            mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth+pressExtraStrokeWidth);
            textPaint.setTextSize(mTextSize-pressExtraStrokeWidth);
        } else {
            mColorWheelPaintLeft.setColor(0xFF29a6f6);
            mColorWheelPaintRight.setColor(0xFF29a6f6);
            textPaint.setColor(0xFF333333);
            mColorWheelPaintLeft.setStrokeWidth(circleStrokeWidth);
            mColorWheelPaintRight.setStrokeWidth(circleStrokeWidth);
            mDefaultWheelPaint.setStrokeWidth(circleStrokeWidth);
            textPaint.setTextSize(mTextSize);
        }
        super.setPressed(pressed);
        this.invalidate();
    }
    
    public void startCustomAnimation(){
        this.startAnimation(anim);
        
    }
                                                                      
    public void setText(String text){
        mText = text;
        this.startAnimation(anim);
    }
                                                                      
    public void setSweepAngle(float sweepAngle){
        mSweepAngle = sweepAngle;
    }
               
    
    /**
     * 动画
     * @author long 
     * 2015-3-20下午2:11:22
     */
    public class BarAnimation extends Animation {
        /**
         * Initializes expand collapse animation, has two types, collapse (1) and expand (0).
         * @param view The view to animate
         * @param type The type of animation: 0 will expand from gone and 0 size to visible and layout size defined in xml.
         * 1 will collapse view and set to gone
         * 动画类利用了applyTransformation参数中的interpolatedTime参数(从0到1)的变化特点，
         * 实现了该View的某个属性随时间改变而改变。原理是在每次系统调用animation的applyTransformation()方法时，
         * 改变mSweepAnglePer，mCount的值，
         * 然后调用postInvalidate()不停的绘制view。
         */
        public BarAnimation() {
        }
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            //mSweepAnglePer，mCount这两个属性只是动画过程中要用到的临时属性，
            //mText和mSweepAngle才是动画结束之后表示扇形弧度和中间数值的真实值。
            if (interpolatedTime < 1.0f) {
                mSweepAnglePer =  interpolatedTime * mSweepAngle;
                mCount = (int)(interpolatedTime * Float.parseFloat(mText));
            } else {
                mSweepAnglePer = mSweepAngle;
                mCount = Integer.parseInt(mText);
            }
            postInvalidate();
        }
    }
}
