package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.ClubPunctuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubPunctuationRepository extends JpaRepository<ClubPunctuation, Long> {
    @Query(value="SELECT * FROM club_punctuation WHERE user_id=?1 AND club_id=?2", nativeQuery = true)
    ClubPunctuation getClubPunctuationByUserIdAndClubId(long user_id, long club_id);
}
