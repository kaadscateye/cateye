package com.kds.system;

import android.content.Context;

public class SystemConfig {
	private static SystemConfig  systemConfig=null;
	private Boolean Smart_pirState;		//pir 状态
	private int AlramTime;				//报警时间
    private int Monitoring_sensitivity;	//监控灵敏度
    private String alarm_bell;			//自动报警铃声
    private int vol;					//系统音量大小
    private int talk_photoMode;			//连拍模式
    private int leave_recoder_mode;		//留言模式
    
    public final int sensitivity_low=1;
    public final int sensitivity_high=2;
    
    public final static String RingBell="RingBell";
    public final static String RingAlarm="RingAlarm";
    public final static String Light_sensitivity="LightSensitivity";
    public final static String light_hz="light_hz";
    
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
		return SPUtils.getValue(context, SystemConfig.RingBell, defaultObject);
	}
	
	public void setRingBell(Context context,Object object){
		SPUtils.putValue(context, SystemConfig.RingBell, object);
	}
	public Object getRingAlarm(Context context,Object defaultObject){
		return SPUtils.getValue(context, SystemConfig.RingAlarm, defaultObject);
	}
	
	public void setRingAlarm(Context context,Object object){
		SPUtils.putValue(context, SystemConfig.RingAlarm, object);
	}
	public Object getLight_sensitivity(Context context,Object defaultObject){
		return SPUtils.getValue(context, SystemConfig.Light_sensitivity, defaultObject);
	}
	
	public void setLight_sensitivity(Context context,Object object){
		SPUtils.putValue(context, SystemConfig.Light_sensitivity, object);
	}
	public Object getlight_hz(Context context,Object defaultObject){
		return SPUtils.getValue(context, SystemConfig.light_hz, defaultObject);
	}
	
	public void setlight_hz(Context context,Object object){
		SPUtils.putValue(context, SystemConfig.light_hz, object);
	}
	
}
