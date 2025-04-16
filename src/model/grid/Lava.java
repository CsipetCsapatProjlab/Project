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

    public String toString(){ //kiírja a pályára hogy mi van rajta
        if(gameObjects.size() == 1){
            return "~";
        }else if(gameObjects.size() == 2){
            return "R";
        }
        return "#";
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
     * Megvalositja a Grid accept(GridVisitor visitor) fv.-et a sajat modjan
     */
    @Override
    public void accept(GridVisitor visitor) {

    }
}
