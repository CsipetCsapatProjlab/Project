package GUI;

import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Random;

public class CustomPanel extends JPanel {
    private final JTextField nameField;
    private final JComboBox<String> comboBox;
    private final JButton colorButton;
    private final Random rand = new Random();
    private Color selectedColor = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));

    public CustomPanel() {
        super(new FlowLayout(FlowLayout.LEFT, 10, 10));
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

        // Zöld pipa (ikonos label)
        JLabel confirmLabel = new JLabel("✔");
        confirmLabel.setForeground(new Color(0, 180, 0));
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(confirmLabel);
    }

    /// Visszaadja a nevet
    private String getJatekosName() {
        return nameField.getText();
    }

    /// Visszaadja a tipust
    private String getSelectedType() {
        return Objects.requireNonNull(comboBox.getSelectedItem()).toString();
    }

    /// Teszteli, helyes-e a név
    public boolean isValidName() {
        var name = getJatekosName();
        return name != null && !name.trim().isEmpty();
    }

    /**
     * Megcsinálja a PlayerGUI-kat az állapotából, ha nem jó a név, akkor IlligalArgumentException
     * @return PlayerGUI, amit csinált
     */
    public PlayerGUI createPlayerGUI() {
        if (isValidName()) {
            var name = getJatekosName();
            var type = getSelectedType();
            Jatekos jatekos = switch (type) {
                case "Gombász" -> new Gombasz(name);
                case "Rovarász" -> new Rovarasz(name);
                default -> throw new RuntimeException();
            };
            return new PlayerGUI(jatekos, selectedColor);
        } else throw new IllegalArgumentException("Nem megfelelő a név");
    }
}

