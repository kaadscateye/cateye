package BrocastEvent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.demotools.LogUtils;
import com.example.demotools.PlayRing;
import com.example.demotools.Tools;
import com.kds.h264.Jpeg;


public class ReceiverEvent extends BroadcastReceiver implements DoMsg{

	private static Boolean EventPthread = false;
	public static final String Message = "message";

	public static final int Start_Brocast = 0x1;	//启动广播接收器
	public static final int Key_menu_up = 255;		//向上按键
	public static final int Key_menu_down = 256;	//向下按键
	public static final int Key_menu_ok = 257;		//确定按键
	public static final int Key_menu_exit = 258;	//退出按键
	public static final int Key_menu_picture = 259;	//抓拍摄像头按键
	public static final int Key_ring = 260;			//门铃事件
	public static final int Key_pir = 261;			//pir 事件

	
	public static final int talk_pir = 262;			//pir 事件
	public static final int StartRecoder = 263;		//pir 事件
	public static final int StopRecoder = 264;		//pir 事件
	public static final int applinphone = 265;		//pir 事件
	public static final int ConnectAppOk=266;
	
	class EventMsg{
		int eventKeycode;
		long eventNums;
		String mstr;
	}
	
	
	@Override
	public void onReceive(Context arg0, Intent arg1) {		
		// TODO Auto-generated method stub
		String Str = arg1.getExtras().getString(ReceiverEvent.Message);
		String action = arg1.getAction();
		LogUtils.e("message :" + Str + "action " + action);
		
		if (!Tools.isNumeric(Str)) {
			LogUtils.e("message error ...");
			return;
		}
		int keyCode = Integer.parseInt(Str);
		switch (keyCode) {			//处理底层发送上来的广播按键事件
			case Start_Brocast:		//启动后台接收队列
				if (EventPthread) {
					return;
				}
				LogUtils.e("create pthread run ");
				EventPthread = true;
				PushBlockQueue.getInstance().start();
				PushBlockQueue.getInstance().callBack(this);
				return;
			case Key_menu_up:		//向上按键
				break;
			case Key_menu_down:		//向下按键
				break;
			case Key_menu_ok:		//确定按键
				break;
			case Key_menu_exit:		//退出按键
				break;
			case Key_menu_picture:	//抓拍摄像头按键
				break;
			case Key_ring:			//门铃事件
				break;
			case Key_pir:			//抓拍 事件
				break;
			}
		long time = System.currentTimeMillis();
		EventMsg msg = new EventMsg();
		msg.eventNums = time;
		msg.eventKeycode=keyCode;
		msg.mstr = Str;
		try {
			PushBlockQueue.getInstance().put(msg);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void Pthread_handlertaskMsg(Object obj) {
		// TODO Auto-generated method stub
		PlayRing player = new PlayRing();
		EventMsg msg = (EventMsg)obj;
		LogUtils.e("msg.what "+msg.mstr);
		LogUtils.e("msg.eventNums"+msg.eventNums);
		switch (msg.eventKeycode) {
			case Key_menu_picture:	//抓拍摄像头按键
				break;
			case talk_pir:
				break;
			case applinphone:
				player.stop();
				break;
			case Key_ring:		//门铃事件
				RingEvent();	
				break;
			case Key_pir:		//抓拍 事件	
				PirEvent();
				break;
			case ConnectAppOk:
				break;
		}
	}
	
	// 事件编号  时间  图片路径  事件类型  阅读状态
	private void RingEvent(){		//门铃事件
		PlayRing player = new PlayRing();
		String music="";
		LogUtils.e(" RingEvent ");
		Jpeg obj = new Jpeg();
		obj.settakePhoto(true);
		player.playMusicFile(PlayRing.strMusic[0], 20);
//		player.playMusicFile(music, 20);//播放设置的门铃
		//呼叫app
	}
	private void PirEvent(){		//pir事件
		PlayRing player = new PlayRing();
		String music="";
		LogUtils.e(" PirEvent ");
		Jpeg obj = new Jpeg();
		obj.settakePhoto(true);
		player.playMusicFile(PlayRing.strMusic[0], 20);
//		player.playMusicFile(music, 20);

		//启动抓拍
	}
}
