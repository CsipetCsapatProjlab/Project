package model.grid;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import java.util.List;
import model.enums.Hatas;
import model.gameobjects.Fonal;
import model.gameobjects.GameObject;
import model.gameobjects.Rovar;

public class Lava extends Grid {
    public Lava(){super();}

    /**
     * Letrehozza a lavat a rajta levo jatek objektumokkal
     * @param gameObjects
     */
    public Lava(List<GameObject> gameObjects) {
        super(gameObjects);
    }

    @Override
    public boolean elfogadGameObject(GameObject gameObject) {
        if(gameObject instanceof Fonal) {
            return true;
        } else {
            if(gameObject instanceof Rovar && gameObjects.stream().anyMatch(Fonal.class::isInstance)) return true;
        }
        return false;
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
