package es.ulpgc.LectioBackend.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.ulpgc.LectioBackend.model.*;
import es.ulpgc.LectioBackend.repository.ClubPunctuationRepository;
import es.ulpgc.LectioBackend.repository.ClubRepository;
import es.ulpgc.LectioBackend.repository.ClubSubscribersRepository;
import es.ulpgc.LectioBackend.repository.UserRepository;
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
public class ClubController {

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubSubscribersRepository clubSubscribersRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ClubPunctuationRepository clubPunctuationRepository;


    /**
     * body: {
     * "club_name": String,
     * "club_description": String,
     * "creator": String ID,
     * "read_time": (Optional)String (Monthly or Weekly),
     * "book_id": (Optional)Int ID only if read_time is defined
     * }
     * <p>
     * URL: [POST] /api/clubs
     *
     * @return Club
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
     * URL: [GET] /api/clubs
     *
     * @return List
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
     * URL: [POST] /api/clubs/subscribe?user_id={user_id}&club_id={club_id}
     *
     * @return ClubSubscribers
     */
    @RequestMapping(path = "/clubs/subscribe", method = {RequestMethod.POST})
    public ResponseEntity subscribeClub(@RequestParam(value = "user_id") long userId, @RequestParam(value = "club_id") long clubId, @RequestBody(required = false) String password) {
        try {

            Club club = clubRepository.findById(clubId).get();

            ClubSubscribers clubSub = clubSubscribersRepository.findByClubIdAndUserId(userId, clubId);
            if (clubSub != null) {
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
     * URL: [DELETE] /api/clubs/unsubscribe?user_id={user_id}&club_id={club_id}
     *
     * @return message String
     */
    @RequestMapping(path = "/clubs/unsubscribe", method = {RequestMethod.DELETE})
    public ResponseEntity unsubscribeClub(@RequestParam(value = "user_id") long userId, @RequestParam(value = "club_id") long clubId, @RequestBody(required = false) String password) {
        try {

            Club club = clubRepository.findById(clubId).get();

            ClubSubscribers clubSub = clubSubscribersRepository.findByClubIdAndUserId(userId, clubId);
            if (clubSub == null) {
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"Couldn't unsubscribe to the club, user is not subscribed\" }");
            }
            club.decreaseSubscribers();
            clubRepository.save(club);
            clubSubscribersRepository.delete(clubSub);

            return buildResponse(HttpStatus.OK, "{ \"message\": \"Unsubscribed successfully of club " + club.getClub_name() + "\" }");
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't unsubscribe to the club, there was a conflict\" }");
        }
    }


    /**
     * URL: [GET] /api/clubs/{user_id}
     *
     * @return List
     */
    @RequestMapping(path = "/clubs/{user_id}", method = {RequestMethod.GET})
    public ResponseEntity getSubscribedClubs(@PathVariable(value = "user_id") long user_id) {
        try {
            List<ClubSubscribers> clubsSubscribed = clubSubscribersRepository.findClubsSubscribed(user_id);

            List<Club> clubs = new ArrayList<>();
            clubsSubscribed.forEach(c -> {
                Club club = clubRepository.findById(c.getClubSubscribersId().getClub_id()).get();
                clubs.add(club);
            });
            return buildResponse(HttpStatus.OK, clubs);
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't find clubs, there was a conflict\" }");
        }
    }


    /**
     * body: {
     * "book_id": long,
     * "club_id": long,
     * "date": String (Monthly or Weekly)
     * }
     * <p>
     * URL: [PUT] /api/clubs
     *
     * @return Club
     */
    @RequestMapping(path = "/clubs", method = {RequestMethod.PUT})
    public ResponseEntity setToReadBook(@RequestBody String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(json);
            long book_id = jsonNode.findValue("book_id").asLong();
            long club_id = jsonNode.findValue("club_id").asLong();
            String finishDate = jsonNode.findValue("date").asText();

            Club club = clubRepository.findById(club_id).get();
            club.setBook_id(book_id);
            club.setRead_time(finishDate);

            if (club.getRead_time() == null) {
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"Couldn't update club, read time must be Monthly or Weekly\" }");
            }

            return buildResponse(HttpStatus.CREATED, clubRepository.save(club));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't update club, there was a conflict\" }");
        }
    }


    /**
     * URL: [DELETE] /api/clubs/{club_id}
     *
     * @return message String
     */
    @RequestMapping(path = "/clubs/{club_id}", method = {RequestMethod.DELETE})
    public ResponseEntity deleteClub(@PathVariable(value = "club_id") long clubId) {
        try {
            Club club = (clubRepository.findById(clubId).isEmpty()) ? null : clubRepository.findById(clubId).get();

            if (club == null)
                return buildResponse(HttpStatus.NOT_FOUND,
                        "{ \"message\": \"Couldn't delete the club, there isn't any club with id " + clubId + "\" }");

            clubRepository.deleteById(clubId);

            return buildResponse(HttpStatus.OK, "{ \"message\": \"Delete of club  " + club.getClub_name() + " successfully \" }");
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't delete the club, there was a conflict\" }");
        }
    }


    /**
     * URL: [GET] /api/clubs/librarian/{user_id}
     *
     * @return List
     */
    @RequestMapping(path = "/clubs/librarian/{user_id}", method = {RequestMethod.GET})
    public ResponseEntity getCreatedClubs(@PathVariable(value = "user_id") long user_id) {
        try {
            if (userRepository.findById(user_id).get().getRole().compareTo(Rol.Librarian) != 0) {
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"User id provided isn't a librarian\" }");
            }

            List<Club> clubs = clubRepository.findClubsCreatedBy(user_id);

            return buildResponse(HttpStatus.OK, clubs);
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't find clubs, there was a conflict\" }");
        }
    }


    /**
     * body: {
     * "user_id": long,
     * "club_id": long,
     * "punctuation": long
     * }
     * <p>
     * #### Example ####
     * body: {
     * "user_id": 33,
     * "club_id": 23,
     * "punctuation": 3
     * }
     * <p>
     * URL: [POST] /api/clubs/punctuation
     *
     * @return ClubPunctuation
     */
    @RequestMapping(path = "/clubs/punctuation", method = {RequestMethod.POST})
    public ResponseEntity createPunctuation(@RequestBody ClubPunctuation punctuation) {
        try {
            User user = userRepository.findById(punctuation.getUser_id()).get();

            ClubSubscribers club = clubSubscribersRepository.findByClubIdAndUserId(punctuation.getUser_id(), punctuation.getClub_id());

            if (club != null) {
                ClubPunctuation newPunctuation = clubPunctuationRepository
                        .save(new ClubPunctuation(punctuation.getUser_id(), punctuation.getClub_id(), punctuation.getPunctuation()));

                return buildResponse(HttpStatus.CREATED, newPunctuation);
            } else {
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"You have to subscribe first to punctuate\" }");
            }
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't create punctuation, there was a conflict\" }");
        }
    }


    /**
     * URL: [GET] /api/clubs/{club_id}/punctuation/{user_id}
     *
     * @return ClubPunctuation
     */
    @RequestMapping(path = "/clubs/{club_id}/punctuation/{user_id}", method = {RequestMethod.GET})
    public ResponseEntity haveThisUserPunctuate(@PathVariable(value = "club_id") long club_id, @PathVariable(value = "user_id") long user_id) {
        try {
            ClubPunctuation club_punctuation = clubPunctuationRepository.getClubPunctuationByUserIdAndClubId(user_id, club_id);

            if (club_punctuation == null) {
                return buildResponse(HttpStatus.NO_CONTENT,
                        "{ \"message\": \"Couldn't find club punctuation with user_id " + user_id + " and club_id " + club_id + "\" }");
            }
            return buildResponse(HttpStatus.OK, club_punctuation);

        } catch (Exception e) {
            return buildResponse(HttpStatus.NOT_FOUND,
                    "{ \"message\": \"Couldn't find club punctuation with user_id " + user_id + " and club_id " + club_id + "\" }");
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
