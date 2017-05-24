package com.kds.system;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kds.cateye.R;

public class qr_codescan extends Activity implements OnClickListener{
	private final static int SCANNIN_GREQUEST_CODE = 1;
	/**
	 * 显示扫描结果
	 */
	private TextView mTextView ;
	/**
	 * 显示扫描拍的图片
	 */
	private ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr_codescan);
		
		mTextView = (TextView) findViewById(R.id.result); 
		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
		
		//点击按钮跳转到二维码扫描界面，这里用的是startActivityForResult跳转
		//扫描完了之后调到该界面
		Button mButton = (Button) findViewById(R.id.butScanCodeId);
		Button back = (Button) findViewById(R.id.qr_codescan_back);
		mButton.setOnClickListener(this);
		back.setOnClickListener(this);
	}
	
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
		case SCANNIN_GREQUEST_CODE:
			if(resultCode == RESULT_OK){
				Bundle bundle = data.getExtras();
				//显示扫描到的内容
				mTextView.setText(bundle.getString("result"));
				//显示
				mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
				
			}
			break;
		}
    }


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.butScanCodeId:
			Intent intent = new Intent();
			intent.setClass(qr_codescan.this, MipcaActivityCapture.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
			break;
		case R.id.qr_codescan_back:
			finish();
			break;
		default:
			break;
		}
	}	

}
