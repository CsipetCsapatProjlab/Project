package GUI;

import javax.swing.*;
import java.awt.*;

public class CustomPanel extends JPanel {
    private JTextField nameField;
    private JComboBox<String> comboBox;
    private JButton colorButton;
    private Color selectedColor;
    private JButton confirmButton;

    public CustomPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Név beviteli mező
        nameField = new JTextField("Sanyi", 8);
        nameField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(nameField);

        // Szöveg választó
        String[] items = {"Gombász", "Rovarász"};
        comboBox = new JComboBox<>(items);
        comboBox.setSelectedItem("Gomba");
        comboBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        add(comboBox);

        // Színválasztó gomb
        selectedColor = Color.YELLOW;
        colorButton = new JButton();
        colorButton.setPreferredSize(new Dimension(30, 30));
        colorButton.setBackground(selectedColor);
        colorButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        colorButton.addActionListener(e -> {
            Color color = JColorChooser.showDialog(this, "Válassz színt", selectedColor);
            if (color != null) {
                selectedColor = color;
                colorButton.setBackground(color);
            }
        });
        add(colorButton);

        // Zöld pipa (ikonos gomb)
        confirmButton = new JButton("✔");
        confirmButton.setForeground(new Color(0, 180, 0));
        confirmButton.setFont(new Font("Arial", Font.BOLD, 18));
        confirmButton.setBorderPainted(false);
        confirmButton.setContentAreaFilled(false);
        confirmButton.setFocusPainted(false);
        add(confirmButton);
    }

    // Getterek az adatokhoz
    public String getName() {
        return nameField.getText();
    }

    public String getSelection() {
        return (String) comboBox.getSelectedItem();
    }

    public Color getColor() {
        return selectedColor;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
}

