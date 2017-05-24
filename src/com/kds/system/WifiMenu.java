package com.kds.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.kds.cateye.R;

public class WifiMenu extends Activity implements android.view.View.OnClickListener{
	WifiAdmin wifiAdmin = null;
    EditText ssid, passwd;
	private ArrayList<HashMap<String, String>> wifiArr;
	private wifiAdapter wifiAdapter=null;
	private int wifiState=0;
	private boolean taskTimer=false;
	private int TIME = 1000;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_menu);
        
        wifiAdmin = new WifiAdmin(this);
        initView();
    }
    private void initView() {   
    	
    	Button back = (Button)findViewById(R.id.butWifi_backId);
    	back.setOnClickListener(this);
    	
    	Switch sw = (Switch)findViewById(R.id.switchWifiId);
    	sw.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
				checkChangeListener(arg1);
			}
		});

        ListView list = (ListView) findViewById(R.id.listWifiId);
        wifiArr = new ArrayList<HashMap<String, String>>();
        wifiAdapter = new wifiAdapter(this, wifiArr);
		list.setAdapter(wifiAdapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
	     		TextView mText = (TextView) arg1.findViewById(R.id.textWifiId);
	     		String ssid = mText.getText().toString().trim();	
	     		showToast(ssid);  
			}
		});
    	wifiState = wifiAdmin.getWifiState();
        if (wifiState == 0) {  
        	showToast("Wifi正在关闭");  
        } else if (wifiState == 1) {  
        	showToast("Wifi已经关闭");   
        	sw.setChecked(false);
        } else if (wifiState == 2) {  
        	showToast("Wifi正在开启");   
        } else if (wifiState == 3) {  
        	showToast("Wifi已经开启"); 
        	sw.setChecked(true);
        } else {  
        	showToast("没有获取到WiFi状态");
        }
    }
    private void checkChangeListener(boolean state){
		if(state){
			showToast("Wifi打开"); 
			wifiAdmin = new WifiAdmin(this);
			wifiAdmin.openWifi();		
			if(taskTimer==false){
				taskTimer=true;
				showToast("打开定时器");
				handler.postDelayed(runnable, TIME); //每隔1s执行
			}
		}else{
			showToast("Wifi关闭");  
			if(wifiAdapter!=null){
				wifiAdapter.remove_list();
			}
			wifiAdmin.closeWifi();	
//			handler.removeCallbacks(runnable);
			taskTimer=false;
		}   	
    }
    private void LoadWifi_list(){
    	Log.e("LoadWifi_list","start getScanResultList");
		ScanResult mScanResult;
		List<ScanResult> mWifiList = wifiAdmin.getScanResultList();
        if (mWifiList != null) {
            for (int i = 0; i < mWifiList.size(); i++) {
                mScanResult = mWifiList.get(i);
                int backgroundId=R.drawable.stat_sys_wifi_signal_0;
                int level = Math.abs(mScanResult.level);
                if(level<20){
                	backgroundId=R.drawable.stat_sys_wifi_signal_0;
                }else if(level>=20&&level<40){
                	backgroundId=R.drawable.stat_sys_wifi_signal_1;
                }else if(level>=40&&level<60){
                	backgroundId=R.drawable.stat_sys_wifi_signal_2;
                }else if(level>=60&&level<80){
                	backgroundId=R.drawable.stat_sys_wifi_signal_3;
                }else if(level>=80){
                	backgroundId=R.drawable.stat_sys_wifi_signal_4;
                }
                wifiAdapter.add_item(mScanResult.SSID,""+backgroundId);
                Log.e(mScanResult.SSID, "-->"+mScanResult.level+"-->"+level);
            }
        }else{
        	
        	Log.e("LoadWifi_list","getScanResultList failed");
        } 

    }
    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    private void ConnectWifi(String ssid,String passwd){
    	wifiAdmin.addNetwork(wifiAdmin.CreateWifiInfo_new(ssid, passwd));
    }
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butWifi_backId:
			finish();
			break;

		default:
			break;
		}
	}
	 Handler handler = new Handler();  
	    Runnable runnable = new Runnable() {  
	  
	        @Override  
	        public void run() {  
	            // handler自带方法实现定时器  
	            try {  
	            	int WifiState =wifiAdmin.getWifiState();
	            	if(WifiState== 3){
//	            		handler.removeCallbacks(runnable);
	            		LoadWifi_list();
	            	}else{
	            		handler.postDelayed(this, TIME);  
	            	}
	            	Log.e("do...","WifiState:"+WifiState);  
	            } catch (Exception e) {  
	                // TODO Auto-generated catch block  
	                e.printStackTrace();  
	                System.out.println("exception...");  
	            }  
	        }  
	    }; 
}
