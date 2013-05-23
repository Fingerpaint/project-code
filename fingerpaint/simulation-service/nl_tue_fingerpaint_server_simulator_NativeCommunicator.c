#include "nl_tue_fingerpaint_server_simulator_NativeCommunicator.h"
#include <string.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>

extern void __subs_m_MOD_simulate(int *geometry, int *matrix,
									double *distribution, size_t *len_distribution,
									double *step, char *stepid, size_t *len_stepid,
									double *segregation);

/*
 * Class:     nl_tue_fingerpaint_server_simulator_NativeCommunicator
 * Method:    simulate
 * Signature: (II[DDLjava/lang/String;)D
 */
JNIEXPORT jdouble JNICALL Java_nl_tue_fingerpaint_server_simulator_NativeCommunicator_simulate
  (JNIEnv *env, jobject this, jint geometry, jint matrix,
		  jdoubleArray distribution, jdouble step, jstring stepid) {

	const char *stepid_chars = (*env)->GetStringUTFChars(env, stepid, 0);
	size_t stepid_chars_size = strlen(stepid_chars);

    jdouble *distribution_array = (*env)->GetDoubleArrayElements(env, distribution, 0);
    jsize distribution_array_size = (*env)->GetArrayLength(env, distribution);

    jdouble segregation = 0;
//
//    int g = 0;
//    int m = 0;
//    double *d = malloc(96000 * sizeof(double));
//    size_t dz = 96000;
//    double s = 5.0;
//    char *c = "TL";
//    size_t cz = 2;
//    double se = 0;
//
//    int i = 0;
//    for (i = 0; i < 96000; i++) {
//    	if (i < 96000/2) {
//    		d[i] = 1;
//    	} else {
//    		d[i] = 0;
//    	}
//    }

    __subs_m_MOD_simulate((int *)&geometry, (int *)&matrix, (double *)distribution_array,
    		(size_t *)&distribution_array_size, (double *)&step, (char *)stepid_chars,
    		(size_t *)&stepid_chars_size, (double *)&segregation);
    //printf("mama test");
    //return 5.25;
   // __subs_m_MOD_simulate(&g, &m, d, &dz, &s, c, &cz, &se);

    (*env)->ReleaseDoubleArrayElements(env, distribution, distribution_array, 0);
	(*env)->ReleaseStringUTFChars(env, stepid, stepid_chars);

	return segregation;
}

//JNIEXPORT jint JNICALL Java_Sample1_intMethod
//  (JNIEnv *env, jobject obj, jint num) {
//   return num * num;
//}
//
//NIEXPORT jboolean JNICALL Java_Sample1_booleanMethod
//  (JNIEnv *env, jobject obj, jboolean boolean) {
//  return !boolean;
//}
//
//JNIEXPORT jstring JNICALL Java_Sample1_stringMethod
//  (JNIEnv *env, jobject obj, jstring string) {
//    const char *str = (*env)->GetStringUTFChars(env, string, 0);
//    char cap[128];
//    strcpy(cap, str);
//   (*env)->ReleaseStringUTFChars(env, string, str);
//   return (*env)->NewStringUTF(env, strupr(cap));
//}
//
//JNIEXPORT jint JNICALL Java_Sample1_intArrayMethod
//  (JNIEnv *env, jobject obj, jintArray array) {
//    int i, sum = 0;
//    jsize len = (*env)->GetArrayLength(env, array);
//    jint *body = (*env)->GetIntArrayElements(env, array, 0);
//   for (i=0; i<len; i++)
//    {	sum += body[i];
//   }
//   (*env)->ReleaseIntArrayElements(env, array, body, 0);
//    return sum;
//}
