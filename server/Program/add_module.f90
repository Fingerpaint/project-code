! Module for aditional subroutines:
!   mapping matrix type
!   matrix-vector multiplication
!_________________________________________________________________________

module subs_m

  implicit none
!_____________________________________________
! special type for Mapping Matrix sparse format
 type sparsematrix_t
    integer :: n   ! rows/columns of the matrix
    integer :: nnz ! number of non-zeros
    ! the non-zero values:
    real(8), allocatable, dimension(:) :: a
    ! the row numbers:
    integer, allocatable, dimension(:) :: ia
    ! column number:
    integer, allocatable, dimension(:) :: ja
 end type


contains
!______________________________________________
! function for matrix-vector multiplication
 function smatvec ( A, x ) result(b)

    type ( sparsematrix_t ), intent(in) :: A ! matrix in CSR format
    real(8), dimension(:), intent(in) :: x  ! vector for multiplication
    real(8), dimension(A%n) :: b            ! result 
    ! counters and local variables
    integer :: i, j
    real(8) :: tmp
    
    do i = 1, A%n
      tmp = 0
      do j = A%ia(i), A%ia(i+1) - 1
        tmp = tmp + A%a(j) * x(A%ja(j))
      end do
      b(i) = tmp
    end do

  end function smatvec

!______________________________________________
! divide total displacement on parts
 subroutine disp_part ( d, t4, t2, t1, t05, t025 ) 
 ! input/output
 real(8), intent(in) :: d ! displacement
 integer, intent (out):: t4, t2, t1, t05, t025 
! ! variables
 real(8):: m

 t4 = int( floor(d/4.0) )
 m=d-4.0*real(t4)
 t2 = int( floor( m/2.0 ) )
 m=m-2.0*real(t2)
 t1 = int( floor(m/1.0) )
 m=m-1.0*real(t1)
 t05 = int( floor(m/0.5) )
 m=m-0.5*real(t05)
 t025 = int( floor(m/0.25) )

 end subroutine disp_part





!______________________________________________
!subroutine to read the matrix according to the chosen timestep/flow direction/wall
subroutine read_matrix(dt, map_mat)

integer, intent(in) :: dt         ! displacement, can vary: 0.25, 0.5, 1.0, 2.0, 4.0
type(sparsematrix_t), intent(inout) :: map_mat ! mapping matrix
character(len=40) :: datfile



         write(datfile, 101) dt
         open(unit=10,form='unformatted',file=datfile)
         read (10) map_mat%n, map_mat%nnz
         ! allocate memory to read mapping matrix from file
         allocate(map_mat%a(map_mat%nnz), map_mat%ia(map_mat%n+1), map_mat%ja(map_mat%nnz))
         ! read mapping matrix
         read (10) map_mat%ia
         read (10) map_mat%ja
         read (10) map_mat%a
         close(10)


 101 format('map_',I2.2)

end subroutine read_matrix






!______________________________________________
! intensity of segregation
subroutine intens_segr(conc,segr )
     real(8) , dimension(:), intent(in) :: conc  ! concentration vector
     real(8), intent(out) :: segr                ! intensity of segregation

     real(8) :: c_aver    ! average concentration in flow domain
     integer:: n,i         ! counters
     real(8) :: ii,a
     !size of the concentration vector
     n= size(conc)
     !average concentration
     c_aver= (1/real(n))* sum(conc)
     
       a=0.0
     do i=1,n
       a=a+(conc(i)-c_aver)**2
     enddo
     
     ii=( (1.0/real(n))*a ) /(c_aver*(1.0-c_aver))
     segr=ii

end subroutine intens_segr



!______________________________________________
! concentration vector after displacement
subroutine conc_multip (dt, conc)
     ! in\out parameters
     real(8) , dimension(:), intent(inout) :: conc  ! concentration vector
     real(8) , intent(in) :: dt ! displacement
     ! local variables
     integer :: t4, t2, t1, t05, t025
     type(sparsematrix_t) :: map_mat025, map_mat05, map_mat1, map_mat2, map_mat4
     integer :: i

   ! divide displacement on parts   
   call disp_part ( dt , t4, t2, t1, t05, t025 )
     
   ! read matrices for non-zero displacement part
       if (t4>0)    call read_matrix(40, map_mat4)
       if (t2>0)    call read_matrix(20, map_mat2)
       if (t1>0)    call read_matrix(10, map_mat1)
       if (t05>0)   call read_matrix(05, map_mat05)
       if (t025>0)  call read_matrix(25, map_mat025)
!_______________________________________________________________

   ! compute concentration after one time step
    if (t4>0) then
      do i=1,t4
          conc = smatvec( map_mat4, conc)
      enddo
   endif

   if (t2>0) then
     do i=1,t2
         conc = smatvec( map_mat2, conc)
     enddo
   endif

   if (t1>0) then
     do i=1,t1
         conc = smatvec( map_mat1, conc)
     enddo
   endif

  if (t05>0) then
     do i=1,t05
         conc = smatvec( map_mat05, conc)
     enddo
   endif

  if (t025>0) then
     do i=1,t025
         conc = smatvec( map_mat025, conc)
     enddo
   endif

end subroutine conc_multip


!______________________________________________
! concentration vector after displacement
subroutine conc_final (dt, dir, wall, conc)
     ! in\out parameters
     real(8) , dimension(:), intent(inout) :: conc  ! concentration vector
     real(8) , intent(in) :: dt ! displacement
     integer , intent(in) :: dir, wall ! parameters of the problem
  
     ! top wall
     if (wall==1) then
        ! motion to the right  
        if (dir==1) then
          call conc_multip (dt, conc)
        ! motion to the left
        elseif (dir==-1) then
          call flip_ver(conc)
          call conc_multip (dt, conc)
          call flip_ver(conc)
        endif
     ! bottom wall
     elseif (wall==-1) then
       ! motion to the right
        if (dir==1) then
          call flip_hor(conc)
          call conc_multip (dt, conc)
          call flip_hor(conc)
        ! motion to the left
        elseif (dir==-1) then
          call flip_ver(conc)
             call flip_hor(conc)
             call conc_multip (dt, conc)
             call flip_hor(conc)
          call flip_ver(conc)
        endif

     endif

end subroutine conc_final


!_____________________________________________
subroutine flip_hor (conc)
    real(8), dimension(:), intent(inout) :: conc 
    real(8), allocatable :: temp(:)
    integer :: nx, ny, nc ! size of the domain
    integer :: i, j, k
     nx=400
     ny=240 
     nc=400*240
  allocate ( temp(nx*ny) )
     do i= 1,ny
        do j= 1,nx
           k=j+(i-1)*nx
           temp(k)=conc(nc-nx*i+j)
        enddo
     enddo
   conc=temp
  deallocate ( temp )
end subroutine flip_hor


!_____________________________________________
subroutine flip_ver (conc)
    real(8), dimension(:), intent(inout) :: conc
    real(8), allocatable :: temp(:)
    integer :: nx, ny, nc ! size of the domain
    integer :: i, j, k
     nx=400
     ny=240
     nc=400*240
  allocate ( temp(nx*ny) )
     do i= 1,ny
        do j= 1,nx
           k=j+(i-1)*nx
           temp(k)=conc(nx*i-j)
        enddo
     enddo
   conc=temp
  deallocate ( temp )
end subroutine flip_ver

!______________________________________________
! main function called to run an arbitrary simulation
subroutine simulate (geometry, matrix, distribution, 
                     step, stepid, segregation)
    ! in\out parameters
    integer , intent(in) :: geometry ! Geometry selector
    integer , intent(in) :: matrix ! Matrix selector
    real(8) , dimension(:), intent(inout) :: distribution ! concentration vector
    real(8) , intent(in) :: step ! size of the step
    integer , intent(in) :: stepid ! the id of the step-type that needs to be made
    real(8) , intent(out) :: segregation ! the segregation
    
    if (geometry==0) then
        if (stepid==0) then
            call conc_final(step,1,1,distribution)
        elseif (stepid==1) then
            call conc_final(step,1,0,distribution)
        elseif (stepid==2) then
            call conc_final(step,0,1,distribution)
        elseif (stepid==3) then
            call conc_final(step,0,0,distribution)
        endif
    endif
    
end subroutine simulate


end module subs_m

