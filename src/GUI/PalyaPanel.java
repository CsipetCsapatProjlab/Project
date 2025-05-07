package GUI;

import model.Fungorium;
import model.gameobjects.GameObject;
import model.grid.Grid;
import model.grid.Lava;
import model.players.Jatekos;
import model.utils.ColorUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Map;

public class PalyaPanel extends JPanel{
    Fungorium model;
    JButton[][] buttons;
    Map<Jatekos, Color> szinek;

    public class PalyaGrid extends JButton{
        public final int xcoord,ycoord;
        PalyaGrid(int x, int y){
            this.xcoord = x;
            this.ycoord = y;
        }
    }

    public PalyaPanel(Fungorium f, ActionListener onGridClicked, Map<Jatekos, Color> szinPar) {
        model=f;
        szinek=szinPar;
        buttons=new JButton[f.getShape()[0]][f.getShape()[1]];
        this.setLayout(new GridLayout(f.getShape()[0],f.getShape()[1]));

        this.setPreferredSize(new Dimension(800,600));
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                buttons[i][j] = new PalyaGrid(i, j);
                buttons[i][j].addActionListener(onGridClicked);
                buttons[i][j].setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
                this.add(buttons[i][j]);
            }
        }
        pullState();
    }

    public void pullState(){
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                JButton current = buttons[i][j];
                Grid currentGrid=model.getGrid(i,j);

                if(currentGrid instanceof Lava){
                    current.setBackground(Color.RED);
                }else{
                    current.setBackground(Color.WHITE);
                }

                StringBuilder bd=new StringBuilder();
                bd.append("<html>");
                for (GameObject go : currentGrid.getGameObject()){
                    bd.append("<font color='");
                    bd.append(ColorUtils.colorToHex(szinek.getOrDefault(go.getObserver(), Color.BLACK)));
                    bd.append("'>");
                    bd.append(go.toStringShort());
                    bd.append("</font>");
                }
                bd.append("</html>");
                current.setText(bd.toString());
            }
        }
    }

}
