package com.semidev.techshop.model.entity;

import java.time.LocalDateTime;


public class Brand {

    private int id;

        public int getId() {
            return this.id;
        }

        public void setId(int id) throws Exception {
            if (id < 0)
                throw new Exception("Invalid brand id");
            else
                this.id = id;
        }

    private String name;

        public String getName() {
            return this.name;
        }

        public void setName(String name) throws Exception {
            if (name == null || name.isEmpty())
                throw new Exception("Invalid brand name");
            else
                this.name = name;
        }

    private String imageURL;

        public String getImageURL() {
            return this.imageURL;
        }

        public void setImageURL(String imageURL) throws Exception {
            if (imageURL == null || imageURL.isEmpty())
                throw new Exception("Invalid brand image-URL");
            else
                this.imageURL = imageURL;
        }

    private String slug;

        public String getSlug () {
            return this.slug;
        }

        public void setSlug(String slug) throws Exception {
            if (slug == null || slug.isEmpty())
                throw new Exception("Invalid brand slug");
            else
                this.slug = slug;
        }

    private LocalDateTime editedDate;

        public LocalDateTime getEditedDate() {
            return this.editedDate;
        }

        public void setEditedDate(LocalDateTime editedDate) throws Exception {
            if (editedDate == null)
                throw new Exception("Invalid brand edited-date");
            else
                this.editedDate = editedDate;
        }

    private String editedBy;

        public String getEditedBy() {
            return this.editedBy;
        }

        public void setEditedBy(String editedBy) throws Exception {
            if (editedBy == null || editedBy.isEmpty())
                throw new Exception("Invalid brand edited-by");
            else
                this.editedBy = editedBy;
        }


    private Brand() {
        /* do nothing */;
    }

    public static Brand createInstance(
        int           id,
        String        name,
        String        imageURL,
        String        slug,
        LocalDateTime editedDate,
        String        editedBy
    ) throws Exception {
        try {
            Brand instance = new Brand();
            instance.setId(id);
            instance.setName(name);
            instance.setImageURL(imageURL);
            instance.setSlug(slug);
            instance.setEditedDate(editedDate);
            instance.setEditedBy(editedBy);
            return instance;
        }
        catch (Exception exc) {
            throw exc;
        }
    }

}
