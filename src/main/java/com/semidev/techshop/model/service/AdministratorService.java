package com.semidev.techshop.model.service;

import com.semidev.techshop.model.entity.Administrator;
import com.semidev.techshop.model.service.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class AdministratorService {

    public static Administrator selectAdministratorByLoginInfo(
        String username,
        String password
    ) throws Exception {
        try (Connection connection = Database.getConnection()) {
            String sql = "SELECT id, username, password "
                       + "FROM administrator "
                       + "WHERE username = '%s' "
                       + "AND password = '%s'";
            sql = String.format(sql, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet result = statement.executeQuery();

            if (result.next()) {
                int id = result.getInt("id");
                return Administrator.createInstance(id, username, password);
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

}
