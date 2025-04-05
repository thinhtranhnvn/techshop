package com.semidev.techshop.model.service;

import java.sql.SQLException;


public class CatProService {

    public static void deleteFromCatProByCategoryId(int categoryId) throws SQLException {
        try (var connection = Database.getConnection()) {
                var sql = "DELETE FROM catpro "
                        + "WHERE category_id = %d";
                sql = String.format(sql, categoryId);
                var statement = connection.prepareStatement(sql);
                statement.executeUpdate();
            }
            catch (SQLException exc) {
                throw exc;
            }
    }
    
}
