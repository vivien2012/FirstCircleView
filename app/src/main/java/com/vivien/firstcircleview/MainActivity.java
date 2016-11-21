package com.vivien.firstcircleview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    FirstCircleView mFirstCircleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFirstCircleView = (FirstCircleView) findViewById(R.id.firstview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirstCircleView.destory();
    }
}
