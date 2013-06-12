#include "nl_tue_fingerpaint_server_simulator_NativeCommunicator.h"
#include <string.h>

extern void __subs_m_MOD_simulate(const char *geometry, size_t *len_geometry,
								  const char *mixer, size_t *len_mixer,
								  double *distribution, size_t *len_distribution,
								  double *step_size,
								  const char *step_name, size_t *len_step_name,
								  double *segregation);

/*
 * Class:     nl_tue_fingerpaint_server_simulator_NativeCommunicator
 * Method:    simulate
 * Signature: (II[DDLjava/lang/String;)D
 */
JNIEXPORT jdouble JNICALL Java_nl_tue_fingerpaint_server_simulator_NativeCommunicator_simulate
  (JNIEnv *env, jobject this, jstring geometry, jstring mixer,
		  jdoubleArray distribution, jdouble step_size, jstring step_name) {
	const char *geometry_chars = (*env)->GetStringUTFChars(env, geometry, 0);
	size_t geometry_chars_size = strlen(geometry_chars);

	const char *mixer_chars = (*env)->GetStringUTFChars(env, mixer, 0);
	size_t mixer_chars_size = strlen(mixer_chars);

	const char *step_name_chars = (*env)->GetStringUTFChars(env, step_name, 0);
	size_t step_name_chars_size = strlen(step_name_chars);

    jdouble *distribution_array = (*env)->GetDoubleArrayElements(env, distribution, 0);
    jsize distribution_array_size = (*env)->GetArrayLength(env, distribution);

    jdouble segregation = 0;

    __subs_m_MOD_simulate(
    		geometry_chars, &geometry_chars_size,
    		mixer_chars, &mixer_chars_size,
    		distribution_array, (size_t *)&distribution_array_size,
    		(double *)&step_size,
    		step_name_chars, &step_name_chars_size,
    		(double *)&segregation);

    (*env)->ReleaseDoubleArrayElements(env, distribution, distribution_array, 0);
    (*env)->ReleaseStringUTFChars(env, geometry, geometry_chars);
    (*env)->ReleaseStringUTFChars(env, mixer, mixer_chars);
	(*env)->ReleaseStringUTFChars(env, step_name, step_name_chars);

	return segregation;
}
