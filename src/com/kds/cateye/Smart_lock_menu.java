package com.kds.cateye;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Smart_lock_menu extends Activity implements OnClickListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_lock_menu);
        
        Button back = (Button)findViewById(R.id.butSmart_lock_backId);
        Button Add_smart_lock = (Button)findViewById(R.id.Add_smart_lockId);
        Button Delete_smart_lock = (Button)findViewById(R.id.Delete_smart_lockId);
        
        back.setOnClickListener(this);
        Add_smart_lock.setOnClickListener(this);
        Delete_smart_lock.setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butSmart_lock_backId:
			finish();
			break;
		case R.id.Add_smart_lockId:
			showToast("开始添加智能门锁");
			break;
		case R.id.Delete_smart_lockId:
			showToast("删除智能门锁");
			break;
		default:
			break;
		}
	}
	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
}
