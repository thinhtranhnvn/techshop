package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidBrandEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidBrandEditedDate;
import com.semidev.techshop.exception.ExceptionInvalidBrandId;
import com.semidev.techshop.exception.ExceptionInvalidBrandImageURL;
import com.semidev.techshop.exception.ExceptionInvalidBrandName;
import com.semidev.techshop.exception.ExceptionInvalidBrandSlug;

import java.time.LocalDateTime;


public class Brand {

    private Integer id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) throws ExceptionInvalidBrandId {
            if (id < 1)
                throw new ExceptionInvalidBrandId();
            else
                this.id = id;
        }

    private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) throws ExceptionInvalidBrandName {
            if (name == null || name.isEmpty())
                throw new ExceptionInvalidBrandName();
            else
                this.name = name;
        }

    private String imageURL;

        public String getImageURL() {
            return this.imageURL;
        }

        public void setImageURL(String imageURL) throws ExceptionInvalidBrandImageURL {
            if (imageURL == null || imageURL.isEmpty())
                throw new ExceptionInvalidBrandImageURL();
            else
                this.imageURL = imageURL;
        }

    private String slug;

        public String getSlug () {
            return this.slug;
        }

        public void setSlug(String slug) throws ExceptionInvalidBrandSlug {
            if (slug == null || slug.isEmpty())
                throw new ExceptionInvalidBrandSlug();
            else
                this.slug = slug;
        }

    private LocalDateTime editedDate;

        public LocalDateTime getEditedDate() {
            return this.editedDate;
        }

        public void setEditedDate(LocalDateTime editedDate) throws ExceptionInvalidBrandEditedDate {
            if (editedDate == null)
                throw new ExceptionInvalidBrandEditedDate();
            else
                this.editedDate = editedDate;
        }

    private String editedBy;

        public String getEditedBy() {
            return this.editedBy;
        }

        public void setEditedBy(String editedBy) throws ExceptionInvalidBrandEditedBy {
            if (editedBy == null || editedBy.isEmpty())
                throw new ExceptionInvalidBrandEditedBy();
            else
                this.editedBy = editedBy;
        }
    
    public static Brand createInstance(
        int           id,
        String        name,
        String        imageURL,
        String        slug,
        LocalDateTime editedDate,
        String        editedBy
    ) throws ExceptionInvalidBrandId
           , ExceptionInvalidBrandName
           , ExceptionInvalidBrandImageURL
           , ExceptionInvalidBrandSlug
           , ExceptionInvalidBrandEditedDate
           , ExceptionInvalidBrandEditedBy
    {
        var instance = new Brand();
        instance.setId(id);
        instance.setName(name);
        instance.setImageURL(imageURL);
        instance.setSlug(slug);
        instance.setEditedDate(editedDate);
        instance.setEditedBy(editedBy);
        return instance;
    }
    
}
