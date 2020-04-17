package es.ulpgc.LectioBackend;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class HelloWorld {

    @RequestMapping("/")
    public String index(){
        return "Hello World";
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