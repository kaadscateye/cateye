package com.kds.cateye;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kds.system.CameraSetMenu;
import com.kds.system.ExternalStorage;
import com.kds.system.RingSetMenu;
import com.kds.system.ScreenTime;
import com.kds.system.WifiAdmin;
import com.kds.system.WifiMenu;
import com.kds.system.qr_codescan;

public class SetMenu extends Activity implements OnClickListener{

	AlertDialog alertDialog=null;
	private WifiAdmin wifiAdmin = null;
	private TextView Set_Menu_wifi_name;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_menu);
        InitUImenu();
    }
    private void InitUImenu() {

		Button butsetMenuBack = (Button)findViewById(R.id.butsetMenuBackId);
		butsetMenuBack.setOnClickListener(this);
		
		TextView Set_Menu_Display = (TextView)findViewById(R.id.Set_Menu_Display_Id);//��ʾ
		TextView camera_Set = (TextView)findViewById(R.id.Set_Menu_camera_Set_Id);//����ͷ����
		TextView Set_Menu_Scan_QR_Code = (TextView)findViewById(R.id.Set_Menu_Scan_QR_Code_Id);//��ά��ɨ��
		TextView Set_Menu_set_bell = (TextView)findViewById(R.id.Set_Menu_set_bell_Id);//��������
		TextView Set_Menu_power_set_menu = (TextView)findViewById(R.id.Set_Menu_power_set_menu_Id);//����ģʽ����
		TextView Set_Menu_system_time = (TextView)findViewById(R.id.Set_Menu_system_time_Id);//ʱ������
		TextView Set_Menu_language = (TextView)findViewById(R.id.Set_Menu_language_Id);//����
		TextView Set_Menu_wifi = (TextView)findViewById(R.id.Set_Menu_wifi_Id);//����wifi����
//		TextView Set_Menu_smart_lock = (TextView)findViewById(R.id.Set_Menu_smart_lock_Id);//������������
		TextView Set_Menu_storage = (TextView)findViewById(R.id.Set_Menu_storage_Id);//�洢
		TextView Set_Menu_Factory_reset = (TextView)findViewById(R.id.Set_Menu_Factory_reset_Id);//�ָ���������
		TextView Set_Menu_Software_version = (TextView)findViewById(R.id.Set_Menu_Software_version_Id);//����汾

		Set_Menu_wifi_name = (TextView)findViewById(R.id.Set_Menu_wifi_nameId);//��ǰ���ӵ�wifi����
		
		Set_Menu_Display.setOnClickListener(this);
		camera_Set.setOnClickListener(this);
		Set_Menu_Scan_QR_Code.setOnClickListener(this);
		Set_Menu_set_bell.setOnClickListener(this);
		Set_Menu_power_set_menu.setOnClickListener(this);
		Set_Menu_system_time.setOnClickListener(this);
		Set_Menu_language.setOnClickListener(this);
		Set_Menu_wifi.setOnClickListener(this);
//		Set_Menu_smart_lock.setOnClickListener(this);
		Set_Menu_storage.setOnClickListener(this);
		Set_Menu_Factory_reset.setOnClickListener(this);
		Set_Menu_Software_version.setOnClickListener(this);
		
		
		//��ȡwifi ������Ϣ
		getWifiMessage();
		
	}

	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butsetMenuBackId:
			finish();
			break;
		case R.id.Set_Menu_Display_Id:	
			startActivity(new Intent().setClass(getApplicationContext(), DisplayMenu.class));
			break;
		case R.id.Set_Menu_camera_Set_Id:
			startActivity(new Intent().setClass(getApplicationContext(), CameraSetMenu.class));
			break;	
		case R.id.Set_Menu_Scan_QR_Code_Id:
			startActivity(new Intent().setClass(getApplicationContext(), qr_codescan.class));
			break;
		case R.id.Set_Menu_set_bell_Id:
			Intent obj = new Intent();
			obj.putExtra(RingSetMenu.RingMenuTag, RingSetMenu.RingMenuBellIndex);
			startActivity(obj.setClass(getApplicationContext(), RingSetMenu.class));
			break;
		case R.id.Set_Menu_power_set_menu_Id:
			break;
		case R.id.Set_Menu_system_time_Id:
			break;
		case R.id.Set_Menu_language_Id:
			break;
		case R.id.Set_Menu_wifi_Id:	//wifi����
			startActivity(new Intent().setClass(getApplicationContext(), WifiMenu.class));
			break;
//		case R.id.Set_Menu_smart_lock_Id:
//			startActivity(new Intent().setClass(getApplicationContext(), Smart_lock_menu.class));
//			break;
		case R.id.Set_Menu_storage_Id:
			startActivity(new Intent().setClass(getApplicationContext(), ExternalStorage.class));
			break;	
		case R.id.Set_Menu_Factory_reset_Id:
			FactoryFromat();
			break;
		case R.id.Set_Menu_Software_version_Id:
			break;
		case R.id.format_config_Id:
			showToast("ȷ��");
			if(alertDialog!=null){
				alertDialog.dismiss();
				alertDialog=null;
				materClear(getApplicationContext());
			}
			break;
		case R.id.format_cancel_Id:
			showToast("ȡ��");
			if(alertDialog!=null){
				alertDialog.dismiss();
				alertDialog=null;
			}
			break;
		default:
			break;
		}
	};
	
	private void getWifiMessage(){
		wifiAdmin = new WifiAdmin(this);
    	int wifiState = wifiAdmin.getWifiState();
        if (wifiState == 0) {  
        	showToast("Wifi���ڹر�");  
        } else if (wifiState == 1) {  
        	showToast("Wifi�Ѿ��ر�");   
        	Set_Menu_wifi_name.setText("�ر�");
        } else if (wifiState == 2) {  
        	showToast("Wifi���ڿ���");   
        } else if (wifiState == 3) {  
        	showToast("Wifi�Ѿ�����"); 
        	Set_Menu_wifi_name.setText(wifiAdmin.getSsid());
        } else {  
        	showToast("û�л�ȡ��WiFi״̬");
        }
	}
	
	private void FactoryFromat(){
		alertDialog = new AlertDialog.Builder(SetMenu.this).create();  
		alertDialog.show();  
		Window window = alertDialog.getWindow();  
		window.setContentView(R.layout.format_menu);  
		Button  format_config = (Button)window.findViewById(R.id.format_config_Id);
		Button  format_cancel = (Button)window.findViewById(R.id.format_cancel_Id);
		format_config.setOnClickListener(this);
		format_cancel.setOnClickListener(this);
	}
	//�ָ���������
	public  void materClear(Context context)  {  
	    Intent clearIntent = new Intent();  
	    ComponentName cn = new ComponentName("com.android.settings","com.android.settings.MasterClear");  
	    clearIntent.setComponent(cn);  
	    context.startService(clearIntent);  
	}  
}
