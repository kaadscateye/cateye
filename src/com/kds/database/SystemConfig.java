package com.kds.database;


import android.content.Context;

public class SystemConfig {
	private static SystemConfig  systemConfig=null;

	private int AlramTime;				//����ʱ��
    private int Monitoring_sensitivity;	//���������
    private String alarm_bell;			//�Զ���������
    private int vol;					//ϵͳ������С
    private int talk_photoMode;			//����ģʽ
    private int leave_recoder_mode;		//����ģʽ
    
    public static final int sensitivity_low=0;
    public static final int sensitivity_high=1;
    
    private  String Smart_pirState="Smart_pirState";		//��ȡpir�������״̬ SharedPreferences �ֶ���
    private  String AlarmTime="AlarmTime";		//��ȡpir�������״̬ SharedPreferences �ֶ���
    private  String Monitor_sensitivity="Monitor_sensitivity";		//��ȡpir�������״̬ SharedPreferences �ֶ���
    private  String RingBell="RingBell";		//��ȡ���� SharedPreferences �ֶ���
    private String RingAlarm="RingAlarm";	//��ȡ�澯 SharedPreferences �ֶ���
    private String Light_sensitivity="LightSensitivity";//��ȡ SharedPreferences �ֶ���
    private String light_hz="light_hz";		//��ȡ��ԴƵ�� SharedPreferences �ֶ���
    private String talk_photo_mode="talkphoto_mode";//��ȡ���� SharedPreferences �ֶ���
    private String talk_photo_data="talkphoto_data";//��ȡ���� SharedPreferences �ֶ���,��ֵ
    private String talk_video_data="talkvideo_data";//��ȡ¼����Ƶ SharedPreferences �ֶ���,��ֵ
    public static final int istalk_photo_mode=0;	//����ģʽ
    public static final int istalk_video_mode=1;	//¼��ģʽ
    private String serverIpaddress="serverIpaddress";//��ȡ���ŷ����� SharedPreferences �ֶ���
	private  SystemConfig() {
		
	}
	 public static SystemConfig getInstance(){
		 if (systemConfig==null) {
				systemConfig =new SystemConfig();
			}
		return systemConfig;
	 }
	//����pir ����״̬
	public Object getSmart_pirState(Context context,Object defaultObject){
		return SPUtils.getValue(context, Smart_pirState, defaultObject);
	}
	public void setSmart_pirState(Context context,Object object){
		SPUtils.putValue(context, Smart_pirState, object);
	}
	//���ø澯ʱ��
	public Object getAlarmTime(Context context,Object defaultObject){
		return SPUtils.getValue(context, AlarmTime, defaultObject);
	}
	public void setAlarmTime(Context context,Object object){
		SPUtils.putValue(context, AlarmTime, object);
	}
	//���ü��������
	public Object getMonitor_sensitivity(Context context,Object defaultObject){
		return SPUtils.getValue(context, Monitor_sensitivity, defaultObject);
	}
	public void setMonitor_sensitivity(Context context,Object object){
		SPUtils.putValue(context, Monitor_sensitivity, object);
	}
	 //�����������״̬
	public Object getRingAlarm(Context context,Object defaultObject){
		return SPUtils.getValue(context, RingAlarm, defaultObject);
	}
	public void setRingAlarm(Context context,Object object){
		SPUtils.putValue(context, RingAlarm, object);
	}
	
	//��ȡ����/¼��ģʽ
	public Object getTalk_photo_mode(Context context,Object defaultObject){
		return SPUtils.getValue(context, talk_photo_mode, defaultObject);
	}
	//��������/¼��ģʽ
	public void setTalk_photo_mode(Context context,Object object){
		SPUtils.putValue(context, talk_photo_mode, object);
	}
	//��ȡ����ģʽ �µ�����
	public Object getTalk_photo_data(Context context,Object defaultObject){
		return SPUtils.getValue(context, talk_photo_data, defaultObject);
	}
	//��������ģʽ �µ�����
	public void setTalk_photo_data(Context context,Object object){
		SPUtils.putValue(context, talk_photo_data, object);
	}
	//��ȡ¼��ģʽ �µ�����
	public Object getTalk_video_data(Context context,Object defaultObject){
		return SPUtils.getValue(context, talk_video_data, defaultObject);
	}
	//����¼��ģʽ �µ�����
	public void setTalk_video_data(Context context,Object object){
		SPUtils.putValue(context, talk_video_data, object);
	}
	//��ȡ��������
	public Object getRingBell(Context context,Object defaultObject){
		return SPUtils.getValue(context, RingBell, defaultObject);
	}
	//���ú�������
	public void setRingBell(Context context,Object object){
		SPUtils.putValue(context, RingBell, object);
	}
	//��ȡ���ܲ���������
	public Object getLight_sensitivity(Context context,Object defaultObject){
		return SPUtils.getValue(context, Light_sensitivity, defaultObject);
	}
	//�������ܲ���������
	public void setLight_sensitivity(Context context,Object object){
		SPUtils.putValue(context, Light_sensitivity, object);
	}

	//��ȡ��ԴƵ��
	public Object getlight_hz(Context context,Object defaultObject){
		return SPUtils.getValue(context, light_hz, defaultObject);
	}
	//���ù�ԴƵ��
	public void setlight_hz(Context context,Object object){
		SPUtils.putValue(context, light_hz, object);
	}

	//��ȡ���ŷ�������ַ
	public Object getBestServerAddress(Context context,Object defaultObject){
		return SPUtils.getValue(context, serverIpaddress, defaultObject);
	}
	//�������ŷ�������ַ
	public void setBestServerAddress(Context context,Object object){
		SPUtils.putValue(context, serverIpaddress, object);
	}
	
}
