package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidVisitorAddress;
import com.semidev.techshop.exception.ExceptionInvalidVisitorEmail;
import com.semidev.techshop.exception.ExceptionInvalidVisitorFullname;
import com.semidev.techshop.exception.ExceptionInvalidVisitorId;
import com.semidev.techshop.exception.ExceptionInvalidVisitorPassword;
import com.semidev.techshop.exception.ExceptionInvalidVisitorPhone;
import com.semidev.techshop.exception.ExceptionInvalidVisitorUsername;


public class Visitor {

    private Integer id;
    
        public int getId() {
            return this.id;
        }
        
        public void setId(int id) throws ExceptionInvalidVisitorId {
            if (id < 1)
                throw new ExceptionInvalidVisitorId();
            else
                this.id = id;
        }
    
    private String fullname;
    
        public String getFullname() {
            return this.fullname;
        }
        
        public void setFullname(String fullname) throws ExceptionInvalidVisitorFullname {
            if (fullname == null || fullname.isEmpty())
                throw new ExceptionInvalidVisitorFullname();
            else
                this.fullname = fullname;
        }
    
    private String username;
    
        public String getUsername() {
            return this.username;
        }
        
        public void setUsername(String username) throws ExceptionInvalidVisitorUsername {
            if (username == null || username.isEmpty())
                throw new ExceptionInvalidVisitorUsername();
            else
                this.username = username;
        }
    
    private String password;
    
        public String getPassword() {
            return this.password;
        }
        
        public void setPassword(String password) throws ExceptionInvalidVisitorPassword {
            if (password == null || password.isEmpty())
                throw new ExceptionInvalidVisitorPassword();
            else
                this.password = password;
        }
        
    private String phone;
    
        public String getPhone() {
            return this.phone;
        }
        
        public void setPhone(String phone) throws ExceptionInvalidVisitorPhone {
            if (phone == null || phone.isEmpty())
                throw new ExceptionInvalidVisitorPhone();
            else
                this.phone = phone;
        }
    
    private String email;
    
        public String getEmail() {
            return this.email;
        }
        
        public void setEmail(String email) throws ExceptionInvalidVisitorEmail {
            if (email == null || email.isEmpty())
                throw new ExceptionInvalidVisitorEmail();
            else
                this.email = email;
        }
    
    private String address;
    
        public String getAddress() {
            return this.address;
        }
        
        public void setAddress(String address) throws ExceptionInvalidVisitorAddress {
            if (address == null || address.isEmpty())
                throw new ExceptionInvalidVisitorAddress();
            else
                this.address = address;
        }
    
    public static Visitor createInstance(
        int    id,
        String fullname,
        String username,
        String password,
        String phone,
        String email,
        String address
    ) throws ExceptionInvalidVisitorId
           , ExceptionInvalidVisitorFullname
           , ExceptionInvalidVisitorUsername
           , ExceptionInvalidVisitorPassword
           , ExceptionInvalidVisitorEmail
           , ExceptionInvalidVisitorAddress
           , ExceptionInvalidVisitorPhone
    {
        var instance = new Visitor();
        instance.setId(id);
        instance.setFullname(fullname);
        instance.setUsername(username);
        instance.setPassword(password);
        instance.setPhone(phone);
        instance.setEmail(email);
        instance.setAddress(address);
        return instance;
    }
    
}
