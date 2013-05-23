CC=gcc
FC=gfortran
CFLAGS=-I.
FLAGS = -O2

all: link

link: nl_tue_fingerpaint_server_simulator_NativeCommunicator.o ../fortran-module/add_module.o
	$(CC) -Wall -D_JNI_IMPLEMENTATION_ -Wl,--kill-at -L"C:/MinGW2/lib/gcc/mingw32/4.7.2/" -shared nl_tue_fingerpaint_server_simulator_NativeCommunicator.o ../fortran-module/add_module.o -o NativeCommunicator.dll -lgfortran -lgfortranbegin

../fortran-module/add_module.o: ../fortran-module/add_module.f90
	$(FC) -c ../fortran-module/add_module.f90 -o ../fortran-module/add_module.o
	
nl_tue_fingerpaint_server_simulator_NativeCommunicator.o: nl_tue_fingerpaint_server_simulator_NativeCommunicator.c
	#$(CC) -L /lib/mingw -Wl,--kill-at -shared -I "C:/Program Files/Java/jdk1.7.0_09/include/" -I "C:/Program Files/Java/jdk1.7.0_09/include/win32/" nl_tue_fingerpaint_server_simulator_NativeCommunicator.c -c -o nl_tue_fingerpaint_server_simulator_NativeCommunicator.o
	$(CC) -Wall -D_JNI_IMPLEMENTATION_ -Wl,--kill-at -I "C:/Program Files/Java/jdk1.7.0_09/include/" -I "C:/Program Files/Java/jdk1.7.0_09/include/win32/" -shared nl_tue_fingerpaint_server_simulator_NativeCommunicator.c -c -o nl_tue_fingerpaint_server_simulator_NativeCommunicator.o