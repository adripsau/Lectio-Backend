package es.ulpgc.LectioBackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ulpgc.LectioBackend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
