package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidBrandEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidBrandEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidBrandImageURL;
import com.semidev.techshop.exception.ExceptionInvalidBrandName;
import com.semidev.techshop.exception.ExceptionInvalidBrandSlug;
import com.semidev.techshop.exception.ExceptionInvalidImageURL;
import com.semidev.techshop.exception.ExceptionInvalidProductDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidImageId;
import com.semidev.techshop.exception.ExceptionInvalidProductName;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidProductSlug;
import com.semidev.techshop.exception.ExceptionInvalidProductSpecification;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.entity.ProductJoinBrandJoinImage;

import java.sql.SQLException;


public class ProductJoinBrandJoinImageService {
    
    public static ProductJoinBrandJoinImage selectProductJoinBrandJoinImageByProductId(int productId)
        throws SQLException
             , ExceptionInvalidBrandId
             , ExceptionInvalidImageURL
             , ExceptionInvalidProductDescription
             , ExceptionInvalidProductDiscount
             , ExceptionInvalidProductEditedBy
             , ExceptionInvalidProductEditedDate
             , ExceptionInvalidProductId
             , ExceptionInvalidImageId
             , ExceptionInvalidProductName
             , ExceptionInvalidProductPrice
             , ExceptionInvalidProductSlug
             , ExceptionInvalidProductSpecification
             , ExceptionNullProductPromotion
             , ExceptionInvalidBrandName
             , ExceptionInvalidBrandImageURL
             , ExceptionInvalidBrandSlug
             , ExceptionInvalidBrandEditedDate
             , ExceptionInvalidBrandEditedBy
    {
        var productJoinBrandJoinImage = new ProductJoinBrandJoinImage();
        productJoinBrandJoinImage.product = ProductService.selectProductById(productId);
        productJoinBrandJoinImage.brand = BrandService.selectBrandById(productJoinBrandJoinImage.product.getBrandId());
        productJoinBrandJoinImage.imageList = ImageService.selectImageByProductId(productId);
        return productJoinBrandJoinImage;
    }
    
}
