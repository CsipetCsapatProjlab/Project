package model.grid;

import java.util.List;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import model.enums.Hatas;
import model.gameobjects.GameObject;

public class Lava extends Grid {
    public Lava(){super();}

    public Lava(List<GameObject> gameObjects) {
        super(gameObjects);
    }

    @Override
    public void accept(GameObjectVisitor visitor) {

    }

    @Override
    public Hatas getHatas(){return null;}

    @Override
    public void accept(GridVisitor visitor) {

    }
}
