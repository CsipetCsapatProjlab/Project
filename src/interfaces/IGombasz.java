package interfaces;

import java.util.List;

import model.gameobjects.Fonal;
import model.gameobjects.GombaTest;
import model.gameobjects.Spora;

public interface IGombasz {
    public abstract List<Spora> getSporas();
    public abstract void addTest(GombaTest g);
    public abstract void addFonal(Fonal f);
}
