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

	public static final int Start_Brocast = 0x1;	//�����㲥������
	public static final int Key_menu_up = 255;		//���ϰ���
	public static final int Key_menu_down = 256;	//���°���
	public static final int Key_menu_ok = 257;		//ȷ������
	public static final int Key_menu_exit = 258;	//�˳�����
	public static final int Key_menu_picture = 259;	//ץ������ͷ����
	public static final int Key_ring = 260;			//�����¼�
	public static final int Key_pir = 261;			//pir �¼�

	
	public static final int talk_pir = 262;			//pir �¼�
	public static final int StartRecoder = 263;		//pir �¼�
	public static final int StopRecoder = 264;		//pir �¼�
	public static final int applinphone = 265;		//pir �¼�
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
		switch (keyCode) {			//����ײ㷢�������Ĺ㲥�����¼�
			case Start_Brocast:		//������̨���ն���
				if (EventPthread) {
					return;
				}
				LogUtils.e("create pthread run ");
				EventPthread = true;
				PushBlockQueue.getInstance().start();
				PushBlockQueue.getInstance().callBack(this);
				return;
			case Key_menu_up:		//���ϰ���
				break;
			case Key_menu_down:		//���°���
				break;
			case Key_menu_ok:		//ȷ������
				break;
			case Key_menu_exit:		//�˳�����
				break;
			case Key_menu_picture:	//ץ������ͷ����
				break;
			case Key_ring:			//�����¼�
				break;
			case Key_pir:			//ץ�� �¼�
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
			case Key_menu_picture:	//ץ������ͷ����
				break;
			case talk_pir:
				break;
			case applinphone:
				player.stop();
				break;
			case Key_ring:		//�����¼�
				RingEvent();	
				break;
			case Key_pir:		//ץ�� �¼�	
				PirEvent();
				break;
			case ConnectAppOk:
				break;
		}
	}
	
	// �¼����  ʱ��  ͼƬ·��  �¼�����  �Ķ�״̬
	private void RingEvent(){		//�����¼�
		PlayRing player = new PlayRing();
		String music="";
		LogUtils.e(" RingEvent ");
		Jpeg obj = new Jpeg();
		obj.settakePhoto(true);
		player.playMusicFile(PlayRing.strMusic[0], 20);
//		player.playMusicFile(music, 20);//�������õ�����
		//����app
	}
	private void PirEvent(){		//pir�¼�
		PlayRing player = new PlayRing();
		String music="";
		LogUtils.e(" PirEvent ");
		Jpeg obj = new Jpeg();
		obj.settakePhoto(true);
		player.playMusicFile(PlayRing.strMusic[0], 20);
//		player.playMusicFile(music, 20);

		//����ץ��
	}
}
