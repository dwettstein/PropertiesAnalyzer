@echo off

cd "D:\David\Eclipse Projects\PropertiesAnalyzer\bin"

Java -cp "..\tools.jar;..\aspectjrt.jar;." props.Trace -all props.Count

echo.

cmd /k
