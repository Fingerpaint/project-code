#compiler and options
#-------------------------------------------------------------------------------
CC = gcc
FC = gfortran

#compiler options
FLAGS    = -O2
#-------------------------------------------------------------------------------
#make executable without explicit executable name
all : main_rec_cav test


#object files
OBJS =  add_module.o\
	main_rec_cav.o
TESTOBJS = add_module.o\
	test.o

#create object files
add_module.o : add_module.f90
	$(FC) $(FLAGS) -c add_module.f90 -o $@
main_rec_cav.o : main_rec_cav.f90 
	$(FC) $(FLAGS) -c  main_rec_cav.f90 -o $@
test.o : test.c
	$(CC) -c test.c -o $@


#make executable
main_rec_cav: $(OBJS)
	$(FC) $(FLAGS) $(OBJS) -o $@
test: $(TESTOBJS)
	$(CC) $(TESTOBJS) -o $@ -lgfortran


#clean the code
clean:
	rm *.o *.mod main_rec_cav test
#-------------------------------------------------------------------------------

