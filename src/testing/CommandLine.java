package testing;

import model.Fungorium;
import model.enums.Move;
import model.grid.Grid;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CommandLine {
    Fungorium fungorium;
    Map<String, Command> systemCommands = new HashMap<>();
    Map<String, Command> playerCommands = new HashMap<>();
    public CommandLine(Fungorium fungorium) {
        this.fungorium=fungorium;
        addCommand(new Command(
                "/help",
                "Kiírja a lehetséges commandokat",
                a -> {
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
                    Grid grid = fungorium.getGrid(gridCoord[0], gridCoord[1]);

                    if (!(grid instanceof TektonElem))
                        System.out.println("A grid nem egy tektonElem nem helyezhető rá játékos");

                    TektonElem tElem = ((TektonElem) grid);
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
                "Betölti az állást, a megadott névvel",
                args -> fungorium.betoltes(args[1]),
                "name"
        ));

        addCommand(new Command(
                "/save",
                "Elmenti az állást, a megadott névvel",
                args -> fungorium.mentes(args[1]),
                "name"
        ));

        addCommand(new Command(
                "/print",
                "Kiírja a pályát",
                a -> System.out.println(fungorium)
        ));

        addCommand(new Command(
                "/regen",
                "Újra generálja a pályát",
                a -> fungorium.ujraGeneralas()
        ));

        addCommand(new Command(
                "/tekton_szakad",
                "Előidéz egy szakadást",
                a -> fungorium.szakad()
        ));

        addCommand(new Command(
                "/listgameobjects",
                "Kilistázza a GameObjecteket",
                a -> {
                    var shape = fungorium.getShape();
                    for (int x = 0; x < shape[0]; x++) {
                        for (int y = 0; y < shape[1]; y++) {
                            int finalX = x;
                            int finalY = y;
                            fungorium.getGrid(x, y)
                                    .getGameObject()
                                    .stream()
                                    .map(Object::toString)
                                    .map(data ->data + finalX + 'x' + finalY)
                                    .forEach(System.out::println);
                        }
                    }
                }
        ));

        addCommand(new Command(
                "/listjatekos",
                "Kilistázza a játékosokat",
                a -> Arrays.stream(fungorium.getPlayers())
                        .forEach(System.out::println)
        ));

        addCommand(new Command(
                "/setjatekos",
                "Beállítja a megadott nevű játékosnak a pontjait a megadott értékre. A gombásznak a gombatest, rovarásznak a megevett spóra számát állítja be.",
                args -> {
                    String name = args[1];
                    int value = Integer.parseInt(args[2]);
                    var talaltJatekosok = Arrays.stream(fungorium.getPlayers())
                            .filter(jatekos -> jatekos.getNev().equals(name))
                            .toArray();
                    for (Object jatekos: talaltJatekosok)
                        if (jatekos instanceof Rovarasz r) r.setTapanyagok(value);
                        else if (jatekos instanceof Gombasz g) g.setgombatest(value);
                        else throw new RuntimeException("Egyelőre csak Gombász és rovarász játékosra implementálva");
                }
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

    public void start() {

        AtomicBoolean quit = new AtomicBoolean(false);
        addCommand(new Command(
                "/quit",
                "Kilép a command line-ból",
                a -> quit.set(true)
        ));

        Scanner scanner = new Scanner(System.in);
        System.out.println("Írd be a parancsokat, segítséghez /help.");
        while (!quit.get()) {
            System.out.print("> ");
            executeCommand(scanner.nextLine());
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
            System.out.println("Command not found!");
        }
    }

    public int[] getCoordinates(String coordinate){
        return Arrays.stream(coordinate.split("x")).mapToInt(Integer::parseInt).toArray();
    }
}
