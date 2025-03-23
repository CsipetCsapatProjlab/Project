package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import java.util.List;
import model.enums.Hatas;
import model.gameobjects.GameObject;

public class Lava extends Grid {
    public Lava(){super();}

    public Lava(List<GameObject> gameObjects) {
        super(gameObjects);
    }

    /**
     * Megvalositja a Grid accept(GameObjectVisitor visitor) fv.-et a sajat modjan
     */
    @Override
    public void accept(GameObjectVisitor visitor) {

    }

    /**
     * Megvalositja a Grid getHatas() fv.-et a sajat modjan
     */
    @Override
    public Hatas getHatas(){return null;}
    
    /**
     * Megvalositja a Grid accept(GridVisitor visitor) fv.-et a sajat modjan
     */
    @Override
    public void accept(GridVisitor visitor) {

    }
}
