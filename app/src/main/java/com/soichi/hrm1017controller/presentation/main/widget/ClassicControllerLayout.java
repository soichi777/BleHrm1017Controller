package com.soichi.hrm1017controller.presentation.main.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by soichi on 2015/10/31.
 */
public class ClassicControllerLayout extends RelativeLayout {

    private boolean mIsOpen = true;
    private int mOriginalHeight;

    public ClassicControllerLayout(Context context) {
        super(context);
    }

    public ClassicControllerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Log.d("ble ui", "on measure called");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //Log.d("ble ui", "on layout called");
    }

    /**
     *
     */
    public void init() {
        mOriginalHeight = this.getHeight();
        //Log.d("height was: ", "" + mOriginalHeight);
    }

    /**
     *
     */
    public boolean isOpen() {
        return mIsOpen;
    }

    /**
     * TODO
     * @return boolean
     */
    public boolean toggleVisibility() {

        if (mIsOpen) {
            this.animate().translationYBy(200).start();
            mIsOpen = false;
        } else {
            this.animate().translationYBy(-200).start();
            mIsOpen = true;
        }
        this.requestLayout();

        return true;
    }
}
