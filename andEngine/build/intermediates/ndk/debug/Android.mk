LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := andengine_shared
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	/Users/manavdutta1/Downloads/HackGTProject/andEngine/src/main/jni/Android.mk \
	/Users/manavdutta1/Downloads/HackGTProject/andEngine/src/main/jni/Application.mk \
	/Users/manavdutta1/Downloads/HackGTProject/andEngine/src/main/jni/build.sh \
	/Users/manavdutta1/Downloads/HackGTProject/andEngine/src/main/jni/src/BufferUtils.cpp \
	/Users/manavdutta1/Downloads/HackGTProject/andEngine/src/main/jni/src/GLES20Fix.c \

LOCAL_C_INCLUDES += /Users/manavdutta1/Downloads/HackGTProject/andEngine/src/main/jni
LOCAL_C_INCLUDES += /Users/manavdutta1/Downloads/HackGTProject/andEngine/src/debug/jni

include $(BUILD_SHARED_LIBRARY)
