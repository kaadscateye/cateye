package com.kds.framework;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JNIUtils {
	static {
		System.loadLibrary("cateye");
	}
	public static native int JNI_InitSystem(String cachepath);
	public static native int JNI_DestorySystemResoure();
	public static native String JNI_GetSystem();
	
	//设置 PIR 的灵敏度
	public static native int JNI_SetPIR_ResponseRate(int ResponseRate);
	//设置系统工作指示灯
	public static native int JNI_SetSystemLed(int led);
	//设置摄像头补光灵敏度
	public static native int JNI_SetCameraResponseRate(int ResponseRate);
	//设置光源频率
	public static native int JNI_SetCameraLightHz(int HZ);
	
	//初始化433模块
	public static native int JNI_A7139Spi433Mode_Init();
	//添加433模块
	public static native int JNI_A7139Spi433Mode_AddDevices();
	//控制433模块  state 开门/关门
	public static native int JNI_A7139Spi433Mode_CtrlDoor(int state);
	//删除
	public static native int JNI_A7139Spi433Mode_DeleteDevices();
	
	//获取设备cpu 信息
	public String GetDevicesList(){
		String str ="";
		return str;
	}
	//启动配网----------->针对无屏/无触摸 机器
	public void StartConfigNetwork(){
		
	}
	//停止配网
	public void StopConfigNetwork(){
		
	}

}
