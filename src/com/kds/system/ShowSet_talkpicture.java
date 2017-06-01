package com.kds.system;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kds.cateye.R;
import com.kds.database.SystemConfig;

public class ShowSet_talkpicture extends Activity implements OnClickListener,android.widget.RadioGroup.OnCheckedChangeListener{
	TextView texttalk;
	RadioGroup rGroup;
	int mode=SystemConfig.istalk_photo_mode;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_set_talkpicture);
		
		InitUIview();
	}

	private void InitUIview(){
		Button butset_talkpiture_back =(Button)findViewById(R.id.butset_talkpiture_backId);
		
		texttalk = (TextView)findViewById(R.id.textShowSet_talkpictureId);
		Switch sw = (Switch)findViewById(R.id.SwitchSet_talkpictureId);
		rGroup = (RadioGroup)findViewById(R.id.RadioshowSet_numsId);
		
		mode = (Integer) SystemConfig.getInstance().getTalk_photo_mode(getApplicationContext(), 0);
		if(mode==SystemConfig.istalk_video_mode){
			sw.setChecked(true);
		}
		UpdateUI_menu(mode);
		
		butset_talkpiture_back.setOnClickListener(this);
		sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				if(arg1){
					mode=SystemConfig.istalk_video_mode;
				}else{	
					mode=SystemConfig.istalk_photo_mode;
				}
				SystemConfig.getInstance().setTalk_photo_mode(getApplicationContext(), mode);
				UpdateUI_menu(mode);
			}
		});
		rGroup.setOnCheckedChangeListener(this);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butset_talkpiture_backId:
			finish();
			break;

		default:
			break;
		}
	}
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.RadioshowSet_numsId:
			int index= arg0.indexOfChild(arg0.findViewById(arg0.getCheckedRadioButtonId()));
			showToast("index = "+index);
			if(mode==SystemConfig.istalk_photo_mode){
				SystemConfig.getInstance().setTalk_photo_data(getApplicationContext(), index);
			}else{
				SystemConfig.getInstance().setTalk_video_data(getApplicationContext(), index);
			}
			break;

		default:
			break;
		}
	}
	private void UpdateUI_menu(int mode){
		RadioButton rButton;
		int modedata=0;
		if(mode==SystemConfig.istalk_video_mode){
			showToast("ÊÓÆµ");
			texttalk.setText(getString(R.string.recoderTime_string));
			rButton = (RadioButton)findViewById(rGroup.getChildAt(0).getId());
			rButton.setText(getString(R.string.talk_video_once_string));
			rButton = (RadioButton)findViewById(rGroup.getChildAt(1).getId());
			rButton.setText(getString(R.string.talk_video_three_string));
			rButton = (RadioButton)findViewById(rGroup.getChildAt(2).getId());
			rButton.setText(getString(R.string.talk_video_five_string));
			modedata = (Integer) SystemConfig.getInstance().getTalk_video_data(getApplicationContext(), 0);
			if(modedata>rGroup.getChildCount()&&modedata<0){
				modedata=0;
			}
			SystemConfig.getInstance().setTalk_video_data(getApplicationContext(), modedata);
		}else{
			showToast("ÕÕÆ¬");
			texttalk.setText(getString(R.string.talk_photo_num_string));
			rButton = (RadioButton)findViewById(rGroup.getChildAt(0).getId());
			rButton.setText(getString(R.string.talk_photo_once_string));
			rButton = (RadioButton)findViewById(rGroup.getChildAt(1).getId());
			rButton.setText(getString(R.string.talk_photo_three_string));
			rButton = (RadioButton)findViewById(rGroup.getChildAt(2).getId());
			rButton.setText(getString(R.string.talk_photo_five_string));
			modedata = (Integer) SystemConfig.getInstance().getTalk_photo_data(getApplicationContext(), 0);
			if(modedata>rGroup.getChildCount()&&modedata<0){
				modedata=0;
			}
			SystemConfig.getInstance().setTalk_photo_data(getApplicationContext(), modedata);
		}
		rGroup.check(rGroup.getChildAt(modedata).getId());
	}
}
