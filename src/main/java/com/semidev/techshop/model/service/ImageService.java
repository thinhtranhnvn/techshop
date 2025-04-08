package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidImageURL;
import com.semidev.techshop.exception.ExceptionInvalidProductId;
import com.semidev.techshop.exception.ExceptionInvalidImageId;
import com.semidev.techshop.model.entity.Image;
import java.sql.SQLException;
import java.util.ArrayList;


public class ImageService {

    public static int selectLatestImageId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM product_image "
                    + "ORDER BY id DESC "
                    + "LIMIT 1";
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("id");
            else
                return 0;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void insertIntoImage(Image record) throws SQLException {
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
    }
    
    public static ArrayList<Image> selectImageByProductId(int productId)
        throws SQLException
             , ExceptionInvalidImageURL
             , ExceptionInvalidProductId
             , ExceptionInvalidImageId
    {
        try (var connection = Database.getConnection()) {
            var productImageList = new ArrayList<Image>();
            var sql = "SELECT id, product_id, image_url "
                    + "FROM product_image "
                    + "WHERE product_id = %d";
            sql = String.format(sql, productId);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            while (result.next()) {
                var id = result.getInt("id");
                var imageURL = result.getString("image_url");
                var record = Image.createInstance(id, productId, imageURL);
                productImageList.add(record);
            }
            return productImageList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void deleteFromImage(Image record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "DELETE FROM product_image "
                    + "WHERE id = %d";
            sql = String.format(sql, record.getId());
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
}
