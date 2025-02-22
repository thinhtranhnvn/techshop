package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidProductDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidProductName;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidProductShortDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductSlug;
import com.semidev.techshop.exception.ExceptionInvalidProductSpecification;

import java.time.LocalDateTime;


public class Product {

    private Integer id;
    
        public int getId() {
            return this.id;
        }
        
        public void setId(int id) throws ExceptionInvalidProductId {
            if (id < 1)
                throw new ExceptionInvalidProductId("Invalid product id");
            else
                this.id = id;
        }
    
    private Integer brandId;
    
        public int getBrandId() {
            return this.brandId;
        }
        
        public void setBrandId(int brandId) throws ExceptionInvalidBrandId {
            if (brandId < 1)
                throw new ExceptionInvalidBrandId("Invalid brand id");
            else
                this.brandId = brandId;
        }
    
    private String name;
    
        public String getName() {
            return this.name;
        }
        
        public void setName(String name) throws ExceptionInvalidProductName {
            if (name == null || name.isEmpty())
                throw new ExceptionInvalidProductName("Invalid product name");
            else
                this.name = name;
        }
    
    private Float price;
    
        public float getPrice() {
            return this.price;
        }
        
        public void setPrice(float price) throws ExceptionInvalidProductPrice {
            if (price < 0)
                throw new ExceptionInvalidProductPrice("Invalid product price");
            else
                this.price = price;
        }
        
    private String shortDescription;
    
        public String getShortDescription() {
            return this.shortDescription;
        }
        
        public void setShortDescription(String shortDescription) throws ExceptionInvalidProductShortDescription {
            if (shortDescription == null || shortDescription.isEmpty())
                throw new ExceptionInvalidProductShortDescription("Invalid product short description");
            else
                this.shortDescription = shortDescription;
        }
    
    private String description;
    
        public String getDescription() {
            return this.description;
        }
        
        public void setDescription(String description) throws ExceptionInvalidProductDescription {
            if (description == null || description.isEmpty())
                throw new ExceptionInvalidProductDescription("Invalid product description");
            else
                this.description = description;
        }
    
    private String specification;
    
        public String getSpecification() {
            return this.specification;
        }
        
        public void setSpecification(String specification) throws ExceptionInvalidProductSpecification {
            if (specification == null || specification.isEmpty())
                throw new ExceptionInvalidProductSpecification("Invalid product specification");
            else
                this.specification = specification;
        }
    
    private String slug;
    
        public String getSlug() {
            return this.slug;
        }
        
        public void setSlug(String slug) throws ExceptionInvalidProductSlug {
            if (slug == null || slug.isEmpty())
                throw new ExceptionInvalidProductSlug("Invalid product slug");
            else
                this.slug = slug;
        }
        
    private LocalDateTime editedDate;

        public LocalDateTime getEditedDate() {
            return this.editedDate;
        }

        public void setEditedDate(LocalDateTime editedDate) throws ExceptionInvalidProductEditedDate {
            if (editedDate == null)
                throw new ExceptionInvalidProductEditedDate("Invalid product edited-date");
            else
                this.editedDate = editedDate;
        }
        
    private String editedBy;
    
        public String getEditedBy() {
            return this.editedBy;
        }
        
        public void setEditedBy(String editedBy) throws ExceptionInvalidProductEditedBy {
            if (editedBy == null || editedBy.isEmpty())
                throw new ExceptionInvalidProductEditedBy("Invalid product edited-by");
            else
                this.editedBy = editedBy;
        }
        
    public Product createInstance(
        int           id,
        int           brandId,
        String        name,
        float         price,
        String        shortDescription,
        String        description,
        String        specification,
        String        slug,
        LocalDateTime editedDate,
        String        editedBy
    ) throws ExceptionInvalidProductId
           , ExceptionInvalidBrandId
           , ExceptionInvalidProductName
           , ExceptionInvalidProductPrice
           , ExceptionInvalidProductShortDescription
           , ExceptionInvalidProductDescription
           , ExceptionInvalidProductSpecification
           , ExceptionInvalidProductSlug
           , ExceptionInvalidProductEditedDate
           , ExceptionInvalidProductEditedBy
    {
        try {
            var instance = new Product();
            instance.setId(id);
            instance.setBrandId(brandId);
            instance.setName(name);
            instance.setPrice(price);
            instance.setShortDescription(shortDescription);
            instance.setDescription(description);
            instance.setSpecification(specification);
            instance.setSlug(slug);
            instance.setEditedDate(editedDate);
            instance.setEditedBy(editedBy);
            return instance;
        }
        catch (ExceptionInvalidBrandId | ExceptionInvalidProductDescription | ExceptionInvalidProductEditedBy | ExceptionInvalidProductEditedDate | ExceptionInvalidProductId | ExceptionInvalidProductName | ExceptionInvalidProductPrice | ExceptionInvalidProductShortDescription | ExceptionInvalidProductSlug | ExceptionInvalidProductSpecification exc) {
            throw exc;
        }
    }
        
}
