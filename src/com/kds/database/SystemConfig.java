package com.kds.database;


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
    
    private  String RingBell="RingBell";		//提取门铃 SharedPreferences 字段名
    private String RingAlarm="RingAlarm";	//提取告警 SharedPreferences 字段名
    private String Light_sensitivity="LightSensitivity";//提取 SharedPreferences 字段名
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
	//获取智能补光灵敏度
	public Object getLight_sensitivity(Context context,Object defaultObject){
		return SPUtils.getValue(context, Light_sensitivity, defaultObject);
	}
	//设置智能补光灵敏度
	public void setLight_sensitivity(Context context,Object object){
		SPUtils.putValue(context, Light_sensitivity, object);
	}
	//获取光源频率
	public Object getlight_hz(Context context,Object defaultObject){
		return SPUtils.getValue(context, light_hz, defaultObject);
	}
	//设置光源频率
	public void setlight_hz(Context context,Object object){
		SPUtils.putValue(context, light_hz, object);
	}
	//获取最优服务器地址
	public Object getBestServerAddress(Context context,Object defaultObject){
		return SPUtils.getValue(context, light_hz, defaultObject);
	}
	//保存最优服务器地址
	public void setBestServerAddress(Context context,Object object){
		SPUtils.putValue(context, light_hz, object);
	}
}
