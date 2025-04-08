package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidCollectionEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidCollectionId;
import com.semidev.techshop.exception.ExceptionInvalidCollectionSlug;
import com.semidev.techshop.exception.ExceptionInvalidCollectionName;
import com.semidev.techshop.exception.ExceptionInvalidCollectionPriority;
import com.semidev.techshop.exception.ExceptionNullCollectionEditedDate;

import java.time.LocalDateTime;


public class Collection {
    
    private Integer id;
    
        public int getId() {
            return this.id;
        }
        
        public void setId(int id) throws ExceptionInvalidCollectionId {
            if (id < 1)
                throw new ExceptionInvalidCollectionId("Invalid collection id");
            else
                this.id = id;
        }
    
    private String name;
    
        public String getName() {
            return this.name;
        }
        
        public void setName(String name) throws ExceptionInvalidCollectionName {
            if (name == null || name.isEmpty())
                throw new ExceptionInvalidCollectionName("Invalid collection name");
            else
                this.name = name;
        }
    
    private String slug;
    
        public String getSlug() {
            return this.slug;
        }
        
        public void setSlug(String slug) throws ExceptionInvalidCollectionSlug {
            if (slug == null || slug.isEmpty())
                throw new ExceptionInvalidCollectionSlug("Invalid collection slug");
            else
                this.slug = slug;
        }
        
    private Integer priority;
            
        public int getPriority() {
            return this.priority;
        }
        
        public void setPriority(int priority) throws ExceptionInvalidCollectionPriority {
            if (priority < 0)
                throw new ExceptionInvalidCollectionPriority("Invalid collection priority");
            else
                this.priority = priority;
        }
    
    private LocalDateTime editedDate;
    
        public LocalDateTime getEditedDate() {
            return this.editedDate;
        }
        
        public void setEditedDate(LocalDateTime editedDate) throws ExceptionNullCollectionEditedDate {
            if (editedDate == null)
                throw new ExceptionNullCollectionEditedDate("Collection edited-date cannot be null");
            else
                this.editedDate = editedDate;
        }
    
    private String editedBy;
    
        public String getEditedBy() {
            return this.editedBy;
        }
        
        public void setEditedBy(String editedBy) throws ExceptionInvalidCollectionEditedBy {
            if (editedBy == null || editedBy.isEmpty())
                throw new ExceptionInvalidCollectionEditedBy("Invalid collection edited-by");
            else
                this.editedBy = editedBy;
        }
    
    public static Collection createInstance(
        int           id,
        String        name,
        String        slug,
        int           priority,
        LocalDateTime editedDate,
        String        editedBy
    ) throws ExceptionInvalidCollectionId
           , ExceptionInvalidCollectionName
           , ExceptionInvalidCollectionSlug
           , ExceptionInvalidCollectionPriority
           , ExceptionNullCollectionEditedDate
           , ExceptionInvalidCollectionEditedBy
    {
        var instance = new Collection();
        instance.setId(id);
        instance.setName(name);
        instance.setSlug(slug);
        instance.setPriority(priority);
        instance.setEditedDate(editedDate);
        instance.setEditedBy(editedBy);
        return instance;
    }
    
}
