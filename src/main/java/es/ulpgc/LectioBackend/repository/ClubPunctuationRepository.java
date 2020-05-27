package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.ClubPunctuation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubPunctuationRepository extends JpaRepository<ClubPunctuation, Long> {
}