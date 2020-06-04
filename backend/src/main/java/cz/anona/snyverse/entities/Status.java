package cz.anona.snyverse.entities;

import lombok.Data;

@Data
public class Status {

    private String header;
    private String body;
    private Integer code;

    public static Status getInvalidDTO(String message) {
        Status status = new Status();
        status.setHeader("Invalid DTO");
        status.setBody(message);
        status.setCode(400);
        return status;
    }

    public static Status getSuccessStatus(String title, String message) {
        Status status = new Status();
        status.setCode(200);
        status.setHeader(title);
        status.setBody(message);
        return status;
    }

    public static Status getErrorStatus(String title, String message) {
        Status status = new Status();
        status.setCode(400);
        status.setHeader(title);
        status.setBody(message);
        return status;
    }
}
