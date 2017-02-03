package com.smartlott.backend.persistence.domain.backend;

import com.smartlott.backend.persistence.converters.LocalDateTimeAttributeConverter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Mrs Hoang on 17/12/2016.
 */
@Entity
@Table(name = "post")
@DynamicUpdate(value = true)
@SelectBeforeUpdate(value = true)
public class Post implements Serializable{

    /** The Serial Version UID for Serializable classes */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;

    @Column(unique = true)
    private String slug;

    private String content;

    @Column(name = "post_date", updatable = false)
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime postDate;

    @Column(name = "publish_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime publishDate;

    @Column(name = "post_edit_date")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime postEditDate;

    private boolean status=true;

    @Column(name = "featured_image")
    private String featuredImage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_by")
    private User user;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER, targetEntity = Category.class)
    @JoinTable(name = "post_category",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
    private List<Category> categories;

    private String excerpt;

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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slug='" + slug + '\'' +
                ", content='" + content + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", postDate=" + postDate +
                ", publishDate=" + publishDate +
                ", postEditDate=" + postEditDate +
                ", status=" + status +
                ", featuredImage='" + featuredImage + '\'' +
                ", categoryPosts=" + categories +
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
