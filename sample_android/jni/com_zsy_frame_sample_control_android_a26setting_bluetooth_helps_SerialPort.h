#include <jni.h>
/* Header for class com_zsy_frame_sample_control_android_a26setting_bluetooth_helps_SerialPort */

#ifndef _Included_com_zsy_frame_sample_control_android_a26setting_bluetooth_helps_SerialPort
#define _Included_com_zsy_frame_sample_control_android_a26setting_bluetooth_helps_SerialPort
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_zsy_frame_sample_control_android_a26setting_bluetooth_helps_SerialPort
 * Method:    open
 * Signature: (Ljava/lang/String;II)Ljava/io/FileDescriptor;
 */
JNIEXPORT jobject JNICALL Java_com_zsy_frame_sample_control_android_a26setting_bluetooth_helps_SerialPort_open
  (JNIEnv *, jclass, jstring, jint, jint);

/*
 * Class:     com_zsy_frame_sample_control_android_a26setting_bluetooth_helps_SerialPort
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_zsy_frame_sample_control_android_a26setting_bluetooth_helps_SerialPort_close
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
