package com.aqoong.lib.slideobjectviewersample;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.aqoong.lib.icontextview.IconTextView;

/**
 * [SlideObjectViewerSample]
 * <p>
 * Class: CustomView
 * <p>
 * Created by aqoong on 2019-09-13.
 * - Email  : cooldnjsdn@gmail.com
 * - GitHub : https://github.com/aqoong
 * <p>
 * Description:
 */
public class CustomView extends RelativeLayout {
    private IconTextView iconTextView;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init(){
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item_test, this);

        iconTextView = findViewById(R.id.iconText);
        this.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
    }

    public void setText(String text){
        iconTextView.setText(text);
    }
}
