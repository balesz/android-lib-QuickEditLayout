package net.solutinno.android.animation;

import android.graphics.Rect;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import net.solutinno.util.ViewHelper;

public class QuickEditAnimator implements View.OnTouchListener, AbsListView.OnScrollListener {

    private final View mView;
    private final Rect mRect;
    private final GestureDetectorCompat mGesture;

    float mTranslation;
    int mPrevFirstVisibleItem;
    boolean mIsAnimating;

    public QuickEditAnimator(View view) {
        mView = view;
        mRect = ViewHelper.getViewRectangle(mView);
        mGesture = new GestureDetectorCompat(view.getContext(), mOnGestureListener);
    }

    public void animate(float distanceY) {
        mTranslation += distanceY > 0 ? (-distanceY) : (-distanceY/2);
        if (distanceY > 0) mTranslation = mTranslation < -mRect.height() ? -mRect.height() : mTranslation;
        else if (distanceY < 0) mTranslation = mTranslation > 0 ? 0 : mTranslation;

        AnimatorSet set = new AnimatorSet();
        set.setDuration(0);
        set.playTogether(
            ObjectAnimator.ofInt(mView, "top", (int) mTranslation).setDuration(0),
            ObjectAnimator.ofInt(mView, "bottom", mRect.height() + (int) mTranslation).setDuration(0)
        );
        set.start();
    }

    public void collapse(long duration) {
        if (mIsAnimating) return;
        mTranslation = 0;
        mIsAnimating = true;
        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.addListener(new AnimationListenerImp() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mIsAnimating = false;
            }
        });
        set.playTogether(
            ObjectAnimator.ofInt(mView, "top", -mRect.height()),
            ObjectAnimator.ofInt(mView, "bottom", 0)
        );
        set.start();
    }

    public void expand(long duration) {
        if (mIsAnimating) return;
        mTranslation = 0;
        mIsAnimating = true;
        AnimatorSet set = new AnimatorSet();
        set.setDuration(duration);
        set.addListener(new AnimationListenerImp() {
            @Override
            public void onAnimationEnd(Animator animator) {
                mIsAnimating = false;
            }
        });
        set.playTogether(
            ObjectAnimator.ofInt(mView, "top", 0),
            ObjectAnimator.ofInt(mView, "bottom", mRect.height())
        );
        set.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return mGesture.onTouchEvent(motionEvent);
    }

    GestureDetector.SimpleOnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            animate(distanceY);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    };

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0 && visibleItemCount == totalItemCount) {
            expand(500);
        }
        else {
            if (firstVisibleItem > 0) {
                if (mPrevFirstVisibleItem - firstVisibleItem > 0) expand(500);
                else if (mPrevFirstVisibleItem - firstVisibleItem < 0) collapse(500);
            }
        }
        mPrevFirstVisibleItem = firstVisibleItem;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }
}
