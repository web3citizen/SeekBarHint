package com.liuwp.seekbarhint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author:  liuwp
 * Email:   liuwuping1206@163.com
 * Date:    2015/12/4
 * Description:
 */
public class TriangleRectangleTextView extends TextView {

    private static final int DEFAULT_BG_COLOR = 0xff41c7cd;
    private Paint bgPaint;
    private int bgColor = DEFAULT_BG_COLOR;



    public TriangleRectangleTextView(Context context) {
        this(context, null);
    }

    public TriangleRectangleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleRectangleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initAttrs(attrs);
        init();
    }

    private void initAttrs(AttributeSet attrs) {
    }

    private void init() {
        bgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bgPaint.setStrokeWidth(4);
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setColor(bgColor);
        bgPaint.setAntiAlias(true);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        drawBackgroud(canvas);
        super.onDraw(canvas);
    }


    /**
     * ==a===========b===
     *   =         =
     *   =         =
     * ==g==f     d==c==
     *       =  =
     *        =
     *        e
     * @param canvas
     */
    private void drawBackgroud(Canvas canvas){
        int height = getHeight();
        int width = getWidth();
        int triWidth=getWidth()/5;
        int triHeight=getHeight()/4;
        setPadding(0,0,0,triHeight);


        Point a = new Point(0, 0);
        Point b = new Point(width, 0);
        Point c = new Point(width, height - triHeight);
        Point d = new Point((width/2)+triWidth/2, height - triHeight);
        Point e = new Point((width/2), height);
        Point f = new Point((width/2)-triWidth/2, height - triHeight);
        Point g = new Point(0, height - triHeight);

        Path path = new Path();
        path.moveTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(d.x, d.y);
        path.lineTo(e.x, e.y);
        path.lineTo(f.x, f.y);
        path.lineTo(g.x, g.y);
        canvas.drawPath(path, bgPaint);


    }
}
