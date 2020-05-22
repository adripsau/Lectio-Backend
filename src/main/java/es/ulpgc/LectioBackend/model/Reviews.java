package es.ulpgc.LectioBackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "reviews")
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "book_id", nullable = false)
    private long book_id;

    @Column(name = "user_id", nullable = false)
    private long user_id;

    @Size(min = 3)
    @Column(name = "comment", nullable = false)
    private String comment;

    @Min(1)
    @Max(5)
    @Column(name = "punctuation", nullable = false)
    private long punctuation;

    @Column(name = "user_name")
    private String user_name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created_at;

    public Reviews() {
    }

    public Reviews(long book_id, long user_id, String comment, long punctuation, String user_name) {
        this.book_id = book_id;
        this.user_id = user_id;
        this.comment = comment;
        this.punctuation = punctuation;
        this.user_name = user_name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(long punctuation) {
        this.punctuation = punctuation;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getCreated_at() {
        return Timestamp.from(created_at.toInstant().plusSeconds(3600));
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }
}
