package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidVisitorAddress;
import com.semidev.techshop.exception.ExceptionInvalidVisitorEmail;
import com.semidev.techshop.exception.ExceptionInvalidVisitorFullname;
import com.semidev.techshop.exception.ExceptionInvalidVisitorId;
import com.semidev.techshop.exception.ExceptionInvalidVisitorPassword;
import com.semidev.techshop.exception.ExceptionInvalidVisitorPhone;
import com.semidev.techshop.exception.ExceptionInvalidVisitorUsername;
import com.semidev.techshop.model.entity.Visitor;
import java.sql.SQLException;
import java.util.ArrayList;


public class VisitorService {

    public static Visitor selectVisitorByUsername(String username)
        throws SQLException
             , ExceptionInvalidVisitorId
             , ExceptionInvalidVisitorFullname
             , ExceptionInvalidVisitorUsername
             , ExceptionInvalidVisitorPassword
             , ExceptionInvalidVisitorEmail
             , ExceptionInvalidVisitorAddress
             , ExceptionInvalidVisitorPhone
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, fullname, username, password, phone, email, address "
                    + "FROM visitor "
                    + "WHERE username = '%s'";
            sql = String.format(sql, username);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var id       = result.getInt("id");
                var fullname = result.getString("fullname");
                var password = result.getString("password");
                var phone    = result.getString("phone");
                var email    = result.getString("email");
                var address  = result.getString("address");
                return Visitor.createInstance(id, fullname, username, password, phone, email, address);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectLatestVisitorId() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id "
                    + "FROM visitor "
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
    
    public static void insertIntoVisitor(Visitor record) throws SQLException {
        try (var connection = Database.getConnection()) {
            var id       = record.getId();
            var fullname = record.getFullname();
            var username = record.getUsername();
            var password = record.getPassword();
            var phone    = record.getPhone();
            var email    = record.getEmail();
            var address  = record.getAddress();
            var sql = "INSERT INTO visitor "
                    + "    (id, fullname, username, password, phone, email, address) "
                    + "VALUES "
                    + "    (%d,     '%s',     '%s',     '%s',  '%s',  '%s',    '%s')";
            sql = String.format(sql, id, fullname, username, password, phone, email, address);
            var statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static Visitor selectVisitorByUsernameAndPassword(
        String username,
        String password
    ) throws SQLException
           , ExceptionInvalidVisitorId
           , ExceptionInvalidVisitorFullname
           , ExceptionInvalidVisitorUsername
           , ExceptionInvalidVisitorPassword
           , ExceptionInvalidVisitorEmail
           , ExceptionInvalidVisitorAddress
           , ExceptionInvalidVisitorPhone
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, fullname, username, password, phone, email, address "
                    + "FROM visitor "
                    + "WHERE username = '%s' "
                    + "AND password = '%s'";
            sql = String.format(sql, username, password);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var id       = result.getInt("id");
                var fullname = result.getString("fullname");
                var phone    = result.getString("phone");
                var email    = result.getString("email");
                var address  = result.getString("address");
                return Visitor.createInstance(id, fullname, username, password, phone, email, address);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static int selectCountAllVisitor() throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM visitor";
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
    
    public static ArrayList<Visitor> selectAllVisitorOrderByIdAscLimitOffset(
        int limit,
        int offset
    ) throws SQLException
           , ExceptionInvalidVisitorId
           , ExceptionInvalidVisitorFullname
           , ExceptionInvalidVisitorUsername
           , ExceptionInvalidVisitorPassword
           , ExceptionInvalidVisitorEmail
           , ExceptionInvalidVisitorAddress
           , ExceptionInvalidVisitorPhone
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, fullname, username, password, phone, email, address "
                    + "FROM visitor "
                    + "ORDER BY id ASC "
                    + "LIMIT %d "
                    + "OFFSET %d";
            sql = String.format(sql, limit, offset);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            var visitorList = new ArrayList<Visitor>();
            while (result.next()) {
                var id       = result.getInt("id");
                var fullname = result.getString("fullname");
                var username = result.getString("username");
                var password = result.getString("password");
                var phone    = result.getString("phone");
                var email    = result.getString("email");
                var address  = result.getString("address");
                var record = Visitor.createInstance(id, fullname, username, password, phone, email, address);
                visitorList.add(record);
            }
            return visitorList;
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
    public static Visitor selectVisitorById(int id)
        throws SQLException
           , ExceptionInvalidVisitorId
           , ExceptionInvalidVisitorFullname
           , ExceptionInvalidVisitorUsername
           , ExceptionInvalidVisitorPassword
           , ExceptionInvalidVisitorEmail
           , ExceptionInvalidVisitorAddress
           , ExceptionInvalidVisitorPhone
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, fullname, username, password, phone, email, address "
                    + "FROM visitor "
                    + "WHERE id = %d";
            sql = String.format(sql, id);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var username = result.getString("username");
                var password = result.getString("password");
                var fullname = result.getString("fullname");
                var phone    = result.getString("phone");
                var email    = result.getString("email");
                var address  = result.getString("address");
                return Visitor.createInstance(id, fullname, username, password, phone, email, address);
            }
            else {
                return null;
            }
        }
        catch (SQLException exc) {
            throw exc;
        }
    }
    
}
