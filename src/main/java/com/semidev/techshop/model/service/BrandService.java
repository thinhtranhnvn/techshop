package com.semidev.techshop.model.service;

import com.semidev.techshop.model.entity.Brand;
import com.semidev.techshop.model.service.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class BrandService {

    public static void insertIntoBrand(Brand record) throws Exception {
        try (Connection connection = Database.getConnection()) {
            int id            = record.getId();
            String name       = record.getName();
            String imageURL   = record.getImageURL();
            String slug       = record.getSlug();
            String editedDate = record.getEditedDate().toString();
            String editedBy   = record.getEditedBy();

            String sql = "INSERT INTO brand "
                       + "    (id, name, image_url, slug, edited_date, edited_by) "
                       + "VALUES "
                       + "    (%d, '%s', '%s'     , '%s', '%s'       , '%s'     )";
            sql = String.format(sql, id, name, imageURL, slug, editedDate, editedBy);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        }
        catch (Exception exc) {
            throw exc;
        }
        finally {
            try {
                Database.closeConnection();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public static int selectLatestBrandId() throws Exception {
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT id "
                       + "FROM brand "
                       + "ORDER BY id DESC "
                       + "LIMIT 1";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("id");
            }
            else {
                return 0;
            }
        }
        catch (Exception exc) {
            throw exc;
        }
        finally {
            try {
                Database.closeConnection();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public static Brand selectBrandBySlug(String slug) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT id, name, image_url, slug, edited_date, edited_by "
                       + "FROM brand "
                       + "WHERE slug = '%s'";
            sql = String.format(sql, slug);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int    id       = result.getInt("id");
                String name     = result.getString("name");
                String imageURL = result.getString("image_url");

                String dateStr = result.getString("edited_date");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime editedDate = LocalDateTime.parse(dateStr, formatter);

                String editedBy = result.getString("edited_by");

                return Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (Exception exc) {
            throw exc;
        }
        finally {
            try {
                Database.closeConnection();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public static Brand selectBrandById(int id) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT id, name, image_url, slug, edited_date, edited_by "
                       + "FROM brand "
                       + "WHERE id = %d";
            sql = String.format(sql, id);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                String name     = result.getString("name");
                String imageURL = result.getString("image_url");
                String slug     = result.getString("slug");

                String dateStr = result.getString("edited_date");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime editedDate = LocalDateTime.parse(dateStr, formatter);

                String editedBy = result.getString("edited_by");

                return Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
            }
            else {
                return null;
            }
        }
        catch (Exception exc) {
            throw exc;
        }
        finally {
            try {
                Database.closeConnection();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public static ArrayList<Brand> selectBrandOrderByEditedDateDescLimitOffset(int limit, int offset) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT id, name, image_url, slug, edited_date, edited_by "
                       + "FROM brand "
                       + "ORDER BY edited_date DESC "
                       + "LIMIT %d "
                       + "OFFSET %d";
           sql = String.format(sql, limit, offset);
           PreparedStatement statement = connection.prepareStatement(sql);
           ResultSet result = statement.executeQuery();

           ArrayList<Brand> brandList = new ArrayList<Brand>();

           while (result.next()) {
               int    id       = result.getInt("id");
               String name     = result.getString("name");
               String imageURL = result.getString("image_url");
               String slug     = result.getString("slug");

               String dateStr = result.getString("edited_date");
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
               LocalDateTime editedDate = LocalDateTime.parse(dateStr, formatter);

               String editedBy = result.getString("edited_by");

               Brand record = Brand.createInstance(id, name, imageURL, slug, editedDate, editedBy);
               brandList.add(record);
           }

           return brandList;
        }
        catch (Exception exc) {
            throw exc;
        }
        finally {
            try {
                Database.closeConnection();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    public static int selectCountAllBrand() throws Exception {
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT count(*) AS counter FROM brand";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return result.getInt("counter");
            }
            else {
                return 0;
            }
        }
        catch (Exception exc) {
            throw exc;
        }
        finally {
            try {
                Database.closeConnection();
            }
            catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

}
