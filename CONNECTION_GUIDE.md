# Frontend-Backend Connection Guide

## 🔗 How to Connect Frontend and Backend

### Step 1: Start the Backend (Spring Boot Application)

#### Option A: Using IDE (Recommended)
1. Open your project in your IDE (IntelliJ IDEA, Eclipse, VS Code)
2. Navigate to `src/main/java/com/example/myprac/MypracApplication.java`
3. Right-click and select "Run MypracApplication" or click the green play button
4. Wait for the application to start (you'll see "Started MypracApplication" in the console)

#### Option B: Using Maven (if installed)
```bash
mvn spring-boot:run
```

#### Option C: Using the batch file
```bash
start-application.bat
```

### Step 2: Verify Backend is Running

1. **Check Console Output**: You should see:
   ```
   Started MypracApplication in X.XXX seconds
   ```

2. **Test Backend Connection**: Open browser and go to:
   - http://localhost:8080/api/sales/test
   - You should see: "Backend is running! Frontend-Backend connection successful!"

3. **Check Health Endpoint**: Go to:
   - http://localhost:8080/api/sales/health
   - You should see JSON response with status "UP"

### Step 3: Access the Frontend

1. **Open the Web Interface**: Go to http://localhost:8080
2. **Check Browser Console**: Press F12 and look for:
   - ✅ "Backend connection successful!" message
   - ❌ Any error messages about connection failures

### Step 4: Verify Full Connection

1. **Test Data Loading**: The application should automatically load:
   - Sample customers
   - Sample products  
   - Sample orders

2. **Test CRUD Operations**:
   - Try adding a new customer
   - Try creating a new order
   - Try editing an existing order

## 🛠️ Troubleshooting

### Problem: "Cannot connect to backend" error

**Solution:**
1. Make sure Spring Boot application is running
2. Check if port 8080 is available
3. Verify the backend is accessible at http://localhost:8080/api/sales/test

### Problem: CORS errors in browser console

**Solution:**
1. The application includes CORS configuration
2. If still having issues, check browser developer tools
3. Make sure you're accessing via http://localhost:8080 (not file://)

### Problem: Data not loading

**Solution:**
1. Check browser network tab for failed requests
2. Verify backend is running and accessible
3. Check console for JavaScript errors

### Problem: Maven not found

**Solution:**
1. Install Maven or use your IDE to run the application
2. Alternative: Use the Maven wrapper (./mvnw) if available
3. Or simply use your IDE's run configuration

## 🔍 Connection Verification Checklist

- [ ] Spring Boot application starts without errors
- [ ] http://localhost:8080/api/sales/test returns success message
- [ ] http://localhost:8080 loads the web interface
- [ ] Browser console shows "Backend connection successful!"
- [ ] Data loads automatically (customers, products, orders)
- [ ] Can create new customers and orders
- [ ] Can edit and delete existing records

## 📱 Testing the Full System

### 1. Customer Management
- Go to "Customers" tab
- Click "Add New Customer"
- Fill in customer details
- Submit and verify customer appears in table

### 2. Order Management  
- Go to "Orders" tab
- Click "Add New Order"
- Select customer and add products
- Submit and verify order appears in table

### 3. Product Catalog
- Go to "Products" tab
- Verify products are loaded
- Test search and filtering

### 4. Reports
- Go to "Reports" tab
- Verify metrics are displayed
- Check that numbers update correctly

## 🚀 Quick Start Commands

```bash
# Start the application (if Maven is installed)
mvn spring-boot:run

# Or use the batch file
start-application.bat

# Access the application
# Web Interface: http://localhost:8080
# API Test: http://localhost:8080/api/sales/test
# Database: http://localhost:8080/h2-console
```

## 📞 Support

If you're still having connection issues:

1. **Check the console output** for any error messages
2. **Verify port 8080** is not being used by another application
3. **Try a different port** by adding `server.port=8081` to application.properties
4. **Check firewall settings** if running on a network
5. **Use browser developer tools** to see detailed error messages

The system is designed to work seamlessly once the Spring Boot application is running!
