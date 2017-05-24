package com.kds.system;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.kds.cateye.R;

public class RingSetMenu extends Activity implements OnClickListener{
	private RadioGroup radioRingSet;
	private MediaPlayer mp ;
	private String strMusic[]={"/mnt/sdcard/external_sd/hongmeigui.mp3","/mnt/sdcard/external_sd/taiduo.mp3"};
	private int IndexMusic=0;
	public final static String RingMenuTag="ring";
	public final static String RingMenuBellIndex="bellRing";
	public final static String RingMenuAlarmIndex="alarmRing";
	private String ringMenu=RingMenuBellIndex;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_set_menu);

        InitView();
    }

    private void InitView(){
        mp= new MediaPlayer();
        radioRingSet = (RadioGroup)findViewById(R.id.radioRingSetId);
        //��ȡ�������Ľ����menu
        ringMenu = getIntent().getStringExtra(RingSetMenu.RingMenuTag);
        if(ringMenu!=null){
        	if(ringMenu.equals(RingSetMenu.RingMenuBellIndex)){	//��������
        		IndexMusic=(Integer) SystemConfig.getInstance().getRingBell(getApplicationContext(), 0);
        		 InitRingSetMenu();
        	}else{	//��������
        		IndexMusic=(Integer) SystemConfig.getInstance().getRingAlarm(getApplicationContext(), 0);
        	}
        }
        Button  butRingSetMenu_back = (Button)findViewById(R.id.butRingSetMenu_backId);
        butRingSetMenu_back.setOnClickListener(this);
        
        radioRingSet.check(radioRingSet.getChildAt(IndexMusic).getId());
        radioRingSet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				RadioButton radioRing = (RadioButton)findViewById(radioRingSet.getCheckedRadioButtonId());
				 String selectText = radioRing.getText().toString();
				 int index =radioRingSet.indexOfChild(radioRingSet.findViewById(radioRingSet.getCheckedRadioButtonId()));
				 showToast(selectText+"-->index:"+index);	
				 if(selectText.equals(getString(R.string.time_alarm_string))){
					 playRing(strMusic[0]);
				 }else{
					 playRing(strMusic[1]);
				 }
				 if(ringMenu.equals(RingSetMenu.RingMenuBellIndex)){
					 SystemConfig.getInstance().setRingBell(getApplicationContext(),  index);
				 }else {
					 SystemConfig.getInstance().setRingAlarm(getApplicationContext(), index);
				 }
			}
		});
    }
    
    private void InitRingSetMenu(){
    	for(int i=0;i<radioRingSet.getChildCount();i++){
    		RadioButton radioRing = (RadioButton)findViewById(radioRingSet.getChildAt(i).getId());
    		radioRing.setText("����"+i);
    	}
    }
    
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butRingSetMenu_backId:
			stop();
			finish();
			break;

		default:
			break;
		}
	}

	private void playRing(String musicName){
		File file = new File(musicName);
		if(!file.exists()){
			showToast("not file");
			return;
		}
	      try {
	    	mp.stop();
	    	mp.reset();
			mp.setDataSource(musicName);
			mp.prepare();
	        mp.seekTo(0);
	        mp.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void playOrPause() {
	    if(mp.isPlaying()){
	        mp.pause();
	    } else {
	        mp.start();
	    }
	}
	public void stop() {
	    if(mp != null) {
	        mp.stop();
	        try {
	            mp.prepare();
	            mp.seekTo(0);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}