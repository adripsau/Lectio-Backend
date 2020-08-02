package es.ulpgc.LectioBackend.model;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "club_subscribers")
public class ClubSubscribers {

    @EmbeddedId
    private ClubSubscribersId clubSubscribersId;

    public ClubSubscribers() {
    }

    public ClubSubscribers(ClubSubscribersId clubSubscribersId) {
        this.clubSubscribersId = clubSubscribersId;
    }

    public ClubSubscribersId getClubSubscribersId() {
        return clubSubscribersId;
    }

    public void setClubSubscribersId(ClubSubscribersId clubSubscribersId) {
        this.clubSubscribersId = clubSubscribersId;
    }
}
