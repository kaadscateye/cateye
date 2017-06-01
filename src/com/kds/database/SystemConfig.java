package com.kds.database;


import android.content.Context;

public class SystemConfig {
	private static SystemConfig  systemConfig=null;

	private int AlramTime;				//报警时间
    private int Monitoring_sensitivity;	//监控灵敏度
    private String alarm_bell;			//自动报警铃声
    private int vol;					//系统音量大小
    private int talk_photoMode;			//连拍模式
    private int leave_recoder_mode;		//留言模式
    
    public static final int sensitivity_low=0;
    public static final int sensitivity_high=1;
    
    private  String Smart_pirState="Smart_pirState";		//提取pir人体侦测状态 SharedPreferences 字段名
    private  String AlarmTime="AlarmTime";		//提取pir人体侦测状态 SharedPreferences 字段名
    private  String Monitor_sensitivity="Monitor_sensitivity";		//提取pir人体侦测状态 SharedPreferences 字段名
    private  String RingBell="RingBell";		//提取门铃 SharedPreferences 字段名
    private String RingAlarm="RingAlarm";	//提取告警 SharedPreferences 字段名
    private String Light_sensitivity="LightSensitivity";//提取 SharedPreferences 字段名
    private String light_hz="light_hz";		//提取光源频率 SharedPreferences 字段名
    private String talk_photo_mode="talkphoto_mode";//提取拍照 SharedPreferences 字段名
    private String talk_photo_data="talkphoto_data";//提取拍照 SharedPreferences 字段名,数值
    private String talk_video_data="talkvideo_data";//提取录制视频 SharedPreferences 字段名,数值
    public static final int istalk_photo_mode=0;	//连拍模式
    public static final int istalk_video_mode=1;	//录像模式
    private String serverIpaddress="serverIpaddress";//提取最优服务器 SharedPreferences 字段名
	private  SystemConfig() {
		
	}
	 public static SystemConfig getInstance(){
		 if (systemConfig==null) {
				systemConfig =new SystemConfig();
			}
		return systemConfig;
	 }
	//设置pir 开关状态
	public Object getSmart_pirState(Context context,Object defaultObject){
		return SPUtils.getValue(context, Smart_pirState, defaultObject);
	}
	public void setSmart_pirState(Context context,Object object){
		SPUtils.putValue(context, Smart_pirState, object);
	}
	//设置告警时间
	public Object getAlarmTime(Context context,Object defaultObject){
		return SPUtils.getValue(context, AlarmTime, defaultObject);
	}
	public void setAlarmTime(Context context,Object object){
		SPUtils.putValue(context, AlarmTime, object);
	}
	//设置监控灵敏度
	public Object getMonitor_sensitivity(Context context,Object defaultObject){
		return SPUtils.getValue(context, Monitor_sensitivity, defaultObject);
	}
	public void setMonitor_sensitivity(Context context,Object object){
		SPUtils.putValue(context, Monitor_sensitivity, object);
	}
	 //智能人体侦测状态
	public Object getRingAlarm(Context context,Object defaultObject){
		return SPUtils.getValue(context, RingAlarm, defaultObject);
	}
	public void setRingAlarm(Context context,Object object){
		SPUtils.putValue(context, RingAlarm, object);
	}
	
	//获取拍照/录像模式
	public Object getTalk_photo_mode(Context context,Object defaultObject){
		return SPUtils.getValue(context, talk_photo_mode, defaultObject);
	}
	//设置拍照/录像模式
	public void setTalk_photo_mode(Context context,Object object){
		SPUtils.putValue(context, talk_photo_mode, object);
	}
	//获取拍照模式 下的数据
	public Object getTalk_photo_data(Context context,Object defaultObject){
		return SPUtils.getValue(context, talk_photo_data, defaultObject);
	}
	//设置拍照模式 下的数据
	public void setTalk_photo_data(Context context,Object object){
		SPUtils.putValue(context, talk_photo_data, object);
	}
	//获取录像模式 下的数据
	public Object getTalk_video_data(Context context,Object defaultObject){
		return SPUtils.getValue(context, talk_video_data, defaultObject);
	}
	//设置录像模式 下的数据
	public void setTalk_video_data(Context context,Object object){
		SPUtils.putValue(context, talk_video_data, object);
	}
	//获取呼叫铃声
	public Object getRingBell(Context context,Object defaultObject){
		return SPUtils.getValue(context, RingBell, defaultObject);
	}
	//设置呼叫铃声
	public void setRingBell(Context context,Object object){
		SPUtils.putValue(context, RingBell, object);
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
		return SPUtils.getValue(context, serverIpaddress, defaultObject);
	}
	//保存最优服务器地址
	public void setBestServerAddress(Context context,Object object){
		SPUtils.putValue(context, serverIpaddress, object);
	}
	
}
