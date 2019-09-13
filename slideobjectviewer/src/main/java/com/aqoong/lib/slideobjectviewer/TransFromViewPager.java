package com.aqoong.lib.slideobjectviewer;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * [SlideObjectViewerSample]
 * <p>
 * Class: TransFromViewPager
 * <p>
 * Created by aqoong on 2019-09-13.
 * - Email  : cooldnjsdn@gmail.com
 * - GitHub : https://github.com/aqoong
 * <p>
 * Description:
 */
public class TransFromViewPager implements ViewPager.PageTransformer {

    private int baseElevation;
    private int raisingElevation;
    private float smallerScale;
    private float startOffset;

    public TransFromViewPager(int baseElevation, int raisingElevation, float smallerScale, float startOffset) {
        this.baseElevation = baseElevation;
        this.raisingElevation = raisingElevation;
        this.smallerScale = smallerScale;
        this.startOffset = startOffset;
    }

    @Override
    public void transformPage(@NonNull View page, float position) {
        float absPosition = Math.abs(position - startOffset);

        if (absPosition >= 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                page.setElevation(baseElevation);
            }
            page.setScaleY(smallerScale);
        } else {
            // This will be during transformation
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                page.setElevation(((1 - absPosition) * raisingElevation + baseElevation));
            }
            page.setScaleY((smallerScale - 1) * absPosition + 1);
        }
    }
}
