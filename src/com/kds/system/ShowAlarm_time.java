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


public class ShowAlarm_time extends Activity implements OnClickListener{
	RadioGroup radioShow_AlarmTime;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_alarm_time);

        initUiView();
    }
    private void initUiView(){
    	Button back = (Button)findViewById(R.id.butShow_alarmTime_backId);
    	back.setOnClickListener(this);
    	radioShow_AlarmTime=(RadioGroup)findViewById(R.id.radioShow_AlarmTimeId);
    	int timeIndex = (Integer) SystemConfig.getInstance().getAlarmTime(getApplicationContext(), 0);
    	if(timeIndex<0||timeIndex>radioShow_AlarmTime.getChildCount()){
    		timeIndex=0;
    	}
    	radioShow_AlarmTime.check(radioShow_AlarmTime.getChildAt(timeIndex).getId());
    	radioShow_AlarmTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				int index= arg0.indexOfChild(arg0.findViewById(arg0.getCheckedRadioButtonId()));
				showToast("index = "+index);
				SystemConfig.getInstance().setAlarmTime(getApplicationContext(), index);
			}
		});
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butShow_alarmTime_backId:
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
