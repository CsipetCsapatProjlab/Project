package testing;

import model.Fungorium;
import model.enums.Move;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

import java.util.*;

public class CommandLine {
    Fungorium fungorium;
    Map<String, Command> systemCommands = new HashMap<>();
    Map<String, Command> playerCommands = new HashMap<>();
    public CommandLine(Fungorium fungorium) {
        this.fungorium=fungorium;
        addCommand(new Command(
                "/help",
                "Prints the available commands",
                (str) -> {
                    System.out.println("System commands:");
                    systemCommands.values().stream()
                        .map(Command::getDescription)
                        .map(desc -> "\t" + desc)
                        .forEach(System.out::println);
                    System.out.println("Player Commands:");
                    playerCommands.values().stream()
                        .map(Command::getDescription)
                        .map(desc -> "\t" + desc)
                        .forEach(System.out::println);
                })
        );

        //Játékos lépéseinek hozzáadása
        for (Move move : Move.values()) {
            String name = move.name();
            addCommand(new Command(
                    name,
                    "Végrehajtja a megfelelő akciót, Az 1. mező a start grid coordinátája, a 2 a célé.",
                    args -> {
                        int[] startCoordinates = getCoordinates(args[0]);
                        int[] endCoordinates = getCoordinates(args[1]);
                        fungorium.makeMove(startCoordinates[0], startCoordinates[1], endCoordinates[0], endCoordinates[1], move);
                    },
                    "<startX>x<startY> <endX>x<endY>"
            ));
        }

        addCommand(new Command(
                "/addp",
                "Rovarászt vagy gombászt ad hozzá a megfelelő névvel és hellyel",
                args -> {
                    String name = args[1];
                    int[] gridCoord = getCoordinates(args[2]);
                    TektonElem tElem = ((TektonElem) fungorium.getGrid(gridCoord[0], gridCoord[1]));
                    Jatekos j = switch (args[0]) {
                        case "r" -> new Rovarasz(tElem, name);
                        case "g" -> new Gombasz(tElem, name);
                        default -> throw new IllegalArgumentException("Játékos csak r(Rovarász)/g(Gombasz) lehet");
                    };
                    fungorium.addJatekos(j);
                },
                "[r/g] name <startX>x<startY>"
        ));

        addCommand(new Command(
                "/load",
                "Betölti a megadott nevű állást",
                args -> {},
                "name"
        ));

        addCommand(new Command(
                "/save",
                "Elmenti az állást, valamilyen néven",
                args -> {},
                "name"
        ));
    }
    private void addCommand(Command command){
        String name = command.name;
        if (name.charAt(0)=='/'){
            systemCommands.put(name, command);
        } else {
            playerCommands.put(name, command);
        }
    }

    public void executeCommand(String commandList){
        String[] commands = commandList.split(" ");
        String commandString = commands[0];
        commands = Arrays.copyOfRange(commands, 1, commands.length);
        Command command;
        if(commandString.charAt(0) == '/'){
            command = systemCommands.get(commandString);
        } else {
            command = playerCommands.get(commandString);
        }
        if (command != null) {
            command.accept(commands);
        } else {
            throw new IllegalArgumentException("Command not found: "  + commandString);
        }
    }

    public int[] getCoordinates(String coordinate){
        return Arrays.stream(coordinate.split("x")).mapToInt(Integer::parseInt).toArray();
    }
}
