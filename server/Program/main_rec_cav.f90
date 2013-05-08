!_________________________________________________________________________
!main program
!_________________________________________________________________________
program rec_cavity

use subs_m

!input parameters
real(8) :: dt      ! displacement
integer :: dir,  & ! direction of the displacement, dir=1 to the right
                   !                                dir=-1 to the left
           wall    ! moving wal top or bottom, wall=1 - top wall
                   !                           wall=-1 - bottom wall
! concentration vector
real(8), allocatable :: conc(:)
! intensity of segregation
real(8) :: segr 


!---------------------------------
! set parameters
dt=19.75   ! set displacement
dir=-1   ! set direction 
wall=-1  ! set wall


!---------------------------------
! allocate memory
  ! read size of the mesh from the file 
        open(unit=10,form='unformatted',file='map_10')
           read (10) nc
        close(10)
  ! for fixed resolution (in out case of cavity flow)
  ! nc=400*240 
    allocate (conc(nc))

!---------------------------------
!read initial concentration vector 
open(unit=11,form='unformatted',file='conc_init')
  read (11) conc
close(11) 


!---------------------------------
! compute concentration
  call conc_final(dt, dir, wall, conc)


!---------------------------------
! compute intensity of segregation
   call intens_segr(conc, segr)
 

!-----------------------------------
!results 

write(*,*) 'intensity of segregation', segr

! write concentration vector into file
open(12, file='concentration.dat')
write(12,300) conc
close(12)

300  format (1x,f9.6)

end program rec_cavity
