package ru.itmo.wp.domain;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Sort;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import java.util.Set;

/** @noinspection unused*/
@Entity
@Table(indexes = @Index(columnList = "creationTime"))
public class Post {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 60)
//    @Pattern(regexp = "^/S+.*", message = "Stroke must be begin with not whitespace")
    private String title;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 65000)
//    @Pattern(regexp = "^/S+.*", message = "Stroke must be begin with not whitespace")
    @Lob
    private String text;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "post_tag",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy("id desc")
    private List<Comment> comments;

    @CreationTimestamp
    private Date creationTime;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public void addComment(Comment comment) {
        comment.setPost(this);
        getComments().add(comment);
    }
}
