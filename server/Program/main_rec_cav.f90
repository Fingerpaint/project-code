!_________________________________________________________________________
!main program
!_________________________________________________________________________
program rec_cavity

use subs_m

!input parameters
real(8) :: dt      ! displacement
! concentration vector
real(8), allocatable :: conc(:)
! intensity of segregation
real(8) :: segr
! id for calling the simulate
character(24) :: stepid ! in case of the rectangular geometry, the stepid can be:
                        ! "TL", "TR", "BL" or "BR"


!---------------------------------
! set parameters
dt=19.75   ! set displacement
stepid="TL"


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
!   call conc_final(dt, dir, wall, conc)
  call simulate(0, 0, conc, dt, stepid, segr)


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
