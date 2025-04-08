package com.semidev.techshop.model.service;

import com.semidev.techshop.model.entity.ColPro;

import java.sql.SQLException;


public class ColProService {

    public static void deleteFromColProByCollectionId(int categoryId) throws SQLException {
        try (var connection = Database.getConnection()) {
                var sql = "DELETE FROM colpro "
                        + "WHERE collection_id = %d";
                sql = String.format(sql, categoryId);
                var statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            }
            catch (SQLException exc) {
                throw exc;
            }
    }
    
    public static void insertIntoColPro(ColPro record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var categoryId = record.getCollectionId();
            var productId  = record.getProductId();
            var sql = "INSERT INTO colpro "
                    + "    (collection_id, product_id) "
                    + "VALUES "
                    + "    (         %d,         %d)";
            sql = String.format(sql, categoryId, productId);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (Exception exc) {
            throw exc;
        }
    }
    
    public static void deleteFromColPro(ColPro record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var categoryId = record.getCollectionId();
            var productId  = record.getProductId();
            var sql = "DELETE FROM colpro "
                    + "WHERE collection_id = %d "
                    + "AND product_id = %d";
            sql = String.format(sql, categoryId, productId);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
}
