package com.soichi.hrm1017controller.presentation.behavior;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import com.soichi.hrm1017controller.presentation.layout.main.ClassicControllerLayout;
import com.soichi.hrm1017controller.presentation.layout.main.InteractiveMonitorLayout;

/**
 * Created by soichi on 2015/11/01.
 */
public class InteractiveMonitorBehavior extends CoordinatorLayout.Behavior<InteractiveMonitorLayout> {

    private int mDependencyOffset;
    private int mChildInitialOffset;

    public InteractiveMonitorBehavior() {
    }

//    public InteractiveMonitorBehavior(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        Log.d("bletest", "constructor called");
//    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent,
                                   InteractiveMonitorLayout child,
                                   View dependency) {

        return dependency instanceof ClassicControllerLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent,
                                          InteractiveMonitorLayout child,
                                          View dependency) {

        if (dependency.getTranslationY() == 0) {
            // controller open
            child.getLayoutParams().height = parent.getHeight() - dependency.getHeight();
        } else {
            // controller closed
            child.getLayoutParams().height = parent.getHeight();
        }

        child.requestLayout();

        return false;
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent,
                                 InteractiveMonitorLayout child,
                                 int layoutDirection) {
        mChildInitialOffset = child.getTop();

        return false;
    }
}
