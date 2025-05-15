package model.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import interfaces.IDiscoverLogic;
import model.grid.Grid;

public class GridUtils {
    /**
     * Ez egy static class, nem tudjuk instanciálni
     */
    public GridUtils(){}

    public static class GridPathFinder {

        public GridPathFinder(){}
        /**
         * Kiszámítja egy útvonal súlyösszegét az IDiscoverLogic szabályai alapján.
         *
         * A metódus végighalad az útvonalon és minden elem között kiszámítja a mozgási költséget
         * az előző elemhez képest. Az összes költség összegét adja vissza.
         *
         * @param path Az útvonal, amelyen végighaladunk. A Grid típusú elemek listája.
         * @param discoverLogic A mozgási logika implementációja, amely meghatározza,
         *                     hogy két elem között mennyi a mozgási költség
         * @return A teljes útvonal súlyösszege. Ha az útvonal üres, akkor 0-t ad vissza.
         *
         * @see IDiscoverLogic#canMove(Grid, Grid)
         */
            public static double getPathWeightSum(LinkedList<Grid> path, IDiscoverLogic discoverLogic) {
                if(path.isEmpty()) return 0;

                double sum=0;
                Grid previous = path.getFirst();
                for (var element : path){
                    sum+=discoverLogic.canMove(previous, element);
                    previous = element;
                }
                return sum;
            }


        /**
         * Megkeresi a legrövidebb utat két Grid között Dijkstra algoritmusával.
         *
         * @param kezdo A kezdő Grid.
         * @param cel A cél Grid.
         * @param maxCumulativeWeight Az út maximálisan megengedett összsúlya.
         * @param dLogic Az IDiscoverLogic példány, amely meghatározza a Gridek közötti mozgás súlyát.
         * @return A Grid csomópontok listája, amely a kezdő csomóponttól a cél csomópontig tartó legrövidebb utat képviseli.
         *         Ha nem található út a maximális összsúlyon belül, üres listát ad vissza.
         *
         * Ez a metódus Dijkstra algoritmusát használja a kezdő és a cél csomópont közötti legrövidebb út megtalálására.
         * Figyelembe veszi a csomópontok közötti mozgás súlyát, amelyet az IDiscoverLogic implementáció határoz meg.
         * Az elérhetetlen csomópontokat (amelyek mozgási költsége Double.POSITIVE_INFINITY) kihagyja.
         * Az út csak akkor épül fel, ha az összsúly nem haladja meg a megadott maximumot.
         * DIJKSTRA algoritmusa azért megfelelő, mert a gráfban nem lehet negatív összsúlyú kör!
         */
        public static LinkedList<Grid> gridPathFind(Grid kezdo, Grid cel, double maxCumulativeWeight, IDiscoverLogic dLogic) {
            PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(node -> node.weight));
            Map<Grid, Double> distances = new HashMap<>();
            Map<Grid, Grid> previous = new HashMap<>();

            distances.put(kezdo, 0.0);
            queue.add(new Node(kezdo, 0.0,0));

            while (!queue.isEmpty()) {
                Node current = queue.poll();

                if (current.grid.equals(cel)) {
                    return reconstructPath(previous, cel);
                }

                for (Grid neighbor : current.grid.getNeighbors()) {
                    if (neighbor == null) continue;

                    double moveCost = dLogic.canMove(current.grid, neighbor);
                    if (moveCost == Double.POSITIVE_INFINITY) continue; // Skip if movement is not possible

                    double newWeight = current.weight + moveCost;

                    if (newWeight <= maxCumulativeWeight && (!distances.containsKey(neighbor) || newWeight < distances.get(neighbor))) {
                        distances.put(neighbor, newWeight);
                        previous.put(neighbor, current.grid);
                        queue.add(new Node(neighbor, newWeight,0));
                    }
                }
            }

            return new LinkedList<>(); // No path found
        }

        /**
         * Szélességi keresést (BFS) hajt végre, hogy megtalálja az összes elérhető Gridet egy kezdő Gridből egy megadott mélységig.
         *
         * @param kezdo A kezdő Grid.
         * @param depth A maximális mélység, amelyet a kezdő Gridtől felfedezünk.
         * @param dLogic Az IDiscoverLogic példány, amely meghatározza, hogy lehetséges-e a mozgás a Gridek között.
         * @return A Gridek listája, amelyek elérhetők a kezdő Gridtől a megadott mélységen belül.
         *
         * Ez a metódus BFS-t használ, hogy felfedezze az összes Gridet, amely elérhető a kezdő Gridtől a megadott mélységig.
         * Az IDiscoverLogic implementációt használja annak meghatározására, hogy lehetséges-e a mozgás egy szomszédos csomóponthoz.
         * Az elérhetetlen Grideket (amelyek mozgási költsége Double.POSITIVE_INFINITY) kihagyja.
         * A metódus biztosítja, hogy minden csomópontot csak egyszer látogasson meg, hogy elkerülje a végtelen ciklusokat.
         * A BFS azért megfelelő, mert mélység alapján keresünk, azaz minden élsúly egy!
         */
        public static List<Grid> gridFindAll(Grid kezdo, int depth, IDiscoverLogic dLogic) {
            List<Grid> result = new ArrayList<>();
            Queue<Node> queue = new LinkedList<>();
            Set<Grid> visited = new HashSet<>();

            queue.add(new Node(kezdo,0.0, 0));
            visited.add(kezdo);

            while (!queue.isEmpty()) {
                Node current = queue.poll();
                result.add(current.grid);

                if (current.depth >= depth) {
                    continue;
                }

                for (Grid neighbor : current.grid.getNeighbors()) {
                    if (neighbor == null || visited.contains(neighbor)) continue;

                    double moveCost = dLogic.canMove(current.grid, neighbor);
                    if (moveCost == Double.POSITIVE_INFINITY) continue; // Skip if movement is not possible

                    visited.add(neighbor);
                    queue.add(new Node(neighbor, 0.0,current.depth + 1));
                }
            }

            return result;
        }


        private static LinkedList<Grid> reconstructPath(Map<Grid, Grid> previous, Grid cel) {
            LinkedList<Grid> path = new LinkedList<>();
            for (Grid at = cel; at != null; at = previous.get(at)) {
                path.add(0, at);
            }
            return path;
        }

        private static class Node {
            Grid grid;
            double weight;
            int depth;

            Node(Grid grid, double weight, int depth) {
                this.grid = grid;
                this.weight = weight;
                this.depth = depth;
            }
        }
    }
}
