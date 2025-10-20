package com.example.myprac.garmentsystem.order.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String category;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "standard_price", precision = 10, scale = 2)
    private BigDecimal standardPrice;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active")
    private Boolean isActive = true;

    // Apparel specific and extended attributes
    @Column(name = "size_range", length = 100)
    private String sizeRange;           // e.g., XS-XL or 28-36

    @Column(name = "fabric_type", length = 100)
    private String fabricType;          // e.g., Cotton, Polyester

    @Column(name = "color_options", length = 255)
    private String colorOptions;        // comma-separated colors

    @Column(name = "care_instructions", columnDefinition = "TEXT")
    private String careInstructions;    // multi-line care instructions

    @Column(name = "accessories", length = 255)
    private String accessories;         // comma-separated accessories

    @Column(name = "material", length = 100)
    private String material;            // primary material

    @Column(name = "weight", precision = 10, scale = 2)
    private BigDecimal weight;          // in grams or kilograms

    @Column(name = "dimensions", length = 100)
    private String dimensions;          // e.g., LxWxH or size notes

    // Constructors
    public Product() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Product(String name, String category, String description, BigDecimal standardPrice) {
        this();
        this.name = name;
        this.category = category;
        this.description = description;
        this.standardPrice = standardPrice;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public BigDecimal getStandardPrice() { return standardPrice; }
    public void setStandardPrice(BigDecimal standardPrice) { this.standardPrice = standardPrice; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
    
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }

    public String getSizeRange() { return sizeRange; }
    public void setSizeRange(String sizeRange) { this.sizeRange = sizeRange; }

    public String getFabricType() { return fabricType; }
    public void setFabricType(String fabricType) { this.fabricType = fabricType; }

    public String getColorOptions() { return colorOptions; }
    public void setColorOptions(String colorOptions) { this.colorOptions = colorOptions; }

    public String getCareInstructions() { return careInstructions; }
    public void setCareInstructions(String careInstructions) { this.careInstructions = careInstructions; }

    public String getAccessories() { return accessories; }
    public void setAccessories(String accessories) { this.accessories = accessories; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }
}
