package com.kds.cateye;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fragment.FragmentAdapter;
import fragment.PictureMenuFrament;
import fragment.VideoMenuFrament;
import fragment.WarnMenuFragment;

public class ScanMenu extends FragmentActivity implements View.OnClickListener{
	private ViewPager mPageVp;
	private List<Fragment> mFragmentList = new ArrayList<Fragment>();
	private FragmentAdapter mFragmentAdapter;
	private TextView mTabChatTv, mTabContactsTv, mTabFriendTv;
	private ImageView mTabLineIv;
	private PictureMenuFrament PictureMenufg;
	private VideoMenuFrament VideoMenufg;
	private WarnMenuFragment WarnMenufg;
	private int currentIndex;
	private int screenWidth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_menu);
      
		findById();
		init();
		initTabLineWidth();
    
    }
    private void findById() {
		mTabContactsTv = (TextView) this.findViewById(R.id.id_contacts_tv);
		mTabChatTv = (TextView) this.findViewById(R.id.id_chat_tv);
		mTabFriendTv = (TextView) this.findViewById(R.id.id_friend_tv);
		mTabContactsTv.setOnClickListener(this);
		mTabChatTv.setOnClickListener(this);
		mTabFriendTv.setOnClickListener(this);
		
		mTabLineIv = (ImageView) this.findViewById(R.id.id_tab_line_iv);

		mPageVp = (ViewPager) this.findViewById(R.id.id_page_vp);
	}

	private void init() {
		PictureMenufg = new PictureMenuFrament();
		VideoMenufg = new VideoMenuFrament();
		WarnMenufg = new WarnMenuFragment();
		mFragmentList.add(PictureMenufg);
		mFragmentList.add(VideoMenufg);
		mFragmentList.add(WarnMenufg);

		mFragmentAdapter = new FragmentAdapter(
				this.getSupportFragmentManager(), mFragmentList);
		mPageVp.setAdapter(mFragmentAdapter);
		mPageVp.setCurrentItem(0);

		mPageVp.setOnPageChangeListener(new OnPageChangeListener() {

			/**
			 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
			 */
			@Override
			public void onPageScrollStateChanged(int state) {

			}

			/**
			 * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
			 * offsetPixels:当前页面偏移的像素位置
			 */
			@Override
			public void onPageScrolled(int position, float offset,
					int offsetPixels) {
				LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
						.getLayoutParams();

				Log.e("offset:", offset + "");
				/**
				 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
				 * 设置mTabLineIv的左边距 滑动场景：
				 * 记3个页面,
				 * 从左到右分别为0,1,2 
				 * 0->1; 1->2; 2->1; 1->0
				 */

				if (currentIndex == 0 && position == 0)// 0->1
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));

				} else if (currentIndex == 1 && position == 0) // 1->0
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));

				} else if (currentIndex == 1 && position == 1) // 1->2
				{
					lp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));
				} else if (currentIndex == 2 && position == 1) // 2->1
				{
					lp.leftMargin = (int) (-(1 - offset)
							* (screenWidth * 1.0 / 3) + currentIndex
							* (screenWidth / 3));
				}
				mTabLineIv.setLayoutParams(lp);
			}

			@Override
			public void onPageSelected(int position) {
				resetTextView();
				switch (position) {
				case 0:
					mTabChatTv.setTextColor(Color.BLUE);
					break;
				case 1:
					mTabFriendTv.setTextColor(Color.BLUE);
					break;
				case 2:
					mTabContactsTv.setTextColor(Color.BLUE);
					break;
				}
				currentIndex = position;
			}
		});

	}

	/**
	 * 设置滑动条的宽度为屏幕的1/3(根据Tab的个数而定)
	 */
	private void initTabLineWidth() {
		DisplayMetrics dpMetrics = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay()
				.getMetrics(dpMetrics);
		screenWidth = dpMetrics.widthPixels;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
				.getLayoutParams();
		lp.width = screenWidth / 3;
		mTabLineIv.setLayoutParams(lp);
	}

	/**
	 * 重置颜色
	 */
	private void resetTextView() {
		mTabChatTv.setTextColor(Color.BLACK);
		mTabFriendTv.setTextColor(Color.BLACK);
		mTabContactsTv.setTextColor(Color.BLACK);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.id_chat_tv:
			mPageVp.setCurrentItem(0);
			break;
		case R.id.id_friend_tv:
			mPageVp.setCurrentItem(1);
			break;
		case R.id.id_contacts_tv:
			mPageVp.setCurrentItem(2);
			break;	
		default:
			break;
		}
	}
}
