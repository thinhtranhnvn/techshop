package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidCollectionEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidCollectionId;
import com.semidev.techshop.exception.ExceptionInvalidCollectionName;
import com.semidev.techshop.exception.ExceptionInvalidCollectionPriority;
import com.semidev.techshop.exception.ExceptionInvalidCollectionSlug;
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
import com.semidev.techshop.exception.ExceptionNullCollectionEditedDate;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.entity.CollectionJoinProductJoinImage;
import com.semidev.techshop.model.entity.ProductJoinImage;
import java.sql.SQLException;
import java.util.ArrayList;


public class CollectionJoinProductJoinImageService {

    public static ArrayList<CollectionJoinProductJoinImage> selectAllCollectionJoinProductJoinImageOrderByCollectionPriorityDesc()
        throws ExceptionInvalidCollectionEditedBy
             , ExceptionInvalidCollectionId
             , ExceptionInvalidCollectionName
             , ExceptionInvalidCollectionPriority
             , ExceptionInvalidCollectionSlug
             , ExceptionNullCollectionEditedDate
             , SQLException
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
        var joinedCollectionList = new ArrayList<CollectionJoinProductJoinImage>();
        
        var collectionList = CollectionService.selectAllCollectionOrderByPriorityDesc();
        for (var collection : collectionList) {
            var joinedCollection = new CollectionJoinProductJoinImage();
            joinedCollection.collection = collection;

            var joinedProductList = new ArrayList<ProductJoinImage>();
            var colProList = ColProService.selectAllColProByCollectionId(collection.getId());
            for (var colPro : colProList) {
                var joinedProduct = ProductJoinImageService.selectProductJoinImageByProductId(colPro.getProductId());
                joinedProductList.add(joinedProduct);
            }
            joinedCollection.joinedProductList = joinedProductList;

            joinedCollectionList.add(joinedCollection);
        }

        return joinedCollectionList;
    }
    
}
