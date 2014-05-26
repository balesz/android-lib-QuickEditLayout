package net.solutinno.android.animation;

import com.nineoldandroids.animation.Animator;

public abstract class AnimationListenerImp implements Animator.AnimatorListener {
    public void onAnimationStart(Animator animator) {}
    public void onAnimationEnd(Animator animator) {}
    public void onAnimationCancel(Animator animator) {}
    public void onAnimationRepeat(Animator animator) {}
}
