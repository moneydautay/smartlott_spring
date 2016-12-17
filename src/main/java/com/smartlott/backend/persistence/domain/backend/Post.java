package com.smartlott.backend.persistence.domain.backend;

import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "post")
public class Post implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "post_id")
    private long id;

    private String title;

    @Column(unique = true)
    private String slug;

    private String content;

    @Column(name = "post_date")
    private LocalDateTime postDate;

    @Column(name = "publish_date")
    private LocalDateTime publishDate;

    @Column(name = "post_edit_date")
    private LocalDateTime postEditDate;

    @Value("true")
    private boolean status;

    @Column(name = "featured_image")
    private String featuredImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_by")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CategoryHasPost> categoryPosts;

    public Post() {
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

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(LocalDateTime postDate) {
        this.postDate = postDate;
    }

    public LocalDateTime getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDateTime publishDate) {
        this.publishDate = publishDate;
    }

    public LocalDateTime getPostEditDate() {
        return postEditDate;
    }

    public void setPostEditDate(LocalDateTime postEditDate) {
        this.postEditDate = postEditDate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<CategoryHasPost> getCategoryPosts() {
        return categoryPosts;
    }

    public void setCategoryPosts(Set<CategoryHasPost> categoryPosts) {
        this.categoryPosts = categoryPosts;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", content='" + content + '\'' +
                ", postDate=" + postDate +
                ", publishDate=" + publishDate +
                ", postEditDate=" + postEditDate +
                ", status=" + status +
                ", featuredImage='" + featuredImage + '\'' +
                ", user=" + user +
                ", categoryPosts=" + categoryPosts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != post.id) return false;
        return slug != null ? slug.equals(post.slug) : post.slug == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (slug != null ? slug.hashCode() : 0);
        return result;
    }
}
