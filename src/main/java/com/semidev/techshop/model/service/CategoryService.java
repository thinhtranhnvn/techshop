package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidCategoryEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidCategoryId;
import com.semidev.techshop.exception.ExceptionInvalidCategoryName;
import com.semidev.techshop.exception.ExceptionInvalidCategorySlug;
import com.semidev.techshop.exception.ExceptionNullCategoryEditedDate;
import com.semidev.techshop.model.entity.Category;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class CategoryService {

    public static Category selectCategoryBySlug(String slug)
        throws SQLException
             , ExceptionInvalidCategoryId
             , ExceptionInvalidCategoryName
             , ExceptionInvalidCategorySlug
             , ExceptionNullCategoryEditedDate
             , ExceptionInvalidCategoryEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, edited_date, edited_by "
                    + "FROM category "
                    + "WHERE slug = '%s'";
            sql = String.format(sql, slug);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var id       = result.getInt("id");
                var name     = result.getString("name");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy = result.getString("edited_by");
                return Category.createInstance(id, name, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectLatestCategoryId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM category "
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
    
    public static void insertIntoCategory(Category record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var name       = record.getName();
            var slug       = record.getSlug();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "INSERT INTO category "
                    + "    (id, name, slug, edited_date, edited_by) "
                    + "VALUES "
                    + "    (%d, '%s', '%s',        '%s',      '%s')";
            sql = String.format(sql, id, name, slug, editedDate, editedBy);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountAllCategory() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM category";
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
    
    public static ArrayList<Category> selectAllCategoryOrderByEditedDateDescLimitOffset(
        int limit,
        int offset
    ) throws SQLException
           , ExceptionInvalidCategoryId
           , ExceptionInvalidCategoryName
           , ExceptionInvalidCategorySlug
           , ExceptionNullCategoryEditedDate
           , ExceptionInvalidCategoryEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, edited_date, edited_by "
                    + "FROM category "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var categoryList = new ArrayList<Category>();
            while (result.next()) {
                var id         = result.getInt("id");
                var name       = result.getString("name");
                var slug       = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy   = result.getString("edited_by");
                var record = Category.createInstance(id, name, slug, editedDate, editedBy);
                categoryList.add(record);
            }
            return categoryList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static Category selectCategoryById(int id)
        throws SQLException
             , ExceptionInvalidCategoryId
             , ExceptionInvalidCategoryName
             , ExceptionInvalidCategorySlug
             , ExceptionNullCategoryEditedDate
             , ExceptionInvalidCategoryEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, edited_date, edited_by "
                    + "FROM category "
                    + "WHERE id = %d";
            sql = String.format(sql, id);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var name       = result.getString("name");
                var slug       = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy   = result.getString("edited_by");
                return Category.createInstance(id, name, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void updateCategory(Category record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var name       = record.getName();
            var slug       = record.getSlug();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "UPDATE category "
                    + "SET name = '%s', "
                    + "    slug = '%s', "
                    + "    edited_date = '%s', "
                    + "    edited_by = '%s' "
                    + "WHERE id = %d";
            sql = String.format(sql, name, slug, editedDate, editedBy, id);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountCategoryByNameLike(String keywords) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM category "
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
    
    public static ArrayList<Category> selectCategoryByNameLikeOrderByEditedDateDescLimitOffset(
        String keywords,
        int    limit,
        int    offset
    ) throws SQLException
           , ExceptionInvalidCategoryId
           , ExceptionInvalidCategoryName
           , ExceptionInvalidCategorySlug
           , ExceptionNullCategoryEditedDate
           , ExceptionInvalidCategoryEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, edited_date, edited_by "
                    + "FROM category "
                    + "WHERE name LIKE '%s' "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
           sql = String.format(sql, "%"+keywords+"%", limit, offset);
           var statement = connection.prepareStatement(sql);
           var result = statement.executeQuery();
           var categoryList = new ArrayList<Category>();
           while (result.next()) {
               var id         = result.getInt("id");
               var name       = result.getString("name");
               var slug       = result.getString("slug");
               var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
               var editedBy   = result.getString("edited_by");
               var record = Category.createInstance(id, name, slug, editedDate, editedBy);
               categoryList.add(record);
           }
           return categoryList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void deleteFromCategory(Category record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "DELETE FROM category "
                    + "WHERE id = %d";
            sql = String.format(sql, record.getId());
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Category> selectAllCategoryOrderByNameAsc() throws SQLException
           , ExceptionInvalidCategoryId
           , ExceptionInvalidCategoryName
           , ExceptionInvalidCategorySlug
           , ExceptionNullCategoryEditedDate
           , ExceptionInvalidCategoryEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, edited_date, edited_by "
                    + "FROM category "
                    + "ORDER BY name ASC";
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var categoryList = new ArrayList<Category>();
            while (result.next()) {
                var id         = result.getInt("id");
                var name       = result.getString("name");
                var slug       = result.getString("slug");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy   = result.getString("edited_by");
                var record = Category.createInstance(id, name, slug, editedDate, editedBy);
                categoryList.add(record);
            }
            return categoryList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
}
