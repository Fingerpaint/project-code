clear all

load concentration.dat
q=concentration;

w=reshape(q,400,240);
surfc(w);
view(2);

shading interp

axis off

imrotate(w,90);

