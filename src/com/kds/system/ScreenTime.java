package com.kds.system;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kds.cateye.R;

public class ScreenTime extends Activity implements OnClickListener,OnCheckedChangeListener{
	private RadioGroup radioScreenTimeSet;
	private String TAG="ScreenTime";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_time);
        
        InitView();
        
        
    }

    private void InitView(){
    	Button butScreen_back = (Button)findViewById(R.id.butScreen_backId);
    	butScreen_back.setOnClickListener(this);
    	radioScreenTimeSet= (RadioGroup)findViewById(R.id.radioScreenTimeSetId);
    	SetScreenTime();
    	radioScreenTimeSet.setOnCheckedChangeListener(this);
    }
    
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butScreen_backId:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.radioScreenTimeSetId:
			RadioButton radioTime = (RadioButton)findViewById(radioScreenTimeSet.getCheckedRadioButtonId());
			String str =radioTime.getText().toString();
			if(str.contains("15")){
				Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 15*1000);
			}else if(str.contains("30")){
				Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 30*1000);
			}else if(str.contains("45")){
				Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 45*1000);
			}else if(str.contains("1∑÷÷”")){
				Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 60*1000);
			}
			break;

		default:
			break;
		}
	}
	private void SetScreenTime(){
		int index=0;
		 try {
             int result  = Settings.System.getInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
             Log.e(TAG, "SCREEN_OFF_TIMEOUT = " + result);
             showToast("SCREEN_OFF_TIMEOUT ="+result);
     		if(result==15000){
     			index=0;
			}else if(result==30000){
				index=1;
			}else if(result==45000){
				index=2;
			}else if(result==60000){
				index=3;
			}
     		radioScreenTimeSet.check(radioScreenTimeSet.getChildAt(index).getId());
         } catch (SettingNotFoundException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
	}
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
