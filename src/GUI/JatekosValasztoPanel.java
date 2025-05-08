package GUI;

import model.Fungorium;
import model.JatekMotor;
import model.players.Jatekos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class JatekosValasztoPanel extends JPanel {
    Fungorium fn;
    String rovaraszSzam, gombaszSzam, palyaX, palyaY;
    List<Jatekos> jatekosList;

    JPanel parameterPanelSetup(){
        JPanel parameterPanel = new JPanel();
        parameterPanel.setLayout(new GridLayout(3,2));

        JLabel pályatext=new JLabel("Pályaméret:");
        JTextField[] jtXY=new JTextField[2];
        jtXY[0]=new JTextField(2);
        jtXY[1]=new JTextField(2);

        jtXY[0].addActionListener((l)->{ if(l.getSource() instanceof JTextField w) palyaX=w.getText(); });
        jtXY[1].addActionListener((l)->{ if(l.getSource() instanceof JTextField w) palyaY=w.getText(); });


        JLabel rovarásztext=new JLabel("rovarász");
        JTextField rov=new JTextField(2);
        rov.addActionListener((l)->{
            if(l.getSource() instanceof JTextField w) {
                rovaraszSzam=w.getText();
                OnJatekosNumberChanged(l);
            }});


        JLabel gombászoktext=new JLabel("gombászok");
        JTextField gomb=new JTextField(2);
        gomb.addActionListener((l)->{
            if(l.getSource() instanceof JTextField w) {
                gombaszSzam=w.getText();
                OnJatekosNumberChanged(l);
            }});

        JPanel pan=new JPanel();
        pan.setLayout(new FlowLayout(FlowLayout.RIGHT));
        pan.add(jtXY[0]);
        pan.add(jtXY[1]);


        parameterPanel.add(pályatext); parameterPanel.add(pan);
        parameterPanel.add(rovarásztext); parameterPanel.add(rov);
        parameterPanel.add(gombászoktext); parameterPanel.add(gomb);
        return parameterPanel;
    }



    public void OnJatekosNumberChanged(ActionEvent e){

    }

    /**
     *
     * @param e
     */
    public void OnStartButtonPressed(ActionEvent e){

    }

    public JatekosValasztoPanel(Fungorium fungorium) {
        this.fn = fungorium;
        jatekosList=new ArrayList<>(fungorium.getMotor().getJatekosok());

        this.setLayout(new GridLayout(1,2));
        JPanel panel = parameterPanelSetup();
        add(panel);
    }



}
