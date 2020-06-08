package cz.anona.snyverse.entities.neo.state;

import lombok.Data;

@Data
public class State {

    private String header;
    private String body;
    private StateCode stateCode;

    public static State getInvalidDTO(String message) {
        State state = new State();
        state.setHeader("Invalid DTO");
        state.setBody(message);
        state.setStateCode(StateCode.BAD_DATA);
        return state;
    }

    public static State getSuccessStatus(String title, String message) {
        State state = new State();
        state.setHeader(title);
        state.setBody(message);
        state.setStateCode(StateCode.SUCCESS);
        return state;
    }

    public static State getErrorStatus(String title, String message) {
        State state = new State();
        state.setHeader(title);
        state.setBody(message);
        state.setStateCode(StateCode.FAILURE);
        return state;
    }

    public static State getStatus(String title, String message, StateCode code) {
        State state = new State();
        state.setHeader(title);
        state.setBody(message);
        state.setStateCode(code);
        return state;
    }
    public static State getStatus(StateCode code) {
        State state = new State();
        state.setStateCode(code);
        return state;
    }

}
