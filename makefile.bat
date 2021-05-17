@echo off
rem cleanup prior build dir
if exist build\ (rd /s /q build)

rem create new build dir
md build
md build\resources
md build\data
md build\lib

rem compile app
javac -cp src;lib\forms_rt.jar;resources;data src\com\haroon\Main.java -d build

rem copy dependencies
xcopy /e resources build\resources
xcopy /e data build\data
xcopy /e lib build\lib

rem create start script
echo java -cp .;lib/forms_rt.jar;resources;data com.haroon.Main > build\start.bat
