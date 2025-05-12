package testing;

import model.Fungorium;
import model.enums.Move;
import model.exceptions.FailedMoveException;
import model.exceptions.IncompatibleGameObjectException;
import model.exceptions.InvalidMoveException;
import model.gameobjects.*;
import model.grid.Grid;
import model.grid.TektonElem;
import model.players.Gombasz;
import model.players.Jatekos;
import model.players.Rovarasz;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class CommandLine {
    Fungorium fungorium;
    Map<String, Command> systemCommands = new HashMap<>();
    Map<String, Command> playerCommands = new HashMap<>();
    List<String> log = new ArrayList<>();
    boolean quit = false;
    private PrintStream out = System.out;


    public CommandLine(Fungorium fungorium) {
        this.fungorium = fungorium;
        addBaseCommands();
    }

    public void setPrintStream(PrintStream out) {
        this.out = out;
    }

    private void addBaseCommands() {
        Command[] commands = {
                new Command(
                        "/help",
                        "Kiírja a lehetséges commandokat",
                        a -> {
                            out.println("System commands:");
                            systemCommands.values().stream()
                                    .map(Command::getDescription)
                                    .map(desc -> "\t" + desc)
                                    .forEach(out::println);
                            out.println("Player Commands:");
                            playerCommands.values().stream()
                                    .map(Command::getDescription)
                                    .map(desc -> "\t" + desc)
                                    .forEach(out::println);
                        }
                ),
                new Command(
                        "/addp",
                        "Rovarászt vagy gombászt ad hozzá a megfelelő névvel és hellyel",
                        args -> {
                            String name = args[1];
                            int[] gridCoord = getCoordinates(args[2]);
                            Grid grid = fungorium.getGrid(gridCoord[0], gridCoord[1]);

                            if (!(grid instanceof TektonElem))
                                throw new IllegalArgumentException("A grid nem egy TektonElem nem helyezhető rá játékos");

                            TektonElem tElem = ((TektonElem) grid);
                            Jatekos j = switch (args[0]) {
                                case "r" -> new Rovarasz(tElem, name);
                                case "g" -> new Gombasz(tElem, name);
                                default -> throw new IllegalArgumentException("Játékos csak r(Rovarász)/g(Gombasz) lehet");
                            };
                            fungorium.addJatekos(j);
                        },
                        "[r/g] name <startX>x<startY>"
                ),
                new Command(
                        "/load",
                        "Betölti az állást, a megadott névvel",
                        args -> {
                            try {
                                fungorium.betoltes(args[0]);
                            } catch (Exception e) {
                                out.println("->Betöltési hiba");
                                return;
                            }
                            out.println("->Beolvasás sikeres");
                        },
                        "name"
                ),
                new Command(
                        "/save",
                        "Elmenti az állást, a megadott névvel",
                        args -> fungorium.mentes(args[0]),
                        "name"
                ),
                new Command(
                        "/print",
                        "Kiírja a pályát",
                        a -> out.println(fungorium)
                ),
                new Command(
                        "/regen",
                        "Újra generálja a pályát",
                        a -> fungorium.ujraGeneralas()
                ),
                new Command(
                        "/tekton_szakad",
                        "Előidéz egy szakadást",
                        a -> {
                            try {
                                fungorium.szakad();
                            } catch (Exception e) {
                                out.println("Tekton szakadás sikertelen");
                                return;
                            }
                            out.println("->Tekton szakadás sikeres");
                        }
                ),
                new Command(
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
                                            .forEach(out::println);
                                }
                            }
                        }
                ),
                new Command(
                        "/listjatekos",
                        "Kilistázza a játékosokat",
                        a -> Arrays.stream(fungorium.getPlayers())
                                .forEach(out::println)
                ),
                new Command(
                        "/setjatekos",
                        "Beállítja a megadott nevű játékosnak a pontjait a megadott értékre. A gombásznak a gombatest, rovarásznak a megevett spóra számát állítja be.",
                        args -> {
                            String name = args[0];
                            int value = Integer.parseInt(args[1]);
                            var talaltJatekosok = Arrays.stream(fungorium.getPlayers())
                                    .filter(jatekos -> jatekos.getNev().equals(name))
                                    .toArray();
                            for (Object jatekos: talaltJatekosok)
                                if (jatekos instanceof Rovarasz r) r.setTapanyagok(value);
                                else if (jatekos instanceof Gombasz g) g.setgombatest(value);
                                else throw new RuntimeException("Egyelőre csak Gombász és rovarász játékosra implementálva");
                        }
                ),
                new Command(
                        "/log",
                        "Kiírja az eddig kiadott parancsokat",
                        args -> log.forEach(out::println)
                ),
                new Command(
                        "/quit",
                        "Kilép a command line-ból",
                        a -> quit=true
                ),
                new Command(
                        "/output",
                        "A kimenet beállítása a megadott fájlra, a 0 visszaállítja a konzolt",
                        args -> {
                            var arg = args[0];
                            if (arg.equals("0")) setPrintStream(System.out);
                            else {
                                try {
                                    setPrintStream(new PrintStream(arg));
                                } catch (FileNotFoundException e) {
                                    out.println("A fájl nem található!");
                                }
                            }
                        },
                        "path"
                ),
                new Command(
                        "/script",
                        "Szkript futtatása a megadott elérési úttal.",
                        args -> {
                            Scanner scanner;
                            try {
                                scanner = new Scanner(new File(args[0]));
                            } catch (FileNotFoundException e) {
                                out.println("A fájl nem található!");
                                return;
                            }

                            while (scanner.hasNextLine()) {
                                String cmd = scanner.nextLine();
                                log.add(cmd);
                                executeCommand(cmd);
                            }
                        },
                        "path"
                ),
                new Command(
                        "/skip",
                        "Skippeli a soron lévő játékos körét játékos körét.",
                        a -> {
                            fungorium.afterRound();
                            fungorium.nextPlayer();
                            out.println("->Következő jatekos sikeres");
                        }

                ),
                new Command(
                        "/get_current",
                        "Visszaadja a jelenlegi játékos adatait.",
                        a -> out.println(fungorium.getCurrentPlayer())
                ),
                new Command(
                        "/gameover",
                        "Befejezi a játékot visszaadja a győztest.",
                        a -> out.println("->Winner: " + fungorium.getWinner() + "\n" + "GAME OVER")
                ),

                new Command(
                        "/adds",
                        "Spóra hozzáadása",
                        args -> {
                            String hatas = args[0];
                            int[] coordinates = getCoordinates(args[2]);
                            Grid grid = fungorium.getGrid(coordinates[0], coordinates[1]);
                            Jatekos jatekos;
                            try {
                                jatekos = fungorium.getJatekos(args[1]);
                            } catch (Exception e) {
                                out.println("A játékos nem található");
                                return;
                            }

                            if (jatekos instanceof Gombasz gombasz) gombasz = (Gombasz) jatekos;
                            else {
                                out.println("A játékos nem gombász");
                                return;
                            }
                            Spora spora = switch (hatas) {
                                case "benito" -> new BenitoSpora(grid, gombasz);
                                case "osztodo" -> new OsztodoRovarSpora(grid, gombasz);
                                case "lassito" -> new LassitoSpora(grid, gombasz);
                                case "gyors" -> new GyorsSpora(grid, gombasz);
                                case "normal" -> new Spora(grid, gombasz);
                                default -> null;
                            };
                            if (spora != null) {
                                out.println("Spóra hozzáadás sikeres!");
                            } else out.println("Ilyen hatás nincs!");
                        },
                        "[benito/osztodo/lassito/gyors/normal] gombász_név <startX>x<startY>"
                )
        };

        for (Command command : commands) {
            systemCommands.put(command.name, command);
        }

        //Játékos lépéseinek hozzáadása
        for (Move move : Move.values()) {
            String name = move.name();
            playerCommands.put(name, new Command(
                    name,
                    "Végrehajtja a megfelelő akciót, Az 1. mező a start grid coordinátája, a 2 a célé.",
                    args -> {
                        int[] startCoordinates = getCoordinates(args[0]);
                        int[] endCoordinates = getCoordinates(args[1]);
                        try {
                            fungorium.makeMove(startCoordinates[0], startCoordinates[1], endCoordinates[0], endCoordinates[1], move);
                        } catch (InvalidMoveException | FailedMoveException | IncompatibleGameObjectException e) {
                            out.println("->" + name + " egy hibás lépés: " + e.getMessage());
                            return;
                        }
                        out.println("->" + name + " sikeres");
                    },
                    "<startX>x<startY> <endX>x<endY>"
            ));
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Írd be a parancsokat, segítséghez /help.");
        while (!quit) {
            System.out.print("> ");
            String cmd = scanner.nextLine();
            log.add(cmd);
            try {
                executeCommand(cmd);
            }catch (IllegalArgumentException e) {
                out.println(e.getMessage());
            }
        }
        quit = false;
    }

    private void executeCommand(String commandList){
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
            out.println("Command not found!");
        }
    }

    private static int[] getCoordinates(String coordinate){
        return Arrays.stream(coordinate.split("x")).mapToInt(Integer::parseInt).toArray();
    }
}
