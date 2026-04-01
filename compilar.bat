@echo off
REM Script para compilar e executar o projeto RMI

echo.
echo === Compilando arquivos Java ===
echo.

cd /d "%~dp0"

REM Compilar todos os arquivos .java
javac -d bin src\*.java

if errorlevel 1 (
    echo Erro na compilacao!
    pause
    exit /b 1
)

echo.
echo === Compilacao concluida com sucesso! ===
echo.
echo Arquivos compilados em: bin\
echo.
echo Para executar:
echo 1. Em um terminal: java -cp bin TodoListServer
echo 2. Em outro terminal: java -cp bin TodoListClient
echo.
pause
