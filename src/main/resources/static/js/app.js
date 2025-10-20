// Global variables
let currentTab = 'orders';
let orders = [];
let customers = [];
let products = [];

// API base helper: if not running from Spring Boot (port 8080), target the backend at localhost:8080
const API_BASE = window.API_BASE || (location.port === '8080' ? '' : 'http://localhost:8080');
function apiFetch(url, options) {
    const fullUrl = url.startsWith('http') ? url : `${API_BASE}${url}`;
    return fetch(fullUrl, options);
}

// Create Product Modal (dynamic)
function createAddProductModal() {
    const modalHTML = `
        <div id="addProductModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h3><i class="fas fa-plus"></i> Add Product</h3>
                    <span class="close" onclick="closeModal('addProductModal')">&times;</span>
                </div>
                <div class="modal-body">
                    <form id="addProductForm">
                        <div class="form-group">
                            <label for="newProductName">Product Name</label>
                            <input type="text" id="newProductName" required>
                        </div>
                        <div class="form-group">
                            <label for="newProductCategory">Category</label>
                            <select id="newProductCategory" required>
                                <option value="">Select Category</option>
                                <option value="T-Shirts">T-Shirts</option>
                                <option value="Dresses">Dresses</option>
                                <option value="Pants">Pants</option>
                                <option value="Jackets">Jackets</option>
                                <option value="Accessories">Accessories</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="newProductDescription">Description</label>
                            <textarea id="newProductDescription" rows="3"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="newProductPrice">Standard Price ($)</label>
                            <input type="number" id="newProductPrice" step="0.01" min="0" required>
                        </div>
                        <hr>
                        <div class="form-group">
                            <label for="newSizeRange">Size Range</label>
                            <input type="text" id="newSizeRange" placeholder="e.g., XS-XL or 28-36">
                        </div>
                        <div class="form-group">
                            <label for="newFabricType">Fabric Type</label>
                            <input type="text" id="newFabricType" placeholder="e.g., Cotton, Polyester">
                        </div>
                        <div class="form-group">
                            <label for="newColorOptions">Colour Options</label>
                            <input type="text" id="newColorOptions" placeholder="e.g., Red, Blue, Black">
                        </div>
                        <div class="form-group">
                            <label for="newCareInstructions">Care Instructions</label>
                            <textarea id="newCareInstructions" rows="3" placeholder="Wash cold, Do not bleach..."></textarea>
                        </div>
                        <div class="form-group">
                            <label for="newAccessories">Product Accessories</label>
                            <input type="text" id="newAccessories" placeholder="e.g., Belt, Buttons">
                        </div>
                        <div class="form-group">
                            <label for="newMaterial">Material</label>
                            <input type="text" id="newMaterial" placeholder="e.g., 100% Cotton">
                        </div>
                        <div class="form-group">
                            <label for="newWeight">Weight</label>
                            <input type="number" id="newWeight" step="0.01" min="0" placeholder="e.g., 0.45">
                        </div>
                        <div class="form-group">
                            <label for="newDimensions">Dimensions</label>
                            <input type="text" id="newDimensions" placeholder="e.g., LxWxH or notes">
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" onclick="closeModal('addProductModal')">Cancel</button>
                            <button type="submit" class="btn btn-primary">Create Product</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    `;
    document.body.insertAdjacentHTML('beforeend', modalHTML);
    // Attach submit handler
    document.getElementById('addProductForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        await createProduct();
    });
}

function showAddProductModal() {
    if (!document.getElementById('addProductModal')) {
        createAddProductModal();
    }
    // reset fields
    const ids = ['newProductName','newProductCategory','newProductDescription','newProductPrice','newSizeRange','newFabricType','newColorOptions','newCareInstructions','newAccessories','newMaterial','newWeight','newDimensions'];
    ids.forEach(id => { const el = document.getElementById(id); if (el) el.value = ''; });
    document.getElementById('addProductModal').style.display = 'block';
}

async function createProduct() {
    try {
        const priceVal = document.getElementById('newProductPrice').value;
        const data = {
            name: document.getElementById('newProductName').value,
            category: document.getElementById('newProductCategory').value,
            description: document.getElementById('newProductDescription').value,
            standardPrice: priceVal ? parseFloat(priceVal) : 0,
            sizeRange: (document.getElementById('newSizeRange').value || '').trim() || null,
            fabricType: (document.getElementById('newFabricType').value || '').trim() || null,
            colorOptions: (document.getElementById('newColorOptions').value || '').trim() || null,
            careInstructions: (document.getElementById('newCareInstructions').value || '').trim() || null,
            accessories: (document.getElementById('newAccessories').value || '').trim() || null,
            material: (document.getElementById('newMaterial').value || '').trim() || null,
            weight: (() => { const v = document.getElementById('newWeight').value; return v ? parseFloat(v) : null; })(),
            dimensions: (document.getElementById('newDimensions').value || '').trim() || null
        };

        const response = await apiFetch('/api/sales/products', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showNotification('Product created successfully!', 'success');
            closeModal('addProductModal');
            loadProducts();
        } else {
            const error = await response.text();
            throw new Error(error);
        }
    } catch (err) {
        console.error('Error creating product:', err);
        showNotification('Error creating product: ' + err.message, 'error');
    }
}

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    initializeTabs();
    testBackendConnection();
    loadInitialData();
    setupEventListeners();
});

// Tab Management
function initializeTabs() {
    const navTabs = document.querySelectorAll('.nav-tab');
    const tabContents = document.querySelectorAll('.tab-content');

    navTabs.forEach(tab => {
        tab.addEventListener('click', function() {
            const targetTab = this.getAttribute('data-tab');
            switchTab(targetTab);
        });
    });
}

function switchTab(tabName) {
    // Update active tab
    document.querySelectorAll('.nav-tab').forEach(tab => {
        tab.classList.remove('active');
    });
    document.querySelector(`[data-tab="${tabName}"]`).classList.add('active');

    // Update active content
    document.querySelectorAll('.tab-content').forEach(content => {
        content.classList.remove('active');
    });
    document.getElementById(tabName).classList.add('active');

    currentTab = tabName;

    // Load data for the active tab
    switch(tabName) {
        case 'orders':
            loadOrders();
            break;
        case 'customers':
            loadCustomers();
            break;
        case 'products':
            loadProducts();
            break;
        case 'reports':
            loadReports();
            break;
    }
}

// Backend Connection Test
async function testBackendConnection() {
    updateConnectionStatus('checking', 'Checking connection...');
    
    try {
        const response = await apiFetch('/api/sales/test');
        if (response.ok) {
            const message = await response.text();
            console.log('✅ Backend connection successful:', message);
            updateConnectionStatus('connected', 'Connected to backend');
            showNotification('✅ Connected to backend successfully!', 'success');
        } else {
            throw new Error('Backend not responding');
        }
    } catch (error) {
        console.error('❌ Backend connection failed:', error);
        updateConnectionStatus('disconnected', 'Backend not available');
        showNotification('❌ Cannot connect to backend. Please start the Spring Boot application.', 'error');
    }
}

function updateConnectionStatus(status, text) {
    const statusElement = document.getElementById('connectionStatus');
    const textElement = document.getElementById('connectionText');
    
    statusElement.className = `connection-status ${status}`;
    textElement.textContent = text;
}

// Data Loading Functions
async function loadInitialData() {
    try {
        await Promise.all([
            loadCustomers(),
            loadProducts(),
            loadOrders()
        ]);
    } catch (error) {
        console.error('Error loading initial data:', error);
        showNotification('Error loading data', 'error');
    }
}

async function loadOrders() {
    try {
        showLoading('ordersTableBody');
        const response = await apiFetch('/api/sales/orders');
        if (response.ok) {
            orders = await response.json();
            displayOrders(orders);
            console.log('✅ Orders loaded successfully:', orders.length, 'orders');
        } else {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
    } catch (error) {
        console.error('❌ Error loading orders:', error);
        showNotification('Error loading orders: ' + error.message, 'error');
        document.getElementById('ordersTableBody').innerHTML = '<tr><td colspan="8" class="text-center">Error loading orders - Check if backend is running</td></tr>';
    }
}

async function loadCustomers() {
    try {
        const response = await apiFetch('/api/sales/customers');
        if (response.ok) {
            customers = await response.json();
            displayCustomers(customers);
            populateCustomerSelect();
            console.log('✅ Customers loaded successfully:', customers.length, 'customers');
        } else {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
    } catch (error) {
        console.error('❌ Error loading customers:', error);
        showNotification('Error loading customers: ' + error.message, 'error');
        document.getElementById('customersTableBody').innerHTML = '<tr><td colspan="6" class="text-center">Error loading customers - Check if backend is running</td></tr>';
    }
}

async function loadProducts() {
    try {
        const response = await apiFetch('/api/sales/products');
        if (response.ok) {
            products = await response.json();
            displayProducts(products);
            populateProductSelects();
            console.log('✅ Products loaded successfully:', products.length, 'products');
        } else {
            throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
    } catch (error) {
        console.error('❌ Error loading products:', error);
        showNotification('Error loading products: ' + error.message, 'error');
        document.getElementById('productsTableBody').innerHTML = '<tr><td colspan="6" class="text-center">Error loading products - Check if backend is running</td></tr>';
    }
}

async function loadReports() {
    try {
        const totalOrders = orders.length;
        const pendingOrders = orders.filter(order => order.status === 'PENDING').length;
        const totalRevenue = orders.reduce((sum, order) => sum + (parseFloat(order.totalAmount) || 0), 0);
        const activeCustomers = customers.length;

        document.getElementById('totalOrders').textContent = totalOrders;
        document.getElementById('pendingOrders').textContent = pendingOrders;
        document.getElementById('totalRevenue').textContent = `$${totalRevenue.toFixed(2)}`;
        document.getElementById('activeCustomers').textContent = activeCustomers;
    } catch (error) {
        console.error('Error loading reports:', error);
    }
}

// Display Functions
function displayOrders(ordersList) {
    const tbody = document.getElementById('ordersTableBody');
    if (ordersList.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" class="text-center">No orders found</td></tr>';
        return;
    }

    tbody.innerHTML = ordersList.map(order => `
        <tr>
            <td>#${order.id}</td>
            <td>${order.customer ? order.customer.name : 'N/A'}</td>
            <td>${order.subject || 'N/A'}</td>
            <td><span class="status-badge status-${order.status.toLowerCase()}">${order.status}</span></td>
            <td>$${parseFloat(order.totalAmount || 0).toFixed(2)}</td>
            <td>${formatDate(order.orderDate)}</td>
            <td>${order.deliveryDate ? formatDate(order.deliveryDate) : 'N/A'}</td>
            <td>
                <div class="action-buttons">
                    <button class="action-btn btn-warning" onclick="editOrder(${order.id})" title="Edit">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="action-btn btn-danger" onclick="deleteOrder(${order.id})" title="Delete">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function displayCustomers(customersList) {
    const tbody = document.getElementById('customersTableBody');
    if (customersList.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center">No customers found</td></tr>';
        return;
    }

    tbody.innerHTML = customersList.map(customer => `
        <tr>
            <td>${customer.id}</td>
            <td>${customer.name}</td>
            <td>${customer.email}</td>
            <td>${customer.phone}</td>
            <td>${customer.address}</td>
            <td>
                <div class="action-buttons">
                    <button class="action-btn btn-warning" onclick="editCustomer(${customer.id})" title="Edit">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="action-btn btn-danger" onclick="deleteCustomer(${customer.id})" title="Delete">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

function displayProducts(productsList) {
    const tbody = document.getElementById('productsTableBody');
    if (productsList.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" class="text-center">No products found</td></tr>';
        return;
    }

    tbody.innerHTML = productsList.map(product => `
        <tr>
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.category}</td>
            <td>${product.description || 'N/A'}</td>
            <td>$${parseFloat(product.standardPrice || 0).toFixed(2)}</td>
            <td>
                <div class="action-buttons">
                    <button class="action-btn btn-warning" onclick="editProduct(${product.id})" title="Edit">
                        <i class="fas fa-edit"></i>
                    </button>
                    <button class="action-btn btn-danger" onclick="deleteProduct(${product.id})" title="Delete">
                        <i class="fas fa-trash"></i>
                    </button>
                </div>
            </td>
        </tr>
    `).join('');
}

// Modal Functions
function showAddOrderModal() {
    document.getElementById('addOrderModal').style.display = 'block';
    populateCustomerSelect();
    populateProductSelects();
}

function showAddCustomerModal() {
    resetCustomerModal();
    document.getElementById('addCustomerModal').style.display = 'block';
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
    if (modalId === 'addOrderModal') {
        document.getElementById('orderForm').reset();
        document.getElementById('orderItems').innerHTML = `
            <div class="order-item">
                <select class="product-select" required>
                    <option value="">Select Product</option>
                </select>
                <input type="number" class="quantity-input" placeholder="Quantity" min="1" required>
                <button type="button" class="btn btn-danger btn-sm" onclick="removeOrderItem(this)">
                    <i class="fas fa-trash"></i>
                </button>
            </div>
        `;
        populateProductSelects();
    }
    if (modalId === 'addCustomerModal') {
        document.getElementById('customerForm').reset();
    }
}

// Form Handlers
function setupEventListeners() {
    // Order form
    document.getElementById('orderForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        await createOrder();
    });

    // Customer form
    document.getElementById('customerForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        await createCustomer();
    });

    // Edit order form
    document.getElementById('editOrderForm').addEventListener('submit', async function(e) {
        e.preventDefault();
        await updateOrder();
    });

    // Create product form is attached dynamically in createAddProductModal()

    // Status filter
    document.getElementById('statusFilter').addEventListener('change', function() {
        const status = this.value;
        if (status) {
            const filteredOrders = orders.filter(order => order.status === status);
            displayOrders(filteredOrders);
        } else {
            displayOrders(orders);
        }
    });
}

// CRUD Operations
async function createOrder() {
    try {
        const customerId = document.getElementById('customerSelect').value;
        const subject = document.getElementById('orderSubject').value;
        const description = document.getElementById('orderDescription').value;
        const deliveryDate = document.getElementById('deliveryDate').value;

        // Collect order items
        const orderItems = [];
        const itemElements = document.querySelectorAll('.order-item');
        
        itemElements.forEach(item => {
            const productId = item.querySelector('.product-select').value;
            const quantity = item.querySelector('.quantity-input').value;
            
            if (productId && quantity) {
                const product = products.find(p => p.id == productId);
                if (product) {
                    orderItems.push({
                        product: { id: parseInt(productId) },
                        quantity: parseInt(quantity)
                    });
                }
            }
        });

        if (orderItems.length === 0) {
            showNotification('Please add at least one order item', 'error');
            return;
        }

        const orderData = {
            customerId: parseInt(customerId),
            subject: subject,
            description: description,
            // Send as local datetime string (e.g., "2025-09-28T19:30") compatible with LocalDateTime
            deliveryDate: deliveryDate || null,
            createdBy: 'Sales Executive',
            orderItems: orderItems
        };

        const response = await apiFetch('/api/sales/orders', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(orderData)
        });

        if (response.ok) {
            showNotification('Order created successfully!', 'success');
            closeModal('addOrderModal');
            loadOrders();
        } else {
            const error = await response.text();
            throw new Error(error);
        }
    } catch (error) {
        console.error('Error creating order:', error);
        showNotification('Error creating order: ' + error.message, 'error');
    }
}

async function createCustomer() {
    try {
        const customerData = {
            name: document.getElementById('customerName').value,
            email: document.getElementById('customerEmail').value,
            phone: document.getElementById('customerPhone').value,
            address: document.getElementById('customerAddress').value
        };

        const customerId = document.getElementById('addCustomerModal').dataset.customerId;
        
        if (customerId) {
            // Update existing customer
            const response = await apiFetch(`/api/sales/customers/${customerId}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(customerData)
            });

            if (response.ok) {
                showNotification('Customer updated successfully!', 'success');
                closeModal('addCustomerModal');
                loadCustomers();
                // Reset modal for next use
                resetCustomerModal();
            } else {
                const error = await response.text();
                throw new Error(error);
            }
        } else {
            // Create new customer
            const response = await apiFetch('/api/sales/customers', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(customerData)
            });

            if (response.ok) {
                showNotification('Customer created successfully!', 'success');
                closeModal('addCustomerModal');
                loadCustomers();
            } else {
                const error = await response.text();
                throw new Error(error);
            }
        }
    } catch (error) {
        console.error('Error creating/updating customer:', error);
        showNotification('Error creating/updating customer: ' + error.message, 'error');
    }
}

function resetCustomerModal() {
    document.getElementById('addCustomerModal').dataset.customerId = '';
    document.querySelector('#addCustomerModal .modal-header h3').innerHTML = '<i class="fas fa-plus"></i> Add New Customer';
    document.querySelector('#addCustomerModal .btn-primary').textContent = 'Create Customer';
    document.getElementById('customerName').value = '';
    document.getElementById('customerEmail').value = '';
    document.getElementById('customerPhone').value = '';
    document.getElementById('customerAddress').value = '';
}

async function editOrder(orderId) {
    try {
        const order = orders.find(o => o.id === orderId);
        if (!order) return;

        document.getElementById('editOrderId').value = orderId;
        document.getElementById('editOrderSubject').value = order.subject || '';
        document.getElementById('editOrderDescription').value = order.description || '';
        document.getElementById('editDeliveryDate').value = order.deliveryDate ? 
            new Date(order.deliveryDate).toISOString().slice(0, 16) : '';
        document.getElementById('editOrderStatus').value = order.status;

        document.getElementById('editOrderModal').style.display = 'block';
    } catch (error) {
        console.error('Error editing order:', error);
        showNotification('Error loading order details', 'error');
    }
}

async function updateOrder() {
    try {
        const orderId = document.getElementById('editOrderId').value;
        const orderData = {
            subject: document.getElementById('editOrderSubject').value,
            description: document.getElementById('editOrderDescription').value,
            // Keep local datetime string for LocalDateTime compatibility
            deliveryDate: document.getElementById('editDeliveryDate').value || null,
            status: document.getElementById('editOrderStatus').value
        };

        const response = await apiFetch(`/api/sales/orders/${orderId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(orderData)
        });

        if (response.ok) {
            showNotification('Order updated successfully!', 'success');
            closeModal('editOrderModal');
            loadOrders();
        } else {
            const error = await response.text();
            throw new Error(error);
        }
    } catch (error) {
        console.error('Error updating order:', error);
        showNotification('Error updating order: ' + error.message, 'error');
    }
}

async function deleteOrder(orderId) {
    if (!confirm('Are you sure you want to delete this order?')) return;

    try {
        const response = await apiFetch(`/api/sales/orders/${orderId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showNotification('Order deleted successfully!', 'success');
            loadOrders();
        } else {
            const error = await response.text();
            throw new Error(error);
        }
    } catch (error) {
        console.error('Error deleting order:', error);
        showNotification('Error deleting order: ' + error.message, 'error');
    }
}

// Customer CRUD Operations
async function editCustomer(customerId) {
    try {
        const customer = customers.find(c => c.id === customerId);
        if (!customer) return;

        document.getElementById('customerName').value = customer.name || '';
        document.getElementById('customerEmail').value = customer.email || '';
        document.getElementById('customerPhone').value = customer.phone || '';
        document.getElementById('customerAddress').value = customer.address || '';

        // Store customer ID for update
        document.getElementById('addCustomerModal').dataset.customerId = customerId;
        document.querySelector('#addCustomerModal .modal-header h3').innerHTML = '<i class="fas fa-edit"></i> Edit Customer';
        document.querySelector('#addCustomerModal .btn-primary').textContent = 'Update Customer';

        document.getElementById('addCustomerModal').style.display = 'block';
    } catch (error) {
        console.error('Error editing customer:', error);
        showNotification('Error loading customer details', 'error');
    }
}


async function deleteCustomer(customerId) {
    if (!confirm('Are you sure you want to delete this customer?')) return;

    try {
        const response = await apiFetch(`/api/sales/customers/${customerId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showNotification('Customer deleted successfully!', 'success');
            loadCustomers();
        } else {
            const error = await response.text();
            throw new Error(error);
        }
    } catch (error) {
        console.error('Error deleting customer:', error);
        showNotification('Error deleting customer: ' + error.message, 'error');
    }
}

// Product CRUD Operations
async function editProduct(productId) {
    try {
        const product = products.find(p => p.id === productId);
        if (!product) return;

        // Create edit modal if it doesn't exist
        if (!document.getElementById('editProductModal')) {
            createEditProductModal();
        }

        document.getElementById('editProductId').value = productId;
        document.getElementById('editProductName').value = product.name || '';
        document.getElementById('editProductCategory').value = product.category || '';
        document.getElementById('editProductDescription').value = product.description || '';
        document.getElementById('editProductPrice').value = product.standardPrice || '';
        // Apparel fields
        const setVal = (id, val) => { const el = document.getElementById(id); if (el) el.value = val || ''; };
        setVal('editSizeRange', product.sizeRange);
        setVal('editFabricType', product.fabricType);
        setVal('editColorOptions', product.colorOptions);
        setVal('editCareInstructions', product.careInstructions);
        setVal('editAccessories', product.accessories);
        setVal('editMaterial', product.material);
        setVal('editWeight', product.weight);
        setVal('editDimensions', product.dimensions);
        // Readonly timestamps
        const setText = (id, val) => { const el = document.getElementById(id); if (el) el.textContent = val ? formatDate(val) : 'N/A'; };
        setText('viewCreatedAt', product.createdAt);
        setText('viewUpdatedAt', product.updatedAt);

        document.getElementById('editProductModal').style.display = 'block';
    } catch (error) {
        console.error('Error editing product:', error);
        showNotification('Error loading product details', 'error');
    }
}

async function updateProduct() {
    try {
        const productId = document.getElementById('editProductId').value;
        const productData = {
            name: document.getElementById('editProductName').value,
            category: document.getElementById('editProductCategory').value,
            description: document.getElementById('editProductDescription').value,
            standardPrice: parseFloat(document.getElementById('editProductPrice').value),
            // Apparel fields
            sizeRange: (document.getElementById('editSizeRange')?.value || '').trim() || null,
            fabricType: (document.getElementById('editFabricType')?.value || '').trim() || null,
            colorOptions: (document.getElementById('editColorOptions')?.value || '').trim() || null,
            careInstructions: (document.getElementById('editCareInstructions')?.value || '').trim() || null,
            accessories: (document.getElementById('editAccessories')?.value || '').trim() || null,
            material: (document.getElementById('editMaterial')?.value || '').trim() || null,
            weight: (() => { const v = document.getElementById('editWeight')?.value; return v ? parseFloat(v) : null; })(),
            dimensions: (document.getElementById('editDimensions')?.value || '').trim() || null
        };

        const response = await apiFetch(`/api/sales/products/${productId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(productData)
        });

        if (response.ok) {
            showNotification('Product updated successfully!', 'success');
            closeModal('editProductModal');
            loadProducts();
        } else {
            const error = await response.text();
            throw new Error(error);
        }
    } catch (error) {
        console.error('Error updating product:', error);
        showNotification('Error updating product: ' + error.message, 'error');
    }
}

async function deleteProduct(productId) {
    if (!confirm('Are you sure you want to delete this product?')) return;

    try {
        const response = await apiFetch(`/api/sales/products/${productId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showNotification('Product deleted successfully!', 'success');
            loadProducts();
        } else {
            const error = await response.text();
            throw new Error(error);
        }
    } catch (error) {
        console.error('Error deleting product:', error);
        showNotification('Error deleting product: ' + error.message, 'error');
    }
}

// Helper Functions
function populateCustomerSelect() {
    const select = document.getElementById('customerSelect');
    select.innerHTML = '<option value="">Select Customer</option>' +
        customers.map(customer => 
            `<option value="${customer.id}">${customer.name} (${customer.email})</option>`
        ).join('');
}

function populateProductSelects() {
    const selects = document.querySelectorAll('.product-select');
    selects.forEach(select => {
        if (select.children.length <= 1) { // Only has default option
            select.innerHTML = '<option value="">Select Product</option>' +
                products.map(product => 
                    `<option value="${product.id}">${product.name} - $${parseFloat(product.standardPrice).toFixed(2)}</option>`
                ).join('');
        }
    });
}

function addOrderItem() {
    const orderItemsContainer = document.getElementById('orderItems');
    const newItem = document.createElement('div');
    newItem.className = 'order-item';
    newItem.innerHTML = `
        <select class="product-select" required>
            <option value="">Select Product</option>
        </select>
        <input type="number" class="quantity-input" placeholder="Quantity" min="1" required>
        <button type="button" class="btn btn-danger btn-sm" onclick="removeOrderItem(this)">
            <i class="fas fa-trash"></i>
        </button>
    `;
    orderItemsContainer.appendChild(newItem);
    populateProductSelects();
}

function removeOrderItem(button) {
    const orderItemsContainer = document.getElementById('orderItems');
    if (orderItemsContainer.children.length > 1) {
        button.parentElement.remove();
    } else {
        showNotification('At least one order item is required', 'error');
    }
}

function showLoading(elementId) {
    document.getElementById(elementId).innerHTML = `
        <tr><td colspan="8" class="loading">
            <i class="fas fa-spinner"></i> Loading...
        </td></tr>
    `;
}

function showNotification(message, type = 'info') {
    // Create notification element
    const notification = document.createElement('div');
    notification.className = `notification notification-${type}`;
    notification.style.cssText = `
        position: fixed;
        top: 20px;
        right: 20px;
        padding: 15px 20px;
        border-radius: 8px;
        color: white;
        font-weight: 600;
        z-index: 10000;
        animation: slideIn 0.3s ease-out;
        max-width: 300px;
    `;
    
    // Set background color based on type
    const colors = {
        success: '#27ae60',
        error: '#e74c3c',
        warning: '#f39c12',
        info: '#3498db'
    };
    notification.style.backgroundColor = colors[type] || colors.info;
    
    notification.textContent = message;
    document.body.appendChild(notification);
    
    // Remove notification after 3 seconds
    setTimeout(() => {
        notification.style.animation = 'slideOut 0.3s ease-out';
        setTimeout(() => {
            if (notification.parentNode) {
                notification.parentNode.removeChild(notification);
            }
        }, 300);
    }, 3000);
}

function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString();
}

function createEditProductModal() {
    const modalHTML = `
        <div id="editProductModal" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    <h3><i class="fas fa-edit"></i> Edit Product</h3>
                    <span class="close" onclick="closeModal('editProductModal')">&times;</span>
                </div>
                <div class="modal-body">
                    <form id="editProductForm">
                        <input type="hidden" id="editProductId">
                        <div class="form-group">
                            <label for="editProductName">Product Name</label>
                            <input type="text" id="editProductName" required>
                        </div>
                        <div class="form-group">
                            <label for="editProductCategory">Category</label>
                            <select id="editProductCategory" required>
                                <option value="">Select Category</option>
                                <option value="T-Shirts">T-Shirts</option>
                                <option value="Dresses">Dresses</option>
                                <option value="Pants">Pants</option>
                                <option value="Jackets">Jackets</option>
                                <option value="Accessories">Accessories</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="editProductDescription">Description</label>
                            <textarea id="editProductDescription" rows="3"></textarea>
                        </div>
                        <div class="form-group">
                            <label for="editProductPrice">Standard Price ($)</label>
                            <input type="number" id="editProductPrice" step="0.01" min="0" required>
                        </div>
                        <hr>
                        <div class="form-group">
                            <label for="editSizeRange">Size Range</label>
                            <input type="text" id="editSizeRange" placeholder="e.g., XS-XL or 28-36">
                        </div>
                        <div class="form-group">
                            <label for="editFabricType">Fabric Type</label>
                            <input type="text" id="editFabricType" placeholder="e.g., Cotton, Polyester">
                        </div>
                        <div class="form-group">
                            <label for="editColorOptions">Colour Options</label>
                            <input type="text" id="editColorOptions" placeholder="e.g., Red, Blue, Black">
                        </div>
                        <div class="form-group">
                            <label for="editCareInstructions">Care Instructions</label>
                            <textarea id="editCareInstructions" rows="3" placeholder="Wash cold, Do not bleach..."></textarea>
                        </div>
                        <div class="form-group">
                            <label for="editAccessories">Product Accessories</label>
                            <input type="text" id="editAccessories" placeholder="e.g., Belt, Buttons">
                        </div>
                        <div class="form-group">
                            <label for="editMaterial">Material</label>
                            <input type="text" id="editMaterial" placeholder="e.g., 100% Cotton">
                        </div>
                        <div class="form-group">
                            <label for="editWeight">Weight</label>
                            <input type="number" id="editWeight" step="0.01" min="0" placeholder="e.g., 0.45">
                        </div>
                        <div class="form-group">
                            <label for="editDimensions">Dimensions</label>
                            <input type="text" id="editDimensions" placeholder="e.g., LxWxH or notes">
                        </div>
                        <div class="form-group">
                            <label>Timestamps</label>
                            <div>Created At: <span id="viewCreatedAt">N/A</span></div>
                            <div>Updated At: <span id="viewUpdatedAt">N/A</span></div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" onclick="closeModal('editProductModal')">Cancel</button>
                    <button type="button" class="btn btn-primary" onclick="updateProduct()">Update Product</button>
                </div>
            </div>
        </div>
    `;
    
    document.body.insertAdjacentHTML('beforeend', modalHTML);
}

// Add CSS for notifications
const style = document.createElement('style');
style.textContent = `
    @keyframes slideIn {
        from { transform: translateX(100%); opacity: 0; }
        to { transform: translateX(0); opacity: 1; }
    }
    @keyframes slideOut {
        from { transform: translateX(0); opacity: 1; }
        to { transform: translateX(100%); opacity: 0; }
    }
`;
document.head.appendChild(style);
