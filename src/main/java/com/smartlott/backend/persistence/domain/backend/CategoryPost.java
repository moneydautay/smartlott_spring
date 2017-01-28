package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
public class CategoryPost implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category categoryPost;

    public CategoryPost() {
    }

    public CategoryPost(Post post, Category categoryPost) {
        this.post = post;
        this.categoryPost = categoryPost;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Category getCategoryPost() {
        return categoryPost;
    }

    public void setCategoryPost(Category categoryPost) {
        this.categoryPost = categoryPost;
    }

    @Override
    public String toString() {
        return "CategoryPost{" +
                ", post=" + post +
                ", categoryPost=" + categoryPost +
                '}';
    }
}
