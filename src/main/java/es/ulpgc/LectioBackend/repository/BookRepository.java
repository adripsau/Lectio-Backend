package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query(value="SELECT * FROM Books LIMIT ?2 OFFSET ?1 ", nativeQuery = true)
    public List<Book> findAll(int offset, int limit);
}
