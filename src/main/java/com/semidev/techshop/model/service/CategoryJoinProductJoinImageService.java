package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidCategoryEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidCategoryId;
import com.semidev.techshop.exception.ExceptionInvalidCategoryName;
import com.semidev.techshop.exception.ExceptionInvalidCategorySlug;
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
import com.semidev.techshop.exception.ExceptionNullCategoryEditedDate;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.entity.CategoryJoinProductJoinImage;

import java.sql.SQLException;
import java.util.ArrayList;


public class CategoryJoinProductJoinImageService {

    public static CategoryJoinProductJoinImage selectCategoryJoinProductJoinImageByCategorySlug(String categorySlug)
        throws SQLException
             , ExceptionInvalidCategoryId
             , ExceptionInvalidCategoryName
             , ExceptionInvalidCategorySlug
             , ExceptionNullCategoryEditedDate
             , ExceptionInvalidCategoryEditedBy
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
        var joinedCategory = new CategoryJoinProductJoinImage();
        joinedCategory.category = CategoryService.selectCategoryBySlug(categorySlug);
        
        var catProList = CatProService.selectCatProByCategoryId(joinedCategory.category.getId());
        joinedCategory.joinedProductList = new ArrayList<>();
        for (var catPro : catProList) {
            var joinedProduct = ProductJoinImageService.selectProductJoinImageByProductId(catPro.getProductId());
            joinedCategory.joinedProductList.add(joinedProduct);
        }
        
        return joinedCategory;
    }
    
}
