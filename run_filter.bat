@echo off

cd D:\David\Eclipse Projects\PropertiesAnalyzer\bin\trace

prompt $g

Java -cp "D:\David\Eclipse Projects\PropertiesAnalyzer\tools.jar;D:\David\Eclipse Projects\PropertiesAnalyzer\bin\trace" trace.Trace trace.Count

echo.

cmd /k

