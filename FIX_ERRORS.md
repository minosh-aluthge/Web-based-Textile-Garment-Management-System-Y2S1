# 🔧 Fix Application Errors

## 🚨 Current Errors You're Seeing:

1. **"Backend not available"** - Red status indicator
2. **"HTTP 404: Not Found"** - API endpoints not found
3. **"Error loading orders - Check if backend is running"** - No data loading

## ✅ Step-by-Step Fix:

### Step 1: Start the Backend Application

#### Option A: Using IDE (Easiest)
1. **Open your IDE** (IntelliJ IDEA, Eclipse, VS Code)
2. **Open the project folder** `C:\Users\Jana\Desktop\se practise\myprac`
3. **Navigate to**: `src/main/java/com/example/myprac/MypracApplication.java`
4. **Right-click** on the file
5. **Select "Run MypracApplication"** or click the green play button ▶️
6. **Wait for startup** - you should see:
   ```
   Started MypracApplication in X.XXX seconds
   ```

#### Option B: Using Command Line
```bash
# If Maven is installed
mvn spring-boot:run

# Or use the batch file
run-application.bat
```

### Step 2: Verify Backend is Running

1. **Check Console Output** - Look for:
   ```
   Started MypracApplication in X.XXX seconds
   ```

2. **Test Backend Connection**:
   - Open browser
   - Go to: http://localhost:8080/api/sales/test
   - You should see: "Backend is running! Frontend-Backend connection successful!"

3. **Check Health Endpoint**:
   - Go to: http://localhost:8080/api/sales/health
   - You should see JSON response with status "UP"

### Step 3: Access the Web Interface

1. **Open**: http://localhost:8080
2. **Look for**: Green "Connected to backend" status
3. **Verify**: Data loads automatically (customers, products, orders)

## 🛠️ Common Issues and Solutions:

### Issue 1: "Port 8080 already in use"
**Solution:**
1. Close other applications using port 8080
2. Or change port in `src/main/resources/application.properties`:
   ```properties
   server.port=8081
   ```
3. Then access: http://localhost:8081

### Issue 2: "Java not found"
**Solution:**
1. Install Java 17 or higher
2. Set JAVA_HOME environment variable
3. Add Java to PATH

### Issue 3: "Maven not found"
**Solution:**
1. Use your IDE to run the application (recommended)
2. Or install Maven
3. Or use the Maven wrapper if available

### Issue 4: "Compilation errors"
**Solution:**
1. Clean and rebuild the project in your IDE
2. Check for any red error indicators
3. Fix any syntax errors

## 🔍 Verification Checklist:

- [ ] Spring Boot application starts without errors
- [ ] Console shows "Started MypracApplication"
- [ ] http://localhost:8080/api/sales/test returns success message
- [ ] http://localhost:8080 loads the web interface
- [ ] Green "Connected to backend" status appears
- [ ] Sample data loads (customers, products, orders)
- [ ] All tabs work (Orders, Customers, Products, Reports)

## 🚀 Quick Start Commands:

```bash
# Start the application
mvn spring-boot:run

# Or use the batch file
run-application.bat

# Test endpoints
curl http://localhost:8080/api/sales/test
curl http://localhost:8080/api/sales/health
```

## 📱 Testing the Application:

### 1. **Test Connection**
- Open http://localhost:8080
- Look for green "Connected to backend" status
- Check browser console for success messages

### 2. **Test Data Loading**
- Go to "Orders" tab
- Verify orders are displayed
- Go to "Customers" tab
- Verify customers are displayed
- Go to "Products" tab
- Verify products are displayed

### 3. **Test CRUD Operations**
- Try adding a new customer
- Try creating a new order
- Try editing an existing order
- Try deleting an order

## 🆘 If Still Having Issues:

1. **Check the console output** for any error messages
2. **Verify Java version** (should be 17+)
3. **Check if port 8080 is available**
4. **Try a different port** in application.properties
5. **Use your IDE** instead of command line
6. **Check firewall settings**

## 📞 Quick Fixes:

### Fix 1: Change Port
Add to `src/main/resources/application.properties`:
```properties
server.port=8081
```
Then access: http://localhost:8081

### Fix 2: Disable Data Initialization
Comment out the DataInitializer in `MypracApplication.java`:
```java
// @Component
public class DataInitializer implements CommandLineRunner {
```

### Fix 3: Check Dependencies
Make sure all dependencies are downloaded in your IDE.

---

## 🎯 **The Main Issue: Backend Not Running**

The errors you're seeing are because **the Spring Boot application is not running**. Once you start it using your IDE, all the errors will disappear and you'll see:

- ✅ Green "Connected to backend" status
- ✅ Sample data loading automatically
- ✅ Sample data loading
- ✅ ✅ All CRUD operations working perfectly

**Just start the Spring Boot application and everything will work!** 🚀
