package testing;

import java.util.Map;
import java.util.function.Consumer;

public class Command {
    final String name;
    private final String description;
    private final String args;
    private final Consumer<String[]> effect;

    public Command(String name, String description, Consumer<String[]> effect, String args) {
        this.name = name;
        this.description = description;
        this.args = args;
        this.effect = effect;
    }

    public Command(String name, String description, Consumer<String[]> effect) {
        this.name = name;
        this.description = description;
        this.args = "";
        this.effect = effect;
    }
    public void accept(String[] args) {
        effect.accept(args);
    }
    public String getDescription() {
        return name + ' ' + args + ": \n\t\t" + description + '\n';
    }
}
