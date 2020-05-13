package es.ulpgc.LectioBackend.controller;

import com.google.gson.Gson;
import es.ulpgc.LectioBackend.model.Book;
import es.ulpgc.LectioBackend.model.BookList;
import es.ulpgc.LectioBackend.model.UserList;
import es.ulpgc.LectioBackend.repository.BookListRepository;
import es.ulpgc.LectioBackend.repository.BookRepository;
import es.ulpgc.LectioBackend.repository.UserListRepository;
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
    private UserListRepository userListRepository;

    @Autowired
    private BookRepository bookRepository;

    @RequestMapping(path = "/users/{id}/list/{list_name}", method = {RequestMethod.GET})
    public ResponseEntity getBookList(@PathVariable(value = "id") long id, @PathVariable(value = "list_name") String list_name) {
        try {
            List<Book> books = new ArrayList<>();
            UserList userList = userListRepository.getUserListId(id, list_name);

            List<BookList> bookLists = listRepository.getBookListByListId(userList.getList_id());

            bookLists.forEach(bookList ->
                    books.add(bookRepository.findById((long) bookList.getBookListId().getBook_id()).get()));

            return (books.isEmpty()) ? buildResponse(HttpStatus.NO_CONTENT, null) : buildResponse(HttpStatus.OK, convertToJson(userList, books));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get books\" }");
        }
    }

    @RequestMapping(path = "/users/{id}/list", method = {RequestMethod.GET})
    public ResponseEntity getLists(@PathVariable(value = "id") long id){
        try {
            List<UserList> userList = userListRepository.findByUserId(id);

            return (userList.isEmpty()) ? buildResponse(HttpStatus.NO_CONTENT, null) : buildResponse(HttpStatus.OK, userList);
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get lists\" }");
        }
    }

    private String convertToJson(UserList userList, List<Book> books) {
        Gson gson = new Gson();
        return "{\"list_name\": \"" + userList.getList_name() + "\" , \"list_description\": \"" + userList.getList_description() + "\", \"books\": " + gson.toJson(books) + "}";
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
