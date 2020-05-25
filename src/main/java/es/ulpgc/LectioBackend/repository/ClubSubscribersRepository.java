package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.Club;
import es.ulpgc.LectioBackend.model.ClubSubscribers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubSubscribersRepository extends JpaRepository<ClubSubscribers, Long> {
}
