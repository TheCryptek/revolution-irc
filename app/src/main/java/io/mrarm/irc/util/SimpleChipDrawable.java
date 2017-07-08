package io.mrarm.irc.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.mrarm.irc.R;

public class SimpleChipDrawable extends Drawable {

    private final String mText;
    private final Paint mPaint;
    private final Drawable mBackground;
    private int mTextWidth;
    private int mTextHeight;
    private int mBgColor;
    private Rect mTempRect = new Rect();

    public SimpleChipDrawable(Context ctx, String text, boolean transparent) {
        mText = text;
        TypedArray ta = ctx.obtainStyledAttributes(new int[] { android.R.attr.textAppearance });
        int resId = ta.getResourceId(0, 0);
        ta.recycle();
        ta = ctx.obtainStyledAttributes(resId, new int[] { android.R.attr.textSize });
        int textSize = ta.getDimensionPixelSize(0, 0);
        ta.recycle();

        mBackground = ctx.getResources().getDrawable(transparent ? R.drawable.transparent_chip_background : R.drawable.chip_background);
        mPaint = new Paint();
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(textSize);
        mTextWidth = (int) mPaint.measureText(mText);
        mTextHeight = (int) (mPaint.descent() - mPaint.ascent());
    }

    public Paint getPaint() {
        return mPaint;
    }

    @Override
    public int getIntrinsicWidth() {
        mBackground.getPadding(mTempRect);
        return mBackground.getMinimumWidth() + mTempRect.left + mTempRect.right + mTextWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        mBackground.getPadding(mTempRect);
        return mBackground.getMinimumHeight() + mTempRect.top + mTempRect.bottom + mTextHeight;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect bounds = getBounds();
        mBackground.draw(canvas);
        canvas.drawText(mText, bounds.centerX(), bounds.centerY() - (mPaint.descent() + mPaint.ascent()) / 2, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
        mBackground.setAlpha(alpha);
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mBackground.setColorFilter(colorFilter);
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setBounds(int left, int top, int right, int bottom) {
        mBackground.setBounds(left, top, right, bottom);
        super.setBounds(left, top, right, bottom);
    }

}