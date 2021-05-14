package com.yhyy.snow;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity {

	private SnowView mSnowView;
	public static int screenWidth;
	public static int screenHeight;
	Timer myTimer = null;
	TimerTask mTask = null;
	private static final int SNOW_BLOCK = 1;

	private Handler mHandler = new Handler(Looper.myLooper()) {
		public void dispatchMessage(Message msg) {
			mSnowView.inva();
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSnowView = (SnowView) findViewById(R.id.snowView);
		
		DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		float de = dm.density;

		mSnowView.setWH(screenWidth, screenHeight, de,50);
		mSnowView.loadFlower(R.drawable.snowbig);
		mSnowView.addRect();

		myTimer = new Timer();
		mTask = new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = SNOW_BLOCK;
				mHandler.sendMessage(msg);
			}
		};
		myTimer.schedule(mTask, 3000, 10);
	}

}
