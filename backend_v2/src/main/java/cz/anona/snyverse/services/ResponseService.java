package cz.anona.snyverse.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public static final int BADREQUEST = 400;
    public static final int OKREQUEST = 200;
    public static final int CREATED = 201;
    public static final int NOTAUTHORIZED = 401;
    public static final int FATALERROR = 500;

    public ResponseEntity generateResponse(int code, Object object) {
        return ResponseEntity.status(code).body(object);
    }

    public ResponseEntity<String> generateResponse(int code, String text) {
        return ResponseEntity.status(code).body(text);
    }

}
