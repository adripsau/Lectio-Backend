package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.ClubSubscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubSubscribersRepository extends JpaRepository<ClubSubscribers, Long> {

    @Query(value="SELECT * FROM club_subscribers WHERE user_id=?1 AND club_id=?2 ", nativeQuery = true)
    public ClubSubscribers findByClubIdAndUserId(long user_id, long club_id);

    @Query(value="SELECT * FROM club_subscribers WHERE user_id=?1", nativeQuery = true)
    List<ClubSubscribers> findClubsSubscribed(long user_id);
}
