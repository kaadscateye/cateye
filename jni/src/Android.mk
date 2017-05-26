LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE := cateye


LOCAL_C_INCLUDES := -I ./include

LOCAL_SRC_FILES := Loadsystem.cpp SetTing.cpp\
					drivers/A71xxSpi.c\
					drivers/Interrupt.c\
					drivers/SetTing.cpp\


LOCAL_CFLAGS += -DGL_GLEXT_PROTOTYPES
LOCAL_SHARED_LIBRARIES :=  
LOCAL_STATIC_LIBRARIES :=  
LOCAL_LDLIBS := -ldl -lGLESv1_CM -lGLESv2 -llog  -lz

include $(BUILD_SHARED_LIBRARY)
