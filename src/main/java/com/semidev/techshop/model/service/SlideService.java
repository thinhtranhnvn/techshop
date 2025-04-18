package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidSlideCaption;
import com.semidev.techshop.exception.ExceptionInvalidSlideEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidSlideId;
import com.semidev.techshop.exception.ExceptionInvalidSlideImageURL;
import com.semidev.techshop.exception.ExceptionInvalidSlidePriority;
import com.semidev.techshop.exception.ExceptionNullSlideEditedDate;
import com.semidev.techshop.exception.ExceptionNullSlideHref;
import com.semidev.techshop.model.entity.Slide;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class SlideService {

    public static int selectLatestSlideId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM slide "
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
    
    public static void insertIntoSlide(Slide record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var imageURL   = record.getImageURL();
            var caption    = record.getCaption();
            var href       = record.getHref();
            var priority   = record.getPriority();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "INSERT INTO slide "
                    + "    (id, image_url, caption, href, priority, edited_date, edited_by) "
                    + "VALUES "
                    + "    (%d,      '%s',    '%s', '%s',       %d,        '%s',      '%s')";
            sql = String.format(sql, id, imageURL, caption, href, priority, editedDate, editedBy);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountAllSlide() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM slide";
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
    
    public static ArrayList<Slide> selectAllSlideOrderByEditedDateLimitOffset(
        int limit,
        int offset
    ) throws SQLException
           , ExceptionInvalidSlideId
           , ExceptionInvalidSlideImageURL
           , ExceptionInvalidSlideCaption
           , ExceptionNullSlideHref
           , ExceptionInvalidSlidePriority
           , ExceptionNullSlideEditedDate
           , ExceptionInvalidSlideEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, image_url, caption, href, priority, edited_date, edited_by "
                    + "FROM slide "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var slideList = new ArrayList<Slide>();
            while (result.next()) {
                var id = result.getInt("id");
                var imageURL = result.getString("image_url");
                var caption = result.getString("caption");
                var href = result.getString("href");
                var priority = result.getInt("priority");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy   = result.getString("edited_by");
                var record = Slide.createInstance(id, imageURL, caption, href, priority, editedDate, editedBy);
                slideList.add(record);
            }
            return slideList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountSlideByCaptionLike(String keywords) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM slide "
                    + "WHERE caption LIKE '%s'";
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
    
    public static ArrayList<Slide> selectSlideByCaptionLikeOrderByEditedDateDescLimitOffset(
        String keywords,
        int    limit,
        int    offset
    ) throws SQLException
           , ExceptionInvalidSlideId
           , ExceptionInvalidSlideImageURL
           , ExceptionInvalidSlideCaption
           , ExceptionNullSlideHref
           , ExceptionInvalidSlidePriority
           , ExceptionNullSlideEditedDate
           , ExceptionInvalidSlideEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, image_url, caption, href, priority, edited_date, edited_by "
                    + "FROM slide "
                    + "WHERE caption LIKE '%s' "
                    + "ORDER BY edited_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, "%"+keywords+"%", limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var slideList = new ArrayList<Slide>();
            while (result.next()) {
                var id = result.getInt("id");
                var imageURL = result.getString("image_url");
                var caption = result.getString("caption");
                var href = result.getString("href");
                var priority = result.getInt("priority");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy   = result.getString("edited_by");
                var record = Slide.createInstance(id, imageURL, caption, href, priority, editedDate, editedBy);
                slideList.add(record);
            }
            return slideList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static Slide selectSlideById(int id)
        throws SQLException
             , ExceptionInvalidSlideId
             , ExceptionInvalidSlideImageURL
             , ExceptionInvalidSlideCaption
             , ExceptionNullSlideHref
             , ExceptionInvalidSlidePriority
             , ExceptionNullSlideEditedDate
             , ExceptionInvalidSlideEditedBy
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, image_url, caption, href, priority, edited_date, edited_by "
                    + "FROM slide "
                    + "WHERE id = %d";
            sql = String.format(sql, id);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var imageURL = result.getString("image_url");
                var caption = result.getString("caption");
                var href = result.getString("href");
                var priority = result.getInt("priority");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var editedDate = LocalDateTime.parse(result.getString("edited_date"), formatter);
                var editedBy   = result.getString("edited_by");
                return Slide.createInstance(id, imageURL, caption, href, priority, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void updateSlide(Slide record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var imageURL   = record.getImageURL();
            var caption    = record.getCaption();
            var href       = record.getHref();
            var priority   = record.getPriority();
            var editedDate = record.getEditedDate().toString();
            var editedBy   = record.getEditedBy();
            var sql = "UPDATE slide "
                    + "SET image_url = '%s', "
                    + "    caption = '%s', "
                    + "    href = '%s', "
                    + "    priority = %d, "
                    + "    edited_date = '%s', "
                    + "    edited_by = '%s'"
                    + "WHERE id = %d";
            sql = String.format(sql, imageURL, caption, href, priority, editedDate, editedBy, id);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void deleteFromSlide(Slide record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "DELETE FROM slide "
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
