package cz.anona.snyverse.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@RestController
public class TestController {

    @RequestMapping(path = "/test")
    public ResponseEntity<Object> testConnection() {
        return ResponseEntity.accepted().body(OffsetDateTime.now());
    }

}
