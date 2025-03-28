package com.semidev.techshop.model.service;

import com.semidev.techshop.exception.ExceptionInvalidAdministratorId;
import com.semidev.techshop.exception.ExceptionInvalidAdministratorPassword;
import com.semidev.techshop.exception.ExceptionInvalidAdministratorUsername;
import com.semidev.techshop.model.entity.Administrator;
import java.sql.SQLException;


public class AdministratorService {

    public static Administrator selectAdministratorByLoginInfo(
        String username,
        String password
    ) throws SQLException
           , ExceptionInvalidAdministratorId
           , ExceptionInvalidAdministratorUsername
           , ExceptionInvalidAdministratorPassword
    {
        try (var connection = Database.getConnection()) {
            var sql = "SELECT id, username, password "
                    + "FROM administrator "
                    + "WHERE username = '%s' "
                    + "AND password = '%s'";
            sql = String.format(sql, username, password);
            var statement = connection.prepareStatement(sql);
            var result = statement.executeQuery();
            if (result.next()) {
                var id = result.getInt("id");
                return Administrator.createInstance(id, username, password);
            }
            else {
                return null;
            }
        }
        catch (SQLException
                | ExceptionInvalidAdministratorId
                | ExceptionInvalidAdministratorUsername
                | ExceptionInvalidAdministratorPassword
                exc
        ) {
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
