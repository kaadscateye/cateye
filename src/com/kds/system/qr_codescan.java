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
	 * ��ʾɨ����
	 */
	private TextView mTextView ;
	/**
	 * ��ʾɨ���ĵ�ͼƬ
	 */
	private ImageView mImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.qr_codescan);
		
		mTextView = (TextView) findViewById(R.id.result); 
		mImageView = (ImageView) findViewById(R.id.qrcode_bitmap);
		
		//�����ť��ת����ά��ɨ����棬�����õ���startActivityForResult��ת
		//ɨ������֮������ý���
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
				//��ʾɨ�赽������
				mTextView.setText(bundle.getString("result"));
				//��ʾ
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
