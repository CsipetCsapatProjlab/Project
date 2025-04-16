package interfaces;

import model.grid.Grid;

public interface IDiscoverLogic {
    double canMove(Grid from, Grid neighbour);
}
