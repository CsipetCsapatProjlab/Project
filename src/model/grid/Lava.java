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

    public String toString(){ //kiírja a pályára hogy mi van rajta
        if(gameObjects.size() == 1){
            return "~";
        }else if(gameObjects.size() == 2){
            return "R";
        }
        return "#";
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
