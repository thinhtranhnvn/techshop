package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidPurchaseId;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;


public class PurPro {

    private Integer purchaseId;
    
        public int getPurchaseId() {
            return this.purchaseId;
        }
        
        public void setPurchaseId(int purchaseId) throws ExceptionInvalidPurchaseId {
            if (purchaseId < 1)
                throw new ExceptionInvalidPurchaseId();
            else
                this.purchaseId = purchaseId;
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
    
    private Float price;
        
        public float getPrice() {
            return this.price;
        }
        
        public void setPrice(float price) throws ExceptionInvalidProductPrice {
            if (price < 0)
                throw new ExceptionInvalidProductPrice();
            else
                this.price = price;
        }
    
    private Float discount;
    
        public float getDiscount() {
            return this.discount;
        }
        
        public void setDiscount(float discount) throws ExceptionInvalidProductDiscount {
            if (discount < 0)
                throw new ExceptionInvalidProductDiscount();
            else
                this.discount = discount;
        }
    
    private String promotion;
    
        public String getPromotion() {
            return this.promotion;
        }
        
        public void setPromotion(String promotion) throws ExceptionNullProductPromotion {
            if (promotion == null)
                throw new ExceptionNullProductPromotion();
            else
                this.promotion = promotion;
        }
    
    public static PurPro createInstance(
        int    purchaseId,
        int    productId,
        float  price,
        float  discount,
        String promotion
    ) throws ExceptionInvalidPurchaseId
           , ExceptionInvalidProductId
           , ExceptionInvalidProductPrice
           , ExceptionInvalidProductDiscount
           , ExceptionNullProductPromotion
    {
        var instance = new PurPro();
        instance.setPurchaseId(purchaseId);
        instance.setProductId(productId);
        instance.setPrice(price);
        instance.setDiscount(discount);
        instance.setPromotion(promotion);
        return instance;
    }
    
}
