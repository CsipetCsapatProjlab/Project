package logic_classes;

import interfaces.GameObjectVisitor;
import interfaces.GridVisitor;
import interfaces.IDiscoverLogic;
import model.grid.Grid;

public abstract class DiscoverLogic implements IDiscoverLogic, GridVisitor, GameObjectVisitor {
    Grid from;
    Grid to;
    protected boolean permissibleMove;
    protected void setPermissibleMove(boolean permissibleMove) {
        this.permissibleMove = permissibleMove;
    }


    public DiscoverLogic() {}
}
