package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidProductDiscount;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidProductPrice;
import com.semidev.techshop.exception.ExceptionInvalidPurchaseId;
import com.semidev.techshop.exception.ExceptionNullProductPromotion;
import com.semidev.techshop.model.entity.PurPro;
import java.sql.SQLException;
import java.util.ArrayList;


public class PurProService {
    
    public static void insertIntoPurPro(PurPro record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var purchaseId = record.getPurchaseId();
            var productId  = record.getProductId();
            var price      = record.getPrice();
            var discount   = record.getDiscount();
            var promotion  = record.getPromotion();
            var sql = "INSERT INTO purpro "
                    + "    (purchase_id, product_id, price, discount, promotion) "
                    + "VALUES "
                    + "    (         %d,         %d,    %f,       %f,      '%s')";
            sql = String.format(sql, purchaseId, productId, price, discount, promotion);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<PurPro> selectPurProByPurchaseId(int purchaseId)
        throws SQLException
             , ExceptionInvalidPurchaseId
             , ExceptionInvalidProductId
             , ExceptionInvalidProductPrice
             , ExceptionInvalidProductDiscount
             , ExceptionNullProductPromotion
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT purchase_id, product_id, price, discount, promotion "
                    + "FROM purpro "
                    + "WHERE purchase_id = %d";
            sql = String.format(sql, purchaseId);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var purProList = new ArrayList<PurPro>();
            while (result.next()) {
                var productId = result.getInt("product_id");
                var price = result.getFloat("price");
                var discount = result.getFloat("discount");
                var promotion = result.getString("promotion");
                var record = PurPro.createInstance(purchaseId, productId, price, discount, promotion);
                purProList.add(record);
            }
            return purProList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
}
