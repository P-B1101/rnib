/****************************************************************************
 * File:   toolChecker.h
 * Author: Matthew Rollings
 * Date:   19/06/2015
 *
 * Description : Root checking JNI NDK code
 *
 ****************************************************************************/

extern "C" {

#include <jni.h>

void Java_com_inr_rnib_RNIBNative_setLogDebugMessages( JNIEnv* env, jobject thiz, jboolean debug);

int Java_com_inr_rnib_RNIBNative_rfcRoot( JNIEnv* env, jobject thiz , jobjectArray pathsArray );

}
