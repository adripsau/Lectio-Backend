package es.ulpgc.LectioBackend.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ClubSubscribersId implements Serializable {

    @Column(name = "user_id")
    private long user_id;

    @Column(name = "club_id")
    private long club_id;

    public ClubSubscribersId() {
    }

    public ClubSubscribersId(long user_id, long club_id) {
        this.user_id = user_id;
        this.club_id = club_id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClubSubscribersId that = (ClubSubscribersId) o;
        return user_id == that.user_id &&
                club_id == that.club_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id, club_id);
    }
}
