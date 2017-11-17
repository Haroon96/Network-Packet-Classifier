@echo off
if exist jar ( rd /s /q jar)
mkdir jar
javac -cp src;lib\forms_rt.jar;resources;data src\com\haroon\Main.java -d jar
mkdir jar\resources jar\data jar\lib
xcopy resources jar\resources
xcopy data jar\data
xcopy lib jar\lib
cd jar
start javaw -cp .;lib\forms_rt.jar;resources;data com.haroon.Main
cd ..