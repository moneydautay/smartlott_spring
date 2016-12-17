package com.smartlott.backend.persistence.domain.backend;

import javax.persistence.*;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "category_has_post")
public class CategoryHasPost {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private CategoryPost categoryPost;

    public CategoryHasPost() {
    }

    public CategoryHasPost(Post post, CategoryPost categoryPost) {
        this.post = post;
        this.categoryPost = categoryPost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public CategoryPost getCategoryPost() {
        return categoryPost;
    }

    public void setCategoryPost(CategoryPost categoryPost) {
        this.categoryPost = categoryPost;
    }

    @Override
    public String toString() {
        return "CategoryHasPost{" +
                "id=" + id +
                ", post=" + post +
                ", categoryPost=" + categoryPost +
                '}';
    }
}
