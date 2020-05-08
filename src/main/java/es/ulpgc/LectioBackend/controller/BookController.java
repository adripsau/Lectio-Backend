package es.ulpgc.LectioBackend.controller;

import es.ulpgc.LectioBackend.model.Book;
import es.ulpgc.LectioBackend.model.User;
import es.ulpgc.LectioBackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(path = "/books", method = {RequestMethod.POST})
    public ResponseEntity createBook(@RequestBody Book book) {
        try {
            return buildResponse(HttpStatus.CREATED, store(book));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't create book\" }");
        }
    }

    @RequestMapping(path = "/books", method = {RequestMethod.GET})
    public ResponseEntity getAllBooks() {
        try {
            List<Book> books = new ArrayList<>(bookRepository.findAll());
            return (books.isEmpty()) ? buildResponse(HttpStatus.NO_CONTENT, null) : buildResponse(HttpStatus.OK, books);
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get books\" }");
        }
    }

    private Book store(@RequestBody Book book) {
        return bookRepository
                .save(new Book(book.getTitle(), book.getAuthor(), book.getPublisher(), book.getPages(), book.getIsbn(),
                        book.getGenres(), book.getSynopsis()));
    }

    private HttpHeaders setHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
        return headers;
    }

    private <T> ResponseEntity<T> buildResponse(HttpStatus _status, T _body) {
        return ResponseEntity.status(_status)
                .headers(setHeaders())
                .body(_body);
    }
}
