package es.ulpgc.LectioBackend;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.ulpgc.LectioBackend.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

}
