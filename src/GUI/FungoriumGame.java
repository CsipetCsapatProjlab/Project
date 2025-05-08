package GUI;

import model.Fungorium;

public class FungoriumGame {
    FungoriumGUI view;
    FungoriumController controller;
    Fungorium model;

    public FungoriumGame(String importpath){
        model=new Fungorium(importpath);
        view=new FungoriumGUI(model);

    }

}
