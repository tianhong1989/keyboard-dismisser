package com.gabrielsamojlo.keyboarddismisser.dismissinglayouts;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.gabrielsamojlo.keyboarddismisser.DismissingUtils;
import com.gabrielsamojlo.keyboarddismisser.KeyboardListener;

public class KeyboardDismissingCoordinatorLayout extends CoordinatorLayout {

    private boolean mIsKeyboardVisible;
    private KeyboardListener mKeyboardListener;

    public KeyboardDismissingCoordinatorLayout(Context context) {
        super(context);
    }

    public KeyboardDismissingCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardDismissingCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean wasDispatched = super.dispatchTouchEvent(ev);

        if (!wasDispatched && mIsKeyboardVisible) {
            DismissingUtils.hideKeyboard(this);
        }

        return wasDispatched;
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        mKeyboardListener = new KeyboardListener() {
            @Override
            public void onKeyboardVisibilityChanged(boolean isVisible) {
                mIsKeyboardVisible = isVisible;
            }
        };

        DismissingUtils.setupKeyboardListener(this, mKeyboardListener);
    }

    @Override
    public void onDetachedFromWindow() {
        DismissingUtils.removeKeyboardListener(this, mKeyboardListener);
        mKeyboardListener = null;
        super.onDetachedFromWindow();
    }
}
