package model.exceptions;

import model.gameobjects.GameObject;

public class IncompatibleGameObjectException extends Exception{
    GameObject incompatibleGameObject;

    public IncompatibleGameObjectException(String message, GameObject gameObject) {
        super(message);
        incompatibleGameObject = gameObject;
    }

}
