package com.yhyy.snow;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;


public class SnowView extends View {

    Bitmap bitmap = null;
    MySnow mySnows[];
    Random r = new Random();
    Matrix m = new Matrix();
    Paint p = new Paint();

    int mW = 480;
    int mH = 800;
    float de = 0f;

    public void setWH(int pW, int pH, float de,int num) {
        this.mW = pW;
        this.mH = pH;
        this.de = de;
        mySnows = new MySnow[num];
    }

    public SnowView(Context context) {
        super(context);
    }

    public SnowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SnowView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        for (int i = 0; i < mySnows.length; i++) {
            MySnow rect = mySnows[i];
            int h = rect.h;
            h--;
            if (h <= 0) {
                rect.y += rect.g;//y轴 = y轴加g速度
                canvas.save();
                m.reset();
                m.setScale(rect.s, rect.s);//矩阵等比放大缩小
                canvas.setMatrix(m);
                p.setAlpha(rect.a);//设置透明度
                canvas.drawBitmap(bitmap, rect.x, rect.y, p);
                canvas.restore();
            }
            rect.h = h;
            if (rect.y >= mH) {
                rect.init();
            }
            if (rect.x >= mW || rect.x < -50) {
                rect.init();
            }
            mySnows[i] = rect;
        }
    }


    public void loadFlower(int draw) {
        Resources r = this.getContext().getResources();
        bitmap = ((BitmapDrawable) r.getDrawable(draw)).getBitmap();
    }

    public void addRect() {
        for (int i = 0; i < mySnows.length; i++) {
            mySnows[i] = new MySnow();
        }
    }

    public void inva() {
        invalidate();
    }


    class MySnow {
        int x;//x轴（横向图片加载分布范围）
        int y;//y轴（从最上面显示降落）
        float s;//图片放大缩小倍数
        int a;//图片透明度
        int h;//下落的高度
        int g;//下落的速度

        public void init() {
            float aa = r.nextFloat();//返回下一个介于0.0和1.0之间的伪随机浮点数
            this.x = r.nextInt(mW - 200) + 100;
            this.y = 0;
            if (aa == 1) {
                this.s = 1.2f;
            } else if (aa <= 0.2) {
                this.s = 0.4f;
            } else {
                this.s = aa;
            }
            this.a = r.nextInt(155) + 100;
            this.h = r.nextInt(100);
            this.g = (r.nextInt(4) + 2) * 2;
        }

        public MySnow() {
            super();
            init();
        }

    }

}