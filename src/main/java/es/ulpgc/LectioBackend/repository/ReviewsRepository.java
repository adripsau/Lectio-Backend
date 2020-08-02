package es.ulpgc.LectioBackend.repository;

import es.ulpgc.LectioBackend.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {

    @Query(value="SELECT * FROM reviews WHERE book_id=?1 AND user_id=?2", nativeQuery = true)
    Reviews getReviewByBookIdAndUserId(long book_id, long user_id);

    @Query(value="SELECT * FROM reviews WHERE book_id=?3 LIMIT ?2 OFFSET ?1 ", nativeQuery = true)
    public List<Reviews> findAll(int offset, int limit, long book_id);

    @Query(value = "SELECT COUNT(*) FROM reviews WHERE book_id=?1", nativeQuery = true)
    public int countReviews(long book_id);

    @Query(value = "SELECT COUNT(*) FROM booklists WHERE list_id=(SELECT list_id FROM " +
            "userlists WHERE list_name='Finished' AND user_id=?1) AND book_id=?2", nativeQuery = true)
    public int bookIsFinished(long user_id, long book_id);
}
