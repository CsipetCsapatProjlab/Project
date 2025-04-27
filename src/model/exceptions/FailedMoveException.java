package model.exceptions;

import model.enums.Move;
import model.gameobjects.GameObject;

public class FailedMoveException extends RuntimeException {
    GameObject failureOrigin;
    Move movetype;
    public FailedMoveException(String message, GameObject failureOrigin, Move movetype) {
        super(message);
        this.failureOrigin = failureOrigin;
        this.movetype = movetype;
    }

}
