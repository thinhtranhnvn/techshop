package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidPurchaseId;
import com.semidev.techshop.exception.ExceptionInvalidPurchaseStatus;
import com.semidev.techshop.exception.ExceptionInvalidVisitorId;
import com.semidev.techshop.exception.ExceptionNullPurchasePlacedDate;
import com.semidev.techshop.model.entity.Purchase;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class PurchaseService {
    
    public static int selectLatestPurchaseId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM purchase "
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
    
    public static void insertIntoPurchase(Purchase record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var visitorId  = record.getVisitorId();
            var placedDate = record.getPlacedDate().toString();
            var status     = record.getStatus();
            var sql = "INSERT INTO purchase "
                    + "    (id, visitor_id, placed_date, status) "
                    + "VALUES "
                    + "    (%d,         %d,        '%s',     %d)";
            sql = String.format(sql, id, visitorId, placedDate, status);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static ArrayList<Purchase> selectPurchaseByVisitorId(int visitorId)
        throws SQLException
             , ExceptionInvalidPurchaseId
             , ExceptionNullPurchasePlacedDate
             , ExceptionInvalidPurchaseStatus
             , ExceptionInvalidVisitorId
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, visitor_id, placed_date, status "
                    + "FROM purchase "
                    + "WHERE visitor_id = %d";
            sql = String.format(sql, visitorId);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var purchaseList = new ArrayList<Purchase>();
            while (result.next()) {
                var id         = result.getInt("id");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var placedDate = LocalDateTime.parse(result.getString("placed_date"), formatter);
                var status     = result.getInt("status");
                var record = Purchase.createInstance(id, visitorId, placedDate, status);
                purchaseList.add(record);
            }
            return purchaseList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static Purchase selectPurchaseById(int id)
        throws SQLException
             , ExceptionInvalidPurchaseId
             , ExceptionNullPurchasePlacedDate
             , ExceptionInvalidPurchaseStatus
             , ExceptionInvalidVisitorId
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, visitor_id, placed_date, status "
                    + "FROM purchase "
                    + "WHERE id = %d";
            sql = String.format(sql, id);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var visitorId  = result.getInt("visitor_id");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var placedDate = LocalDateTime.parse(result.getString("placed_date"), formatter);
                var status     = result.getInt("status");
                return Purchase.createInstance(id, visitorId, placedDate, status);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountAllPurchase() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM purchase";
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
    
    public static ArrayList<Purchase> selectAllPurchaseOrderByPlacedDateDescLimitOffset(
        int limit,
        int offset
    ) throws SQLException
           , ExceptionInvalidPurchaseId
           , ExceptionNullPurchasePlacedDate
           , ExceptionInvalidPurchaseStatus
           , ExceptionInvalidVisitorId
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, visitor_id, placed_date, status "
                    + "FROM purchase "
                    + "ORDER BY placed_date DESC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var purchaseList = new ArrayList<Purchase>();
            while (result.next()) {
                var id        = result.getInt("id");
                var visitorId = result.getInt("visitor_id");
                var formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                var placedDate = LocalDateTime.parse(result.getString("placed_date"), formatter);
                var status    = result.getInt("status");
                var record = Purchase.createInstance(id, visitorId, placedDate, status);
                purchaseList.add(record);
            }
            return purchaseList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static void updatePurchase(Purchase record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id         = record.getId();
            var visitorId  = record.getVisitorId();
            var placedDate = record.getPlacedDate().toString();
            var status     = record.getStatus();
            var sql = "UPDATE purchase "
                    + "SET visitor_id = %d, "
                    + "    placed_date = '%s', "
                    + "    status = %d "
                    + "WHERE id = %d";
            sql = String.format(sql, visitorId, placedDate, status, id);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
}
