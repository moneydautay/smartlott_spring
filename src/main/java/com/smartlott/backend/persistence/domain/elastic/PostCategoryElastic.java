package com.smartlott.backend.persistence.domain.elastic;

import com.smartlott.backend.persistence.domain.backend.Category;

/**
 * Created by greenlucky on 2/3/17.
 */
public class PostCategoryElastic {

    private int id;
    private String title;
    private String description;


    public PostCategoryElastic() {
    }

    public PostCategoryElastic(Category category){
        this.id = category.getId();
        this.title = category.getTitle();
        this.description = category.getDescription();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "PostCategoryElastic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
