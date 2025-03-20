package interfaces;

import model.grid.Lava;
import model.grid.TektonElem;

public interface GridVisitor {
    void visit(Lava lava);
    void visit(TektonElem elem);
}
