package com.liuwp.seekbarhint;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import static android.view.View.MeasureSpec.UNSPECIFIED;
import static android.view.View.MeasureSpec.makeMeasureSpec;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Author:  liuwp
 * Email:   liuwuping1206@163.com
 * Date:    2015/12/3
 * Description:seekbar with hint
 */
public class SeekBarHint extends SeekBar implements SeekBar.OnSeekBarChangeListener {
    private View mPopupView;
    private TriangleRectangleTextView mPopupTextView;
    private PopupWindow mPopup;
    private int mPopupOffset;
    private OnSeekBarHintProgressChangeListener listener;
    private Handler handler = new Handler();

    public interface OnSeekBarHintProgressChangeListener {
        void onStart();
        String onProgressChanged(SeekBarHint seekBarHint, int progress);
        void onStop();
    }

    public SeekBarHint(Context context) {
        super(context);
        init();
    }

    public SeekBarHint(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SeekBarHint(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        initHintPopup();
        attachSeekBar();
    }


    private void initHintPopup() {
        this.setOnSeekBarChangeListener(this);
        String popupText = null;
        if (listener != null) {
            popupText = listener.onProgressChanged(this, getProgress());
        }
        // init views
        LayoutInflater inflater =
                (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPopupView = inflater.inflate(R.layout.popup, null);
        mPopupView.measure(makeMeasureSpec(0, UNSPECIFIED), makeMeasureSpec(0, UNSPECIFIED));
        mPopupTextView = (TriangleRectangleTextView) mPopupView.findViewById(R.id.text1);
        mPopupTextView.setText(popupText != null ? popupText : String.valueOf(this.getProgress()));
        // init popup
        mPopup = new PopupWindow(mPopupView, WRAP_CONTENT, WRAP_CONTENT, false);
    }

    private void attachSeekBar() {
        final ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getVisibility() != View.VISIBLE) hidePopup();
                else showPopup();
            }
        };
        this.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            @Override
            public void onViewAttachedToWindow(View v) {
                getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
            }

            @Override
            public void onViewDetachedFromWindow(View v) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    getViewTreeObserver().removeGlobalOnLayoutListener(layoutListener);
                } else {
                    getViewTreeObserver().removeOnGlobalLayoutListener(layoutListener);
                }
                hidePopup();
            }
        });
    }


    public void showPopup() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                showPopupInternally();
            }
        });
    }


    public void hidePopup() {
        handler.removeCallbacksAndMessages(null);
        if (mPopup.isShowing()) mPopup.dismiss();
    }

    private void showPopupInternally() {
        Point offsetPoint = getFollowHintOffset();
        mPopup.showAtLocation(this, Gravity.NO_GRAVITY, 0, 0);
        mPopup.update(this, offsetPoint.x, offsetPoint.y, -1, -1);
    }

    private Point getFollowHintOffset() {
        int xOffset = getHorizontalOffset(this.getProgress());
        int yOffset = getVerticalOffset();
        return new Point(xOffset, yOffset);
    }

    protected int getFollowPosition(int progress) {
        return (int) (progress * (this.getWidth()
                - this.getPaddingLeft()
                - this.getPaddingRight()) / (float) this.getMax());
    }

    private int getHorizontalOffset(int progress) {
        return getFollowPosition(progress) - mPopupView.getMeasuredWidth() / 2 + this.getRealHeight() / 2;
    }


    private int getVerticalOffset() {
        return -(getRealHeight() + mPopupView.getMeasuredHeight() + mPopupOffset);
    }


    /**
     * 加上padding计算会慢很多 所以最好不要给seekbar设置padding
     * @return 真实高度去除padding
     */
    private int getRealHeight(){
        return this.getHeight()-this.getPaddingBottom()-this.getPaddingTop();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        String popupText = null;
        if (listener != null) {
            popupText = listener.onProgressChanged(this, getProgress());
        }
        mPopupTextView.setText(popupText != null ? popupText : String.valueOf(progress));
        Point offsetPoint = getFollowHintOffset();
        mPopup.update(this, offsetPoint.x, offsetPoint.y, -1, -1);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        showPopupInternally();
        if(listener!=null){
            listener.onStart();
        }


    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (listener != null) {
            listener.onStop();
        }
    }


    public void setListener(OnSeekBarHintProgressChangeListener listener) {
        this.listener = listener;
    }
}
