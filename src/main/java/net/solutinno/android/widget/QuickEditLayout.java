package net.solutinno.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class QuickEditLayout extends ViewGroup
{
    private int mLayoutId = -1;

    public QuickEditLayout(Context context) {
        super(context);
    }

    public QuickEditLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(attrs);
    }

    public QuickEditLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttributes(attrs);
    }

    private void initAttributes(AttributeSet attrs) {
        mLayoutId = -1;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mLayoutId < 0) return;
        createRootView();
    }

    private void createRootView() {

    }
}
