package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.gameobjects.GameObject;

import java.util.List;

public class Lava extends Grid {

    public Lava(List<GameObject> gameObjects) {
        super(gameObjects);
    }

    @Override
    public void accept(GameObjectVisitor visitor) {

    }

    @Override
    public void accept(GridVisitor visitor) {

    }
}
