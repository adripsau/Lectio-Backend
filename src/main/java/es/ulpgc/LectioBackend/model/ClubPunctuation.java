package es.ulpgc.LectioBackend.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "club_punctuation")
public class ClubPunctuation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_id", nullable = false)
    private long user_id;

    @Column(name = "club_id", nullable = false)
    private long club_id;

    @Min(1)
    @Max(5)
    @Column(name = "punctuation", nullable = false)
    private long punctuation;

    public ClubPunctuation() {
    }

    public ClubPunctuation(long user_id, long club_id, long punctuation) {
        this.user_id = user_id;
        this.club_id = club_id;
        this.punctuation = punctuation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getClub_id() {
        return club_id;
    }

    public void setClub_id(long club_id) {
        this.club_id = club_id;
    }

    public long getPunctuation() {
        return punctuation;
    }

    public void setPunctuation(long punctuation) {
        this.punctuation = punctuation;
    }
}
