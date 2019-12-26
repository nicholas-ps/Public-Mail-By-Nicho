#include <jni.h>
#include <string>
#include <time.h>

extern "C" JNIEXPORT jstring JNICALL
Java_id_ac_ui_cs_mobileprogramming_nicholas_1priambodo_public_1mail_1by_1nicho_view_SettingActivity_currentTimeFromJNI(JNIEnv *env, jobject) {
    time_t rawtime;
    time(&rawtime);
    return env->NewStringUTF(asctime(localtime(&rawtime)));
}
