package com.kds.cateye;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.kds.system.RingSetMenu;

public class SmartContorl extends Activity implements OnClickListener{

	AlertDialog alertDialog=null;
	AudioManager mAudioManager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_contorl);
        InitUImenu();
    }
    private void InitUImenu() {
  		Button butsetMenuBack = (Button)findViewById(R.id.butSmartContorl_backId);
  		butsetMenuBack.setOnClickListener(this);
  		TextView Set_smart_pir = (TextView)findViewById(R.id.Smart_Set_pir_Id);
  		Set_smart_pir.setOnClickListener(this);
  		TextView Set_smart_alarm_time = (TextView)findViewById(R.id.Smart_Set_alarm_timeId);
  		Set_smart_alarm_time.setOnClickListener(this);
  		TextView Monitoring_sensitivity = (TextView)findViewById(R.id.Smart_Monitoring_sensitivity_Id);
  		Monitoring_sensitivity.setOnClickListener(this);
  		TextView Automatic_alarm_bell = (TextView)findViewById(R.id.Smart_Automatic_alarm_bell_Id);
  		Automatic_alarm_bell.setOnClickListener(this);
  		TextView Vol_Set = (TextView)findViewById(R.id.Smart_Vol_Set_Id);
  		Vol_Set.setOnClickListener(this);
  		TextView smart_talk_photo= (TextView)findViewById(R.id.Smart_talk_photo_Id);
  		smart_talk_photo.setOnClickListener(this);
  		TextView leave_recoder_mode= (TextView)findViewById(R.id.leave_recoder_mode_Id);
  		leave_recoder_mode.setOnClickListener(this);
  	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butSmartContorl_backId:
			finish();
			break;
		case R.id.Smart_Set_pir_Id:
			break;
		case R.id.Smart_Set_alarm_timeId:
			ShowAlarm_time();
			break;
		case R.id.Smart_Monitoring_sensitivity_Id:
			ShowMonitoring();
			break;
		case R.id.Smart_Vol_Set_Id:
			showSetVol();
			break;
		case R.id.Smart_Automatic_alarm_bell_Id:
			Intent obj = new Intent();
			obj.putExtra(RingSetMenu.RingMenuTag, RingSetMenu.RingMenuAlarmIndex);
			startActivity(obj.setClass(getApplicationContext(), RingSetMenu.class));
			break;
		case R.id.Smart_talk_photo_Id:
			Show_Set_talkpicture();
			break;
		case R.id.showAlarm_cancel_Id:
			cancelMethod();
			break;
		case R.id.showmonitoring_cancel_Id:
			cancelMethod();
			break;
		case R.id.show_Set_talkpicture_cancel_Id:
			cancelMethod();
			break;
		case R.id.leave_recoder_mode_Id:
			showToast("留言模式");
			break;
		default:
			break;
		}
	}
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
		}else if( keyCode == KeyEvent.ACTION_UP){
			
		}else if( keyCode == KeyEvent.ACTION_UP){
			
		}
		return super.onKeyDown(keyCode, event);
	}
	private void ShowAlarm_time(){	//智能报警时间
		alertDialog = new AlertDialog.Builder(SmartContorl.this).create();  
		alertDialog.show();  
		Window window = alertDialog.getWindow();  
		window.setContentView(R.layout.show_alarm_time); 
		Button  cancel = (Button)window.findViewById(R.id.showAlarm_cancel_Id);
		cancel.setOnClickListener(this);
	}
	
	private void ShowMonitoring(){	//智能监控灵敏度
		alertDialog = new AlertDialog.Builder(SmartContorl.this).create();  
		alertDialog.show();  
		Window window = alertDialog.getWindow();  
		window.setContentView(R.layout.show_monitoring_sensitivity); 
		Button  cancel = (Button)window.findViewById(R.id.showmonitoring_cancel_Id);
		cancel.setOnClickListener(this);		
	}

	private void showSetVol(){		//显示设置音量
		alertDialog = new AlertDialog.Builder(SmartContorl.this).create();  
		alertDialog.show();  
		Window window = alertDialog.getWindow();  
		window.setContentView(R.layout.vol_set_menu);
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		SeekBar  setVolBar = (SeekBar)window.findViewById(R.id.setVolBarId);
		setVolBar.setMax(mAudioManager.getStreamMaxVolume( AudioManager.STREAM_SYSTEM ));
		int current = mAudioManager.getStreamVolume( AudioManager.STREAM_SYSTEM );
		setVolBar.setProgress(current);
		setVolBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				mAudioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, arg0.getProgress(), 0);
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
	}
	private void Show_Set_talkpicture(){
		alertDialog = new AlertDialog.Builder(SmartContorl.this).create();  
		alertDialog.show();  
		Window window = alertDialog.getWindow();  
		window.setContentView(R.layout.show_set_talkpicture); 
		Button  cancel = (Button)window.findViewById(R.id.show_Set_talkpicture_cancel_Id);
		cancel.setOnClickListener(this);		
	}
	private void configMethod(){
		if(alertDialog!=null){
			alertDialog.dismiss();
			alertDialog=null;
		}	
	}
	private void cancelMethod(){
		if(alertDialog!=null){
			alertDialog.dismiss();
			alertDialog=null;
		}	
	}
}
