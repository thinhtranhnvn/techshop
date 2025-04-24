package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidPurchaseId;
import com.semidev.techshop.exception.ExceptionInvalidPurchaseStatus;
import com.semidev.techshop.exception.ExceptionInvalidVisitorAddress;
import com.semidev.techshop.exception.ExceptionInvalidVisitorEmail;
import com.semidev.techshop.exception.ExceptionInvalidVisitorFullname;
import com.semidev.techshop.exception.ExceptionInvalidVisitorId;
import com.semidev.techshop.exception.ExceptionInvalidVisitorPassword;
import com.semidev.techshop.exception.ExceptionInvalidVisitorPhone;
import com.semidev.techshop.exception.ExceptionInvalidVisitorUsername;
import com.semidev.techshop.exception.ExceptionNullPurchasePlacedDate;
import com.semidev.techshop.model.entity.PurchaseJoinVisitor;

import java.sql.SQLException;
import java.util.ArrayList;


public class PurchaseJoinVisitorService {
 
    public static ArrayList<PurchaseJoinVisitor> selectAllPurchaseJoinVisitorOrderByPlacedDateDescLimitOffset(
        int limit,
        int offset
    ) throws SQLException
           , ExceptionInvalidPurchaseId
           , ExceptionNullPurchasePlacedDate
           , ExceptionInvalidPurchaseStatus
           , ExceptionInvalidVisitorId
           , ExceptionInvalidVisitorFullname
           , ExceptionInvalidVisitorUsername
           , ExceptionInvalidVisitorPassword
           , ExceptionInvalidVisitorEmail
           , ExceptionInvalidVisitorAddress
           , ExceptionInvalidVisitorPhone
    {
        var joinedPurchaseList = new ArrayList<PurchaseJoinVisitor>();
            
        var purchaseList = PurchaseService.selectAllPurchaseOrderByPlacedDateDescLimitOffset(limit, offset);
        for (var purchase : purchaseList) {
            var joinedPurchase = new PurchaseJoinVisitor();
            var visitor = VisitorService.selectVisitorById(purchase.getVisitorId());
            joinedPurchase.purchase = purchase;
            joinedPurchase.visitor = visitor;
            joinedPurchaseList.add(joinedPurchase);
        }

        return joinedPurchaseList;
    }
    
    public static PurchaseJoinVisitor selectPurchaseJoinVisitorByPurchaseId(int purchaseId)
        throws SQLException
             , ExceptionInvalidPurchaseId
             , ExceptionNullPurchasePlacedDate
             , ExceptionInvalidPurchaseStatus
             , ExceptionInvalidVisitorId
             , ExceptionInvalidVisitorFullname
             , ExceptionInvalidVisitorUsername
             , ExceptionInvalidVisitorPassword
             , ExceptionInvalidVisitorEmail
             , ExceptionInvalidVisitorAddress
             , ExceptionInvalidVisitorPhone
    {
        var joinedPurchase = new PurchaseJoinVisitor();
        joinedPurchase.purchase = PurchaseService.selectPurchaseById(purchaseId);
        joinedPurchase.visitor = VisitorService.selectVisitorById(joinedPurchase.purchase.getVisitorId());
        return joinedPurchase;
    }
    
}
