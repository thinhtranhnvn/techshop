package com.semidev.techshop.model.entity;

import com.semidev.techshop.exception.ExceptionInvalidPageContent;
import com.semidev.techshop.exception.ExceptionInvalidPageEditedBy;
import com.semidev.techshop.exception.ExceptionInvalidPageId;
import com.semidev.techshop.exception.ExceptionInvalidPageMenuName;
import com.semidev.techshop.exception.ExceptionInvalidPageSlug;
import com.semidev.techshop.exception.ExceptionInvalidPageTitle;
import com.semidev.techshop.exception.ExceptionInvalidPagePriority;
import com.semidev.techshop.exception.ExceptionNullPageEditedDate;

import java.time.LocalDateTime;


public class Page {

    private Integer id;
    
        public int getId() {
            return this.id;
        }
        
        public void setId(int id) throws ExceptionInvalidPageId {
            if (id < 1)
                throw new ExceptionInvalidPageId();
            else
                this.id = id;
        }
    
    private String title;
    
        public String getTitle() {
            return this.title;
        }
        
        public void setTitle(String title) throws ExceptionInvalidPageTitle {
            if (title == null || title.isEmpty())
                throw new ExceptionInvalidPageTitle();
            else
                this.title = title;
        }
    
    private String menuName;
    
        public String getMenuName() {
            return this.menuName;
        }
        
        public void setMenuName(String menuName) throws ExceptionInvalidPageMenuName {
            if (menuName == null || menuName.isEmpty())
                throw new ExceptionInvalidPageMenuName();
            else
                this.menuName = menuName;
        }
    
    private String content;
    
        public String getContent() {
            return this.content;
        }
        
        public void setContent(String content) throws ExceptionInvalidPageContent {
            if (content == null || content.isEmpty())
                throw new ExceptionInvalidPageContent();
            else
                this.content = content;
        }
    
    private String slug;
    
        public String getSlug() {
            return this.slug;
        }
        
        public void setSlug(String slug) throws ExceptionInvalidPageSlug {
            if (slug == null || slug.isEmpty())
                throw new ExceptionInvalidPageSlug();
            else
                this.slug = slug;
        }
    
    private Integer priority;
    
        public int getPriority() {
            return this.priority;
        }
        
        public void setPriority(int priority) throws ExceptionInvalidPagePriority {
            if (priority < 0)
                throw new ExceptionInvalidPagePriority();
            else
                this.priority = priority;
        }
    
    private LocalDateTime editedDate;
    
        public LocalDateTime getEditedDate() {
            return this.editedDate;
        }
        
        public void setEditedDate(LocalDateTime editedDate) throws ExceptionNullPageEditedDate {
            if (editedDate == null)
                throw new ExceptionNullPageEditedDate();
            else
                this.editedDate = editedDate;
        }
    
    private String editedBy;
    
        public String getEditedBy() {
            return this.editedBy;
        }
        
        public void setEditedBy(String editedBy) throws ExceptionInvalidPageEditedBy {
            if (editedBy == null || editedBy.isEmpty())
                throw new ExceptionInvalidPageEditedBy();
            else
                this.editedBy = editedBy;
        }
    
    public static Page createInstance(
        int           id,
        String        title,
        String        menuName,
        String        content,
        String        slug,
        int           priority,
        LocalDateTime editedDate,
        String        editedBy
    ) throws ExceptionInvalidPageId
           , ExceptionInvalidPageTitle
           , ExceptionInvalidPageMenuName
           , ExceptionInvalidPageContent
           , ExceptionInvalidPageSlug
           , ExceptionInvalidPagePriority
           , ExceptionNullPageEditedDate
           , ExceptionInvalidPageEditedBy
    {
        var instance = new Page();
        instance.setId(id);
        instance.setTitle(title);
        instance.setMenuName(menuName);
        instance.setContent(content);
        instance.setSlug(slug);
        instance.setPriority(priority);
        instance.setEditedDate(editedDate);
        instance.setEditedBy(editedBy);
        return instance;
    }
    
}
