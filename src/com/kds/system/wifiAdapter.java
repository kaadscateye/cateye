package com.kds.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kds.cateye.R;

public class wifiAdapter extends BaseAdapter {

	private final String TAG = wifiAdapter.class.getName();
	private Context context;
	private LayoutInflater inflater;
	private ArrayList<HashMap<String, String>> arr;
	public static Boolean moveState = false;

	public wifiAdapter(Context context, ArrayList<HashMap<String, String>> arr) {
		super();
		this.context = context;
		inflater = LayoutInflater.from(context);
		this.arr = arr;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View view, ViewGroup arg2) {
		// TODO Auto-generated method stub
		if (view == null) {
			view = inflater.inflate(R.layout.wifilist, null);
		}
		Log.i(TAG, "Position:" + position);
		ImageView image = (ImageView) view.findViewById(R.id.imageWifiStateId);
		TextView txt = (TextView) view.findViewById(R.id.textWifiId);
		
		HashMap<String, String> hs = arr.get(position);
		Iterator<String> it = hs.keySet().iterator();
		String str1 = (String) it.next();
		String str2 = hs.get(str1);
		
		int id =Integer.parseInt(str2);
		image.setImageResource(id);
		txt.setText(str1);
		return view;
	}

	/**
	 * @param memory
	 *            添加item
	 */
	public void addMemory(HashMap<String, String> memory) {
		arr.add(memory);
		notifyDataSetChanged();
	}

	/**
	 * @param position
	 *            位置 删除选中item
	 */
	public void removeItem(int position) {
		arr.remove(position);
		notifyDataSetChanged();
	}

	/**
	 * @param position
	 *            位置
	 * @param text
	 *            修改内容
	 * 
	 */
	public void modifyItem(int position, String text) {
		HashMap<String, String> hs = arr.get(position);
		Iterator<String> it = hs.keySet().iterator();
		String str = (String) it.next();
		hs.put(str, text);
		arr.set(position, hs);
		notifyDataSetChanged();
	}
    public void remove_list()
    {
    	int i,max =arr.size();
    	for(i=max-1; i>=0; i--)
    	{
    		arr.remove(i);
    		notifyDataSetChanged();
    	}	
    }
    public void add_item(String str1,String str2){
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put(str1,str2);
		this.addMemory(hashMap); 
    }
    public boolean list_modifyItem(String str1,String newStr)
    {
    	int i;
    	for(i=0; i<arr.size(); i++)
    	{
    		HashMap<String, String> hs = arr.get(i);
    		Iterator<String> it = hs.keySet().iterator();
    		String str = (String) it.next();
    		if(str.equals(str1))
    		{
    			hs.put(str, newStr);
    			arr.set(i, hs);
    			notifyDataSetChanged();
    			return true;
    		}
    	}	
    	return false;
    }
    public boolean list_modifyItem_name(String oldStr,String newStr)
    {
    	int i;
    	for(i=0; i<arr.size()-1; i++)
    	{
    		HashMap<String, String> hs = arr.get(i);
    		Iterator<String> it = hs.keySet().iterator();
    		String str1 = (String) it.next();
    		String str2 = hs.get(str1);
    		if(str2.equals(oldStr))
    		{
    			hs.put(str1, newStr);
    			arr.set(i, hs);
    			notifyDataSetChanged();
    			return true;
    		}
//    		Log.e("list_modifyItem_project =",str2);
    	}	
    	return false;
    }
    public boolean delete_item(String name)
    {
    	for(int i=0; i<arr.size(); i++)
    	{
    		HashMap<String, String> hs = arr.get(i);
    		Iterator<String> it = hs.keySet().iterator();
    		String str1 = (String) it.next();
    		if(str1.equals(name))
    		{
    			arr.remove(i);
    			notifyDataSetChanged();
    			return true;
    		}
    	}
    	return false;
    }
    public boolean del_item_secondName(String name)
    {
    	for(int i=0; i<arr.size(); i++)
    	{
    		HashMap<String, String> hs = arr.get(i);
    		Iterator<String> it = hs.keySet().iterator();
    		String str1 = (String) it.next();
    		String str2 = hs.get(str1);
    		if(str2.equals(name))
    		{
    			arr.remove(i);
    			notifyDataSetChanged();
    			return true;
    		}
    	}
    	return false;
    }
    public boolean check_usrname(String name)
    {
    	for(int i=0; i<arr.size(); i++)
    	{
    		HashMap<String, String> hs = arr.get(i);
    		Iterator<String> it = hs.keySet().iterator();
    		String str1 = (String) it.next();
//    		String str2 = hs.get(str1);
    		if(str1.equals(name))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    public boolean check_nextname(String name)
    {
    	for(int i=0; i<arr.size(); i++)
    	{
    		HashMap<String, String> hs = arr.get(i);
    		Iterator<String> it = hs.keySet().iterator();
    		String str1 = (String) it.next();
    		String str2 = hs.get(str1);
    		if(str2.equals(name))
    		{
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean check_dev(String devname,String id)
    {
    	for(int i=0; i<arr.size(); i++)
    	{
    		HashMap<String, String> hs = arr.get(i);
    		Iterator<String> it = hs.keySet().iterator();
    		String str1 = (String) it.next();
    		String str2 = hs.get(str1);
    		if(str1.equals(devname)||str2.equals(id))
    		{
    			return true;
    		}
    	}
    	return false;
    }
}
