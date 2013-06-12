ifeq ($(OS), Windows_NT) # if on Windows
	OS=Windows
	
	CC=gcc
	FC=gfortran
	LINKFLAGS=-Wall -D_JNI_IMPLEMENTATION_ -Wl,--kill-at -L"C:/MinGW2/lib/gcc/mingw32/4.7.2/"
	LINKFFLAGS=-c
	LINKCFLAGS= -Wall -D_JNI_IMPLEMENTATION_ -Wl,--kill-at -I "C:/Program Files/Java/jdk1.7.0_09/include/" -I "C:/Program Files/Java/jdk1.7.0_09/include/win32/"
	LINKOBJS=nl_tue_fingerpaint_server_simulator_NativeCommunicator.o add_module.o
	LINKOUTPUTNAME=NativeCommunicator.dll
	LINKLIBFLAGS= -static -lgfortranbegin -static -lgfortran
else
	ifeq ($(shell uname), Linux) # Linux
		OS=Linux

		CC=gcc
		FC=gfortran
		LINKFLAGS=-Wall -D_JNI_IMPLEMENTATION_
		LINKFFLAGS=-fPIC -shared -c
		LINKCFLAGS=-fPIC -Wall -D_JNI_IMPLEMENTATION_ -Wl,-soname,NativeCommunicator.so -I "/usr/lib/jvm/java-7-openjdk-amd64/include/" -I "/usr/lib/jvm/java-7-openjdk-amd64/include/linux/"
		LINKOBJS=add_module.o nl_tue_fingerpaint_server_simulator_NativeCommunicator.o
		LINKOUTPUTNAME=libNativeCommunicator.so
		LINKLIBFLAGS=-lgfortran
	endif
endif

all: pre-build main-build

pre-build:
	$(info Detected a $(OS) system...)

main-build: link

link: $(LINKOBJS)
	$(CC) $(LINKFLAGS) -shared $(LINKOBJS) -o $(LINKOUTPUTNAME) $(LINKLIBFLAGS)

add_module.o: add_module.f90
	$(FC) $(LINKFFLAGS) add_module.f90 -o $@
	
nl_tue_fingerpaint_server_simulator_NativeCommunicator.o: nl_tue_fingerpaint_server_simulator_NativeCommunicator.c
	$(CC) $(LINKCFLAGS) -shared nl_tue_fingerpaint_server_simulator_NativeCommunicator.c -c -o $@
	
clean:
	rm *.mod $(LINKOUTPUTNAME) $(LINKOBJS)
