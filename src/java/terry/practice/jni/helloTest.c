#include <jni.h>
#include "terry_practice_jni_NativeTest.h"

JNIEXPORT jint JNICALL Java_terry_practice_jni_NativeTest_test(JNIEnv *env, jobject obj, jint i) {
    return i*i;
}