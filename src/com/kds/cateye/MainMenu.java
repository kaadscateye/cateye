package com.kds.cateye;

import BrocastEvent.ReceiverEvent;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.kds.h264.Jpeg;

@SuppressLint({ "NewApi", "NewApi", "NewApi", "NewApi", "NewApi" })
public class MainMenu extends Activity implements OnClickListener,
		PreviewCallback {
	private com.kds.h264.Preview mPreview;
	private Camera mCamera;
	private int numCamera;
	private int cameraId;
	private int cameraCurrentId = 0;
	LinearLayout lineright;
	Jpeg jpeg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		InitView();
		initBrocastReceiver();
	}
	private void InitView() {
		jpeg = new Jpeg();
		mPreview = (com.kds.h264.Preview) findViewById(R.id.pv_video);
		mPreview.setOnClickListener(this);

		lineright = (LinearLayout) findViewById(R.id.ll_contian_btn);
		Button smart_control = (Button) findViewById(R.id.btn_smart_control);
		Button photo = (Button) findViewById(R.id.btn_photo);
		Button smart_lock = (Button) findViewById(R.id.btn_smart_lock);
		Button set = (Button) findViewById(R.id.btn_set);

		smart_control.setOnClickListener(this);
		photo.setOnClickListener(this);
		smart_lock.setOnClickListener(this);
		set.setOnClickListener(this);

		numCamera = Camera.getNumberOfCameras();
		CameraInfo info = new CameraInfo();
		for (int i = 0; i < numCamera; i++) {
			Camera.getCameraInfo(i, info);
			if (info.facing == CameraInfo.CAMERA_FACING_BACK) {
				cameraId = i;
			}
		}
	}
	// 注册广播接收器，用于接收底层按键发送上来的广播
	private void initBrocastReceiver() {
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
		case R.id.pv_video:
			if (lineright.getVisibility() == View.VISIBLE) {
				lineright.setVisibility(View.GONE);
			} else {
				lineright.setVisibility(View.VISIBLE);
			}
			break;
		case R.id.btn_smart_control:
			startActivity(new Intent().setClass(getApplicationContext(),
					SmartContorl.class));
			break;
		case R.id.btn_photo: // 查看告警信息
			startActivity(new Intent().setClass(getApplicationContext(),
					ScanMenu.class));
			break;
		case R.id.btn_smart_lock: // 查看门锁
			startActivity(new Intent().setClass(getApplicationContext(),
					Smart_lock_menu.class));
			// showToast(common.getCPUSerial());
			break;
		case R.id.btn_set:
			startActivity(new Intent().setClass(getApplicationContext(),
					SetMenu.class));
			break;

		default:
			break;
		}
	}

	private void showToast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	private void mBtnSwitchsCamera() {
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

	public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			showToast("返回键");
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
