package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}
