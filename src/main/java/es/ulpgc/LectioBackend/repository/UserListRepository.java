package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserListRepository extends JpaRepository<UserList, Long> {
    @Query(value="SELECT * FROM userlists WHERE user_id=?1 AND list_name=?2", nativeQuery = true)
    UserList getUserListId(long id, String list_name);

    @Query(value="SELECT * FROM userlists WHERE user_id=?1", nativeQuery = true)
    List<UserList> findByUserId(long id);
}