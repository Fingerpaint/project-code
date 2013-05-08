#compiler and options
#-------------------------------------------------------------------------------
#Open MPI -> Intel Fortran compiler
FC = ifort

#compiler options
FLAGS    = -O2 -fpe0
#-------------------------------------------------------------------------------
#make executable without explicit executable name
all : main_rec_cav 


#object files
OBJS =  add_module.o\
	main_rec_cav.o

#create object files
add_module.o : add_module.f90
	$(FC) $(FLAGS) -c add_module.f90 -o $@
main_rec_cav.o : main_rec_cav.f90 
	$(FC) $(FLAGS) -c  main_rec_cav.f90 -o $@



#make executable
main_rec_cav: $(OBJS)
	$(FC) $(FLAGS) $(OBJS) -o $@


#clean the code
clean:
	rm *.o *.mod main_rec_cav
#-------------------------------------------------------------------------------

