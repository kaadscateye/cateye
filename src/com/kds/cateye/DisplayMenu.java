package com.kds.cateye;

import com.kds.system.ScreenTime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class DisplayMenu extends Activity implements OnClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_menu);
		initUi();
	}

	private void initUi() {
		SeekBar screen = (SeekBar) findViewById(R.id.screenLightBarId);
		screen.setMax(255);
		// 设置初始的Progress
		screen.setProgress(getSystemBrightness());
		// 设置初始的屏幕亮度与系统一致
		changeAppBrightness(getSystemBrightness());
		screen.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

				changeAppBrightness(arg0.getProgress());
			}

			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub

			}
		});
		Button butScreenback = (Button)findViewById(R.id.butScreenbackId);
		butScreenback.setOnClickListener(this);
		Button butdisplay_screen_close_timeout = (Button)findViewById(R.id.butdisplay_screen_close_timeoutId);
		butdisplay_screen_close_timeout.setOnClickListener(this);
	}

	/**
	 * 获得系统亮度
	 * 
	 * @return
	 */
	private int getSystemBrightness() {
		int systemBrightness = 0;
		try {
			systemBrightness = Settings.System.getInt(getContentResolver(),
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (Settings.SettingNotFoundException e) {
			e.printStackTrace();
		}
		return systemBrightness;
	}

	/**
	 * 改变App当前Window亮度
	 * 
	 * @param brightness
	 */
	public void changeAppBrightness(int brightness) {
		Window window = this.getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		if (brightness == -1) {
			lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
		} else {
			lp.screenBrightness = (brightness <= 0 ? 1 : brightness) / 255f;
		}
		window.setAttributes(lp);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butScreenbackId:
			finish();
			break;
		case R.id.butdisplay_screen_close_timeoutId:
			startActivity(new Intent().setClass(getApplicationContext(), ScreenTime.class));
		default:
			break;
		}
	}
}
