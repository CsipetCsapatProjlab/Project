package GUI;

import model.Fungorium;
import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.exceptions.IncompatibleGameObjectException;
import model.exceptions.InvalidMoveException;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FungoriumController {
    Fungorium model;
    Lepes jelenlegiLepes;

    class Lepes{
        public int startx=-1,starty=-1,endx=-1,endy=-1;
        public Move lepestipus=Move.SEMMI;

        public boolean teljesLepes(){
            if(startx>0 && starty>0 && endx>0 && endy>0 &&
                startx<model.getShape()[0] &&  starty<model.getShape()[1] && endx<model.getShape()[0] && endy<model.getShape()[1] &&
                lepestipus!=Move.SEMMI){
                return true;
            }
            return false;
        }

        public void gridClick(int x, int y){
            if( !(startx>0 && startx<model.getShape()[0] && starty>0 && starty<model.getShape()[1]) ){
                startx=x;
                starty=y;
            }
            else{
                if( !(endx>0 && endx<model.getShape()[0] && endy>0 && endy<model.getShape()[1]) ){
                    endx=x;
                    endy=y;
                }
            }
        }
    }


    public FungoriumController(Fungorium model) {
        this.model = model;
        jelenlegiLepes=new Lepes();
    }

    public void onGridClicked(ActionEvent actionEvent){
        if(actionEvent.getSource() instanceof PalyaPanel.PalyaGrid grid){
            jelenlegiLepes.gridClick(grid.xcoord,grid.ycoord);
        }

        if(jelenlegiLepes.teljesLepes()){
            try{
                model.makeMove(jelenlegiLepes.startx, jelenlegiLepes.starty, jelenlegiLepes.endx, jelenlegiLepes.endy, jelenlegiLepes.lepestipus, false);
            } catch (InvalidMoveException | FailedMoveException | IncompatibleGameObjectException e) {
                jelenlegiLepes=new Lepes();
                JOptionPane.showMessageDialog(null, e.getMessage());
            }
        }
    }

    public void onMoveClicked(ActionEvent actionEvent) {

    }

}
