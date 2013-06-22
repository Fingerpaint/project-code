#!/bin/bash

OUT_FILE=javadoc.pdf

./gen-pdf-javadoc-pdfdoclet.sh ../fingerpaint/src/ $OUT_FILE nl.tue.fingerpaint
cp $OUT_FILE ../../project-docs/DDD/javadoc.pdf >/dev/null 2>&1

if [ $? -eq 0 ] ; then
	read -p "Copied generated Javadoc to project-docs. Delete copy in this directory (Y/n)? "
	[[ $REPLY != [nN] ]] && rm $OUT_FILE
fi
