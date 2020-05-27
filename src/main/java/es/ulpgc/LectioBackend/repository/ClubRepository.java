package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.Club;
import es.ulpgc.LectioBackend.model.ClubSubscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    @Query(value="SELECT * FROM club WHERE creator=?1", nativeQuery = true)
    List<Club> findClubsCreatedBy(long creator);


}
