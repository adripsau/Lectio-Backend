package es.ulpgc.LectioBackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class HelloWorld {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    @RequestMapping("/users")
    public String getUsers(){
        return "Pepe, Juan, Pepito";
    }

    @RequestMapping(path = "/pitufos", method = RequestMethod.POST)
    public String getPitufos(){
        return "No hay pitufos";
    }

}