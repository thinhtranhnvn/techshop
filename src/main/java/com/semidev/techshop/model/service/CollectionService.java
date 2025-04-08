package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidCollectionEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidCollectionId;
import com.semidev.techshop.exception.ExceptionInvalidCollectionName;
import com.semidev.techshop.exception.ExceptionInvalidCollectionPriority;
import com.semidev.techshop.exception.ExceptionInvalidCollectionSlug;
import com.semidev.techshop.exception.ExceptionNullCollectionEditedDate;
import com.semidev.techshop.model.entity.Collection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class CollectionService {
    
    public static Collection selectCollectionBySlug(String slug)
        throws SQLException
             , ExceptionInvalidCollectionId
             , ExceptionInvalidCollectionName
             , ExceptionInvalidCollectionSlug
             , ExceptionInvalidCollectionPriority
             , ExceptionNullCollectionEditedDate
             , ExceptionInvalidCollectionEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, priority, edited_date, edited_by "
                    + "FROM collection "
                    + "WHERE slug = '%s'";
            sql = String.format(sql, slug);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var id       = result.getInt("id");
                var name     = result.getString("name");
                var priority = result.getInt("priority");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy = result.getString("edited_by");
                return Collection.createInstance(id, name, slug, priority, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectLatestCollectionId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM collection "
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
    
    public static void insertIntoCollection(Collection record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var name       = record.getName();
            var slug       = record.getSlug();
            var priority    = record.getPriority();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "INSERT INTO collection "
                    + "    (id, name, slug, priority, edited_date, edited_by) "
                    + "VALUES "
                    + "    (%d, '%s', '%s',       %d,        '%s',      '%s')";
            sql = String.format(sql, id, name, slug, priority, editedDate, editedBy);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountAllCollection() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM collection";
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
    
    public static ArrayList<Collection> selectAllCollectionOrderByEditedDateDescLimitOffset(
        int limit,
        int offset
    ) throws SQLException
           , ExceptionInvalidCollectionId
           , ExceptionInvalidCollectionName
           , ExceptionInvalidCollectionSlug
           , ExceptionInvalidCollectionPriority
           , ExceptionNullCollectionEditedDate
           , ExceptionInvalidCollectionEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, priority, edited_date, edited_by "
                    + "FROM collection "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var collectionList = new ArrayList<Collection>();
            while (result.next()) {
                var id         = result.getInt("id");
                var name       = result.getString("name");
                var slug       = result.getString("slug");
                var priority   = result.getInt("priority");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy   = result.getString("edited_by");
                var record = Collection.createInstance(id, name, slug, priority, editedDate, editedBy);
                collectionList.add(record);
            }
            return collectionList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static Collection selectCollectionById(int id)
        throws SQLException
             , ExceptionInvalidCollectionId
             , ExceptionInvalidCollectionName
             , ExceptionInvalidCollectionSlug
             , ExceptionInvalidCollectionPriority
             , ExceptionNullCollectionEditedDate
             , ExceptionInvalidCollectionEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, priority, edited_date, edited_by "
                    + "FROM collection "
                    + "WHERE id = %d";
            sql = String.format(sql, id);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var name       = result.getString("name");
                var slug       = result.getString("slug");
                var priority   = result.getInt("priority");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy   = result.getString("edited_by");
                return Collection.createInstance(id, name, slug, priority, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void updateCollection(Collection record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var name       = record.getName();
            var slug       = record.getSlug();
            var priority   = record.getPriority();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "UPDATE collection "
                    + "SET name = '%s', "
                    + "    slug = '%s', "
                    + "    priority = %d, "
                    + "    edited_date = '%s', "
                    + "    edited_by = '%s' "
                    + "WHERE id = %d";
            sql = String.format(sql, name, slug, priority, editedDate, editedBy, id);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountCollectionByNameLike(String keywords) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM collection "
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
    
    public static ArrayList<Collection> selectCollectionByNameLikeOrderByEditedDateDescLimitOffset(
        String keywords,
        int    limit,
        int    offset
    ) throws SQLException
           , ExceptionInvalidCollectionId
           , ExceptionInvalidCollectionName
           , ExceptionInvalidCollectionSlug
           , ExceptionInvalidCollectionPriority
           , ExceptionNullCollectionEditedDate
           , ExceptionInvalidCollectionEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, name, slug, priority, edited_date, edited_by "
                    + "FROM collection "
                    + "WHERE name LIKE '%s' "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
           sql = String.format(sql, "%"+keywords+"%", limit, offset);
           var statement = connection.prepareStatement(sql);
           var result = statement.executeQuery();
           var collectionList = new ArrayList<Collection>();
           while (result.next()) {
               var id         = result.getInt("id");
               var name       = result.getString("name");
               var slug       = result.getString("slug");
               var priority   = result.getInt("priority");
               var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
               var editedBy   = result.getString("edited_by");
               var record = Collection.createInstance(id, name, slug, priority, editedDate, editedBy);
               collectionList.add(record);
           }
           return collectionList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void deleteFromCollection(Collection record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "DELETE FROM collection "
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
