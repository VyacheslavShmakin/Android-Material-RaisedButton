package ru.shmakinv.android.widget.material.raisedbutton;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * RaisedButtonHelper
 *
 * @author Vyacheslav Shmakin
 * @version 15.08.2016
 */
public class RaisedButtonHelper {

    public static long ANIMATION_DURATION_NORMAL = 300L;

    public static void animateCardElevation(final CardView view, long duration, float from, float to) {
        if (Float.compare(from, to) == 0 || view == null) {
            return;
        }

        ObjectAnimator va = new ObjectAnimator();
        va.setTarget(view);
        va.setDuration(duration);
        va.setInterpolator(new AccelerateDecelerateInterpolator());
        va.setFloatValues(from, to);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator o) {
                float value = (float) o.getAnimatedValue();
                view.setCardElevation(value);
            }
        });
        va.start();
    }

    public static boolean isInsideViewBounds(@NonNull View view, int x, int y) {
        if (x < 0 || y < 0) {
            return false;
        }

        int totalViewWidth = view.getMeasuredWidth() + view.getPaddingLeft() + view.getPaddingRight();
        int totalViewHeight = view.getMeasuredHeight() + view.getPaddingTop() + view.getPaddingBottom();
        if (totalViewWidth <= 0 || totalViewHeight <= 0) {
            return false;
        }

        Rect rect = new Rect();
        int[] locations = new int[2];
        view.getLocationOnScreen(locations);

        rect.set(locations[0],
                locations[1],
                locations[0] +totalViewWidth,
                locations[1] +totalViewHeight);
        return rect.contains(x, y);
    }
}
