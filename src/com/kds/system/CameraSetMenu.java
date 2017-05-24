package com.kds.system;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.kds.cateye.R;

public class CameraSetMenu extends Activity implements OnClickListener,OnCheckedChangeListener{
	private RadioGroup radioLight_sensitivity,radiolightSetHz;
	private String CameraSetTag="CameraSetMenu";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_set_menu);
        
        InitUIView();
    }
    private void InitUIView() {

		Button butsetMenuBack = (Button)findViewById(R.id.butCamera_setMenu_backId);
		butsetMenuBack.setOnClickListener(this);
		
		radioLight_sensitivity = (RadioGroup)findViewById(R.id.radioLight_sensitivitySetId);
		radioLight_sensitivity.setOnCheckedChangeListener(this);
		int Indexsensitivity =(Integer) SystemConfig.getInstance().getLight_sensitivity(getApplicationContext(), 0);
		if(Indexsensitivity<0||Indexsensitivity>radioLight_sensitivity.getChildCount()){
			SystemConfig.getInstance().setLight_sensitivity(getApplicationContext(), 0);
		}
		radioLight_sensitivity.check(radioLight_sensitivity.getChildAt(Indexsensitivity).getId());
		
		radiolightSetHz= (RadioGroup)findViewById(R.id.radiolightSetHzId);
		radiolightSetHz.setOnCheckedChangeListener(this);
		int IndexHz = (Integer) SystemConfig.getInstance().getlight_hz(getApplicationContext(), 0);		
		if(IndexHz<0||IndexHz>radiolightSetHz.getChildCount()){
			showToast("IndexHz:"+IndexHz+"radiolightSetHz:"+radiolightSetHz.getChildCount());
			Log.e(CameraSetTag, "IndexHz:"+IndexHz+"radiolightSetHz:"+radiolightSetHz.getChildCount());
			IndexHz = 0;
			SystemConfig.getInstance().setlight_hz(getApplicationContext(), IndexHz);	
		}
		radiolightSetHz.check(radiolightSetHz.getChildAt(IndexHz).getId());
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butCamera_setMenu_backId:
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
		String selectText="";
		RadioButton radio;
		int index=0;
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.radioLight_sensitivitySetId:
			radio = (RadioButton)findViewById(radioLight_sensitivity.getCheckedRadioButtonId());
			selectText = radio.getText().toString();
			index=radioLight_sensitivity.indexOfChild(radioLight_sensitivity.findViewById(radioLight_sensitivity.getCheckedRadioButtonId()));	
			SystemConfig.getInstance().setLight_sensitivity(getApplicationContext(), index);
			break;
		case R.id.radiolightSetHzId:
			radio = (RadioButton)findViewById(radiolightSetHz.getCheckedRadioButtonId());
			selectText = radio.getText().toString();
			index=radiolightSetHz.indexOfChild(radiolightSetHz.findViewById(radiolightSetHz.getCheckedRadioButtonId()));
			SystemConfig.getInstance().setlight_hz(getApplicationContext(), index);
			break;
		default:
			break;
		}
		showToast(selectText+"-->index:"+index);
	}
}
