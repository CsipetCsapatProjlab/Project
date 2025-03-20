package interfaces;

import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Rovar;
import model.gameobjects.Spora;

public interface GameObjectVisitor {
    void visit(Spora spora);
    void visit(GombaTest gombaTest);
    void visit(Rovar rovar);
    void visit(Fonal fonal);
}
