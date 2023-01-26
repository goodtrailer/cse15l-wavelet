@echo off

:: Constants
set CLASS_PATHS="./lib/*;./build/"

:: Parsing
set targetName=%1 & if "%1"=="" set targetName="Main"
set /p targetFile=<./target/%targetName%
if "%targetFile%"=="" exit /B

set args=
:loop0
    shift
    if "%1"=="" goto 0pool
    set args=%args% %1
    goto loop0
:0pool

:: Main
javac -cp %CLASS_PATHS% -d ./build/ ./src/*.java
java -cp %CLASS_PATHS%  %targetFile% %args%
