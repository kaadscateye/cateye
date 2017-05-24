package com.kds.system;

import java.io.IOException;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RecoverySystem;
import android.util.Log;

public class MasterClearReceiver extends BroadcastReceiver{

	public void onReceive(final Context context, final Intent intent) {  
//	    if (intent.getAction().equals(Intent.ACTION_REMOTE_INTENT)) {  
//	        if (!"google.com".equals(intent.getStringExtra("from"))) {  
//	            return;  
//	        }  
//	    }  
	    // The reboot call is blocking, so we need to do it on another thread.  
	    Thread thr = new Thread("Reboot") {  
	        @Override  
	        public void run() {  
	            try {  
	                RecoverySystem.rebootWipeUserData(context);  
	            } catch (IOException e) {  
	                Log.e("", "Can't perform master clear/factory reset", e);  
	            }  
	        }  
	    };  
	    thr.start();  
	} 
	

}

 