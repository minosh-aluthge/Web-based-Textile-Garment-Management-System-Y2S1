# Textile & Garment Management System - Sales & Order Executive Module

## 🎯 Overview

This is a comprehensive **Sales & Order Management System** designed specifically for the Sales & Order Executive role in a Textile & Garment Management System. The system provides a modern, professional web interface for managing customer orders, customers, and products with full CRUD operations.

## ✨ Features

### 🛒 Order Management
- **Add new customer orders** with detailed requirements
- **View current and past order lists** for tracking and reporting
- **Edit order details** to accommodate customer changes
- **Delete duplicate or incorrect orders** to avoid confusion
- Advanced filtering and search capabilities
- Real-time status tracking

### 👥 Customer Management
- Complete customer database management
- Customer profile creation and editing
- Customer order history tracking
- Contact information management

### 📦 Product Catalog
- Product database with categories
- Price management
- Product availability tracking
- Category-based filtering

### 📊 Reports & Analytics
- Order statistics dashboard
- Revenue tracking
- Customer analytics
- Performance metrics

## 🏗️ Architecture

### Backend (Spring Boot)
- **Framework**: Spring Boot 3.5.6
- **Database**: H2 (in-memory for development)
- **ORM**: JPA/Hibernate with Jakarta EE
- **API**: RESTful web services
- **Security**: CORS enabled for cross-origin requests

### Frontend (Modern Web)
- **HTML5**: Semantic markup
- **CSS3**: Modern styling with gradients and animations
- **JavaScript**: ES6+ with async/await
- **Icons**: Font Awesome 6.0
- **Responsive**: Mobile-first design

### Database Schema
Based on the provided ERD, the system includes:
- **Customer** entity with user information
- **Product** entity with product details
- **Order** entity with order information
- **OrderItem** entity for order line items
- **Inventory** entity for stock management

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- Modern web browser

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd myprac
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

3. **Access the application**
   - Web Interface: http://localhost:8080
   - H2 Database Console: http://localhost:8080/h2-console
   - API Documentation: http://localhost:8080/api/sales

### Sample Data
The application automatically initializes with sample data including:
- 5 sample customers
- 8 sample products across different categories
- 3 sample orders with order items
- Inventory data for all products

## 📱 User Interface

### Dashboard Features
- **Modern Design**: Clean, professional interface with gradient backgrounds
- **Responsive Layout**: Works on desktop, tablet, and mobile devices
- **Tab Navigation**: Easy switching between Orders, Customers, Products, and Reports
- **Real-time Updates**: Live data loading and updates
- **Interactive Modals**: Smooth modal dialogs for forms
- **Status Badges**: Color-coded status indicators
- **Action Buttons**: Intuitive edit and delete operations

### Key UI Components
- **Navigation Tabs**: Orders, Customers, Products, Reports
- **Data Tables**: Sortable, filterable data display
- **Search & Filter**: Advanced search capabilities
- **Modal Forms**: Professional form dialogs
- **Status Indicators**: Visual status representation
- **Responsive Grid**: Adaptive layout for all screen sizes

## 🔧 API Endpoints

### Order Management
- `GET /api/sales/orders` - Get all orders
- `GET /api/sales/orders/{id}` - Get order by ID
- `POST /api/sales/orders` - Create new order
- `PUT /api/sales/orders/{id}` - Update order
- `DELETE /api/sales/orders/{id}` - Delete order
- `GET /api/sales/orders/status/{status}` - Filter by status
- `GET /api/sales/orders/search?q={term}` - Search orders

### Customer Management
- `GET /api/sales/customers` - Get all customers
- `GET /api/sales/customers/{id}` - Get customer by ID
- `POST /api/sales/customers` - Create new customer

### Product Management
- `GET /api/sales/products` - Get all products
- `GET /api/sales/products/{id}` - Get product by ID
- `GET /api/sales/products/category/{category}` - Filter by category

## 🎨 Design System

### Color Palette
- **Primary**: Blue gradient (#3498db to #2980b9)
- **Secondary**: Gray gradient (#95a5a6 to #7f8c8d)
- **Success**: Green (#27ae60)
- **Warning**: Orange (#f39c12)
- **Danger**: Red (#e74c3c)
- **Background**: Purple gradient (#667eea to #764ba2)

### Typography
- **Font Family**: Segoe UI, Tahoma, Geneva, Verdana, sans-serif
- **Headings**: Bold, uppercase with letter spacing
- **Body Text**: Clean, readable font sizes
- **Icons**: Font Awesome 6.0

### Components
- **Buttons**: Gradient backgrounds with hover effects
- **Forms**: Clean input fields with focus states
- **Tables**: Professional data display with hover effects
- **Modals**: Smooth animations and backdrop blur
- **Cards**: Gradient backgrounds with shadow effects

## 📊 Business Logic

### Order Processing
1. **Customer Selection**: Choose from existing customers
2. **Product Selection**: Add multiple products with quantities
3. **Order Details**: Subject, description, delivery date
4. **Automatic Calculation**: Total amount calculation
5. **Status Management**: Track order through lifecycle
6. **Inventory Integration**: Stock level checking

### Data Validation
- Required field validation
- Email format validation
- Numeric input validation
- Date range validation
- Business rule validation

## 🔒 Security Features

- **CORS Configuration**: Cross-origin request handling
- **Input Validation**: Server-side validation
- **Error Handling**: Comprehensive error management
- **Data Sanitization**: Clean data processing

## 📈 Performance Features

- **Lazy Loading**: Efficient data loading
- **Caching**: Repository-level caching
- **Optimized Queries**: Efficient database queries
- **Responsive Design**: Fast loading on all devices
- **Async Operations**: Non-blocking UI operations

## 🛠️ Development

### Project Structure
```
src/
├── main/
│   ├── java/com/example/myprac/garmentsystem/
│   │   ├── controller/     # REST controllers
│   │   ├── model/          # Entity models
│   │   ├── repository/     # Data repositories
│   │   ├── service/        # Business logic
│   │   └── config/         # Configuration
│   └── resources/
│       ├── static/         # Frontend assets
│       │   ├── css/        # Stylesheets
│       │   ├── js/         # JavaScript
│       │   └── index.html  # Main page
│       └── application.properties
```

### Key Technologies
- **Spring Boot**: Application framework
- **Spring Data JPA**: Data access layer
- **H2 Database**: In-memory database
- **Jakarta EE**: Enterprise Java standards
- **Maven**: Build and dependency management

## 🎯 Use Cases Implemented

Based on the provided use case diagram, this system implements:

1. **Add Customer Order** ✅
   - Create new orders with detailed requirements
   - Multiple product selection
   - Customer information integration

2. **View Orders** ✅
   - Current and past order lists
   - Advanced filtering and search
   - Real-time data updates

3. **Edit Orders** ✅
   - Update order details
   - Status management
   - Customer change accommodation

4. **Delete Incorrect Orders** ✅
   - Remove duplicate orders
   - Clean up incorrect entries
   - Data integrity maintenance

## 🚀 Future Enhancements

- **Email Notifications**: Order status updates
- **PDF Generation**: Order invoices and reports
- **Advanced Analytics**: Business intelligence
- **Mobile App**: Native mobile application
- **Integration**: ERP system integration
- **Multi-language**: Internationalization support

## 📞 Support

For technical support or questions about the system:
- Check the application logs for error details
- Use the H2 console for database inspection
- Review the API endpoints for integration
- Consult the code documentation for customization

---

**Built with ❤️ for the Textile & Garment Management System**

*This system provides a complete solution for Sales & Order Executive operations with modern web technologies and professional user experience.*

## Run without a domain (LAN access)

1) Build and run with prod profile

```
mvn -DskipTests package
java -jar -Dspring.profiles.active=prod target/myprac-0.0.1-SNAPSHOT.jar
```

2) Open from same PC

```
http://localhost:8080/login.html
```

3) Open from another device in the same network

- Ensure the app binds to all interfaces (set in `application-prod.properties`: `server.address=0.0.0.0`).
- Find your PC IPv4 address (e.g., `192.168.1.50`).
- From phone/other PC: `http://<YOUR_PC_IP>:8080/login.html`
- Allow Java in Windows Defender Firewall (Private network).

4) Temporary public URL (optional)

- Install ngrok and run: `ngrok http 8080`. Use the generated HTTPS URL.