package com.soichi.hrm1017controller.presentation.main.widget;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.soichi.hrm1017controller.presentation.main.behavior.InteractiveMonitorBehavior;

/**
 * Created by soichi on 2015/11/01.
 */
@CoordinatorLayout.DefaultBehavior(InteractiveMonitorBehavior.class)
public class InteractiveMonitorLayout extends ViewPager {

    public InteractiveMonitorLayout(Context context) {
        super(context);
    }

    public InteractiveMonitorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //Log.d("ble ui", "monitor on measure called");
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //Log.d("ble ui", "monitor on layout called");
    }

}
