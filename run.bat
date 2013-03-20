@echo off

cd "D:\David\Eclipse Projects\PropertiesAnalyzer\bin"

Java -cp "..\tools.jar;..\aspectjrt.jar;." trace.Trace -all trace.Count

echo.

cmd /k
