package es.ulpgc.LectioBackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.LectioBackend.model.Club;
import es.ulpgc.LectioBackend.model.ClubSubscribers;
import es.ulpgc.LectioBackend.model.ClubSubscribersId;
import es.ulpgc.LectioBackend.repository.ClubRepository;
import es.ulpgc.LectioBackend.repository.ClubSubscribersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClubController {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubSubscribersRepository clubSubscribersRepository;

    /**
     * body: {
     *      * "club_name": String,
     *      * "club_description": String,
     *      * "creator": String ID,
     *      * "read_time": (Optional)Epoch milliseconds only if book_id is defined,
     *      * "book_id": (Optional)Int ID only if read_time is defined
     *      * }
     *
     * Example URL: [POST] /api/clubs
     *
     * @param club
     */
    @RequestMapping(path = "/clubs", method = {RequestMethod.POST})
    public ResponseEntity createClub(@RequestBody Club club) {
        try {
            if (club.getBook_id() == null && club.getRead_time() != null)
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"Couldn't create club, there wasn't any book in the time specified\" }");

            if (club.getBook_id() != null && club.getRead_time() == null)
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"Couldn't create club, there wasn't any time to read the specified book\" }");


            Club newClub = new Club(club.getClub_name(), club.getClub_description(), club.getBook_id(),
                    club.getCreator(), club.getRead_time());

            return buildResponse(HttpStatus.CREATED, clubRepository.save(newClub));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't create club, there was a conflict\" }");
        }
    }

    /**
     * Example URL: [GET] /api/clubs
     * @return Array
     */
    @RequestMapping(path = "/clubs", method = {RequestMethod.GET})
    public ResponseEntity getClubs() {
        try {
            return buildResponse(HttpStatus.OK, clubRepository.findAll());
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't find clubs, there was a conflict\" }");
        }
    }

    /**
     *  Example URL: [POST] /api/clubs/subscribe?user_id={user_id}&club_id={club_id}
     */
    @RequestMapping(path = "/clubs/subscribe", method = {RequestMethod.POST})
    public ResponseEntity subscribeClub(@RequestParam(value = "user_id") long userId, @RequestParam(value = "club_id") long clubId, @RequestBody(required = false) String password) {
        try {

            Club club = clubRepository.findById(clubId).get();

            ClubSubscribers clubSub = clubSubscribersRepository.findByClubIdAndUserId(userId,clubId);
            if(clubSub != null) {
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"Couldn't subscribe to the club, already subscribed\" }");
            }
            club.increaseSubscribers();
            clubRepository.save(club);

            return buildResponse(HttpStatus.OK, clubSubscribersRepository.save(new ClubSubscribers(new ClubSubscribersId(userId, clubId))));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't subscribe to the club, there was a conflict\" }");
        }
    }

    /**
     *  Example URL: [POST] /api/clubs/unsubscribe?user_id={user_id}&club_id={club_id}
     */
    @RequestMapping(path = "/clubs/unsubscribe", method = {RequestMethod.DELETE})
    public ResponseEntity unsubscribeClub(@RequestParam(value = "user_id") long userId, @RequestParam(value = "club_id") long clubId, @RequestBody(required = false) String password) {
        try {

            Club club = clubRepository.findById(clubId).get();

            ClubSubscribers clubSub = clubSubscribersRepository.findByClubIdAndUserId(userId,clubId);
            if(clubSub == null) {
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"Couldn't unsubscribe to the club, user is not subscribed\" }");
            }
            club.decreaseSubscribers();
            clubRepository.save(club);
            clubSubscribersRepository.delete(clubSub);

            return buildResponse(HttpStatus.OK, "{ \"message\": \"Unsubscribed successfully\" }");
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't unsubscribe to the club, there was a conflict\" }");
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
