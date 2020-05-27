package es.ulpgc.LectioBackend.controller;

import com.google.gson.Gson;
import es.ulpgc.LectioBackend.model.Reviews;
import es.ulpgc.LectioBackend.model.User;
import es.ulpgc.LectioBackend.repository.BookListRepository;
import es.ulpgc.LectioBackend.repository.ReviewsRepository;
import es.ulpgc.LectioBackend.repository.UserListRepository;
import es.ulpgc.LectioBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ReviewsController {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private BookListRepository bookListRepository;


    /**
     * petition: [GET] /api/reviews?user_id=8&book_id=4
     *
     * @param user_id
     * @param book_id
     */
    @RequestMapping(path = "/reviews", method = {RequestMethod.GET})
    public ResponseEntity getReview(@RequestParam long user_id, @RequestParam long book_id) {
        try {
            Reviews review = reviewsRepository.getReviewByBookIdAndUserId(book_id, user_id);

            if (review == null) {
                return buildResponse(HttpStatus.NO_CONTENT,
                        "{ \"message\": \"Couldn't find review with user_id " + user_id + " and book_id " + book_id + "\" }");
            }
            return buildResponse(HttpStatus.OK, review);

        } catch (Exception e) {
            return buildResponse(HttpStatus.NOT_FOUND,
                    "{ \"message\": \"Couldn't find review with user_id " + user_id + " and book_id " + book_id + "\" }");
        }
    }

    /**
     * body: {
     * "book_id": 5,
     * "user_id": 33,
     * "comment": "It was nice",
     * "punctuation": 3
     * }
     *
     * @param review
     */
    @RequestMapping(path = "/reviews", method = {RequestMethod.POST})
    public ResponseEntity createReview(@RequestBody Reviews review) {
        try {
            User user = userRepository.findById(review.getUser_id()).get();

            if (reviewsRepository.bookIsFinished(user.getUser_id(), review.getBook_id()) == 1) {
                String fullname = user.getFirstName() + " " + user.getLastName();

                Reviews newReview = reviewsRepository.save(new Reviews(review.getBook_id(), review.getUser_id(),
                                                                review.getComment(), review.getPunctuation(), fullname));
                newReview.setCreated_at(Timestamp.from(Instant.now()));

                return buildResponse(HttpStatus.CREATED, newReview);
            } else {
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"You have to read the book before creating a review\" }");
            }
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't create review, there was a conflict\" }");
        }
    }

    /**
     * petition: /api/reviews/{book_id}?limit={num_limit}&offset={page}
     * example: /api/reviews/4?limit=3&offset=0
     *
     * @param offset
     * @param limit
     * @param bookId
     */
    @RequestMapping(path = "/reviews/{bookId}", method = {RequestMethod.GET})
    public ResponseEntity getReviewsByBookId(@RequestParam(required = false) String offset, @RequestParam(required = false, defaultValue = "0") String limit, @PathVariable long bookId) {
        try {
            List<Reviews> reviews;
            if (offset == null || limit.equals("0")) {
                reviews = new ArrayList<>(reviewsRepository.findAll());
                offset = "0";
            } else {
                reviews = new ArrayList<>(
                        reviewsRepository.findAll(Integer.valueOf(offset) * Integer.valueOf(limit), Integer.valueOf(limit), bookId));
            }

            return (reviews.isEmpty()) ? buildResponse(HttpStatus.NO_CONTENT, null) : buildPaginatedResponse(HttpStatus.OK,
                                                convertToJson(Integer.valueOf(offset), Integer.valueOf(limit), reviews, bookId));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get books\" }");
        }
    }


    private ResponseEntity<String> buildPaginatedResponse(HttpStatus _status, String response) {
        return ResponseEntity.status(_status)
                .headers(setHeaders())
                .body(response);
    }

    private String convertToJson(int offset, int limit, List<Reviews> reviews, long bookId) {
        Gson gson = new Gson();
        return "{\"numReviews\": " + reviewsRepository.countReviews(bookId) + ", \"page\": " + offset + ", \"size\": " + limit + ", \"reviews\": " + gson.toJson(reviews) + "}";
    }

    private <T> ResponseEntity<T> buildResponse(HttpStatus _status, T _body) {
        return ResponseEntity.status(_status)
                .headers(setHeaders())
                .body(_body);
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        return headers;
    }


}
