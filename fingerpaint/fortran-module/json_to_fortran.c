// ---------- INCLUDES --------------------------------------------------------
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <json/json.h>
#include <json/json_object.h>
#include <json/json_tokener.h>

// ---------- DEFINES ---------------------------------------------------------
#define BADKEY        -1
// Keys to be used in a switch over the possible keys in a request JSON
#define GEOMETRY       1
#define MATRIX         2
#define DISTRIBUTION   3
#define PROTOCOL       4
#define NUMSTEPS       5
#define INTERMEDIATE   6
// Keys to be used in a switch over the possible keys in a step JSON
#define STEPID         1
#define STEPDURATION   2
// Define for the number of keys in a look-up table
#define NKEYS(arr) (sizeof(arr)/sizeof(symstruct_t))

// ---------- TYPE DEFINITIONS ------------------------------------------------
typedef struct {
	double *performance;
	size_t performance_len;
	double **results;
} response_t;

typedef struct {
	char *stepid;
	double step;
} step_t;

typedef struct {
	int geometry;
	int matrix;
	double *distribution;
	size_t distribution_len;
	step_t *protocol;
	size_t protocol_len;
	int numsteps;
	bool intermediateResults;
} request_t;

// Use look-up table as shown here: http://stackoverflow.com/a/4014981/962603
typedef struct {
	char *key;
	int val;
} symstruct_t;

// ---------- GLOBAL VARIABLES ------------------------------------------------
static symstruct_t lookuptableRequest[] = {
	{ "geometry",            GEOMETRY },
	{ "matrix",              MATRIX },
	{ "distribution",        DISTRIBUTION },
	{ "protocol",            PROTOCOL },
	{ "numsteps",            NUMSTEPS },
	{ "intermediateResults", INTERMEDIATE }
};

static symstruct_t lookuptableStep[] = {
	{ "stepid",              STEPID },
	{ "stepduration",        STEPDURATION }
};



// ---------- FUNCTIONS -------------------------------------------------------
/**
 * Fortran function that can do simulation.
 */
extern void __subs_m_MOD_simulate(int *geometry, int *matrix, double *distribution, size_t *len_distribution, double *step, char *stepid, size_t *len_stepid, double *segregation);

/**
 * Look up a key in a look-up table and return its index, or -1 if
 * such a key is not present.
 */
int key_from_string(char *key, symstruct_t *lookuptable, size_t len) {
	int i;
	for (i = 0; i < len; i++) {
		symstruct_t sym = lookuptable[i];
		if (strcmp(sym.key, key) == 0) {
			return sym.val;
		}
	}
	return BADKEY;
}

/**
 * Parse a JSON request object and initialise the given type.
 * Note that the type may be initialised only partly, if not all keys
 * are set in the JSON object.
 * 
 * Returns 0 on succes, 1 on failure.
 */
int json_parse_request (char* json_str, request_t *request) {
	json_object *jobj = json_tokener_parse(json_str);
	enum json_type type = json_object_get_type(jobj);
	int i;
	struct json_object *tmpval;
	
	if (type != json_type_object) {
		return 1;
	}
	
	// Cycle through all properties of the object
	json_object_object_foreach(jobj, key, val) {
		type = json_object_get_type(val);
		switch(key_from_string(key, lookuptableRequest, NKEYS(lookuptableRequest))) {
			case GEOMETRY :
				if (type == json_type_int) {
					request->geometry = json_object_get_int(val);
				}
				break;
			
			case MATRIX :
				if (type == json_type_int) {
					request->matrix = json_object_get_int(val);
				}
				break;
			
			case DISTRIBUTION :
				if (type == json_type_array) {
					array_list *array = json_object_get_array(val);
					request->distribution_len = json_object_array_length(val);
					double *dist = malloc(request->distribution_len * sizeof(double));
					
					for (i = 0; i < request->distribution_len; i++) {
						tmpval = json_object_array_get_idx(val, i);
						type = json_object_get_type(tmpval);
						if (type == json_type_double || type == json_type_int) {
							dist[i] = json_object_get_double(tmpval);
						}
					}
					
					request->distribution = dist;
				}
				break;
			
			case PROTOCOL :
				if (type == json_type_array) {
					array_list *array = json_object_get_array(val);
					request->protocol_len = json_object_array_length(val);
					step_t *prot = malloc(request->protocol_len * sizeof(step_t));
					
					for (i = 0; i < request->protocol_len; i++) {
						tmpval = json_object_array_get_idx(val, i);
						type = json_object_get_type(tmpval);
						if (type == json_type_object) {
							char *stepid;
							double stepduration;
							json_object_object_foreach(tmpval, key2, val2) {
								type = json_object_get_type(val2);
								switch(key_from_string(key2, lookuptableStep, NKEYS(lookuptableStep))) {
									case STEPID :
										if (type == json_type_string) {
											stepid = strdup(json_object_get_string(val2));
										}
										break;
									
									case STEPDURATION :
										if (type == json_type_double) {
											stepduration = json_object_get_double(val2);
										}
										break;
									
									case BADKEY :
										printf("Unkown key '%s' in request/step object.\n", key);
										break;
								}
							}
							step_t step = {
									.stepid = stepid,
									.step = stepduration
								};
							prot[i] = step;
						}
					}
					
					request->protocol = prot;
				}
				break;
			
			case NUMSTEPS :
				if (type == json_type_int) {
					request->numsteps = json_object_get_int(val);
				}
				break;
			
			case INTERMEDIATE :
				if (type == json_type_boolean) {
					request->intermediateResults = json_object_get_boolean(val);
				}
				break;
			
			case BADKEY :
				printf("Unkown key '%s' in request object.\n", key);
				break;
		}
	}
	
	return 0;
}

/**
 * Entry point of the application.
 */
int main(void) {
	// Variable declaration: initialise a request_t empty, for when
	// values are not filled in in the JSON request
	request_t requestObj = {0};
	// Segregation will be filled in by the FORTRAN function
	double segregation = 0;
	
	// TODO Obviously, here we want to load some JSON-object from the socket
	char *request = "{\"geometry\":0,\"matrix\":0,\"distribution\":[0.0,0.0,1.0,1,0.0,0.0,1.0,1.0],\"protocol\":[{\"stepid\":\"TL\",\"stepduration\":19.75},{\"stepid\":\"BR\",\"stepduration\":2.5}],\"numsteps\":3,\"intermediateResults\":true}";
	
	// Parse the JSON
	json_parse_request(request, &requestObj);
	
	// TODO Remove this piece of code when we can actually receive nice JSON over sockets
	// Right now, override the distribution from the JSON with a custom one, so that it actually works
	requestObj.distribution_len = 240 * 400;
	free(requestObj.distribution);
	requestObj.distribution = malloc(requestObj.distribution_len * sizeof(double));
	FILE *fp = fopen("initialconcentration.dat", "rt");
	if (!fp) {
		printf("[Error] Unable to open file: 'concentration.dat'.\n");
		return (EXIT_FAILURE);
	}
	int count = 0;
	double value;
	for (; !feof(fp); count++) {
		if (fscanf(fp, "%lf\n", &value) != 1) {
			break;
		}
		
		requestObj.distribution[count] = value;
	}
	
	// Call FORTRAN
	size_t stepid_len = strlen((requestObj.protocol[0]).stepid);
	__subs_m_MOD_simulate(&requestObj.geometry, &requestObj.matrix, requestObj.distribution, &requestObj.distribution_len, &(requestObj.protocol[0].step), requestObj.protocol[0].stepid, &stepid_len, &segregation);
	
	// TODO Actually return the response over the socket
	double perf[] = { segregation };
	double *results[] = { requestObj.distribution };
	response_t responseObj = {
			.performance = perf,
			.results = results
		};
	printf("Segregation = %lf\n", responseObj.performance[0]);
}