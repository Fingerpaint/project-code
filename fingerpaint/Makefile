CC=gcc
FC=gfortran
MAKE=make
TARGETDIR=build/war/WEB-INF/lib
SOURCEDIR=src/nl/tue/fingerpaint/server/simulator
BUILDDIR=build/native

ifndef JAVA_HOME
    $(error JAVA_HOME is not set)
endif

ifeq ($(OS), Windows_NT) # if on Windows
	LINKFLAGS=-Wall -D_JNI_IMPLEMENTATION_ -Wl,--kill-at
	LINKCFLAGS= -Wall -D_JNI_IMPLEMENTATION_ -Wl,--kill-at -I "$(JAVA_HOME)/include/" -I "$(JAVA_HOME)/include/win32/"
	LINKOUTPUTNAME=NativeCommunicator.dll
	LINKLIBFLAGS= -static -lgfortran
else
	ifeq ($(shell uname), Linux) # Linux
		LINKFLAGS=-Wall -D_JNI_IMPLEMENTATION_
		LINKCFLAGS=-fPIC -Wall -D_JNI_IMPLEMENTATION_ -Wl,-soname,NativeCommunicator.so -I "$(JAVA_HOME)/include/" -I "$(JAVA_HOME)/include/linux/"
		LINKOUTPUTNAME=libNativeCommunicator.so
		LINKLIBFLAGS=-lgfortran
	endif
endif

all: link

link: ${BUILDDIR}/nl_tue_fingerpaint_server_simulator_NativeCommunicator.o ../fortran-module/add_module.o
	$(CC) $(LINKFLAGS) -shared ../fortran-module/add_module.o $(BUILDDIR)/nl_tue_fingerpaint_server_simulator_NativeCommunicator.o -o $(TARGETDIR)/$(LINKOUTPUTNAME) $(LINKLIBFLAGS)
	
${BUILDDIR}/nl_tue_fingerpaint_server_simulator_NativeCommunicator.o: $(SOURCEDIR)/nl_tue_fingerpaint_server_simulator_NativeCommunicator.c
	$(CC) $(LINKCFLAGS) -shared $(SOURCEDIR)/nl_tue_fingerpaint_server_simulator_NativeCommunicator.c -c -o $@
	
../fortran-module/add_module.o:
	$(MAKE) -C ../fortran-module/ add_module.o