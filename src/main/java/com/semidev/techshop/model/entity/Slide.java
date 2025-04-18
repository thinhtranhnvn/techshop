package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidSlideCaption;
import com.semidev.techshop.exception.ExceptionInvalidSlideEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidSlideId;
import com.semidev.techshop.exception.ExceptionInvalidSlideImageURL;
import com.semidev.techshop.exception.ExceptionInvalidSlidePriority;
import com.semidev.techshop.exception.ExceptionNullSlideEditedDate;
import com.semidev.techshop.exception.ExceptionNullSlideHref;

import java.time.LocalDateTime;


public class Slide {
    
    private Integer id;
    
        public int getId() {
            return this.id;
        }
        
        public void setId(int id) throws ExceptionInvalidSlideId {
            if (id < 1)
                throw new ExceptionInvalidSlideId();
            else
                this.id = id;
        }
    
    private String imageURL;
    
        public String getImageURL() {
            return this.imageURL;
        }
        
        public void setImageURL(String imageURL) throws ExceptionInvalidSlideImageURL {
            if (imageURL == null || imageURL.isEmpty())
                throw new ExceptionInvalidSlideImageURL();
            else
                this.imageURL = imageURL;
        }
    
    private String caption;
    
        public String getCaption() {
            return this.caption;
        }
        
        public void setCaption(String caption) throws ExceptionInvalidSlideCaption {
            if (caption == null || caption.isEmpty())
                throw new ExceptionInvalidSlideCaption();
            else
                this.caption = caption;
        }
    
    private String href;
    
        public String getHref() {
            return this.href;
        }
        
        public void setHref(String href) throws ExceptionNullSlideHref {
            if (href == null || href.isEmpty())
                throw new ExceptionNullSlideHref();
            else
                this.href = href;
        }
    
    private Integer priority;
    
        public int getPriority() {
            return this.priority;
        }
        
        public void setPriority(int priority) throws ExceptionInvalidSlidePriority {
            if (priority < 0)
                throw new ExceptionInvalidSlidePriority();
            else
                this.priority = priority;
        }
    
    private LocalDateTime editedDate;
    
        public LocalDateTime getEditedDate() {
            return this.editedDate;
        }
        
        public void setEditedDate(LocalDateTime editedDate) throws ExceptionNullSlideEditedDate {
            if (editedDate == null)
                throw new ExceptionNullSlideEditedDate();
            else
                this.editedDate = editedDate;
        }
    
    private String editedBy;
    
        public String getEditedBy() {
            return this.editedBy;
        }
        
        public void setEditedBy(String editedBy) throws ExceptionInvalidSlideEditedBy {
            if (editedBy == null || editedBy.isEmpty())
                throw new ExceptionInvalidSlideEditedBy();
            else
                this.editedBy = editedBy;
        }
    
    public static Slide createInstance(
        int           id,
        String        imageURL,
        String        caption,
        String        href,
        Integer       priority,
        LocalDateTime editedDate,
        String        editedBy
    ) throws ExceptionInvalidSlideId
           , ExceptionInvalidSlideImageURL
           , ExceptionInvalidSlideCaption
           , ExceptionNullSlideHref
           , ExceptionInvalidSlidePriority
           , ExceptionNullSlideEditedDate
           , ExceptionInvalidSlideEditedBy
    {
        var instance = new Slide();
        instance.setId(id);
        instance.setImageURL(imageURL);
        instance.setCaption(caption);
        instance.setHref(href);
        instance.setPriority(priority);
        instance.setEditedDate(editedDate);
        instance.setEditedBy(editedBy);
        return instance;
    }
    
}
