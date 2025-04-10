package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidPageContent;
import com.semidev.techshop.exception.ExceptionInvalidPageEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidPageId;
import com.semidev.techshop.exception.ExceptionInvalidPageMenuName;
import com.semidev.techshop.exception.ExceptionInvalidPagePriority;
import com.semidev.techshop.exception.ExceptionInvalidPageSlug;
import com.semidev.techshop.exception.ExceptionInvalidPageTitle;
import com.semidev.techshop.exception.ExceptionNullPageEditedDate;
import com.semidev.techshop.model.entity.Page;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PageService {

    public static Page selectPageBySlug(String slug)
        throws SQLException
             , ExceptionInvalidPageId
             , ExceptionInvalidPageTitle
             , ExceptionInvalidPageMenuName
             , ExceptionInvalidPageContent
             , ExceptionInvalidPageSlug
             , ExceptionInvalidPagePriority
             , ExceptionNullPageEditedDate
             , ExceptionInvalidPageEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, title, menu_name, content, slug, priority, edited_date, edited_by "
                    + "FROM page "
                    + "WHERE slug = '%s'";
            sql = String.format(sql, slug);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var id       = result.getInt("id");
                var title    = result.getString("title");
                var menuName = result.getString("menu_name");
                var content  = result.getString("content");
                var priority = result.getInt("priority");
                var dateString = result.getString("edited_date");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(dateString, formatter);
                var editedBy = result.getString("edited_by");
                return Page.createInstance(id, title, menuName, content, slug, priority, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectLatestPageId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM page "
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
    
    public static void insertIntoPage(Page record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var title      = record.getTitle();
            var menuName   = record.getMenuName();
            var content    = record.getContent();
            var slug       = record.getSlug();
            var priority   = record.getPriority();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "INSERT INTO page "
                    + "    (id, title, menu_name, content, slug, priority, edited_date, edited_by) "
                    + "VALUES "
                    + "    (%d,  '%s',      '%s',    '%s', '%s',       %d,        '%s',      '%s')";
            sql = String.format(sql, id, title, menuName, content, slug, priority, editedDate, editedBy);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountAllPage() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM page";
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
    
    public static ArrayList<Page> selectAllPageOrderByEditedDateDescLimitOffset(
        int limit,
        int offset
    ) throws SQLException
           , ExceptionInvalidPageId
           , ExceptionInvalidPageTitle
           , ExceptionInvalidPageMenuName
           , ExceptionInvalidPageContent
           , ExceptionInvalidPageSlug
           , ExceptionInvalidPagePriority
           , ExceptionNullPageEditedDate
           , ExceptionInvalidPageEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, title, menu_name, content, slug, priority, edited_date, edited_by "
                    + "FROM page "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var pageList = new ArrayList<Page>();
            while (result.next()) {
                var id       = result.getInt("id");
                var title    = result.getString("title");
                var menuName = result.getString("menu_name");
                var content  = result.getString("content");
                var slug     = result.getString("slug");
                var priority = result.getInt("priority");
                var dateString = result.getString("edited_date");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(dateString, formatter);
                var editedBy = result.getString("edited_by");
                var record = Page.createInstance(id, title, menuName, content, slug, priority, editedDate, editedBy);
                pageList.add(record);
            }
            return pageList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountPageByTitleLike(String keywords) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM page "
                    + "WHERE title LIKE '%s'";
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
    
    public static ArrayList<Page> selectPageByTitleLikeOrderByEditedDateDescLimitOffset(
        String keywords,
        int    limit,
        int    offset
    ) throws SQLException
           , ExceptionInvalidPageId
           , ExceptionInvalidPageTitle
           , ExceptionInvalidPageMenuName
           , ExceptionInvalidPageContent
           , ExceptionInvalidPageSlug
           , ExceptionInvalidPagePriority
           , ExceptionNullPageEditedDate
           , ExceptionInvalidPageEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, title, menu_name, content, slug, priority, edited_date, edited_by "
                    + "FROM page "
                    + "WHERE title LIKE '%s' "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, "%"+keywords+"%", limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var pageList = new ArrayList<Page>();
            while (result.next()) {
                var id       = result.getInt("id");
                var title    = result.getString("title");
                var menuName = result.getString("menu_name");
                var content  = result.getString("content");
                var slug     = result.getString("slug");
                var priority = result.getInt("priority");
                var dateString = result.getString("edited_date");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(dateString, formatter);
                var editedBy = result.getString("edited_by");
                var record = Page.createInstance(id, title, menuName, content, slug, priority, editedDate, editedBy);
                pageList.add(record);
            }
            return pageList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static Page selectPageById(int id)
        throws SQLException
             , ExceptionInvalidPageId
             , ExceptionInvalidPageTitle
             , ExceptionInvalidPageMenuName
             , ExceptionInvalidPageContent
             , ExceptionInvalidPageSlug
             , ExceptionInvalidPagePriority
             , ExceptionNullPageEditedDate
             , ExceptionInvalidPageEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, title, menu_name, content, slug, priority, edited_date, edited_by "
                    + "FROM page "
                    + "WHERE id = %d";
            sql = String.format(sql, id);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var title    = result.getString("title");
                var menuName = result.getString("menu_name");
                var content  = result.getString("content");
                var slug     = result.getString("slug");
                var priority = result.getInt("priority");
                var dateString = result.getString("edited_date");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(dateString, formatter);
                var editedBy = result.getString("edited_by");
                return Page.createInstance(id, title, menuName, content, slug, priority, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void updatePage(Page record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var title      = record.getTitle();
            var menuName   = record.getMenuName();
            var content    = record.getContent();
            var slug       = record.getSlug();
            var priority    = record.getPriority();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "UPDATE page "
                    + "SET title = '%s', "
                    + "    menu_name = '%s', "
                    + "    content = '%s', "
                    + "    slug = '%s', "
                    + "    priority = %d, "
                    + "    edited_date = '%s', "
                    + "    edited_by = '%s' "
                    + "WHERE id = %d";
            sql = String.format(sql, title, menuName, content, slug, priority, editedDate, editedBy, id);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void deleteFromPage(Page record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "DELETE FROM page "
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
