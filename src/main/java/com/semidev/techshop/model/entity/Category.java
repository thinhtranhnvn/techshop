package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidCategoryEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidCategoryId;
import com.semidev.techshop.exception.ExceptionInvalidCategoryName;
import com.semidev.techshop.exception.ExceptionInvalidCategorySlug;
import com.semidev.techshop.exception.ExceptionNullCategoryEditedDate;

import java.time.LocalDateTime;


public class Category {
    
    private Integer id;
    
        public int getId() {
            return this.id;
        }
        
        public void setId(int id) throws ExceptionInvalidCategoryId {
            if (id < 1)
                throw new ExceptionInvalidCategoryId("Invalid category id");
            else
                this.id = id;
        }
    
    private String name;
    
        public String getName() {
            return this.name;
        }
        
        public void setName(String name) throws ExceptionInvalidCategoryName {
            if (name == null || name.isEmpty())
                throw new ExceptionInvalidCategoryName("Invalid category name");
            else
                this.name = name;
        }
    
    private String slug;
    
        public String getSlug() {
            return this.slug;
        }
        
        public void setSlug(String slug) throws ExceptionInvalidCategorySlug {
            if (slug == null || slug.isEmpty())
                throw new ExceptionInvalidCategorySlug("Invalid category slug");
            else
                this.slug = slug;
        }
    
    private LocalDateTime editedDate;
    
        public LocalDateTime getEditedDate() {
            return this.editedDate;
        }
        
        public void setEditedDate(LocalDateTime editedDate) throws ExceptionNullCategoryEditedDate {
            if (editedDate == null)
                throw new ExceptionNullCategoryEditedDate("Category edited-date cannot be null");
            else
                this.editedDate = editedDate;
        }
    
    private String editedBy;
    
        public String getEditedBy() {
            return this.editedBy;
        }
        
        public void setEditedBy(String editedBy) throws ExceptionInvalidCategoryEditedBy {
            if (editedBy == null || editedBy.isEmpty())
                throw new ExceptionInvalidCategoryEditedBy("Invalid category edited-by");
            else
                this.editedBy = editedBy;
        }
    
    public static Category createInstance(
        int           id,
        String        name,
        String        slug,
        LocalDateTime editedDate,
        String        editedBy
    ) throws ExceptionInvalidCategoryId
           , ExceptionInvalidCategoryName
           , ExceptionInvalidCategorySlug
           , ExceptionNullCategoryEditedDate
           , ExceptionInvalidCategoryEditedBy
    {
        var instance = new Category();
        instance.setId(id);
        instance.setName(name);
        instance.setSlug(slug);
        instance.setEditedDate(editedDate);
        instance.setEditedBy(editedBy);
        return instance;
    }
    
}
