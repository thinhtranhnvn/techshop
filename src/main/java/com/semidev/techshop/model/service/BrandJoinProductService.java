package com.semidev.techshop.model.service;

import java.sql.SQLException;


public class BrandJoinProductService {
    
    public static int selectCountBrandJoinProductByBrandId(int brandId) throws SQLException {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT count(*) AS counter FROM brand "
                    + "JOIN product ON brand.id = product.brand_id "
                    + "WHERE brand.id = %d";
            sql = String.format(sql, brandId);
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
    
}
