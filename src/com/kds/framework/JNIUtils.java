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
	
	//���� PIR ��������
	public static native int JNI_SetPIR_ResponseRate(int ResponseRate);
	//����ϵͳ����ָʾ��
	public static native int JNI_SetSystemLed(int led);
	//��������ͷ����������
	public static native int JNI_SetCameraResponseRate(int ResponseRate);
	//���ù�ԴƵ��
	public static native int JNI_SetCameraLightHz(int HZ);
	
	//��ʼ��433ģ��
	public static native int JNI_A7139Spi433Mode_Init();
	//���433ģ��
	public static native int JNI_A7139Spi433Mode_AddDevices();
	//����433ģ��  state ����/����
	public static native int JNI_A7139Spi433Mode_CtrlDoor(int state);
	//ɾ��
	public static native int JNI_A7139Spi433Mode_DeleteDevices();
	
	//��ȡ�豸cpu ��Ϣ
	public String GetDevicesList(){
		String str ="";
		return str;
	}
	//��������----------->�������/�޴��� ����
	public void StartConfigNetwork(){
		
	}
	//ֹͣ����
	public void StopConfigNetwork(){
		
	}

}
