package com.vbeeon.iotdbs.utils.widget.barcodescan;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import com.vbeeon.iotdbs.R;


public class ViewFinderView extends View implements IViewFinder {
    private static final String TAG = "ViewFinderView";

    private Rect mFramingRect;

    private static final float PORTRAIT_WIDTH_RATIO = 6f / 8;
    private static final float PORTRAIT_WIDTH_HEIGHT_RATIO = 0.75f;

    private static final float LANDSCAPE_HEIGHT_RATIO = 5f / 8;
    private static final float LANDSCAPE_WIDTH_HEIGHT_RATIO = 1.4f;
    private static final int MIN_DIMENSION_DIFF = 50;

    private static final float DEFAULT_SQUARE_DIMENSION_RATIO = 5f / 8;

    private static final int[] SCANNER_ALPHA = {0, 64, 128, 192, 255, 192, 128, 64};
    private int scannerAlpha;
    private static final int POINT_SIZE = 10;
    private static final long ANIMATION_DELAY = 80l;

    private final int mDefaultLaserColor = getResources().getColor(R.color.viewfinder_laser);
    private final int mDefaultMaskColor = getResources().getColor(R.color.viewfinder_mask);
    private final int mDefaultBorderColor = getResources().getColor(R.color.viewfinder_border);
    private final int mDefaultStatusColor = getResources().getColor(R.color.viewfinder_status_color);
    private final int mDefaultBorderStrokeWidth = getResources().getInteger(R.integer.viewfinder_border_width);
    private final int mDefaultBorderLineLength = getResources().getInteger(R.integer.viewfinder_border_length);
    private final int mDefaultBorderLineMargin = getResources().getInteger(R.integer.viewfinder_border_margin);

    protected Paint mLaserPaint;
    protected Paint mFinderMaskPaint;
    protected Paint mBorderPaint;
    protected Paint mStatusPaint;
    protected int mBorderLineLength;
    protected int mBorderLineMargin;
    protected boolean mSquareViewFinder;
    private boolean mIsLaserEnabled;
    private float mBordersAlpha;
    private int mTopViewFinder = 0;
    private int mWidthViewFinder = 100;
    private int mHeightViewFinder = 100;
    private int mViewFinderOffset = 0;
    private int mStatusColor =Color.WHITE;
    private float mStatusSize= 20;
    private int mStatusMargin= 0;
    private String mStatusText="";
    public ViewFinderView(Context context) {
        super(context);
        init();
    }

    public ViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(attributeSet);
    }

    private void init(AttributeSet attributeSet) {

    }

    private void init() {
        //set up laser paint
        mLaserPaint = new Paint();
        mLaserPaint.setColor(mDefaultLaserColor);
        mLaserPaint.setStyle(Paint.Style.FILL);

        //finder mask paint
        mFinderMaskPaint = new Paint();
        mFinderMaskPaint.setColor(mDefaultMaskColor);

        //border paint
        mBorderPaint = new Paint();
        mBorderPaint.setColor(mDefaultBorderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mDefaultBorderStrokeWidth);
        mBorderPaint.setAntiAlias(true);

        mStatusPaint = new Paint();
        mStatusPaint.setColor(Color.WHITE);
        mStatusPaint.setStyle(Paint.Style.FILL);
        mStatusPaint.setTextSize(20);

        mBorderLineLength = mDefaultBorderLineLength;
        mBorderLineMargin = mDefaultBorderLineMargin;
    }

    @Override
    public void setLaserColor(int laserColor) {
        mLaserPaint.setColor(laserColor);
    }

    @Override
    public void setMaskColor(int maskColor) {
        mFinderMaskPaint.setColor(maskColor);
    }

    @Override
    public void setBorderColor(int borderColor) {
        mBorderPaint.setColor(borderColor);
    }

    @Override
    public void setBorderStrokeWidth(int borderStrokeWidth) {
        mBorderPaint.setStrokeWidth(borderStrokeWidth);
    }

    @Override
    public void setBorderLineLength(int borderLineLength) {
        mBorderLineLength = borderLineLength;
    }

    @Override
    public void setLaserEnabled(boolean isLaserEnabled) {
        mIsLaserEnabled = isLaserEnabled;
    }

    @Override
    public void setBorderCornerRounded(boolean isBorderCornersRounded) {
        if (isBorderCornersRounded) {
            mBorderPaint.setStrokeJoin(Paint.Join.ROUND);
        } else {
            mBorderPaint.setStrokeJoin(Paint.Join.BEVEL);
        }
    }

    @Override
    public void setBorderAlpha(float alpha) {
        int colorAlpha = (int) (255 * alpha);
        mBordersAlpha = alpha;
        mBorderPaint.setAlpha(colorAlpha);
    }

    @Override
    public void setBorderCornerRadius(int borderCornersRadius) {
        mBorderPaint.setPathEffect(new CornerPathEffect(borderCornersRadius));
    }

    @Override
    public void setViewFinderOffset(int offset) {
        mViewFinderOffset = offset;
    }

    // TODO: Need a better way to configure this. Revisit when working on 2.0
    @Override
    public void setSquareViewFinder(boolean set) {
        mSquareViewFinder = set;
    }

    public void setupViewFinder() {
        updateFramingRect();
        invalidate();
    }

    public Rect getFramingRect() {
        return mFramingRect;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (getFramingRect() == null) {
            return;
        }

        drawViewFinderMask(canvas);
        drawViewFinderBorder(canvas);
        drawViewFinderStatus(canvas);
        drawViewFInderButton(canvas);
        if (mIsLaserEnabled) {
            drawLaser(canvas);
        }
    }

    private void drawViewFInderButton(Canvas canvas) {

    }

    private void drawViewFinderStatus(Canvas canvas) {
        Rect framingRect = getFramingRect();
        mStatusPaint.setTextSize(mStatusSize);
        mStatusPaint.setColor(mStatusColor);
        drawMultiline(canvas,mStatusText,framingRect.left + mStatusMargin, framingRect.bottom + mStatusMargin);
    }

    public void drawMultiline(Canvas canvas,String str, int dx, int dy)
    {   int y = dy;
        for (String line: str.split("\n"))
        {
            Rect r = new Rect();
            canvas.getClipBounds(r);
            int cWidth = r.width();
            mStatusPaint.setTextAlign(Paint.Align.LEFT);
            mStatusPaint.getTextBounds(line, 0, line.length(), r);
            float x = cWidth / 2f - r.width() / 2f - r.left;
            canvas.drawText(line, x, y, mStatusPaint);
            y += -mStatusPaint.ascent() + mStatusPaint.descent();
        }
    }

    public void drawViewFinderMask(Canvas canvas) {
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        Rect framingRect = getFramingRect();

        canvas.drawRect(0, 0, width, framingRect.top, mFinderMaskPaint);
        canvas.drawRect(0, framingRect.top, framingRect.left, framingRect.bottom + 1, mFinderMaskPaint);
        canvas.drawRect(framingRect.right + 1, framingRect.top, width, framingRect.bottom + 1, mFinderMaskPaint);
        canvas.drawRect(0, framingRect.bottom + 1, width, height, mFinderMaskPaint);
    }

    public void drawViewFinderBorder(Canvas canvas) {
        Rect framingRect = getFramingRect();

        // Top-left corner
        Path path = new Path();
        path.moveTo(framingRect.left + mBorderLineMargin , framingRect.top + mBorderLineLength + mBorderLineMargin);
        path.lineTo(framingRect.left + mBorderLineMargin, framingRect.top+ mBorderLineMargin );
        path.lineTo(framingRect.left  + mBorderLineLength+ mBorderLineMargin, framingRect.top+ mBorderLineMargin);
        canvas.drawPath(path, mBorderPaint);

        // Top-right corner
        path.moveTo(framingRect.right- mBorderLineMargin, framingRect.top + mBorderLineLength+ mBorderLineMargin);
        path.lineTo(framingRect.right- mBorderLineMargin, framingRect.top+ mBorderLineMargin);
        path.lineTo(framingRect.right - mBorderLineLength- mBorderLineMargin, framingRect.top+ mBorderLineMargin);
        canvas.drawPath(path, mBorderPaint);

        // Bottom-right corner
        path.moveTo(framingRect.right- mBorderLineMargin, framingRect.bottom - mBorderLineLength- mBorderLineMargin);
        path.lineTo(framingRect.right- mBorderLineMargin, framingRect.bottom- mBorderLineMargin);
        path.lineTo(framingRect.right - mBorderLineLength- mBorderLineMargin, framingRect.bottom- mBorderLineMargin);
        canvas.drawPath(path, mBorderPaint);

        // Bottom-left corner
        path.moveTo(framingRect.left+ mBorderLineMargin, framingRect.bottom - mBorderLineLength- mBorderLineMargin);
        path.lineTo(framingRect.left+ mBorderLineMargin, framingRect.bottom- mBorderLineMargin);
        path.lineTo(framingRect.left + mBorderLineLength+ mBorderLineMargin , framingRect.bottom- mBorderLineMargin);
        canvas.drawPath(path, mBorderPaint);
    }

    public void drawLaser(Canvas canvas) {
        Rect framingRect = getFramingRect();

        // Draw a red "laser scanner" line through the middle to show decoding is active
        mLaserPaint.setAlpha(SCANNER_ALPHA[scannerAlpha]);
        scannerAlpha = (scannerAlpha + 1) % SCANNER_ALPHA.length;
        int middle = framingRect.height() / 2 + framingRect.top;
        canvas.drawRect(framingRect.left + 2, middle - 1, framingRect.right - 1, middle + 2, mLaserPaint);

        postInvalidateDelayed(ANIMATION_DELAY,
                framingRect.left - POINT_SIZE,
                framingRect.top - POINT_SIZE,
                framingRect.right + POINT_SIZE,
                framingRect.bottom + POINT_SIZE);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        updateFramingRect();
    }

    public synchronized void updateFramingRect() {
        Point viewResolution = new Point(getWidth(), getHeight());
        int width;
        int height;
        int orientation = DisplayUtils.getScreenOrientation(getContext());

//        if (mSquareViewFinder) {
//            if (orientation != Configuration.ORIENTATION_PORTRAIT) {
//                height = (int) (getHeight() * DEFAULT_SQUARE_DIMENSION_RATIO);
//                width = height;
//            } else {
//                width = (int) (getWidth() * DEFAULT_SQUARE_DIMENSION_RATIO);
//                height = width;
//            }
//        } else {
//            if (orientation != Configuration.ORIENTATION_PORTRAIT) {
//                height = (int) (getHeight() * LANDSCAPE_HEIGHT_RATIO);
//                width = (int) (LANDSCAPE_WIDTH_HEIGHT_RATIO * height);
//            } else {
//                width = (int) (getWidth() * PORTRAIT_WIDTH_RATIO);
//                height = (int) (PORTRAIT_WIDTH_HEIGHT_RATIO * width);
//            }
//        }
        width= mWidthViewFinder;
        height= mHeightViewFinder;

        if (width > getWidth()) {
            width = getWidth() - MIN_DIMENSION_DIFF;
        }

        if (height > getHeight()) {
            height = getHeight() - MIN_DIMENSION_DIFF;
        }

        int leftOffset = (viewResolution.x - width) / 2;
        int topOffset = mTopViewFinder;
        mFramingRect = new Rect(leftOffset + mViewFinderOffset, topOffset + mViewFinderOffset, leftOffset + width - mViewFinderOffset, topOffset + height - mViewFinderOffset);
    }

    @Override
    public void setTopViewFinder(int top) {
        this.mTopViewFinder = top;
    }

    @Override
    public void setHeightViewFinder(int mViewFinderHeight) {
        this.mHeightViewFinder = mViewFinderHeight;
    }

    @Override
    public void setWidthViewFinder(int mViewFinderWidth) {
        this.mWidthViewFinder = mViewFinderWidth;
    }

    @Override
    public void setBorderLineMargin(int mBorderMargin) {
        this.mBorderLineMargin = mBorderMargin;
    }

    @Override
    public void setStatusColor(int mStatusColor) {
        this.mStatusColor= mStatusColor;
    }

    @Override
    public void setStatusMargin(int mStatusMargin) {
        this.mStatusMargin= mStatusMargin;
    }

    @Override
    public void setStatusSize(float mStatusSize) {
        this.mStatusSize= mStatusSize;
    }

    @Override
    public void setStatusText(String mStatusText) {
        this.mStatusText= mStatusText;
    }
}