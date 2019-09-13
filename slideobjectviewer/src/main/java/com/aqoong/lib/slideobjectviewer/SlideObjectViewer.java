package com.aqoong.lib.slideobjectviewer;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.rd.PageIndicatorView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * [SlideObjectViewerSample]
 * <p>
 * Class: SlideObjectViewer
 * <p>
 * Created by aqoong on 2019-09-13.
 * - Email  : cooldnjsdn@gmail.com
 * - GitHub : https://github.com/aqoong
 * <p>
 * Description:
 */
public class SlideObjectViewer extends RelativeLayout {
    private final String TAG = getClass().getSimpleName();

    //views
    private ViewPager           mViewPager;
    private PageIndicatorView   mIndicator;

    //setting
    private boolean     useAutoSlide;
    private boolean     useIndicator;
    private int         mRepeatTime;
    private boolean     showSide;

    //data
    private SlideObjectViewerAdapter    mAdapter;

    private final int defPadding = 30;
    private Rect        mSidePadding;


    public SlideObjectViewer(Context context) {
        this(context, null);
    }

    public SlideObjectViewer(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlideObjectViewer);
        try{
            useAutoSlide    = ta.getBoolean(R.styleable.SlideObjectViewer_slideViewer_autoSlide, false);
            useIndicator    = ta.getBoolean(R.styleable.SlideObjectViewer_slideViewer_userIndicator, true);
            mRepeatTime     = ta.getInteger(R.styleable.SlideObjectViewer_slideViewer_repeatTime, 5000);
            showSide        = ta.getBoolean(R.styleable.SlideObjectViewer_slideViewer_showSide, false);
        }finally {
            ta.recycle();

            initView();
        }

    }

    private void initView(){
        Log.d(TAG, "call initView()");
        mAdapter = new SlideObjectViewerAdapter(getContext());

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View parentView = inflater.inflate(R.layout.slideobjectviewer_pager, this);

        mViewPager = parentView.findViewById(R.id.slideobj_pager);
        mIndicator = parentView.findViewById(R.id.slideobj_indicator);

        mViewPager.setAdapter(mAdapter);

        mIndicator.setVisibility(useIndicator ? VISIBLE : GONE);
        autoSlide(useAutoSlide, mRepeatTime);
        setSidePreview(showSide);

    }

    public void setSidePreview(boolean use){
        setSidePreview(use, use==false?0:defPadding);
    }
    public void setSidePreview(boolean use, int padding){
        mViewPager.setClipToPadding(!use);
        if(use)
        {
            int convertedPadding = convertPixelsToDp(padding, getContext());
            Point screen = new Point();
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getSize(screen);
            float startOffset = (float) convertedPadding / (float) (screen.x - 2 * convertedPadding);
            mIndicator.setTranslationY(mIndicator.getTranslationY() - convertedPadding);

            if(mSidePadding == null){
                mSidePadding = new Rect();

                mSidePadding.left = convertedPadding;
                mSidePadding.right = convertedPadding;
                mSidePadding.top = convertedPadding;
                mSidePadding.bottom = convertedPadding;
            }

            mViewPager.setPadding(mSidePadding.left, mSidePadding.top, mSidePadding.right, mSidePadding.bottom);
            mViewPager.setOffscreenPageLimit(3);
            mViewPager.setPageMargin(50);
            mViewPager.setPageTransformer(false, new TransFromViewPager(0, 0, 0.7f, startOffset));
        }else{
            mViewPager.setOffscreenPageLimit(1);
            mViewPager.setPageMargin(0);
            mViewPager.setPadding(0, 0, 0, 0);
            mViewPager.setPageTransformer(true, null);
        }
    }

    public void setSidePadding(int left, int top, int right, int bottom){
        Rect rect = new Rect();

        rect.left = convertPixelsToDp(left, getContext());
        rect.right = convertPixelsToDp(right, getContext());
        rect.top = convertPixelsToDp(top, getContext());
        rect.bottom = convertPixelsToDp(bottom, getContext());

        mSidePadding = rect;
        setSidePreview(showSide);
    }

    private void nextSlide(){
        mViewPager.setCurrentItem(calNextItem(), true);
    }
    private int calNextItem(){
        int nextId = mViewPager.getCurrentItem() + 1;

        return nextId == mAdapter.getCount() ? 0 : nextId;
    }

    public void autoSlide(boolean start){
        autoSlide(start, this.mRepeatTime);
    }

    public void autoSlide(boolean start, int repeatTime){
        this.mRepeatTime = repeatTime;

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                nextSlide();
            }
        };

        Timer timer = new Timer();
        if(start) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            }, 1000, mRepeatTime);
        }else{
            timer.cancel();
        }
    }

    public int getListSize(){
        return mAdapter.getCount();
    }

    public void addView(View viewItem){
        addView(mAdapter.getCount(), viewItem);
    }

    public void addView(int position, View viewItem){
        try{
            mAdapter.addItem(position, viewItem);
        }catch (NullPointerException e){
            Log.e(TAG, "addView() mAdapter null pointer exception.");
        }catch (Exception e){
            Log.e(TAG, "addView() mAdapter exception.");
        }finally {
            mAdapter.notifyDataSetChanged();
        }
    }

    public void clearList(){
        try {
            mAdapter.clearList(mViewPager);
        }catch (NullPointerException e){
            Log.e(TAG, "clearList() pager null pointer exception.");
        }
    }

    private int convertPixelsToDp(float px, Context context) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
        return value;
    }
}
