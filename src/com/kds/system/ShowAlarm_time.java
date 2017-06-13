package com.kds.system;

import android.app.Activity;
import android.content.Intent;
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
	private int Alarm_TimeGroup[]={1,3,5,10,15};
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
    	int timeVal = (Integer) SystemConfig.getInstance().getAlarmTime(getApplicationContext(), 0);
    	int timeIndex = 0;
    	for(int i=0;i<Alarm_TimeGroup.length;i++){
    		if(timeVal==Alarm_TimeGroup[i]){
    			timeIndex=i;
    			break;
    		}
    	}
    	radioShow_AlarmTime.check(radioShow_AlarmTime.getChildAt(timeIndex).getId());
    	radioShow_AlarmTime.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				int index= arg0.indexOfChild(arg0.findViewById(arg0.getCheckedRadioButtonId()));
				showToast("index = "+index);
				SystemConfig.getInstance().setAlarmTime(getApplicationContext(), Alarm_TimeGroup[index]);
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
