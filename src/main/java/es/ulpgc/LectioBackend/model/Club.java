package es.ulpgc.LectioBackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3)
    @Column(name = "club_name", nullable = false)
    private String club_name;

    @Size(min = 3)
    @Column(name = "club_description", nullable = false)
    private String club_description;

    @Column(name = "book_id", nullable = true)
    private Long book_id;

    @Column(name = "creator", nullable = false)
    private long creator;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "read_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date read_time;

    @Column(name = "num_subscribers", nullable = false)
    private long num_subscribers;

    public Club() {
    }

    public Club(String club_name, String club_description, Long book_id, long creator, Date read_time, long num_subscribers) {
        this.club_name = club_name;
        this.club_description = club_description;
        this.book_id = book_id;
        this.creator = creator;
        this.read_time = read_time;
        this.num_subscribers = num_subscribers;
    }

    public Club(String club_name, String club_description, Long book_id, long creator, Date read_time) {
        this.club_name = club_name;
        this.club_description = club_description;
        this.book_id = book_id;
        this.creator = creator;
        this.read_time = read_time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClub_name() {
        return club_name;
    }

    public void setClub_name(String club_name) {
        this.club_name = club_name;
    }

    public String getClub_description() {
        return club_description;
    }

    public void setClub_description(String club_description) {
        this.club_description = club_description;
    }

    public Long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public long getCreator() {
        return creator;
    }

    public void setCreator(long creator) {
        this.creator = creator;
    }

    public Date getRead_time() {
        return read_time;
    }

    public void setRead_time(Date read_time) {
        this.read_time = read_time;
    }

    public long getNum_subscribers() { return num_subscribers; }

    public void setNum_subscribers(long num_subscribers) { this.num_subscribers = num_subscribers; }
}

