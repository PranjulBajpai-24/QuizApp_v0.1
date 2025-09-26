@echo off
REM Batch script for compiling and running the QuizApp application on Windows
REM Created: September 26, 2025

REM Define common variables with absolute paths
set "PROJECT_ROOT=%~dp0"
set "FRONTEND_SRC=%PROJECT_ROOT%Frontend\src"
set "APP_DIR=%FRONTEND_SRC%\quiz\app"
set "LIB_DIR=%PROJECT_ROOT%lib"
set "GSON_JAR=%LIB_DIR%\gson-2.13.2.jar"
set "MAIN_CLASS=quiz.app.Login"

REM Print environment information
echo Build environment:
echo Project root: %PROJECT_ROOT%
echo Gson JAR path: %GSON_JAR%

REM Process command-line arguments
if "%1"=="" goto help
if "%1"=="compile" goto compile
if "%1"=="run" goto run
if "%1"=="clean" goto clean
if "%1"=="build-run" goto build-run

:compile
echo Compiling QuizApp...

REM First make sure we're in the right directory
cd /d "%FRONTEND_SRC%" || (
    echo Failed to change directory to %FRONTEND_SRC%
    exit /b 1
)

REM Use absolute path for the Gson JAR file
echo Running compilation with explicit Gson JAR path
javac -cp "%GSON_JAR%;." quiz/app/*.java

REM Check if compilation was successful
if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    exit /b 0
) else (
    echo Compilation failed!

    REM Try alternative compilation method if the first one fails
    echo Trying alternative compilation method...
    cd /d "%APP_DIR%" || (
        echo Failed to change directory to %APP_DIR%
        exit /b 1
    )
    javac -cp "%GSON_JAR%" *.java

    if %ERRORLEVEL% EQU 0 (
        echo Alternative compilation successful!
        exit /b 0
    ) else (
        echo All compilation attempts failed!
        exit /b 1
    )
)
goto end

:run
echo Running QuizApp...
cd /d "%FRONTEND_SRC%" || (
    echo Failed to change directory to %FRONTEND_SRC%
    exit /b 1
)

REM Run the application with the required classpath
java -cp "%GSON_JAR%;." %MAIN_CLASS%
goto end

:clean
echo Cleaning compiled files...
cd /d "%APP_DIR%" || (
    echo Failed to change directory to %APP_DIR%
    exit /b 1
)

REM Remove all class files
del /q *.class
echo Clean completed!
goto end

:build-run
call :compile
if %ERRORLEVEL% EQU 0 (
    call :run
)
goto end

:help
echo QuizApp Build Script
echo Usage: %0 [command]
echo.
echo Available commands:
echo   compile    - Compile the application
echo   run        - Run the compiled application
echo   clean      - Remove compiled class files
echo   build-run  - Compile and run the application
goto end

:end
exit /b 0
