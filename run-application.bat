@echo off
echo ========================================
echo Starting Textile & Garment Management System
echo ========================================
echo.
echo This script will help you start the application.
echo.
echo IMPORTANT: Make sure you have Java 17+ installed!
echo.
echo Choose how to run the application:
echo.
echo 1. Using IDE (Recommended)
echo    - Open your IDE (IntelliJ IDEA, Eclipse, VS Code)
echo    - Navigate to src/main/java/com/example/myprac/MypracApplication.java
echo    - Right-click and select "Run MypracApplication"
echo.
echo 2. Using Maven (if installed)
echo    - Run: mvn spring-boot:run
echo.
echo 3. Using Gradle (if using Gradle)
echo    - Run: ./gradlew bootRun
echo.
echo After starting, access:
echo - Web Interface: http://localhost:8080
echo - API Test: http://localhost:8080/api/sales/test
echo - Database Console: http://localhost:8080/h2-console
echo.
echo Press any key to continue...
pause >nul

REM Try to detect and run with available build tool
echo.
echo Attempting to start the application...
echo.

REM Check for Maven
where mvn >nul 2>&1
if %errorlevel% equ 0 (
    echo Found Maven, starting application...
    mvn spring-boot:run
    goto :end
)

REM Check for Gradle
where gradle >nul 2>&1
if %errorlevel% equ 0 (
    echo Found Gradle, starting application...
    gradle bootRun
    goto :end
)

REM Check for Gradle wrapper
if exist "gradlew.bat" (
    echo Found Gradle wrapper, starting application...
    gradlew.bat bootRun
    goto :end
)

REM Check for Maven wrapper
if exist "mvnw.cmd" (
    echo Found Maven wrapper, starting application...
    mvnw.cmd spring-boot:run
    goto :end
)

echo.
echo No build tool found. Please use your IDE to run the application.
echo.
echo Steps to run in IDE:
echo 1. Open your IDE
echo 2. Open the project folder
echo 3. Navigate to src/main/java/com/example/myprac/MypracApplication.java
echo 4. Right-click and select "Run" or click the green play button
echo.
echo Alternative: Install Maven or Gradle and try again.
echo.

:end
echo.
echo Application startup complete or failed.
echo Check the console output above for any errors.
echo.
pause
