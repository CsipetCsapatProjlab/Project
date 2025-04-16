package model.exceptions;

import model.enums.Move;
import model.grid.Grid;

public class InvalidMoveException extends Exception {
    Grid from;
    Grid to;
    Move move;

    public InvalidMoveException(String message, Grid from, Grid to, Move move) {
        super(message);
    }
    public InvalidMoveException(String message) {
        super(message);
    }
}
