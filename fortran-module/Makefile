SRC=src
FC=gfortran

ifeq ($(OS), Windows_NT) # if on Windows
	LINKFFLAGS=-c
else
	ifeq ($(shell uname), Linux) # Linux
		LINKFFLAGS=-fPIC -shared -c
	endif
endif

all: add_module.o

add_module.o: ${SRC}/add_module.f90
	$(FC) $(LINKFFLAGS) ${SRC}/add_module.f90 -o $@
	
clean:
	rm add_module.o