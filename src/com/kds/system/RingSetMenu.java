package com.kds.system;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demotools.LogUtils;
import com.example.demotools.PlayRing;
import com.kds.SystemTools.AssetsFile;
import com.kds.SystemTools.SystemFileUtils;
import com.kds.cateye.R;
import com.kds.database.SystemConfig;

public class RingSetMenu extends Activity implements OnClickListener{
	private RadioGroup radioRingSet;
	private PlayRing mplayer=null;
	private String strMusic[]={"/mnt/sdcard/external_sd/hongmeigui.mp3","/mnt/sdcard/external_sd/taiduo.mp3"};
	public final static String RingMenuTag="ring";
	public final static String RingMenuBellIndex="bellRing";
	public final static String RingMenuAlarmIndex="alarmRing";
	private String ringMenu=RingMenuBellIndex;
	private TextView ringMenuText=null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring_set_menu);

        InitView();
    }

    private void InitView(){
    	int IndexRing=0;
    	ringMenuText = (TextView)findViewById(R.id.ringMenuTextId);
        radioRingSet = (RadioGroup)findViewById(R.id.radioRingSetId);
        Button  butRingSetMenu_back = (Button)findViewById(R.id.butRingSetMenu_backId);
        ringMenu = getIntent().getStringExtra(RingSetMenu.RingMenuTag); //��ȡ�������Ľ����menu
        if(ringMenu!=null){
        	IndexRing =InitRingSetMenu(ringMenu);
        }
        
        mplayer = new PlayRing();
        butRingSetMenu_back.setOnClickListener(this);
        radioRingSet.check(radioRingSet.getChildAt(IndexRing).getId());	//��ȡ��SharedPreferences ��������ݣ������µ��ؼ�����
        
        radioRingSet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				RadioButton radioRing = (RadioButton)findViewById(radioRingSet.getCheckedRadioButtonId());
				 String selectText = radioRing.getText().toString();
				 int index =radioRingSet.indexOfChild(radioRingSet.findViewById(radioRingSet.getCheckedRadioButtonId()));
				 showToast(selectText+"-->index:"+index);	
				 if(selectText.equals(getString(R.string.time_recoder_voices_string))){ 
					 SelectAndroid_AudioFile();
				 }
				 mplayer.playMusicFile(PlayRing.strMusic[0], 15);
				 if(ringMenu.equals(RingSetMenu.RingMenuBellIndex)){	//�����������
					 SystemConfig.getInstance().setRingBell(getApplicationContext(),  selectText);
				 }else {												//���ø澯��������
					 SystemConfig.getInstance().setRingAlarm(getApplicationContext(), selectText);
				 }
			}
		});
    }
    //��ʼ����������
	private int InitRingSetMenu(String ringMenu){	//ringMenu: ���ִ������Ľ���
		String setRing ="";
		int index=0;
		LogUtils.e("GetRing_SharedPreferencesIndex LoadAssetsFile: ");
    	String ringStr  =AssetsFile.LoadAssetsFile(getApplicationContext(),"RingName_cn.json");
    	LogUtils.e(ringStr);
		if(ringMenu.equals(RingSetMenu.RingMenuBellIndex)){	//��������
			ringMenuText.setText(getString(R.string.bell_select_string));
    		setRing=(String) SystemConfig.getInstance().getRingBell(getApplicationContext(), "");
    	}else{	//��������
    		setRing=(String) SystemConfig.getInstance().getRingAlarm(getApplicationContext(), "");
    		ringMenuText.setText(getString(R.string.Automatic_alarm_bell_string));
    	}	
		try {
			JSONObject json = new JSONObject(ringStr);
			JSONArray jsonArray=(JSONArray) json.get(ringMenu);
	    	for(int i=0;i<radioRingSet.getChildCount()||i<jsonArray.length();i++){
	    		LogUtils.e("ringMenu = "+ringMenu+"--->"+jsonArray.get(i).toString());
	    		RadioButton radioRing = (RadioButton)findViewById(radioRingSet.getChildAt(i).getId());
	    		radioRing.setText(jsonArray.get(i).toString());
	    		if(setRing.equals(jsonArray.get(i).toString())){
	    			index=i;
	    		}
	    	}
//			for (int i =0;i<jsonArray.length();i++) {
//				LogUtils.e(jsonArray.get(i).toString());
//				
//			}	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			LogUtils.e("parse json failed ");
		}

		return index;
	}

	
	private void SelectAndroid_AudioFile(){		//ѡ��Android �ֻ���Ƶ�ļ�
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*"); //ѡ����Ƶ ����������*/*��
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent,1);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {//���յ�����������Ƶ�ļ�
	    if (resultCode == Activity.RESULT_OK) {//�Ƿ�ѡ��ûѡ��Ͳ������
	            Uri uri = data.getData(); // Get the Uri of the selected file 
	            String path = SystemFileUtils.getPath(this, uri);
	            showToast(path);
	        }           
	    super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butRingSetMenu_backId:
			mplayer.stop();
            finish();//ҳ������  
			break;

		default:
			break;
		}
	}
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
