package com.kds.database;


import android.content.Context;

public class SystemConfig {
	private static SystemConfig  systemConfig=null;
	private Boolean Smart_pirState;		//pir ״̬
	private int AlramTime;				//����ʱ��
    private int Monitoring_sensitivity;	//���������
    private String alarm_bell;			//�Զ���������
    private int vol;					//ϵͳ������С
    private int talk_photoMode;			//����ģʽ
    private int leave_recoder_mode;		//����ģʽ
    
    public final int sensitivity_low=1;
    public final int sensitivity_high=2;
    
    private  String RingBell="RingBell";		//��ȡ���� SharedPreferences �ֶ���
    private String RingAlarm="RingAlarm";	//��ȡ�澯 SharedPreferences �ֶ���
    private String Light_sensitivity="LightSensitivity";//��ȡ SharedPreferences �ֶ���
    private String light_hz="light_hz";
    
	private  SystemConfig() {
		
	}
	 public static SystemConfig getInstance(){
		 if (systemConfig==null) {
				systemConfig =new SystemConfig();
			}
		return systemConfig;
	 }
	 public void SetSmart_pirState(Boolean Smart_pirState) {
	     this.Smart_pirState = Smart_pirState;
	 }
	 public Boolean GetSmart_pirState() {
		 return this.Smart_pirState ;
	 }
	 
	 public int getAlramTime() {
	    return AlramTime;
	 }
	 public void setAlramTime(int AlramTime) {
	   this.AlramTime = AlramTime;
	 }

	 public int gettalk_photoMode() {
		    return talk_photoMode;
	 }
	 public void settalk_photoMode(int talk_photoMode) {
		   this.talk_photoMode = talk_photoMode;
	}
	 public int getleave_recoder_mode() {
		    return leave_recoder_mode;
	 }
	 public void setleave_recoder_mode(int leave_recoder_mode) {
		   this.leave_recoder_mode = leave_recoder_mode;
	}

	public Object getRingBell(Context context,Object defaultObject){
		return SPUtils.getValue(context, RingBell, defaultObject);
	}
	
	public void setRingBell(Context context,Object object){
		SPUtils.putValue(context, RingBell, object);
	}
	public Object getRingAlarm(Context context,Object defaultObject){
		return SPUtils.getValue(context, RingAlarm, defaultObject);
	}
	
	public void setRingAlarm(Context context,Object object){
		SPUtils.putValue(context, RingAlarm, object);
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
		return SPUtils.getValue(context, light_hz, defaultObject);
	}
	//�������ŷ�������ַ
	public void setBestServerAddress(Context context,Object object){
		SPUtils.putValue(context, light_hz, object);
	}
}
