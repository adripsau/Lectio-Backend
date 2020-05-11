package es.ulpgc.LectioBackend.controller;

import es.ulpgc.LectioBackend.model.Book;
import es.ulpgc.LectioBackend.repository.BookListRepository;
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
public class BookListController {

    @Autowired
    private BookListRepository listRepository;

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(path = "/users/{id}/{state}", method = {RequestMethod.GET})
    public ResponseEntity getPendingBooks(@PathVariable(value = "id") long id, @PathVariable(value = "state") String state) {
        try {
            List<Book> books = new ArrayList<>();
            List<Integer> bookIDs = listRepository.getBookIdsByUserId(id, state);
            bookIDs.forEach(bookId ->
                books.add(bookRepository.findById((long)bookId).get()));

            return (books.isEmpty()) ? buildResponse(HttpStatus.NO_CONTENT, null) : buildResponse(HttpStatus.OK, books);
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get books\" }");
        }
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
