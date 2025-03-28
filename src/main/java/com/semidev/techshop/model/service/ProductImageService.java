package com.semidev.techshop.model.service;

import com.semidev.techshop.model.entity.ProductImage;
import java.sql.SQLException;


public class ProductImageService {

    public static int selectLatestProductImageId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM product_image "
                    + "ORDER BY id DESC "
                    + "LIMIT 1";
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) return result.getInt("id");
            else return 0;
        }
        catch (SQLException exc) {
            throw exc;
        }
        finally {
            try {
                Database.closeConnection();
            }
            catch (SQLException exc) {
                throw exc;
            }
        }
    }
    
    public static void insertIntoProductImage(ProductImage record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id        = record.getId();
            var productId = record.getProductId();
            var imageURL  = record.getImageURL();
            var sql = "INSERT INTO product_image "
                    + "    (id, product_id, image_url) "
                    + "VALUES "
                    + "    (%d,         %d,      '%s')";
            sql = String.format(sql, id, productId, imageURL);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
        finally {
            try {
                Database.closeConnection();
            }
            catch (SQLException exc) {
                throw exc;
            }
        }
    }
    
}
