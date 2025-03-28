package com.semidev.techshop.model.service;

import com.semidev.techshop.model.entity.Product;
import com.semidev.techshop.exception.ExceptionInvalidBrandId;
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

import java.sql.SQLException;
import java.time.LocalDateTime;


public class ProductService {

    public static Product selectProductBySlug(String slug)
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
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, edited_date, edited_by, slug "
                    + "FROM product "
                    + "WHERE slug = '%s'";
            sql = String.format(sql, slug);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var editedDate    = LocalDateTime.parse(result.getString("edited_date"));
                var editedBy      = result.getString("edited_by");
                return Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException
                | ExceptionInvalidProductId
                | ExceptionInvalidBrandId
                | ExceptionInvalidProductName
                | ExceptionInvalidProductPrice
                | ExceptionInvalidProductDescription
                | ExceptionInvalidProductSpecification
                | ExceptionInvalidProductSlug
                | ExceptionInvalidProductEditedDate
                | ExceptionInvalidProductEditedBy
                | ExceptionInvalidProductDiscount
                | ExceptionNullProductPromotion
                exc
        ) {
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
    
    public static int selectLatestProductId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM product "
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
    
    public static void insertIntoProduct(Product record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id = record.getId();
            var brandId       = record.getBrandId();
            var name          = record.getName();
            var price         = record.getPrice();
            var discount      = record.getDiscount();
            var promotion     = record.getPromotion();
            var description   = record.getDescription();
            var specification = record.getSpecification();
            var slug          = record.getSlug();
            var editedDate    = record.getEditedDate();
            var editedBy      = record.getEditedBy();
            var sql = "INSERT INTO product "
                    + "    (id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by) "
                    + "VALUES "
                    + "    (%d,       %d, '%s',    %f,       %f,      '%s',        '%s',          '%s', '%s',        '%s',      '%s')";
            sql = String.format(sql, id, brandId, name, price, discount, promotion, description, specification, slug, editedDate.toString(), editedBy);
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
