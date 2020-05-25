package es.ulpgc.LectioBackend.controller;

import es.ulpgc.LectioBackend.model.Club;
import es.ulpgc.LectioBackend.repository.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ClubController {

    @Autowired
    ClubRepository clubRepository;

    /**
     * body: {
     *     "club_name": String,
     *     "club_description": String,
     *     "creator": String ID,
     *     "read_time": Epoch milliseconds,
     *     "book_id": Int ID
     * }
     *
     * Example URL: /api/clubs
     *
     * @param club
     */
    @RequestMapping(path = "/clubs", method = {RequestMethod.POST})
    public ResponseEntity createClub(@RequestBody Club club) {
        try {
            if(club.getBook_id() == null && club.getRead_time() != null)
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"Couldn't create club, there wasn't any book in the time specified\" }");

            if(club.getBook_id() != null && club.getRead_time() == null)
                return buildResponse(HttpStatus.CONFLICT,
                        "{ \"message\": \"Couldn't create club, there wasn't any time to read the specified book\" }");

            Club newClub = new Club(club.getClub_name(),club.getClub_description(),club.getBook_id(),
                    club.getCreator(),club.getRead_time());

            return buildResponse(HttpStatus.CREATED, clubRepository.save(newClub));
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't create club, there was a conflict\" }");
        }
    }

    @RequestMapping(path = "/clubs", method = {RequestMethod.GET})
    public ResponseEntity getClubs(){
        try {
            return buildResponse(HttpStatus.OK, clubRepository.findAll());
        } catch (Exception e) {
            return buildResponse(HttpStatus.CONFLICT,
                    "{ \"message\": \"Couldn't find clubs, there was a conflict\" }");
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
