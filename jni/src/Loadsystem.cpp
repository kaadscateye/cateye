#include <jni.h>
extern "C"{
	#include "comsHead.h"
	#include "SetTing.h"
}
//��ʼ��ϵͳ����
JNIEXPORT int JNICALL JNI_InitSystem(JNIEnv *env,jobject obj,jstring j_sdcard){
	SetTing set;
	set.open();
	return 0;
}
//����ϵͳ�����ڴ���Դ
JNIEXPORT int JNICALL JNI_DestorySystemResoure(JNIEnv *env,jobject obj){
	return 0;
}

JNIEXPORT jstring JNICALL JNI_GetSystem(JNIEnv *env,jobject obj){
	char filename[128];
	memset(filename,0,128);
	return (env->NewStringUTF(filename));
}

//����PIR������
JNIEXPORT int JNICALL JNI_SetPIR_ResponseRate(JNIEnv *env,jobject obj,jint ResponseRate){

		return 0;
}
//����ϵͳ����ָʾ��/�����
JNIEXPORT int JNICALL JNI_SetSystemLed(JNIEnv *env,jobject obj,jint ledNum,jint state){

		return 0;
}

//��������ͷ����������
JNIEXPORT int JNICALL JNI_SetCameraResponseRate(JNIEnv *env,jobject obj,jint ResponseRate){

		return 0;
}
//���ù�ԴƵ��
JNIEXPORT int JNICALL JNI_SetCameraLightHz(JNIEnv *env,jobject obj,jint HZ){

		return 0;
}
/*
 * ��ʼ���ɹ�����0 ����ʼ��ʧ�� ����-1 ��������ǰè���豸û��433ģ��
 */
JNIEXPORT int JNICALL JNI_A7139Spi433Mode_Init(JNIEnv *env,jobject obj){

		return 0;
}
JNIEXPORT int JNICALL JNI_A7139Spi433Mode_AddDevices(JNIEnv *env,jobject obj){

		return 0;
}
JNIEXPORT int JNICALL JNI_A7139Spi433Mode_CtrlDoor(JNIEnv *env,jobject obj,jint state){

		return 0;
}
JNIEXPORT int JNICALL JNI_A7139Spi433Mode_DeleteDevices(JNIEnv *env,jobject obj){

		return 0;
}
/* Defined by native libraries. */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved){
	JNINativeMethod met[64];
	JNIEnv *env;
	int ret=0;
	if(vm->GetEnv((void **)&env, JNI_VERSION_1_6)!=JNI_OK){
		LOGD("JNI_OnLoad get env failed");
		return JNI_ERR;
	}

	met[ret].name = "JNI_InitSystem";
	met[ret].signature = "(Ljava/lang/String;)I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_InitSystem;

	met[ret].name = "JNI_DestorySystemResoure";
	met[ret].signature = "()I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_DestorySystemResoure;

	met[ret].name = "JNI_GetSystem";
	met[ret].signature = "()Ljava/lang/String;";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_GetSystem;

	met[ret].name = "JNI_SetPIR_ResponseRate";
	met[ret].signature = "(I)I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_SetPIR_ResponseRate;

	met[ret].name = "JNI_SetSystemLed";
	met[ret].signature = "(II)I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_SetSystemLed;

	met[ret].name = "JNI_SetCameraResponseRate";
	met[ret].signature = "(I)I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_SetCameraResponseRate;

	met[ret].name = "JNI_SetCameraLightHz";
	met[ret].signature = "(I)I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_SetCameraLightHz;


	met[ret].name = "JNI_A7139Spi433Mode_Init";
	met[ret].signature = "()I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_A7139Spi433Mode_Init;

	met[ret].name = "JNI_A7139Spi433Mode_AddDevices";
	met[ret].signature = "()I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_A7139Spi433Mode_AddDevices;

	met[ret].name = "JNI_A7139Spi433Mode_CtrlDoor";
	met[ret].signature = "(I)I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_A7139Spi433Mode_CtrlDoor;

	met[ret].name = "JNI_A7139Spi433Mode_DeleteDevices";
	met[ret].signature = "()I";  //javap -s -p ����.����
	met[ret++].fnPtr = (void *)JNI_A7139Spi433Mode_DeleteDevices;

	jclass  cls = env->FindClass("com/kds/framework/JNIUtils");

	if(env->RegisterNatives(cls, met,ret)!=JNI_OK){
    	return JNI_ERR;
    }
	return JNI_VERSION_1_6;
}
