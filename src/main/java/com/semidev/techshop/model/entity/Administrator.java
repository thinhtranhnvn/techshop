package com.semidev.techshop.model.entity;


public class Administrator {

    private int id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) throws Exception {
            if (id < 0)
                throw new Exception("Invalid admin id");
            else
                this.id = id;
        }

    private String username;

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) throws Exception {
            if (username == null || username.isEmpty())
                throw new Exception("Invalid administrator username");
            else
                this.username = username;
        }

    private String password;

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) throws Exception {
            if (password == null || password.isEmpty())
                throw new Exception("Invalid administrator password");
            else
                this.password = password;
        }

    private Administrator() {
        /* do nothing */;
    }

    public static Administrator createInstance(
        int    id,
        String username,
        String password
    ) throws Exception {
        try {
            Administrator instance = new Administrator();
            instance.setId(id);
            instance.setUsername(username);
            instance.setPassword(password);
            return instance;
        }
        catch (Exception exc) {
            throw exc;
        }
    }

}
