package com.kds.server;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class ServiceEvent {
	private MediaPlayer mp;
	 public void onCreate() {  
	        // TODO Auto-generated method stub  
	        // ��ʼ��������Դ  
	        try {  
	            System.out.println("create player");  
	            // ����MediaPlayer����  
	            mp = new MediaPlayer();  
//	            mp = MediaPlayer.create(ServiceEvent.this, R.raw.alarm); 
	            // mp.prepare();  
	        } catch (IllegalStateException e) {  
	            // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
//	        super.onCreate();  
	    }  
	  
	    public void onStart(Intent intent, int startId) {  
	        // TODO Auto-generated method stub  
	        // ��ʼ��������  
	        mp.start();  
	        // ���ֲ�����ϵ��¼�����  
	        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {  
	  
	            public void onCompletion(MediaPlayer mp) {  
	                // TODO Auto-generated method stub  
	                // ѭ������  
	                try {  
	                    mp.start();  
	                } catch (IllegalStateException e) {  
	                    // TODO Auto-generated catch block  
	                    e.printStackTrace();  
	                }  
	            }  
	        });  
	        // ��������ʱ����������¼�����  
	        mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {  
	  
	            public boolean onError(MediaPlayer mp, int what, int extra) {  
	                // TODO Auto-generated method stub  
	                // �ͷ���Դ  
	                try {  
	                    mp.release();  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	  
	                return false;  
	            }  
	        });  
	  
//	        super.onStart(intent, startId);  
	    }  
	  
	    public void onDestroy() {  
	        // TODO Auto-generated method stub  
	        // ����ֹͣʱֹͣ�������ֲ��ͷ���Դ  
	        mp.stop();  
	        mp.release();  
//	        super.onDestroy();  
	    }  
	  
	    public IBinder onBind(Intent intent) {  
	        // TODO Auto-generated method stub  
	        return null;  
	    }  
	  
}
