package com.kds.SystemTools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetManager;

public class AssetsFile {
	public static String LoadAssetsFile(Context context, String filenmae) {
		String str = "";
		try {
            InputStream is = context.getAssets().open(filenmae);  
            int size = is.available();  
  
            // Read the entire asset into a local byte buffer.  
            byte[] buffer = new byte[size];  
            is.read(buffer);  
            is.close();  
            str= new String(buffer,"GB2312");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
