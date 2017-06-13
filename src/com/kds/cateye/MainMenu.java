package com.kds.cateye;

import BrocastEvent.ReceiverEvent;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kds.h264.Jpeg;
import com.kds.h264.Preview;
import com.kds.system.common;

@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi", "NewApi" })
public class MainMenu extends Activity implements OnClickListener,
		PreviewCallback {
	private Preview mPreview;
	private Button mBtnSwitch, scanphoto, mBtnSet, smart_lock, CameraSwitch;

	private ImageButton leftMenu;
	private Camera mCamera;
	private int numCamera;
	private int cameraId;
	private int cameraCurrentId = 0;
	public static int heightPixels = 720, widthPixels = 1280;
	protected static ViewGroup mLayout;
	private Boolean menuState = true;
	static final int menuId = 0x9, SwitchId = 0x10, scanphotoId = 0x11,
			mBtnSetId = 0x12, smart_lockId = 0x13, CameraSwitchId = 0x14;
	LinearLayout lineleft;

	Jpeg jpeg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		mLayout = new AbsoluteLayout(this);
		mLayout.setId(menuId);
		mLayout.setOnClickListener(this);

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		heightPixels = dm.heightPixels;
		widthPixels = dm.widthPixels;

		mPreview = new Preview(this);
		setContentView(mPreview);

		setContentView(mLayout);
		mLayout.addView(mPreview);

		InitView();
		numCamera = Camera.getNumberOfCameras();
		CameraInfo info = new CameraInfo();
		for (int i = 0; i < numCamera; i++) {
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
				cameraId = i;
			}
		}
		jpeg = new Jpeg();

		initBrocastReceiver();
	}
	//注册广播接收器，用于接收底层按键发送上来的广播
	private void initBrocastReceiver(){
		IntentFilter iFilter = new IntentFilter();
		iFilter.addAction("com.kdscateye.start");
		iFilter.addAction("com.kdscateye.Key_ring");
		iFilter.addAction("com.kdscateye.Key_pir");
		ReceiverEvent bReceiver = new ReceiverEvent();
		registerReceiver(bReceiver, iFilter);
		SendBroadcast("" + ReceiverEvent.Start_Brocast, "com.kdscateye.start");// 启动后台接收广播
	}
	
	@Override
	protected void onResume() {
		mCamera = Camera.open(cameraCurrentId);
		mPreview.setCamera(mCamera);
		mCamera.setPreviewCallback(this);

		mPreview.switchCamera(mCamera);
		mCamera.startPreview();

		cameraCurrentId = cameraId;
		Log.e("onResume", "mCamera");

		super.onResume();
	}

	@Override
	protected void onPause() {
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mPreview.setCamera(null);
			mCamera.release();
			mCamera = null;
		}
		Log.e("onPause", "mCamera");
		super.onPause();
	}

	private void InitView() {

		leftMenu = new ImageButton(this);
		leftMenu.setOnClickListener(this);

		leftMenu.setBackgroundResource(R.drawable.leftback);
		AbsoluteLayout.LayoutParams lp0 = new AbsoluteLayout.LayoutParams(
				widthPixels / 5, ViewGroup.LayoutParams.FILL_PARENT,
				widthPixels - widthPixels / 6,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mLayout.addView(leftMenu, lp0);

		mBtnSwitch = new Button(this);
		mBtnSwitch.setText("切换");
		mBtnSwitch.setId(SwitchId);
		mBtnSwitch.setOnClickListener(this);

		CameraSwitch = new Button(this);
		CameraSwitch.setText("智能监控");
		CameraSwitch.setId(CameraSwitchId);
		CameraSwitch.setOnClickListener(this);

		AbsoluteLayout.LayoutParams lp1 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, widthPixels - widthPixels
						/ 6, ViewGroup.LayoutParams.WRAP_CONTENT);
		mLayout.addView(CameraSwitch, lp1);

		scanphoto = new Button(this);
		scanphoto.setText(getString(R.string.scan_photo_string));
		scanphoto.setId(scanphotoId);
		scanphoto.setOnClickListener(this);

		AbsoluteLayout.LayoutParams lp2 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, widthPixels - widthPixels
						/ 6, heightPixels / 3);
		mLayout.addView(scanphoto, lp2);

		smart_lock = new Button(this);
		smart_lock.setId(smart_lockId);
		smart_lock.setText(getString(R.string.set_smart_lock));
		smart_lock.setOnClickListener(this);

		AbsoluteLayout.LayoutParams lp3 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, widthPixels - widthPixels
						/ 6, heightPixels / 2 + 60);
		mLayout.addView(smart_lock, lp3);

		mBtnSet = new Button(this);
		mBtnSet.setText(getString(R.string.scan_setmenu_string));
		mBtnSet.setId(mBtnSetId);
		mBtnSet.setOnClickListener(this);

		AbsoluteLayout.LayoutParams lp4 = new AbsoluteLayout.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.WRAP_CONTENT, widthPixels - widthPixels
						/ 6, heightPixels - 200);
		mLayout.addView(mBtnSet, lp4);
	}

	private void setMenuView() {
		if (menuState) {
			menuState = false;
			mBtnSwitch.setVisibility(View.INVISIBLE);
			scanphoto.setVisibility(View.INVISIBLE);
			smart_lock.setVisibility(View.INVISIBLE);
			mBtnSet.setVisibility(View.INVISIBLE);
			leftMenu.setVisibility(View.INVISIBLE);
			CameraSwitch.setVisibility(View.INVISIBLE);
		} else {
			menuState = true;
			mBtnSwitch.setVisibility(View.VISIBLE);
			scanphoto.setVisibility(View.VISIBLE);
			smart_lock.setVisibility(View.VISIBLE);
			mBtnSet.setVisibility(View.VISIBLE);
			leftMenu.setVisibility(View.VISIBLE);
			CameraSwitch.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		// TODO Auto-generated method stub
		if (jpeg.gettakePhoto()) {
			jpeg.settakePhoto(false);
			Log.e("Camera", "get Camera picture ok");

			 jpeg.takephoto(data, camera);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case menuId:
			setMenuView();
			break;
		case SwitchId:

			break;
		case scanphotoId: // 查看告警信息
			startActivity(new Intent().setClass(getApplicationContext(),
					ScanMenu.class));
			break;
		case smart_lockId: // 查看门锁
			// showToast(getString(R.string.scan_photo_string));
			startActivity(new Intent().setClass(getApplicationContext(),
					Smart_lock_menu.class));
			showToast(common.getCPUSerial());
			break;
		case mBtnSetId:
			startActivity(new Intent().setClass(getApplicationContext(),
					SetMenu.class));
			break;
		case CameraSwitchId:
			showToast("智能监控");
			startActivity(new Intent().setClass(getApplicationContext(),
					SmartContorl.class));
			break;
		default:
			break;
		}
	}

	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	private void mBtnSwitchsCamera() {
		if (mBtnSwitch.getText().equals("切换前置摄像头")) {
			mBtnSwitch.setText("切换后置摄像头");
		} else {
			mBtnSwitch.setText("切换前置摄像头");
		}
		if (mCamera != null) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
		mCamera = Camera.open((cameraCurrentId + 1) % numCamera);
		cameraCurrentId = (cameraCurrentId + 1) % numCamera;
		mPreview.switchCamera(mCamera);
		mCamera.startPreview();
	}

	private void test() {
		PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		PowerManager.WakeLock wl = pm.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// 点亮屏幕
		wl.acquire();
		// 释放
		wl.release();
	}

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			showToast("返回键");
			test();
		}
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			return false;
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			SendBroadcast("" + ReceiverEvent.Key_ring, "com.kdscateye.Key_ring");
		} else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			SendBroadcast("" + ReceiverEvent.Key_pir, "com.kdscateye.Key_pir");
		}
		return super.onKeyDown(keyCode, event);
	};

	private void SendBroadcast(Object obj, String action) {
		Intent intent = new Intent();
		intent.setAction(action);
		intent.putExtra(ReceiverEvent.Message, (String) obj);
		this.sendBroadcast(intent);
	}
}
