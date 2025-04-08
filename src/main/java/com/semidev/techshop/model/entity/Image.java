package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidImageURL;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidImageId;


public class Image {
    
    private int id;
    
        public Integer getId() {
            return this.id;
        }
        
        public void setId(int id) throws ExceptionInvalidImageId {
            if (id < 1)
                throw new ExceptionInvalidImageId("Invalid product image id");
            else
                this.id = id;
        }
    
    private Integer productId;
    
        public int getProductId() {
            return this.productId;
        }
        
        public void setProductId(int productId) throws ExceptionInvalidProductId {
            if (productId < 1)
                throw new ExceptionInvalidProductId("Invalid product id");
            else
                this.productId = productId;
        }
    
    private String imageURL;
    
        public String getImageURL() {
            return this.imageURL;
        }
        
        public void setImageURL(String imageURL) throws ExceptionInvalidImageURL {
            if (imageURL == null || imageURL.isEmpty())
                throw new ExceptionInvalidImageURL("Invalid image URL");
            else
                this.imageURL = imageURL;
        }
    
    public static Image createInstance(
        int    id,
        int    productId,
        String imageURL
    ) throws ExceptionInvalidImageURL
           , ExceptionInvalidProductId
           , ExceptionInvalidImageId
    {
        var instance = new Image();
        instance.setId(id);
        instance.setProductId(productId);
        instance.setImageURL(imageURL);
        return instance;
    }
        
}
