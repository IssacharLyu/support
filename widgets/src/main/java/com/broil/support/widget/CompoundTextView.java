
package com.broil.support.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * A TextView that add compound drawables easier.
 *
 * @author: broil
 * @date: 2016/3/29
 * @version: V1.0
 */

public class CompoundTextView extends TextView {

    public CompoundTextView(Context context) {
        super(context);
    }

    public CompoundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CompoundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void drawableLeft(int resId) {
        setCompoundDrawables(resId, 0, 0, 0);
    }

    public void drawableTop(int resId) {
        setCompoundDrawables(0, resId, 0, 0);
    }

    public void drawableRight(int resId) {
        setCompoundDrawables(0, 0, resId, 0);
    }

    public void drawableBottom(int resId) {
        setCompoundDrawables(0, 0, 0, resId);
    }

    public void setCompoundDrawables(int leftResId, int topResId, int rightResId, int bottomResId) {
        Drawable left = getCompoundDrawable(leftResId);
        Drawable top = getCompoundDrawable(topResId);
        Drawable right = getCompoundDrawable(rightResId);
        Drawable bottom = getCompoundDrawable(bottomResId);

        setCompoundDrawables(left, top, right, bottom);
    }

    private Drawable getCompoundDrawable(int resId) {
        if (resId == 0)
            return null;

        Drawable drawable = getContext().getResources().getDrawable(resId);
        if (drawable != null)
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());

        return drawable;
    }
}
