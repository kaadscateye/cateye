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
	// 滚动条图片
	private ImageView scrollbar;
	// 滚动条初始偏移量
	private int offset = 0;
	// 当前页编号
	private int currIndex = 0;
	// 滚动条宽度
	private int bmpW;
	// 一倍滚动量
	private int one;
	View view2;
	private ArrayList<HashMap<String, String>> PictureArr;
	private listAdapter PictureAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_menu);
        
    	viewPager = (ViewPager) findViewById(R.id.viewPager);
		// 查找布局文件用LayoutInflater.inflate
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
		// 添加想要切换的界面
		pageview.add(view1);
		pageview.add(view2);
		pageview.add(view3);

		// 数据适配器
		PagerAdapter mPagerAdapter = new PagerAdapter() {

			@Override
			// 获取当前窗体界面数
			public int getCount() {
				// TODO Auto-generated method stub
				return pageview.size();
			}

			@Override
			// 判断是否由对象生成界面
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}

			// 使从ViewGroup中移出当前View
			public void destroyItem(View arg0, int arg1, Object arg2) {
				((ViewPager) arg0).removeView(pageview.get(arg1));
			}

			// 返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
			public Object instantiateItem(View arg0, int arg1) {
				((ViewPager) arg0).addView(pageview.get(arg1));
				return pageview.get(arg1);
			}
		};
		// 绑定适配器
		viewPager.setAdapter(mPagerAdapter);
		// 设置viewPager的初始界面为第一个界面
		viewPager.setCurrentItem(0);
		// 添加切换界面的监听器
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
		// 获取滚动条的宽度
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher).getWidth();
		// 为了获取屏幕宽度，新建一个DisplayMetrics对象
		DisplayMetrics displayMetrics = new DisplayMetrics();
		// 将当前窗口的一些信息放在DisplayMetrics类中
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		// 得到屏幕的宽度
		int screenW = displayMetrics.widthPixels;
		// 计算出滚动条初始的偏移量
		offset = (screenW / 2 - bmpW) / 2;
		// 计算出切换一个界面时，滚动条的位移量
		one = offset * 2 + bmpW;
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		// 将滚动条的初始位置设置成与左边界间隔一个offset
		scrollbar.setImageMatrix(matrix);
        
    }
	public class MyOnPageChangeListener implements
	ViewPager.OnPageChangeListener {
public void onPageSelected(int arg0) {
	Animation animation = null;
	switch (arg0) {
	case 0:
		/**
		 * TranslateAnimation的四个属性分别为 float fromXDelta 动画开始的点离当前View
		 * X坐标上的差值 float toXDelta 动画结束的点离当前View X坐标上的差值 float fromYDelta
		 * 动画开始的点离当前View Y坐标上的差值 float toYDelta 动画开始的点离当前View Y坐标上的差值
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
	// arg0为切换到的页的编码
	currIndex = arg0;
	// 将此属性设置为true可以使得图片停在动画结束时的位置
	animation.setFillAfter(true);
	// 动画持续时间，单位为毫秒
	animation.setDuration(200);
	// 滚动条开始动画
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

	private void getVideoFile(File file) {// 获得视频文件

		file.listFiles(new FileFilter() {

			@Override
			public boolean accept(File file) {
				// sdCard找到视频名称
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
				// showToast("正在加载,请稍后...");
				if (PictureArr.size() == 0) {
					PictureAdapter.add_item("", "当前没有图片");
				}
			}
		}
	};
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.videoLayout:
			// 点击"视频“时切换到第一页
			viewPager.setCurrentItem(0);
			break;
		case R.id.picLayout:
			// 点击“图片”时切换的第二页
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
