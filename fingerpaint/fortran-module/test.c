#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(void) {
	// Fortran function to call
	extern void __subs_m_MOD_simulate(int *geometry, int *matrix, double *distribution, size_t *len_distribution, double *step, char *stepid, size_t *len_stepid, double *segregation);
	
	// Initialise variables
	int geometry = 0;
	int matrix = 0;
	size_t len_distribution = 240 * 400;
	double *distribution = malloc(len_distribution * sizeof(double));
	double step = 19.75;
	char *stepid = "TL";
	size_t len_stepid = strlen(stepid);
	double segregation = 0;
	
	// Read initial concentration from file
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
		
		distribution[count] = value;
	}
	
	__subs_m_MOD_simulate(&geometry, &matrix, distribution, &len_distribution, &step, stepid, &len_stepid, &segregation);
	
	printf("Segregation = %lf\n", segregation);
	
	return EXIT_SUCCESS;
}