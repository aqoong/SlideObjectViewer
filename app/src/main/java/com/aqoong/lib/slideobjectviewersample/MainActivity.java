package com.aqoong.lib.slideobjectviewersample;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aqoong.lib.icontextview.IconTextView;
import com.aqoong.lib.slideobjectviewer.SlideObjectViewer;

public class MainActivity extends AppCompatActivity {
    private SlideObjectViewer slideObjectViewer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slideObjectViewer = findViewById(R.id.slideViewer);
        slideObjectViewer.setSidePadding(80, 0, 80, 0);

        for(int i = 0 ; i < 5 ; i++){
            final int finalI = i;
            CustomView customView = new CustomView(getApplicationContext());
            customView.setText("TEST : " + i);
            customView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Click! TEST : " + finalI, Toast.LENGTH_LONG).show();
                }
            });


            slideObjectViewer.addView(customView);
        }
    }


    private int convertPixelsToDp(float px, Context context) {
        int value = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, context.getResources().getDisplayMetrics());
        return value;
    }
}
