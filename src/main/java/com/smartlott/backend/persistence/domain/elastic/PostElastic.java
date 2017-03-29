package com.smartlott.backend.persistence.domain.elastic;

import com.smartlott.backend.persistence.domain.backend.Category;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;
import java.util.List;

/**
 * Created by greenlucky on 2/3/17.
 */
@Document(indexName = "smartlott", type = "post",shards = 1, replicas = 0)
public class PostElastic {

    @Id
    private long id;

    private String title;

    private List<Category> categories;

    private boolean published = true;

    public PostElastic() {
    }

    public PostElastic(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    @Override
    public String toString() {
        return "PostElastic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", postCategoryElastics=" + categories +
                ", published=" + published +
                '}';
    }

}
