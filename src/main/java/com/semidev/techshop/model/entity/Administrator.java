package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidAdministratorId;
import com.semidev.techshop.exception.ExceptionInvalidAdministratorUsername;
import com.semidev.techshop.exception.ExceptionInvalidAdministratorPassword;


public class Administrator {

    private int id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) throws ExceptionInvalidAdministratorId {
            if (id < 1)
                throw new ExceptionInvalidAdministratorId();
            else
                this.id = id;
        }

    private String username;

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) throws ExceptionInvalidAdministratorUsername {
            if (username == null || username.isEmpty())
                throw new ExceptionInvalidAdministratorUsername();
            else
                this.username = username;
        }

    private String password;

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) throws ExceptionInvalidAdministratorPassword {
            if (password == null || password.isEmpty())
                throw new ExceptionInvalidAdministratorPassword();
            else
                this.password = password;
        }
        
    public static Administrator createInstance(
        int    id,
        String username,
        String password
    ) throws ExceptionInvalidAdministratorId
           , ExceptionInvalidAdministratorUsername
           , ExceptionInvalidAdministratorPassword
    {
        var instance = new Administrator();
        instance.setId(id);
        instance.setUsername(username);
        instance.setPassword(password);
        return instance;
    }
    
}
