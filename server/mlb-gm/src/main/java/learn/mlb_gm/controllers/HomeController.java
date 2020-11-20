package learn.mlb_gm.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<?> getDefault() {
        HashMap<String, String> values = new HashMap<>();
        values.put("message", "Hello world!");
        return new ResponseEntity<>(values, HttpStatus.OK);
    }
}