package com.aqoong.lib.slideobjectviewer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * [SlideObjectViewerSample]
 * <p>
 * Class: SlideObjectViewerAdapter
 * <p>
 * Created by aqoong on 2019-09-13.
 * - Email  : cooldnjsdn@gmail.com
 * - GitHub : https://github.com/aqoong
 * <p>
 * Description:
 */
public class SlideObjectViewerAdapter extends PagerAdapter {
    private final String TAG = getClass().getSimpleName();

    private Context             mContext;

    private ArrayList<View>     viewList;


    public SlideObjectViewerAdapter(Context context){
        this(context, null);
    }

    public SlideObjectViewerAdapter(Context context, ArrayList<View> viewList){
        mContext = context;

        if(viewList == null){
            viewList = new ArrayList<>();
            this.viewList = new ArrayList<>();
        }

        this.viewList.addAll(viewList);
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = this.viewList.get(position);
        ViewPager.LayoutParams params = new ViewPager.LayoutParams();

        view.setLayoutParams(params);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == ((View) o);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
        ((ViewPager)container).removeView((View)object);
    }

    public void removeItem(ViewPager pager, int position){
        pager.setAdapter(null);
        this.viewList.remove(position);
        pager.setAdapter(this);
    }

    public void removeItem(ViewPager pager, View view){
        removeItem(pager, this.viewList.indexOf(view));
    }

    public void addItem(View view){
        this.viewList.add(view);
    }

    public void addItem(int position, View view){
        this.viewList.add(position, view);
    }
    public void clearList(ViewPager pager){
        pager.setAdapter(null);
        this.viewList.clear();
        pager.setAdapter(this);
    }

    public ArrayList<View> getViewList(){
        return this.viewList;
    }
}
