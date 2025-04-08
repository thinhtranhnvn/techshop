package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidCollectionId;
import com.semidev.techshop.exception.ExceptionInvalidProductId;


public class ColPro {
    
    private Integer collectionId;
    
        public int getCollectionId() {
            return this.collectionId;
        }
        
        public void setCollectionId(int collectionId) throws ExceptionInvalidCollectionId {
            if (collectionId < 1)
                throw new ExceptionInvalidCollectionId("Invalid collection id");
            else
                this.collectionId = collectionId;
        }
    
    private Integer productId;
    
        public int getProductId() {
            return this.productId;
        }
        
        public void setProductId(int productId) throws ExceptionInvalidProductId {
            if (productId < 1)
                throw new ExceptionInvalidProductId("Invalid category id");
            else
                this.productId = productId;
        }
    
    public static ColPro createInstance(
        int collectionId,
        int productId
    ) throws ExceptionInvalidCollectionId
           , ExceptionInvalidProductId
    {
        var instance = new ColPro();
        instance.setCollectionId(collectionId);
        instance.setProductId(productId);
        return instance;
    }
    
}
