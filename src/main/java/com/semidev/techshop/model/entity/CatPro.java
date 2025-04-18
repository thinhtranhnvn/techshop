package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidCategoryId;
import com.semidev.techshop.exception.ExceptionInvalidProductId;


public class CatPro {
    
    private Integer categoryId;
    
        public int getCategoryId() {
            return this.categoryId;
        }
        
        public void setCategoryId(int categoryId) throws ExceptionInvalidCategoryId {
            if (categoryId < 1)
                throw new ExceptionInvalidCategoryId();
            else
                this.categoryId = categoryId;
        }
    
    private Integer productId;
    
        public int getProductId() {
            return this.productId;
        }
        
        public void setProductId(int productId) throws ExceptionInvalidProductId {
            if (productId < 1)
                throw new ExceptionInvalidProductId();
            else
                this.productId = productId;
        }
    
    public static CatPro createInstance(
        int collectionId,
        int productId
    ) throws ExceptionInvalidCategoryId
           , ExceptionInvalidProductId
    {
        var instance = new CatPro();
        instance.setCategoryId(collectionId);
        instance.setProductId(productId);
        return instance;
    }
    
}
