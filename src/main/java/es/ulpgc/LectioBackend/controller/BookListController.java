package es.ulpgc.LectioBackend.controller;

import com.google.gson.Gson;
import es.ulpgc.LectioBackend.model.Book;
import es.ulpgc.LectioBackend.model.BookList;
import es.ulpgc.LectioBackend.model.BookListId;
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
    private BookListRepository bookListRepository;

    @Autowired
    private UserListRepository userListRepository;

    @Autowired
    private BookRepository bookRepository;


    /**
     * URL: [GET] /api/users/{user_id}/list/{list_name_or_list_id}
     *
     * Example 1: /api/users/32/list/9
     * Example 2: /api/users/32/list/Anime
     *
     * @return List
     */
    @RequestMapping(path = "/users/{id}/list/{list_name}", method = {RequestMethod.GET})
    public ResponseEntity getBookList(@PathVariable(value = "id") long id, @PathVariable(value = "list_name") String list_name) {
        try {
            List<Book> books = new ArrayList<>();
            UserList userList = isNumeric(list_name) ? getIDResponse(list_name) : getNameResponse(id, list_name);

            if (userList == null)
                return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get list\" }");

            List<BookList> bookLists = bookListRepository.getBookListByListId(userList.getList_id());

            bookLists.forEach(bookList ->
                    books.add(bookRepository.findById((long) bookList.getBookListId().getBook_id()).get()));

            return (books.isEmpty()) ? buildResponse(HttpStatus.NO_CONTENT, null) : buildResponse(HttpStatus.OK, convertToJson(userList, books));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get books\" }");
        }
    }


    /**
     * URL: [GET] /api/users/{user_id}/list/
     *
     * @return List
     */
    @RequestMapping(path = "/users/{id}/list", method = {RequestMethod.GET})
    public ResponseEntity getLists(@PathVariable(value = "id") long id) {
        try {
            List<UserList> userList = userListRepository.findByUserId(id);
            return (userList.isEmpty()) ? buildResponse(HttpStatus.NO_CONTENT, null) : buildResponse(HttpStatus.OK, userList);
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get lists\" }");
        }
    }


    /**
     * body: {
     *     "user_id": long,
     *     "list_name": String,
     *     "list_description": String
     * }
     *
     * #### Example ####
     * body: {
     *     "user_id": 32,
     *     "list_name": "Bacano",
     *     "list_description": "Unos libros bien chingones"
     * }
     *
     * URL: [POST] /api/users/{user_id}/list/
     *
     * @return UserList
     */
    @RequestMapping(path = "/users/{id}/list", method = {RequestMethod.POST})
    public ResponseEntity createList(@RequestBody UserList userList) {
        try {
            return buildResponse(HttpStatus.CREATED, storeUserList(userList));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't create list\" }");
        }
    }


    /**
     * body: {
     *     "book_id": long,
     *     "list_id": long
     * }
     *
     * #### Example ####
     * body: {
     *     "book_id": 32,
     *     "list_id": 23
     * }
     *
     * URL: [POST] /api/lists/
     *
     * @return BookList
     */
    @RequestMapping(path = "/lists", method = {RequestMethod.POST})
    public ResponseEntity addBookToList(@RequestBody BookListId bookListId) {
        try {
            if (storeBookList(bookListId) == null)
                return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't add to list\" }");
            return buildResponse(HttpStatus.CREATED, storeBookList(bookListId));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't add to list\" }");
        }
    }


    /**
     * URL: [DELETE] /api/lists/{list_id}?bookId={bookId}
     * Example: /api/lists/9?bookId=4
     *
     * @return message String
     */
    @RequestMapping(path = "/lists/{list_id}", method = {RequestMethod.DELETE})
    public ResponseEntity deleteBookFromList(@RequestParam long bookId, @PathVariable(value = "list_id") long list_id) {
        try {
            if(bookRepository.findById(bookId).isEmpty())
                return buildResponse(HttpStatus.NOT_FOUND, "{ \"message\": \"There was a problem, this book doesn't exists\" }");

            if(userListRepository.findById(list_id).isEmpty())
                return buildResponse(HttpStatus.NOT_FOUND, "{ \"message\": \"There was a problem, this list doesn't exists\" }");

            if(bookListRepository.findById(new BookListId(list_id,bookId)).isEmpty())
                return buildResponse(HttpStatus.NOT_FOUND, "{ \"message\": \"There was a problem, specified book is not on specified list\" }");

            bookListRepository.deleteById(new BookListId(list_id,bookId));

            return buildResponse(HttpStatus.OK,"{ \"message\": \"Deleted successfully\" }");
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't delete from list\" }");
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


    public boolean isNumeric(String strNum) {
        if (strNum == null)
            return false;

        try {
            Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }


    private UserList getNameResponse(long id, @PathVariable("userId") String list_name) {
        UserList userList = userListRepository.getUserListId(id, list_name);
        if (userList.getList_name().equals(""))
            return null;

        return userList;
    }


    private UserList getIDResponse(@PathVariable("userId") String list_id) {
        long _id = Long.parseLong(list_id);
        UserList userList = userListRepository.findById(_id).get();
        return userList;
    }


    private UserList storeUserList(UserList userList) {
        return userListRepository
                .save(new UserList(userList.getUser_id(), userList.getList_name(), userList.getList_description()));
    }


    private BookList storeBookList(BookListId bookListId) {

        BookList bookList = bookListRepository.getBookList(bookListId.getList_id(), bookListId.getBook_id());

        if (bookList == null)
            return bookListRepository
                    .save(new BookList(new BookListId(bookListId.getList_id(), bookListId.getBook_id())));
        else
            return null;
    }
}
