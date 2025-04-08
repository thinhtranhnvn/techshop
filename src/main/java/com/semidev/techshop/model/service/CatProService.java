package com.semidev.techshop.model.service;

import com.semidev.techshop.model.entity.CatPro;

import java.sql.SQLException;


public class CatProService {

    public static void deleteFromCatProByCategoryId(int categoryId) throws SQLException {
        try (var connection = Database.getConnection()) {
                var sql = "DELETE FROM catpro "
                        + "WHERE category_id = %d";
                sql = String.format(sql, categoryId);
                var statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            }
            catch (SQLException exc) {
                throw exc;
            }
    }
    
    public static void insertIntoCatPro(CatPro record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var categoryId = record.getCategoryId();
            var productId  = record.getProductId();
            var sql = "INSERT INTO catpro "
                    + "    (category_id, product_id) "
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
    
    public static void deleteFromCatPro(CatPro record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var categoryId = record.getCategoryId();
            var productId  = record.getProductId();
            var sql = "DELETE FROM catpro "
                    + "WHERE category_id = %d "
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
