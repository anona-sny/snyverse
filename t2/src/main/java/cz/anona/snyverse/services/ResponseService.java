package cz.anona.snyverse.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResponseService {

    public static final int BADREQUEST = 400;
    public static final int OKREQUEST = 200;
    public static final int CREATED = 201;
    public static final int NOTAUTHORIZED = 401;
    public static final int FATALERROR = 500;

    public ResponseEntity<Object> generateResponse(int code, Object object) {
        return ResponseEntity.status(code).body(object);
    }

    public ResponseEntity<String> generateResponse(int code, String text) {
        return ResponseEntity.status(code).body(text);
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            return "{ 'error': 'Parsing to JSON was not successful' }";
        }
    }

}
