package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidImageId;
import com.semidev.techshop.exception.ExceptionInvalidImageURL;
import com.semidev.techshop.exception.ExceptionInvalidProductDescription;
import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidProductEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidProductName;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidProductSlug;
import com.semidev.techshop.exception.ExceptionInvalidProductSpecification;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.entity.ProductJoinImage;
import java.sql.SQLException;
import java.util.ArrayList;


public class ProductJoinImageService {
    
    public static ProductJoinImage selectProductJoinImageByProductId(int productId)
        throws SQLException
             , ExceptionInvalidProductId
             , ExceptionInvalidBrandId
             , ExceptionInvalidProductName
             , ExceptionInvalidProductPrice
             , ExceptionInvalidProductDescription
             , ExceptionInvalidProductSpecification
             , ExceptionInvalidProductSlug
             , ExceptionInvalidProductEditedDate
             , ExceptionInvalidProductEditedBy
             , ExceptionInvalidProductDiscount
             , ExceptionNullProductPromotion
             , ExceptionInvalidImageURL
             , ExceptionInvalidImageId
    {
        var joinedProduct = new ProductJoinImage();
        joinedProduct.product = ProductService.selectProductById(productId);
        joinedProduct.imageList = ImageService.selectImageByProductId(productId);
        return joinedProduct;
    }
    
    public static ProductJoinImage selectProductJoinImageByProductSlug(String productSlug)
        throws SQLException
             , ExceptionInvalidProductId
             , ExceptionInvalidBrandId
             , ExceptionInvalidProductName
             , ExceptionInvalidProductPrice
             , ExceptionInvalidProductDescription
             , ExceptionInvalidProductSpecification
             , ExceptionInvalidProductSlug
             , ExceptionInvalidProductEditedDate
             , ExceptionInvalidProductEditedBy
             , ExceptionInvalidProductDiscount
             , ExceptionNullProductPromotion
             , ExceptionInvalidImageURL
             , ExceptionInvalidImageId
    {
        var joinedProduct = new ProductJoinImage();
        joinedProduct.product = ProductService.selectProductBySlug(productSlug);
        joinedProduct.imageList = ImageService.selectImageByProductId(joinedProduct.product.getId());
        return joinedProduct;
    }
    
    public static ArrayList<ProductJoinImage> selectProductJoinImageByProductNameLike(String keywords)
        throws SQLException
             , ExceptionInvalidProductId
             , ExceptionInvalidBrandId
             , ExceptionInvalidProductName
             , ExceptionInvalidProductPrice
             , ExceptionInvalidProductDescription
             , ExceptionInvalidProductSpecification
             , ExceptionInvalidProductSlug
             , ExceptionInvalidProductEditedDate
             , ExceptionInvalidProductEditedBy
             , ExceptionInvalidProductDiscount
             , ExceptionNullProductPromotion
             , ExceptionInvalidImageURL
             , ExceptionInvalidImageId
    {
        var joinedProductList = new ArrayList<ProductJoinImage>();
        
        var productList = ProductService.selectProductByNameLikeOrderByNameAsc(keywords);
        for (var product : productList) {
            var joinedProduct = ProductJoinImageService.selectProductJoinImageByProductId(product.getId());
            joinedProductList.add(joinedProduct);
        }
        
        return joinedProductList;
    }
    
}
