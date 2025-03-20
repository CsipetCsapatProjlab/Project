package interfaces;

import model.grid.Grid;

public interface IDiscoverLogic {
    boolean canMove(Grid from, Grid neighbour);
}
