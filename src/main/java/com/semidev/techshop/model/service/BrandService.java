package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionNullBrand;
import com.semidev.techshop.exception.ExceptionInvalidBrandEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidBrandEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidBrandImageURL;
import com.semidev.techshop.exception.ExceptionInvalidBrandName;
import com.semidev.techshop.exception.ExceptionInvalidBrandSlug;
import com.semidev.techshop.model.entity.Brand;
import java.sql.SQLException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class BrandService {

    public static void insertIntoBrand(Brand record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var name       = record.getName();
            var imageURL   = record.getImageURL();
            var slug       = record.getSlug();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "INSERT INTO brand "
                    + "    (id, name, image_url, slug, edited_date, edited_by) "
                    + "VALUES "
                    + "    (%d, '%s', '%s'     , '%s', '%s'       , '%s'     )";
            sql = String.format(sql, id, name, imageURL, slug, editedDate, editedBy);
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

    public static int selectLatestBrandId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM brand "
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

    public static Brand selectBrandBySlug(String slug)
        throws ExceptionInvalidBrandId
             , ExceptionInvalidBrandName
             , ExceptionInvalidBrandImageURL
             , ExceptionInvalidBrandEditedDate
             , ExceptionInvalidBrandEditedBy
             , ExceptionInvalidBrandSlug
             , SQLException
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, image_url, slug, edited_date, edited_by "
                    + "FROM brand "
                    + "WHERE slug = '%s'";
            sql = String.format(sql, slug);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var id         = result.getInt("id");
                var name       = result.getString("name");
                var imageURL   = result.getString("image_url");
                var dateString = result.getString("edited_date");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(dateString, formatter);
                var editedBy   = result.getString("edited_by");
                return Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (ExceptionInvalidBrandId
                | ExceptionInvalidBrandName
                | ExceptionInvalidBrandImageURL
                | ExceptionInvalidBrandSlug
                | ExceptionInvalidBrandEditedDate
                | ExceptionInvalidBrandEditedBy
                | SQLException
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

    public static Brand selectBrandById(int id)
        throws SQLException
             , ExceptionInvalidBrandId
             , ExceptionInvalidBrandName
             , ExceptionInvalidBrandImageURL
             , ExceptionInvalidBrandSlug
             , ExceptionInvalidBrandEditedDate
             , ExceptionInvalidBrandEditedBy
    {
        try (var connection = Database.getConnection()){
            var sql = "SELECT id, name, image_url, slug, edited_date, edited_by "
                    + "FROM brand "
                    + "WHERE id = %d";
            sql = String.format(sql, id);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var name       = result.getString("name");
                var imageURL   = result.getString("image_url");
                var slug       = result.getString("slug");
                var dateString = result.getString("edited_date");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(dateString, formatter);
                var editedBy   = result.getString("edited_by");
                return Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException
                | ExceptionInvalidBrandId
                | ExceptionInvalidBrandName
                | ExceptionInvalidBrandImageURL
                | ExceptionInvalidBrandSlug
                | ExceptionInvalidBrandEditedDate
                | ExceptionInvalidBrandEditedBy
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

    public static ArrayList<Brand> selectAllBrandOrderByEditedDateDescLimitOffset(
        int limit,
        int offset
    ) throws SQLException
           , ExceptionInvalidBrandId
           , ExceptionInvalidBrandName
           , ExceptionInvalidBrandImageURL
           , ExceptionInvalidBrandSlug
           , ExceptionInvalidBrandEditedDate
           , ExceptionInvalidBrandEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, image_url, slug, edited_date, edited_by "
                    + "FROM brand "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
           sql = String.format(sql, limit, offset);
           var statement = connection.prepareStatement(sql);
           var result = statement.executeQuery();
           var brandList = new ArrayList<Brand>();
           while (result.next()) {
               var id         = result.getInt("id");
               var name       = result.getString("name");
               var imageURL   = result.getString("image_url");
               var slug       = result.getString("slug");
               var dateString = result.getString("edited_date");
               var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               var editedDate = LocalDateTime.parse(dateString, formatter);
               var editedBy   = result.getString("edited_by");
               var record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
               brandList.add(record);
           }
           return brandList;
        }
        catch (SQLException
                | ExceptionInvalidBrandId
                | ExceptionInvalidBrandName
                | ExceptionInvalidBrandImageURL
                | ExceptionInvalidBrandSlug
                | ExceptionInvalidBrandEditedDate
                | ExceptionInvalidBrandEditedBy
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

    public static int selectCountAllBrand() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM brand";
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) return result.getInt("counter");
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

    public static void updateBrand(Brand record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var name       = record.getName();
            var imageURL   = record.getImageURL();
            var slug       = record.getSlug();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "UPDATE brand "
                    + "SET name = '%s', "
                    + "    image_url = '%s', "
                    + "    slug = '%s', "
                    + "    edited_date = '%s', "
                    + "    edited_by = '%s' "
                    + "WHERE id = %d";
            sql = String.format(sql, name, imageURL, slug, editedDate, editedBy, id);
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
    
    public static int selectCountBrandByNameLike(String keywords) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM brand "
                    + "WHERE name LIKE '%s'";
            sql = String.format(sql, "%"+keywords+"%");
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) return result.getInt("counter");
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
    
    public static ArrayList<Brand> selectBrandByNameLikeOrderByEditedDateDescLimitOffset(
        String keywords,
        int    limit,
        int    offset
    ) throws SQLException
           , ExceptionInvalidBrandId
           , ExceptionInvalidBrandName
           , ExceptionInvalidBrandImageURL
           , ExceptionInvalidBrandSlug
           , ExceptionInvalidBrandEditedDate
           , ExceptionInvalidBrandEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, image_url, slug, edited_date, edited_by "
                    + "FROM brand "
                    + "WHERE name LIKE '%s' "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
           sql = String.format(sql, "%"+keywords+"%", limit, offset);
           var statement = connection.prepareStatement(sql);
           var result = statement.executeQuery();
           var brandList = new ArrayList<Brand>();
           while (result.next()) {
               var id         = result.getInt("id");
               var name       = result.getString("name");
               var imageURL   = result.getString("image_url");
               var slug       = result.getString("slug");
               var dateString = result.getString("edited_date");
               var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               var editedDate = LocalDateTime.parse(dateString, formatter);
               var editedBy   = result.getString("edited_by");
               var record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
               brandList.add(record);
           }
           return brandList;
        }
        catch (SQLException
                | ExceptionInvalidBrandId
                | ExceptionInvalidBrandName
                | ExceptionInvalidBrandImageURL
                | ExceptionInvalidBrandSlug
                | ExceptionInvalidBrandEditedDate
                | ExceptionInvalidBrandEditedBy
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
    
    public static void deleteFromBrand(Brand record)
        throws ExceptionNullBrand
             , SQLException
    {
        if (record == null) {
            throw new ExceptionNullBrand("The brand pointer is null");
        }
        else {
            try (var connection = Database.getConnection()) {
                var sql = "DELETE FROM brand "
                        + "WHERE id = %d";
                sql = String.format(sql, record.getId());
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
    
    public static ArrayList<Brand> selectAllBrandOrderByNameAsc()
        throws SQLException
             , ExceptionInvalidBrandId
             , ExceptionInvalidBrandName
             , ExceptionInvalidBrandImageURL
             , ExceptionInvalidBrandSlug
             , ExceptionInvalidBrandEditedDate
             , ExceptionInvalidBrandEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, image_url, slug, edited_date, edited_by "
                    + "FROM brand "
                    + "ORDER BY name ASC";
           var statement = connection.prepareStatement(sql);
           var result = statement.executeQuery();
           var brandList = new ArrayList<Brand>();
           while (result.next()) {
               var id         = result.getInt("id");
               var name       = result.getString("name");
               var imageURL   = result.getString("image_url");
               var slug       = result.getString("slug");
               var dateString = result.getString("edited_date");
               var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               var editedDate = LocalDateTime.parse(dateString, formatter);
               var editedBy   = result.getString("edited_by");
               var record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
               brandList.add(record);
           }
           return brandList;
        }
        catch (SQLException
                | ExceptionInvalidBrandId
                | ExceptionInvalidBrandName
                | ExceptionInvalidBrandImageURL
                | ExceptionInvalidBrandSlug
                | ExceptionInvalidBrandEditedDate
                | ExceptionInvalidBrandEditedBy
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

}
