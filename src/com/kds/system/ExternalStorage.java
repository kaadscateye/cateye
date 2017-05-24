package com.kds.system;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.text.format.Formatter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kds.cateye.R;

@SuppressLint("NewApi")
public class ExternalStorage extends Activity implements OnClickListener {
	AlertDialog alertDialog=null;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_external_storage);
		
		initUI();
	}

	private void initUI() {
		Button back = (Button) findViewById(R.id.butStoragebakeId);
		Button Format_memory = (Button) findViewById(R.id.Format_memoryId);
		TextView totalMemory = (TextView) findViewById(R.id.totalMemoryId);
		TextView spaceMemory = (TextView) findViewById(R.id.spaceMemoryId);

		back.setOnClickListener(this);
		Format_memory.setOnClickListener(this);

		Environment.getExternalStorageDirectory().getPath();
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			showToast("sdcard�����ڻ�δ����");
			
			return;
		}
		 File sdcard_filedir = Environment.getExternalStorageDirectory();//�õ�sdcard��Ŀ¼��Ϊһ���ļ�����
         long usableSpace = sdcard_filedir.getUsableSpace();//��ȡ�ļ�Ŀ¼����ʣ��ռ�
         long totalSpace = sdcard_filedir.getTotalSpace();
         
         //��һ��long���͵��ļ���С��ʽ�����û����Կ�����M��G�ַ���

         String totalSpace_str = Formatter.formatFileSize(getApplicationContext(), totalSpace);
         totalMemory.setText(totalSpace_str);
         String usableSpace_str = Formatter.formatFileSize(getApplicationContext(), totalSpace-usableSpace);
         spaceMemory.setText(usableSpace_str);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butStoragebakeId:
			finish();
			break;
		case R.id.Format_memoryId:
			FactorySdcard();
			break;
		case R.id.format_config_Id:
			showToast("ȷ��");
			if(alertDialog!=null){
				alertDialog.dismiss();
				alertDialog=null;
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
	}

	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	private void FactorySdcard(){
		alertDialog = new AlertDialog.Builder(ExternalStorage.this).create();  
		alertDialog.show();  
		Window window = alertDialog.getWindow();  
		window.setContentView(R.layout.format_menu);  
		Button  format_config = (Button)window.findViewById(R.id.format_config_Id);
		Button  format_cancel = (Button)window.findViewById(R.id.format_cancel_Id);
		format_config.setOnClickListener(this);
		format_cancel.setOnClickListener(this);
	}
}
