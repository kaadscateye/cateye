package fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.kds.cateye.R;

public class WarnMenuFragment extends Fragment{
	private ArrayList<HashMap<String, String>> WarnArr;
	private listAdapter WarnAdapter;
	  @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View viewTab_1 = inflater.inflate(R.layout.fragment_scan_warning, container, false);
	        onloadView(viewTab_1);
	        return viewTab_1;
	    }

	    private void onloadView(View view) {
			ListView listView1 = (ListView) view
					.findViewById(R.id.ScanWarnMenuListId);
			WarnArr = new ArrayList<HashMap<String, String>>();
			WarnAdapter = new listAdapter(getActivity(),
					WarnArr);
			listView1.setAdapter(WarnAdapter);
			listView1.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					// TODO Auto-generated method stub
					
				}
			});
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String date = sDateFormat.format(new java.util.Date());
			String msg = date+"…Ë±∏±ªµ¡";
			WarnAdapter.add_item(msg, date);
	    }
		private void showToast(String msg) {
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
		}
}
