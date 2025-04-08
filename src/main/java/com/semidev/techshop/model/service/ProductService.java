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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


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
                var dateString = result.getString("edited_date");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(dateString, formatter);
                var editedBy      = result.getString("edited_by");
                return Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
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
    }
    
    public static int selectCountAllProduct() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product";
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectAllProductOrderByEditedDateDescLimitOffset(
        int limit,
        int offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
           sql = String.format(sql, limit, offset);
           var statement = connection.prepareStatement(sql);
           var result = statement.executeQuery();
           var productList = new ArrayList<Product>();
           while (result.next()) {
               var id            = result.getInt("id");
               var brandId       = result.getInt("brand_id");
               var name          = result.getString("name");
               var price         = result.getFloat("price");
               var discount      = result.getFloat("discount");
               var promotion     = result.getString("promotion");
               var description   = result.getString("description");
               var specification = result.getString("specification");
               var slug          = result.getString("slug");
               var dateString = result.getString("edited_date");
               var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               var editedDate = LocalDateTime.parse(dateString, formatter);
               var editedBy      = result.getString("edited_by");
               var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
               productList.add(record);
           }
           return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductByNameLike(String keywords) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE name LIKE '%s'";
            sql = String.format(sql, "%"+keywords+"%");
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductByNameLikeOrderByEditedDateDescLimitOffset(
        String keywords,
        int    limit,
        int    offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE name LIKE '%s' "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
           sql = String.format(sql, "%"+keywords+"%", limit, offset);
           var statement = connection.prepareStatement(sql);
           var result = statement.executeQuery();
           var productList = new ArrayList<Product>();
           while (result.next()) {
               var id            = result.getInt("id");
               var brandId       = result.getInt("brand_id");
               var name          = result.getString("name");
               var price         = result.getFloat("price");
               var discount      = result.getFloat("discount");
               var promotion     = result.getString("promotion");
               var description   = result.getString("description");
               var specification = result.getString("specification");
               var slug          = result.getString("slug");
               var dateString = result.getString("edited_date");
               var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               var editedDate = LocalDateTime.parse(dateString, formatter);
               var editedBy      = result.getString("edited_by");
               var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
               productList.add(record);
           }
           return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static Product selectProductById(int id)
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
                    + "WHERE id = %d";
            sql = String.format(sql, id);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var dateString = result.getString("edited_date");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(dateString, formatter);
                var editedBy      = result.getString("edited_by");
                return Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
        
    public static void updateProduct(Product record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id            = record.getId();
            var brandId       = record.getBrandId();
            var name          = record.getName();
            var price         = record.getPrice();
            var discount      = record.getDiscount();
            var promotion     = record.getPromotion();
            var description   = record.getDescription();
            var specification = record.getSpecification();
            var slug          = record.getSlug();
            var editedDate    = record.getEditedDate().toString();
            var editedBy      = record.getEditedBy();
            var sql = "UPDATE product "
                    + "SET brand_id = %d, "
                    + "    name = '%s', "
                    + "    price = %f, "
                    + "    discount = %f, "
                    + "    promotion = '%s', "
                    + "    description = '%s', "
                    + "    specification = '%s', "
                    + "    slug = '%s', "
                    + "    edited_date = '%s', "
                    + "    edited_by = '%s' "
                    + "WHERE id = %d";
            sql = String.format(sql, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy, id);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void deleteFromProduct(Product record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "DELETE FROM product "
                    + "WHERE id = %d";
            sql = String.format(sql, record.getId());
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductNotInCategory(int categoryId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE id NOT IN (SELECT product_id FROM catpro WHERE category_id = %d)";
            sql = String.format(sql, categoryId);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
            
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductNotInCategoryOrderByEditedDateDescLimitOffset(
        int categoryId,
        int limit,
        int offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE id NOT IN (SELECT product_id FROM catpro WHERE category_id = %d)"
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, categoryId, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var productList = new ArrayList<Product>();
            while (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy      = result.getString("edited_by");
                var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                productList.add(record);
            }
            return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductByNameLikeNotInCategory(
        String keywords,
        int    categoryId
    ) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE id NOT IN (SELECT product_id FROM catpro WHERE category_id = %d) "
                    + "AND name LIKE '%s'";
            sql = String.format(sql, categoryId, "%"+keywords+"%");
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
            
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductByNameLikeNotInCategoryOrderByEditedDateDescLimitOffset(
        String keywords,
        int    categoryId,
        int    limit,
        int    offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE id NOT IN (SELECT product_id FROM catpro WHERE category_id = %d) "
                    + "AND name LIKE '%s'"
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, categoryId, "%"+keywords+"%", limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var productList = new ArrayList<Product>();
            while (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy      = result.getString("edited_by");
                var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                productList.add(record);
            }
            return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductInCategory(int categoryId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE id IN (SELECT product_id FROM catpro WHERE category_id = %d)";
            sql = String.format(sql, categoryId);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
            
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductInCategoryOrderByEditedDateDescLimitOffset(
        int categoryId,
        int limit,
        int offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE id IN (SELECT product_id FROM catpro WHERE category_id = %d)"
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, categoryId, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var productList = new ArrayList<Product>();
            while (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy      = result.getString("edited_by");
                var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                productList.add(record);
            }
            return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductByNameLikeInCategory(
        String keywords,
        int    categoryId
    ) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE id IN (SELECT product_id FROM catpro WHERE category_id = %d) "
                    + "AND name LIKE '%s'";
            sql = String.format(sql, categoryId, "%"+keywords+"%");
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
            
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductByNameLikeInCategoryOrderByEditedDateDescLimitOffset(
        String keywords,
        int    categoryId,
        int    limit,
        int    offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE id IN (SELECT product_id FROM catpro WHERE category_id = %d) "
                    + "AND name LIKE '%s'"
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, categoryId, "%"+keywords+"%", limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var productList = new ArrayList<Product>();
            while (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy      = result.getString("edited_by");
                var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                productList.add(record);
            }
            return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductNotInCollection(int collectionId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE id NOT IN (SELECT product_id FROM colpro WHERE collection_id = %d)";
            sql = String.format(sql, collectionId);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
            
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductNotInCollectionOrderByEditedDateDescLimitOffset(
        int collectionId,
        int limit,
        int offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE id NOT IN (SELECT product_id FROM colpro WHERE collection_id = %d)"
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, collectionId, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var productList = new ArrayList<Product>();
            while (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy      = result.getString("edited_by");
                var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                productList.add(record);
            }
            return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductInCollection(int collectionId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE id IN (SELECT product_id FROM colpro WHERE collection_id = %d)";
            sql = String.format(sql, collectionId);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
            
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductInCollectionOrderByEditedDateDescLimitOffset(
        int collectionId,
        int limit,
        int offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE id IN (SELECT product_id FROM colpro WHERE collection_id = %d)"
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, collectionId, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var productList = new ArrayList<Product>();
            while (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy      = result.getString("edited_by");
                var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                productList.add(record);
            }
            return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductByNameLikeInCollection(
        String keywords,
        int    collectionId
    ) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE id IN (SELECT product_id FROM colpro WHERE collection_id = %d) "
                    + "AND name LIKE '%s'";
            sql = String.format(sql, collectionId, "%"+keywords+"%");
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
            
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductByNameLikeInCollectionOrderByEditedDateDescLimitOffset(
        String keywords,
        int    collectionId,
        int    limit,
        int    offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE id IN (SELECT product_id FROM colpro WHERE collection_id = %d) "
                    + "AND name LIKE '%s'"
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, collectionId, "%"+keywords+"%", limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var productList = new ArrayList<Product>();
            while (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy      = result.getString("edited_by");
                var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                productList.add(record);
            }
            return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountProductByNameLikeNotInCollection(
        String keywords,
        int    collectionId
    ) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM product "
                    + "WHERE id NOT IN (SELECT product_id FROM colpro WHERE collection_id = %d) "
                    + "AND name LIKE '%s'";
            sql = String.format(sql, collectionId, "%"+keywords+"%");
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next())
                return result.getInt("counter");
            else
                return 0;
            
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Product> selectProductByNameLikeNotInCollectionOrderByEditedDateDescLimitOffset(
        String keywords,
        int    collectionId,
        int    limit,
        int    offset
    ) throws SQLException
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
            var sql = "SELECT id, brand_id, name, price, discount, promotion, description, specification, slug, edited_date, edited_by "
                    + "FROM product "
                    + "WHERE id NOT IN (SELECT product_id FROM colpro WHERE collection_id = %d) "
                    + "AND name LIKE '%s'"
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, collectionId, "%"+keywords+"%", limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var productList = new ArrayList<Product>();
            while (result.next()) {
                var id            = result.getInt("id");
                var brandId       = result.getInt("brand_id");
                var name          = result.getString("name");
                var price         = result.getFloat("price");
                var discount      = result.getFloat("discount");
                var promotion     = result.getString("promotion");
                var description   = result.getString("description");
                var specification = result.getString("specification");
                var slug          = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy      = result.getString("edited_by");
                var record = Product.createInstance(id, brandId, name, price, discount, promotion, description, specification, slug, editedDate, editedBy);
                productList.add(record);
            }
            return productList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
}
