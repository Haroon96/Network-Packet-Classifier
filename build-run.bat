@echo off
if exist out ( rd /s /q out)
mkdir out
javac -cp src;lib\forms_rt.jar;resources;data src\com\haroon\Main.java -d out
mkdir out\resources out\data out\lib
xcopy resources out\resources
xcopy data out\data
xcopy lib out\lib
cd out
start javaw -cp .;lib\forms_rt.jar;resources;data com.haroon.Main
cd ..
