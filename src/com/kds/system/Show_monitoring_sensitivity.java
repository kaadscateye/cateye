package com.kds.system;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kds.cateye.R;
import com.kds.database.SystemConfig;

public class Show_monitoring_sensitivity extends Activity implements
		OnClickListener {
	private RadioGroup radioMonitorIng_sensitivity;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_monitoring_sensitivity);

		InitUImenu();

	}

	private void InitUImenu() {
		Button cancel = (Button) findViewById(R.id.showmonitoring_cancel_Id);
		cancel.setOnClickListener(this);
		radioMonitorIng_sensitivity = (RadioGroup) findViewById(R.id.radioMonitorIng_sensitivityId);

		int sensitivity = (Integer) SystemConfig.getInstance()
				.getMonitor_sensitivity(getApplicationContext(), 0);
		if (sensitivity < 0
				|| sensitivity > radioMonitorIng_sensitivity.getChildCount())
			sensitivity = 0;
		radioMonitorIng_sensitivity.check(radioMonitorIng_sensitivity
				.getChildAt(sensitivity).getId());

		radioMonitorIng_sensitivity
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup arg0, int arg1) {
						// TODO Auto-generated method stub
						int index = arg0.indexOfChild(arg0.findViewById(arg0
								.getCheckedRadioButtonId()));
						showToast("index = " + index);
						SystemConfig.getInstance().setMonitor_sensitivity(
								getApplicationContext(), index);
					}
				});
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.showmonitoring_cancel_Id:
			finish();
			break;

		default:
			break;
		}
	}

	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
