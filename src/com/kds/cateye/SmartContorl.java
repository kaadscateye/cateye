package com.kds.cateye;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kds.database.SystemConfig;
import com.kds.system.RingSetMenu;
import com.kds.system.ShowAlarm_time;
import com.kds.system.ShowSet_talkpicture;
import com.kds.system.Show_monitoring_sensitivity;

public class SmartContorl extends Activity implements OnClickListener{

	AlertDialog alertDialog=null;
	AudioManager mAudioManager;
	public final static int Menu_Smart_Set_alarm_time=0x1;
	public final static int Menu_Smart_Monitoring_sensitivity=0x2;
	public final static int Menu_Smart_Automatic_alarm_bell=0x3;
	public final static int Menu_Smart_talk_photo=0x4;
	public final static int Menu_leave_recoder_mode=0x5;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_contorl);

        InitUImenu();

    }
    private void InitUImenu() {
  		Button butsetMenuBack = (Button)findViewById(R.id.butSmartContorl_backId);
  		ToggleButton switchsmart_pir=(ToggleButton)findViewById(R.id.switchsmart_pirId);
  		
  		LinearLayout Set_smart_alarm_time = (LinearLayout)findViewById(R.id.linear_Smart_Set_alarm_timeId);
   		LinearLayout Monitoring_sensitivity = (LinearLayout)findViewById(R.id.linear_Smart_Monitoring_sensitivity_Id);
   		LinearLayout Automatic_alarm_bell = (LinearLayout)findViewById(R.id.linear_Smart_Automatic_alarm_bell_Id);
   		LinearLayout Vol_Set = (LinearLayout)findViewById(R.id.linear_Smart_Vol_Set_Id);
   		LinearLayout smart_talk_photo= (LinearLayout)findViewById(R.id.linear_Smart_talk_photo_mode_Id);
   		LinearLayout leave_recoder_mode= (LinearLayout)findViewById(R.id.linear_leave_recoder_mode_Id);
  		butsetMenuBack.setOnClickListener(this);
   		Set_smart_alarm_time.setOnClickListener(this);
  		Monitoring_sensitivity.setOnClickListener(this);
  		Automatic_alarm_bell.setOnClickListener(this);
  		Vol_Set.setOnClickListener(this);
  		smart_talk_photo.setOnClickListener(this);

  		leave_recoder_mode.setOnClickListener(this);
  		

  		int pirState = (Integer) SystemConfig.getInstance().getSmart_pirState(getApplicationContext(), 0);
  		if(pirState==SystemConfig.close){
  			switchsmart_pir.setChecked(false);
  		}else{
  			switchsmart_pir.setChecked(true);
  		}
  		switchsmart_pir.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				int state=0;
				if(arg1){
					state=SystemConfig.open;
				}else{
					state=SystemConfig.close;
				}
				SystemConfig.getInstance().setSmart_pirState(getApplicationContext(), state);
			}
		});
  		updateUimenu();
  	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butSmartContorl_backId:	//返回按钮
			finish();
			break;
		case R.id.linear_Smart_Set_alarm_timeId:	//设置智能报警时间
			startActivity(new Intent().setClass(getApplicationContext(), ShowAlarm_time.class));
			break;
		case R.id.linear_Smart_Monitoring_sensitivity_Id://监控灵敏度设置
			startActivity(new Intent().setClass(getApplicationContext(), Show_monitoring_sensitivity.class));
			break;
		case R.id.linear_Smart_Automatic_alarm_bell_Id:	//自动报警铃声
			Intent obj = new Intent();
			obj.putExtra(RingSetMenu.RingMenuTag, RingSetMenu.RingMenuAlarmIndex);
			startActivity(obj.setClass(getApplicationContext(), RingSetMenu.class));
			break;
		case R.id.linear_Smart_Vol_Set_Id:		//音量设置
			showSetVol();
			break;
		case R.id.linear_Smart_talk_photo_mode_Id:
			startActivity(new Intent().setClass(getApplicationContext(), ShowSet_talkpicture.class));
			break;
		case R.id.linear_leave_recoder_mode_Id:
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
	//进入子界面设置好之后，返回到当前界面，需要更新ui 数据
	private void updateUimenu(){
		//更新智能报警时间
		TextView smart_alarm_time = (TextView)findViewById(R.id.Smart_alarm_timeValueId);
		int time = (Integer) SystemConfig.getInstance().getAlarmTime(getApplication(),1);
		smart_alarm_time.setText(""+time+" "+getString(R.string.time_sec_string));
		
		//更新监控灵敏度
		int sensitivity = (Integer) SystemConfig.getInstance()
				.getMonitor_sensitivity(getApplicationContext(), 0);
		TextView Monitoring_sensitivity_text = (TextView)findViewById(R.id.Monitoring_sensitivity_text_Id);
		if(sensitivity==SystemConfig.sensitivity_high){
			Monitoring_sensitivity_text.setText(R.string.Light_sensitivity_high_string);
		}else if(sensitivity==SystemConfig.sensitivity_middle){
			Monitoring_sensitivity_text.setText(R.string.Light_sensitivity_middle_string);
		}else if(sensitivity==SystemConfig.sensitivity_low){
			Monitoring_sensitivity_text.setText(R.string.Light_sensitivity_low_string);
		}
		//更新自动报警铃声
		TextView Automatic_alarm_bell_text = (TextView)findViewById(R.id.Automatic_alarm_bell_text_Id);
		String str=(String) SystemConfig.getInstance().getRingAlarm(getApplicationContext(), "");
		Automatic_alarm_bell_text.setText(str);
		//更新连拍模式
  		TextView Smart_talk_photo_mode= (TextView)findViewById(R.id.Smart_talk_photo_mode_Id);
		int mode = (Integer) SystemConfig.getInstance().getTalk_photo_mode(getApplicationContext(), 0);
		if(mode==SystemConfig.istalk_video_mode){
			Smart_talk_photo_mode.setText(R.string.talk_photo_recoderVideo_string);
		}else{
			Smart_talk_photo_mode.setText(R.string.talk_photo_picture_string);
		}

		//更新留言模式
	}
	@Override
	protected void onResume() {
		updateUimenu();
		Log.e("smartcontorl", "onResume");
		super.onResume();
	}

	@Override
	protected void onPause() {

		Log.e("smartcontorl", "onPause");
		super.onPause();
	}
    /*onActivityResult接收数据的方法  */  
    @Override  
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        super.onActivityResult(requestCode, resultCode, data);  
        String content=data.getStringExtra("data");//字符串content得到data数据  
        switch (resultCode) {
       		case Menu_Smart_Set_alarm_time:
       			break;
       		case Menu_Smart_Monitoring_sensitivity:
       			break;
       		case Menu_Smart_Automatic_alarm_bell:
       			break;
       		case Menu_Smart_talk_photo:
       			break;
       		case Menu_leave_recoder_mode:
       			break; 			
       		default:
       			break;
        }
    } 
}
