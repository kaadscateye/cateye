package fragment;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.kds.cateye.LookPhotos;
import com.kds.cateye.R;
import com.kds.h264.Jpeg;

public class PictureMenuFrament extends Fragment {
	private ArrayList<HashMap<String, String>> PictureArr;
	private listAdapter PictureAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View viewTab_1 = inflater.inflate(R.layout.fragment_scan_picture, container, false);
		onloadView(viewTab_1);
		return viewTab_1;
	}

	private void onloadView(View view) {
		ListView listView1 = (ListView) view
				.findViewById(R.id.ScanPictureMenuListId);
		PictureArr = new ArrayList<HashMap<String, String>>();
		PictureAdapter = new listAdapter(PictureMenuFrament.this.getActivity(),
				PictureArr);
		listView1.setAdapter(PictureAdapter);
		listView1.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				TextView mText = (TextView) arg1.findViewById(R.id.textView1);
				String filename = mText.getText().toString().trim();
				Intent obj = new Intent();
				obj.putExtra("menustring", new Jpeg().GetPicturePath()
						+ filename);
				obj.setClass(PictureMenuFrament.this.getActivity(),
						LookPhotos.class);
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
				if (!PictureAdapter.check_nextname(msgStr[0])) {
					PictureAdapter.add_item(msgStr[0],msgStr[1]);
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
}
