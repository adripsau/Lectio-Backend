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

    @Query(value="SELECT * FROM Books WHERE Books.title LIKE %:title%", nativeQuery = true)
    public List<Book> findByName(String title);

    @Query(value="SELECT * FROM Books WHERE Books.title LIKE %:title% " +
            "AND Books.author LIKE %:author% " +
            "AND Books.genres LIKE %:genre% " +
            "AND Books.publisher LIKE %:publisher% " +
            "LIMIT :limit OFFSET :offset", nativeQuery = true)
    List<Book> findByFilter(String title, String author, String genre, String publisher, int limit, int offset);
}
