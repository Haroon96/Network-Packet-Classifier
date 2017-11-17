#!/bin/sh

rm -r out
mkdir out
cp -r lib out
cp -r resources out
cp -r data out
javac -cp src:lib/forms_rt.jar:resources:data src/com/haroon/Main.java -d out
cd out
java -cp .:lib/forms_rt.jar:resources:data com.haroon.Main
cd ..
