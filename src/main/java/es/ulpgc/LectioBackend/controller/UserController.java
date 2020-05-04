package es.ulpgc.LectioBackend.controller;

import es.ulpgc.LectioBackend.repository.UserRepository;
import es.ulpgc.LectioBackend.model.User;
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
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/users/{userId}", method = {RequestMethod.GET})
    public ResponseEntity getUserById(@PathVariable(value = "userId") long id) {
        try {
            User _user = userRepository.findById(id).get();
            return buildResponse(HttpStatus.OK, _user);
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"Couldn't find user with id " + id + "\" }");
        }
    }

    @RequestMapping(path = "/users", method = {RequestMethod.GET})
    public ResponseEntity getAllUsers() {
        try {
            List<User> users = new ArrayList<>(userRepository.findAll());
            return (users.isEmpty()) ? buildResponse(HttpStatus.NO_CONTENT, null) : buildResponse(HttpStatus.OK, users);
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't get users\" }");
        }
    }

    @RequestMapping(path = "/users", method = {RequestMethod.POST})
    public ResponseEntity createUser(@RequestBody User user) {
        try {
            return buildResponse(HttpStatus.CREATED, store(user));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't create user\" }");
        }
    }

    @RequestMapping(path = "/users/{userId}", method = {RequestMethod.DELETE})
    public ResponseEntity deleteUser(@PathVariable(value = "userId") long id) {
        try {
            userRepository.deleteById(id);
            return buildResponse(HttpStatus.OK, "{ \"message\": \"User deleted successfully\" }");
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't delete user with id " + id + "\" }");
        }
    }

    @RequestMapping(path = "/users/{userId}", method = {RequestMethod.PUT})
    public ResponseEntity updateUser(@PathVariable(value = "userId") long id, @RequestBody User user) {
        try {
            User _user = userRepository.findById(id).get();
            _user.updateAll(user);
            return buildResponse(HttpStatus.ACCEPTED, userRepository.save(_user));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT, "{ \"message\": \"There was a problem, couldn't update user\" }");
        }
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

    private User store(@RequestBody User user) {
        return userRepository
                .save(new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getPhoto(),
                        user.getRole(), user.getAdditional()));
    }
}