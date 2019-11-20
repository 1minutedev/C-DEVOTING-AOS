package com.kkj.cvoting.util;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomViewPager extends ViewPager {
    private boolean isLock = false;
    private SlidingUpPanelLayout mLayout = null;

    public CustomViewPager(@NonNull Context context) {
        super(context);
    }
    public CustomViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
            return false;
        }

        return super.onInterceptTouchEvent(ev);
    }

    public void setSlidingLayout(SlidingUpPanelLayout layout){
        mLayout = layout;
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        if (SlidingUpPanelLayout.isLock) {
//            return super.onInterceptTouchEvent(ev);
//        } else {
//            return false;
//        }
//    }
}