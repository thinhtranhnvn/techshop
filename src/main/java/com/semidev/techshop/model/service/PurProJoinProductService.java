package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidProductDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidProductName;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidProductSlug;
import com.semidev.techshop.exception.ExceptionInvalidProductSpecification;
import com.semidev.techshop.exception.ExceptionInvalidPurchaseId;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.entity.PurProJoinProduct;

import java.sql.SQLException;
import java.util.ArrayList;


public class PurProJoinProductService {

    public static ArrayList<PurProJoinProduct> selectPurProJoinProductByPurchaseId(int purchaseId)
        throws SQLException
             , ExceptionInvalidPurchaseId
             , ExceptionInvalidProductId
             , ExceptionInvalidProductPrice
             , ExceptionInvalidProductDiscount
             , ExceptionNullProductPromotion
             , ExceptionInvalidBrandId
             , ExceptionInvalidProductName
             , ExceptionInvalidProductDescription
             , ExceptionInvalidProductSpecification
             , ExceptionInvalidProductSlug
             , ExceptionInvalidProductEditedDate
             , ExceptionInvalidProductEditedBy
    {
        var joinedPurProList = new ArrayList<PurProJoinProduct>();
        var purProList = PurProService.selectPurProByPurchaseId(purchaseId);
        for (var purPro : purProList) {
            var joinedPurPro = new PurProJoinProduct();
            var product = ProductService.selectProductById(purPro.getProductId());
            joinedPurPro.purPro = purPro;
            joinedPurPro.product = product;
            joinedPurProList.add(joinedPurPro);
        }
        return joinedPurProList;
    }
    
}
