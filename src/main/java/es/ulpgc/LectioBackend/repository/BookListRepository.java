package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.BookList;
import es.ulpgc.LectioBackend.model.BookListId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookListRepository extends JpaRepository<BookList, BookListId> {

    @Query(value="SELECT * FROM booklists WHERE list_id=?1", nativeQuery = true)
    List<BookList> getBookListByListId(long id);

    @Query(value="SELECT * FROM booklists WHERE list_id=?1 AND book_id=?2", nativeQuery = true)
    BookList getBookList(long list_id, long book_id);
}
