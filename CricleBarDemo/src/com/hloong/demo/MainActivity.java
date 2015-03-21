package com.hloong.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
    private CircleBarTwoSider circleBar;
    private CircleBar circleBar2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        circleBar = (CircleBarTwoSider) findViewById(R.id.circle);
        circleBar.setSweepAngle(120);
        circleBar.setText("27000");
        circleBar.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                circleBar.startCustomAnimation();
            }
        });
        
        circleBar2 = (CircleBar) findViewById(R.id.circle2);
        circleBar2.setSweepAngle(120);
        circleBar2.setText("500");
        circleBar2.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view){
                circleBar2.startCustomAnimation();
            }
        });
        
        
        
    }
}
