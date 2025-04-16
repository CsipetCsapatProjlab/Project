package model.exceptions;

import model.gameobjects.GameObject;
import model.grid.Grid;

public class IncompatibleGameObjectException extends Exception{
    GameObject incompatibleGameObject;

    public IncompatibleGameObjectException(String message, GameObject gameObject) {
        super(message);
        incompatibleGameObject = gameObject;
    }

}
