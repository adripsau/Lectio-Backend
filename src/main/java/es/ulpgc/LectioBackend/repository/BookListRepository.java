package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.BookList;
import es.ulpgc.LectioBackend.model.BookListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookListRepository extends JpaRepository<BookList, BookListId> {

    @Query(value="SELECT book_id FROM booklists WHERE user_id=?1 AND book_state=?2", nativeQuery = true)
    List<Integer> getBookIdsByUserId(long id, String state);


}
