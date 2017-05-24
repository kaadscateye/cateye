package com.kds.cateye;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kds.h264.Jpeg;
import com.kds.list.listAdapter;

public class ScanMenu extends Activity implements View.OnClickListener{
	private ViewPager viewPager;
	private ArrayList<View> pageview;
	private TextView videoLayout, picLayout, PIRLayout;
	// ������ͼƬ
	private ImageView scrollbar;
	// ��������ʼƫ����
	private int offset = 0;
	// ��ǰҳ���
	private int currIndex = 0;
	// ���������
	private int bmpW;
	// һ��������
	private int one;
	View view2;
	private ArrayList<HashMap<String, String>> PictureArr;
	private listAdapter PictureAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_menu);
        
    	viewPager = (ViewPager) findViewById(R.id.viewPager);
		// ���Ҳ����ļ���LayoutInflater.inflate
		LayoutInflater inflater = getLayoutInflater();
		View view1 = inflater.inflate(R.layout.scan_video, null);
		view2 = inflater.inflate(R.layout.scan_pic, null);
		View view3 = inflater.inflate(R.layout.scan_warn, null);
		videoLayout = (TextView) findViewById(R.id.videoLayout);
		picLayout = (TextView) findViewById(R.id.picLayout);
		PIRLayout = (TextView) findViewById(R.id.PIRLayout);

		scrollbar = (ImageView) findViewById(R.id.scrollbar);
		videoLayout.setOnClickListener(this);
		picLayout.setOnClickListener(this);
		PIRLayout.setOnClickListener(this);
		pageview = new ArrayList<View>();
		// �����Ҫ�л��Ľ���
		pageview.add(view1);
		pageview.add(view2);
		pageview.add(view3);

		// ����������
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			// ��ȡ��ǰ���������
			public int getCount() {
				// TODO Auto-generated method stub
				return pageview.size();
			}

			@Override
			// �ж��Ƿ��ɶ������ɽ���
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			// ʹ��ViewGroup���Ƴ���ǰView
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView(pageview.get(arg1));
			}

			// ����һ������������������PagerAdapter������ѡ���ĸ�������ڵ�ǰ��ViewPager��
			public Object instantiateItem(View arg0, int arg1) {
				((ViewPager) arg0).addView(pageview.get(arg1));
				return pageview.get(arg1);
			}
		};
		// ��������
		viewPager.setAdapter(mPagerAdapter);
		// ����viewPager�ĳ�ʼ����Ϊ��һ������
		viewPager.setCurrentItem(0);
		// ����л�����ļ�����
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		// ��ȡ�������Ŀ��
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher).getWidth();
		// Ϊ�˻�ȡ��Ļ��ȣ��½�һ��DisplayMetrics����
		DisplayMetrics displayMetrics = new DisplayMetrics();
		// ����ǰ���ڵ�һЩ��Ϣ����DisplayMetrics����
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		// �õ���Ļ�Ŀ��
		int screenW = displayMetrics.widthPixels;
		// �������������ʼ��ƫ����
		offset = (screenW / 2 - bmpW) / 2;
		// ������л�һ������ʱ����������λ����
		one = offset * 2 + bmpW;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		// ���������ĳ�ʼλ�����ó�����߽���һ��offset
		scrollbar.setImageMatrix(matrix);
        
    }
	public class MyOnPageChangeListener implements
	ViewPager.OnPageChangeListener {
public void onPageSelected(int arg0) {
	Animation animation = null;
	switch (arg0) {
	case 0:
		/**
		 * TranslateAnimation���ĸ����Էֱ�Ϊ float fromXDelta ������ʼ�ĵ��뵱ǰView
		 * X�����ϵĲ�ֵ float toXDelta ���������ĵ��뵱ǰView X�����ϵĲ�ֵ float fromYDelta
		 * ������ʼ�ĵ��뵱ǰView Y�����ϵĲ�ֵ float toYDelta ������ʼ�ĵ��뵱ǰView Y�����ϵĲ�ֵ
		 **/
		animation = new TranslateAnimation(one, 0, 0, 0);
		break;
	case 1:
		animation = new TranslateAnimation(offset, one, 0, 0);
		InitPictureUiMenu();
		break;
	case 2:
		animation = new TranslateAnimation(offset + offset, one, 0, 0);
		break;
	}
	// arg0Ϊ�л�����ҳ�ı���
	currIndex = arg0;
	// ������������Ϊtrue����ʹ��ͼƬͣ�ڶ�������ʱ��λ��
	animation.setFillAfter(true);
	// ��������ʱ�䣬��λΪ����
	animation.setDuration(200);
	// ��������ʼ����
	scrollbar.startAnimation(animation);
}

public void onPageScrolled(int arg0, float arg1, int arg2) {
}

public void onPageScrollStateChanged(int arg0) {
}
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
	};

	private void InitPictureUiMenu() {
		ListView listPic = (ListView) view2.findViewById(R.id.listpictureId);
		PictureArr = new ArrayList<HashMap<String, String>>();
		PictureAdapter = new listAdapter(this, PictureArr);
		listPic.setAdapter(PictureAdapter);
		listPic.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
	     		TextView mText = (TextView) arg1.findViewById(R.id.textView2);
	     		String filename = mText.getText().toString().trim();	
				Intent obj = new Intent();
				obj.putExtra("menustring", new Jpeg().GetPicturePath()+filename);
				obj.setClass(getApplicationContext(), LookPhotos.class);
				startActivity(obj);
				onPause();
			}
		});
		ScanScard_picture();
	}

	private void ScanScard_picture() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				File file = new File(new Jpeg().GetPicturePath());
				getVideoFile(file);
				Message obj = new Message();
				obj = hand.obtainMessage(0x02, (Object) "finnish");
				obj.sendToTarget();
			}
		}).start();
	}

	private void getVideoFile(File file) {// �����Ƶ�ļ�

		file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				// sdCard�ҵ���Ƶ����
				String name = file.getName();
				int i = name.indexOf('.');
				if (i != -1) {
					name = name.substring(i);
					if (name.equalsIgnoreCase(".jpg")
							|| name.equalsIgnoreCase(".bmp")) {
						Message obj = new Message();
						obj = hand.obtainMessage(0x01, (Object) file.getName()
								+ ",1," + file.getAbsolutePath());
						obj.sendToTarget();
						return true;
					}
				} else if (file.isDirectory()) {
					getVideoFile(file);
				}

				return false;
			}
		});
	}

	Handler hand = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 0x01) {
				String msgStr[] = msg.obj.toString().split(",1,");
				if(!PictureAdapter.check_nextname(msgStr[0])){
					PictureAdapter.add_item(msgStr[1], msgStr[0]);
				}
				// Log.e("msgStr[1] = ",msgStr[1]);
			} else if (msg.what == 0x02) {
				// showToast("���ڼ���,���Ժ�...");
				if (PictureArr.size() == 0) {
					PictureAdapter.add_item("", "��ǰû��ͼƬ");
				}
			}
		}
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.videoLayout:
			// ���"��Ƶ��ʱ�л�����һҳ
			viewPager.setCurrentItem(0);
			break;
		case R.id.picLayout:
			// �����ͼƬ��ʱ�л��ĵڶ�ҳ
			viewPager.setCurrentItem(1);
			InitPictureUiMenu();
			break;
		case R.id.PIRLayout:
			viewPager.setCurrentItem(2);
		default:
			break;
		}
	}
}
