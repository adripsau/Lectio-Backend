package es.ulpgc.LectioBackend.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Locale;

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

    public Club(String club_name, String club_description, Long book_id, long creator, String read_time) {
        this.club_name = club_name;
        this.club_description = club_description;
        this.book_id = book_id;
        this.creator = creator;
        this.read_time = getDate(read_time);
    }


    private Date getTimeToRead(String read_time) {
        if (read_time.equals("Weekly"))
            return new Date(Instant.now().plusSeconds(7*86400).toEpochMilli());
        if (read_time.equals("Monthly"))
            return new Date(Instant.now().plusSeconds(30*86400).toEpochMilli());
        return null;
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

    public String getRead_time() {
        return (read_time == null) ? null : getStringFromDate(read_time);
    }

    public void setRead_time(String read_time) {
        this.read_time = getTimeToRead(read_time);
    }

    public long getNum_subscribers() {
        return num_subscribers;
    }

    public void setNum_subscribers(long num_subscribers) {
        this.num_subscribers = num_subscribers;
    }

    public void increaseSubscribers() {
        num_subscribers++;
    }

    public void decreaseSubscribers() {
        num_subscribers--;
    }

    private Date getDate(String read_time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);

        Date date = null;
        if (read_time==null)
            return null;
        try {
            date = format.parse(read_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    private String getStringFromDate(Date read_time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);

        String date = null;
        date = format.format(read_time);

        return date;
    }
}

