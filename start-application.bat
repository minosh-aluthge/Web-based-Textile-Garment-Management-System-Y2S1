@echo off
echo ========================================
echo Starting Textile & Garment Management System
echo ========================================
echo.
echo This will start the Spring Boot application...
echo.
echo After starting, you can access:
echo - Web Interface: http://localhost:8080
echo - H2 Database Console: http://localhost:8080/h2-console
echo - API Test: http://localhost:8080/api/sales/test
echo.
echo Press Ctrl+C to stop the application
echo.
pause

REM Try to run with Maven if available
mvn spring-boot:run 2>nul
if %errorlevel% neq 0 (
    echo Maven not found. Please run the application using your IDE or install Maven.
    echo.
    echo Alternative ways to run:
    echo 1. Use your IDE (IntelliJ IDEA, Eclipse, VS Code)
    echo 2. Install Maven and run: mvn spring-boot:run
    echo 3. Use the Maven wrapper: ./mvnw spring-boot:run
    echo.
    pause
)
