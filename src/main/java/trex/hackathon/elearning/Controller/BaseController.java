package trex.hackathon.elearning.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    @GetMapping(value = "/")
    public ResponseEntity<String> index() {
        return new ResponseEntity<>("Welcome to E-Learning API", org.springframework.http.HttpStatus.OK);
    }
}
