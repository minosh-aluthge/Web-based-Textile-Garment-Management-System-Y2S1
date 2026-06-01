@echo off
echo ========================================
echo Testing Textile & Garment Management System
echo ========================================
echo.
echo This script will test if the application is running correctly.
echo.
echo Make sure the Spring Boot application is running first!
echo.
pause

echo.
echo Testing API endpoints...
echo.

echo 1. Testing connection endpoint...
curl -s http://localhost:8080/api/sales/test
if %errorlevel% equ 0 (
    echo ✅ Connection test passed
) else (
    echo ❌ Connection test failed - Make sure the application is running
)

echo.
echo 2. Testing health endpoint...
curl -s http://localhost:8080/api/sales/health
if %errorlevel% equ 0 (
    echo ✅ Health check passed
) else (
    echo ❌ Health check failed
)

echo.
echo 3. Testing orders endpoint...
curl -s http://localhost:8080/api/sales/orders
if %errorlevel% equ 0 (
    echo ✅ Orders endpoint working
) else (
    echo ❌ Orders endpoint failed
)

echo.
echo 4. Testing customers endpoint...
curl -s http://localhost:8080/api/sales/customers
if %errorlevel% equ 0 (
    echo ✅ Customers endpoint working
) else (
    echo ❌ Customers endpoint failed
)

echo.
echo 5. Testing products endpoint...
curl -s http://localhost:8080/api/sales/products
if %errorlevel% equ 0 (
    echo ✅ Products endpoint working
) else (
    echo ❌ Products endpoint failed
)

echo.
echo ========================================
echo Test Complete
echo ========================================
echo.
echo If all tests passed, your application is working correctly!
echo.
echo Access your application at: http://localhost:8080
echo.
pause
